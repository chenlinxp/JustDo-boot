package com.justdo.system.notice.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.justdo.common.config.Constant;
import com.justdo.common.controller.BaseController;
import com.justdo.system.dict.domain.DictDO;
import com.justdo.system.dict.service.DictService;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.notice.domain.NoticeDO;
import com.justdo.system.notice.domain.NoticeRecordDO;
import com.justdo.system.notice.service.NoticeRecordService;
import com.justdo.system.notice.service.NoticeService;




/**
 * 通知通告
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */

@Controller
@RequestMapping("/system/notice")
public class NoticeController extends BaseController {
	@Autowired
	private NoticeService NoticeService;
	@Autowired
	private NoticeRecordService NoticeRecordService;
	@Autowired
	private DictService dictService;

	@GetMapping()
	@RequiresPermissions("system:notice:notice")
	String Notice() {
		return "system/notice/notice";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:notice:notice")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<NoticeDO> NoticeList = NoticeService.list(query);
		int total = NoticeService.count(query);
		PageUtils pageUtils = new PageUtils(NoticeList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("system:notice:add")
	String add() {
		return "system/notice/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:notice:edit")
	String edit(@PathVariable("id") Long id, Model model) {
		NoticeDO notice = NoticeService.get(id);
		List<DictDO> dictDOS = dictService.listByType("notice_type");
		String type = notice.getType();
		for (DictDO dictDO:dictDOS){
			if(type.equals(dictDO.getValue())){
				dictDO.setRemarks("checked");
			}
		}
		model.addAttribute("noticeTypes",dictDOS);
		model.addAttribute("notice", notice);
		return "system/notice/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:notice:add")
	public R save(NoticeDO Notice) {
		Notice.setCreateBy(getUserId());
		if (NoticeService.save(Notice) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:notice:edit")
	public R update(NoticeDO Notice) {
		NoticeService.update(Notice);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/del")
	@ResponseBody
	@RequiresPermissions("system:notice:del")
	public R remove(Long id) {
		if (NoticeService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchDel")
	@ResponseBody
	@RequiresPermissions("system:notice:batchDel")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		NoticeService.batchDel(ids);
		return R.ok();
	}

	@ResponseBody
	@GetMapping("/message")
	PageUtils message() {
		Map<String, Object> params = new HashMap<>(16);
		params.put("offset", 0);
		params.put("limit", 3);
		Query query = new Query(params);
        query.put("userId", getUserId());
        query.put("isRead",Constant.Notice_READ_NO);
		return NoticeService.selfList(query);
	}

	@GetMapping("/selfnotice")
	String selefNotice() {
		return "system/notice/selfnotice";
	}

	@ResponseBody
	@GetMapping("/selflist")
	PageUtils selfList(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		query.put("userId", getUserId());

		return NoticeService.selfList(query);
	}

	@GetMapping("/read/{id}")
	@RequiresPermissions("system:notice:edit")
	String read(@PathVariable("id") Long id, Model model) {
		NoticeDO notice = NoticeService.get(id);
		//更改阅读状态
		NoticeRecordDO noticeRecordDO = new NoticeRecordDO();
		noticeRecordDO.setNoticeId(id);
		noticeRecordDO.setUserId(getUserId());
		noticeRecordDO.setReadDate(new Date());
		noticeRecordDO.setIsRead(Constant.Notice_READ_YES);
		NoticeRecordService.changeRead(noticeRecordDO);
		model.addAttribute("notice", notice);
		return "system/notice/read";
	}



}
