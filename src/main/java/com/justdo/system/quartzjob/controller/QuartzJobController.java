package com.justdo.system.quartzjob.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.justdo.system.quartzjob.service.QuartzJobService;
import com.justdo.common.controller.BaseController;
import com.justdo.common.config.Constant;
import com.justdo.system.quartzjob.domain.QuartzJobTaskDO;

import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;


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
	@Autowired
	private QuartzJobService quartzJobService;

	@GetMapping()
	@RequiresPermissions("system:quartzjob:quartzjob")
	String QuartzJobController() {
		return "system/quartzjob/quartzjob";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:quartzjob:quartzjob")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<QuartzJobTaskDO> taskScheduleJobList = quartzJobService.list(query);
		int total = quartzJobService.count(query);
		PageUtils pageUtils = new PageUtils(taskScheduleJobList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	String add() {
		return "system/quartzjob/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		QuartzJobTaskDO job = quartzJobService.get(id);
		model.addAttribute("job", job);
		return "system/quartzjob/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		QuartzJobTaskDO quartzJobTaskDO = quartzJobService.get(id);
		return R.ok().put("quartzJobTaskDO", quartzJobTaskDO);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save(QuartzJobTaskDO taskScheduleJob) {
		if (quartzJobService.save(taskScheduleJob) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@PostMapping("/update")
	public R update(QuartzJobTaskDO taskScheduleJob) {
		quartzJobService.update(taskScheduleJob);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/del")
	@ResponseBody
	public R remove(Long id) {
		if (quartzJobService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchDel")
	@ResponseBody
	public R remove(@RequestParam("ids[]") Long[] ids) {
		quartzJobService.batchDel(ids);

		return R.ok();
	}

	@PostMapping(value = "/changeJobStatus")
	@ResponseBody
	public R changeJobStatus(Long id,String cmd ) {
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
