package com.justdo.system.redis.controller;

import com.alibaba.fastjson.JSON;
import com.justdo.common.annotation.Log;
import com.justdo.system.redis.domain.Operate;
import com.justdo.system.redis.domain.RedisInfoDetail;
import com.justdo.system.redis.service.RedisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *
 * 自动任务
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */

@Controller
@RequestMapping(value="/system/redis")
public class RedisController {

	private String preUrl="/system/redis";

	@Autowired
	RedisService redisService;

	@Log("redis监控页面")
	@RequiresPermissions("system:redis:Monitor")
	@ResponseBody
	@GetMapping("/Monitor")
	public String redisMonitor(Model model) {
		//获取redis的info
		List<RedisInfoDetail> ridList = redisService.getRedisInfo();
		//获取redis的日志记录
		List<Operate> logList = redisService.getLogs(1000);
		//获取日志总数
		long logLen = redisService.getLogLen();
		model.addAttribute("infoList", ridList);
		model.addAttribute("logList", logList);
		model.addAttribute("logLen", logLen);
		return "redisMonitor";
	}


	@Log("清空日志redis")
	@RequiresPermissions("system:redis:logEmpty")
	@GetMapping("/logEmpty")
	@ResponseBody
	public String logEmpty(){
		return redisService.logEmpty();
	}


	/**
	 * 获取当前数据库中key的数量
	 * @return
	 */
	@RequiresPermissions("system:redis:getKeysSize")
	@GetMapping("/getKeysSize")
	@ResponseBody
	public String getKeysSize(){
		return JSON.toJSONString(redisService.getKeysSize());
	}

	/**
	 * 获取当前数据库内存使用大小情况
	 * @return
	 */
	@RequiresPermissions("system:redis:getMemeryInfo")
	@GetMapping("/getMemeryInfo")
	@ResponseBody
	public String getMemeryInfo(){
		return JSON.toJSONString(redisService.getMemeryInfo());
	}
}
