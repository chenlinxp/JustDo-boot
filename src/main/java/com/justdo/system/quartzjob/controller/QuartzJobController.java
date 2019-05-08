package com.justdo.system.quartzjob.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.quartzjob.domain.QuartzJobTaskDO;
import com.justdo.system.quartzjob.service.QuartzJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * 自动任务
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */
@Controller
@RequestMapping("/system/quartzjob")
public class QuartzJobController extends BaseController {

	private String preUrl="/system/quartzjob";
	@Autowired
	private QuartzJobService quartzJobService;

	@GetMapping()
	@RequiresPermissions("system:quartzjob:list")
	String QuartzJobController() {
		return preUrl+"/quartzjob";
	}

	@Log("任务管理列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:quartzjob:list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<QuartzJobTaskDO> taskScheduleJobList = quartzJobService.list(query);
		int total = quartzJobService.count(query);
		PageUtils pageUtils = new PageUtils(taskScheduleJobList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("system:quartzjob:add")
	String add() {
		return preUrl+"/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:quartzjob:edit")
	String edit(@PathVariable("id") String id, Model model) {
		QuartzJobTaskDO quartzJobTaskDO = quartzJobService.get(id);
		model.addAttribute("quartzJobTaskDO", quartzJobTaskDO);
		return preUrl+"/edit";
	}

	/**
	 * 详情
	 */
	@GetMapping("/view/{id}")
	@RequiresPermissions("system:quartzjob:view")
	String view(@PathVariable("id") String id, Model model) {
		QuartzJobTaskDO quartzJobTaskDO = quartzJobService.get(id);
		model.addAttribute("quartzJobTaskDO", quartzJobTaskDO);
		return preUrl + "/view";
	}

	/**
	 * 保存
	 */
	@Log("保存任务管理")
	@ResponseBody
	@RequiresPermissions("system:quartzjob:add")
	@PostMapping("/save")
	public R save(QuartzJobTaskDO taskScheduleJob) {
		Date date = new Date();
		taskScheduleJob.setCreateTime(date);
		taskScheduleJob.setModifyTime(date);
		taskScheduleJob.setCareatEmployeeId(getEmployeeId());
		if (quartzJobService.save(taskScheduleJob) > 0) {
			return R.ok();
		}
		return R.error("保存任务管理失败");
	}

	/**
	 * 修改
	 */
	@Log("修改任务管理")
	@ResponseBody
	@RequiresPermissions("system:quartzjob:edit")
	@PostMapping("/update")
	public R update(QuartzJobTaskDO taskScheduleJob) {
		Date date = new Date();
		taskScheduleJob.setModifyTime(date);
		taskScheduleJob.setModifyEmployeeId(getEmployeeId());
		if (quartzJobService.update(taskScheduleJob) > 0) {
			return R.ok();
		}
		return R.error("修改任务管理失败");
	}

	/**
	 * 删除
	 */
	@Log("删除任务管理")
	@RequiresPermissions("system:quartzjob:del")
	@PostMapping("/del")
	@ResponseBody
	public R remove(String id) {
		if (quartzJobService.del(id) > 0) {
			return R.ok();
		}
		return R.error("删除任务管理失败");
	}

	/**
	 * 批量删除
	 */
	@Log("批量删除任务管理")
	@RequiresPermissions("system:quartzjob:batchDel")
	@PostMapping("/batchDel")
	@ResponseBody
	public R remove(@RequestParam("ids[]") String[] ids) {
		if (quartzJobService.batchDel(ids) > 0) {
			return R.ok();
		}
		return R.error("批量删除任务管理失败");
	}

	@Log("启动/停止任务")
	@RequiresPermissions("system:quartzjob:changeJobStatus")
	@PostMapping(value = "/changeJobStatus")
	@ResponseBody
	public R changeJobStatus(String id,String cmd ) {
		String label = "停止";
		if ("start".equals(cmd)) {
			label = "启动";
		} else {
			label = "停止";
		}
		try {
			quartzJobService.changeStatus(id, cmd);
			return R.ok("任务" + label + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok("任务" + label + "失败");
	}

}
