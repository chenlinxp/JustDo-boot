package com.justdo.system.notice.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.config.ConstantConfig;
import com.justdo.system.dict.domain.DictContentDO;
import com.justdo.system.dict.service.DictContentService;
import com.justdo.system.notice.domain.NoticeDO;
import com.justdo.system.notice.domain.NoticeRecordDO;
import com.justdo.system.notice.service.NoticeRecordService;
import com.justdo.system.notice.service.NoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知通告
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-04-25 12:46:25
 */

@Controller
@RequestMapping("/system/notice")
public class NoticeController extends BaseController {
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeRecordService noticeRecordService;
	@Autowired
	private DictContentService dictContentService;


	 String preUrl = "/system/notice";
	/**
	 * 列表页面
	 * @return 列表页面路径
	 */
	@GetMapping()
	@RequiresPermissions("system:notice:list")
	String Notice(){
		return preUrl + "/notice";
	}


	/**
	 * 列表数据
	 * @param params
	 * @return
	 */
	@Log("通知通告列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:notice:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<NoticeDO> noticeList = noticeService.list(query);
		int total = noticeService.count(query);
		PageUtils pageUtils = new PageUtils(noticeList, total);
		return pageUtils;
	}

	/**
	 * 详情页面
	 * @param id
	 * @return 详情页面路径
	 */
	@GetMapping("/view/{id}")
	@RequiresPermissions("system:notice:view")
	String view(@PathVariable("id") String id,Model model){
		NoticeDO notice = noticeService.get(id);
		model.addAttribute("notice", notice);
		return preUrl + "/view";
	}

	/**
	 * 增加页面
	 * @return 增加页面路径
	 */
	@GetMapping("/add")
	@RequiresPermissions("system:notice:add")
	String add(Model model){
		List<DictContentDO> dictDOS = dictContentService.listDictByCode("noticeCode");
		model.addAttribute("noticeTypes",dictDOS);
		return preUrl + "/add";
	}


	/**
	 * 保存
	 * @param notice
	 * @return R
	 */
	@Log("通知通告保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:notice:add")
	public R save( NoticeDO notice){
		Date date = new Date();
		notice.setModifyTime(date);
		notice.setCreateTime(date);
		notice.setCreateEmployeeId(getEmployeeId());
		if(notice.getNoticeStatus()==null){
			notice.setNoticeStatus(0);
		}else{
			notice.setNoticeStatus(1);
		}
		if(noticeService.save(notice)>0){
			return R.ok();
		}
		return R.error(1, "保存失败!");

	}

	/**
	 * 编辑页面
	 * @param noticeId
	 * @return 编辑页面路径
	 */
	@GetMapping("/edit/{noticeId}")
	@RequiresPermissions("system:notice:edit")
	String edit(@PathVariable("noticeId") String noticeId,Model model){
		NoticeDO notice = noticeService.get(noticeId);
		List<DictContentDO> dictDOS = dictContentService.listDictByCode("noticeCode");
		model.addAttribute("noticeTypes",dictDOS);
		model.addAttribute("notice", notice);
		return preUrl + "/edit";
	}


	/**
	 * 更新
	 * @param notice
	 * @return R
	 */
	@Log("通知通告更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:notice:edit")
	public R update( NoticeDO notice){
		Date date = new Date();
		notice.setModifyTime(date);
		notice.setModifyEmployeeId(getEmployeeId());
		if(noticeService.update(notice)>0){
			return R.ok();
		}
		return R.error(1, "更新失败!");
	}

	/**
	 * 删除
	 * @param noticeId
	 * @return R
	 */
	@Log("通知通告删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:notice:del")
	public R remove( String noticeId){
		if(noticeService.del(noticeId)>0){
			return R.ok();
		}
		return R.error(1, "删除失败!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return R
	 */
	@Log("通知通告批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:notice:batchDel")
	public R remove(@RequestParam("ids[]") String[] ids){
		if(noticeService.batchDel(ids)>0){
			return R.ok();
		}
		return R.error(1, "批量删除失败!");
	}

	/**
	 * 未读的通知列表
	 * @return
	 */
	@ResponseBody
	@GetMapping("/message")
	PageUtils unread() {
		Map<String, Object> params = new HashMap<>(3);
		params.put("offset", 0);
		params.put("limit", 3);
		Query query = new Query(params);
		query.put("employeeId", getEmployeeId());
		query.put("isRead", "0");
		PageUtils pageUtils = noticeService.selfList(query);
		return pageUtils;
	}

	@GetMapping("/selfnotice")
	@RequiresPermissions("system:notice:selflist")
	String selefNotice() {
		return preUrl +  "/selfnotice";
	}

	@Log("个人通知列表")
	@ResponseBody
	@GetMapping("/selflist")
	@RequiresPermissions("system:notice:selflist")
	PageUtils selfList(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		query.put("employeeId", getEmployeeId());
		return noticeService.selfList(query);
	}

	@Log("阅读个人通知详情")
	@GetMapping("/read/{noticeId}")
	@RequiresPermissions("system:notice:read")
	String read(@PathVariable("noticeId") String noticeId, Model model) {
		List<DictContentDO> dictDOS = dictContentService.listDictByCode("noticeCode");
		model.addAttribute("noticeTypes",dictDOS);
		NoticeDO notice = noticeService.get(noticeId);
		//更改阅读状态
		NoticeRecordDO noticeRecordDO = new NoticeRecordDO();
		noticeRecordDO.setNoticeId(noticeId);
		noticeRecordDO.setEmployeeId(getEmployeeId());
		noticeRecordDO.setReadDate(new Date());
		noticeRecordDO.setIsRead(ConstantConfig.Notice_READ_YES);
		noticeRecordService.changeRead(noticeRecordDO);
		model.addAttribute("notice", notice);
		return preUrl +  "/read";
	}



}
