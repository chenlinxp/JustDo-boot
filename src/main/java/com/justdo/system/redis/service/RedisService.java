package com.justdo.system.redis.service;

import com.alibaba.fastjson.JSON;
import com.justdo.common.utils.RedisUtils;
import com.justdo.system.redis.domain.Operate;
import com.justdo.system.redis.domain.RedisInfoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.util.Slowlog;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author justdo
 * @version V1.0
 */

@Service
public class RedisService {
	
	@Autowired
	RedisUtils redisUtils;
	
	public List<RedisInfoDetail> getRedisInfo() {
		//获取redis服务器信息
		String info = redisUtils.getRedisInfo();
		List<RedisInfoDetail> ridList = new ArrayList<RedisInfoDetail>();
		String[] strs = info.split("\n");
		RedisInfoDetail rif = null;
		if (strs != null && strs.length > 0) {
			for (int i = 0; i < strs.length; i++) {
				rif = new RedisInfoDetail();
				String s = strs[i];
				String[] str = s.split(":");
				if (str != null && str.length > 1) {
					String key = str[0];
					String value = str[1];
					rif.setKey(key);
					rif.setValue(value);
					ridList.add(rif);
				}
			}
		}
		return ridList;
	}
	
	//获取redis日志列表
	public List<Operate> getLogs(long entries) {
        List<Slowlog> list = redisUtils.getLogs(entries);
		List<Operate> opList = null;
		Operate op  = null;
		boolean flag = false;
		if (list != null && list.size() > 0) {
			opList = new LinkedList<Operate>();
			for (Slowlog sl : list) {
				String args = JSON.toJSONString(sl.getArgs());
				if (args.equals("[\"PING\"]") || args.equals("[\"SLOWLOG\",\"get\"]") || args.equals("[\"DBSIZE\"]") || args.equals("[\"INFO\"]")) {
					continue;
				}	
				op = new Operate();
				flag = true;
				op.setId(sl.getId());
				op.setExecuteTime(getDateStr(sl.getTimeStamp() * 1000));
				op.setUsedTime(sl.getExecutionTime()/1000.0 + "ms");
				op.setArgs(args);
				opList.add(op);
			}
		} 
		if (flag) 
			return opList;
		else 
			return null;
	}
	//获取日志总数
	public Long getLogLen() {
		return redisUtils.getLogsLen();
	}
	
	//清空日志
	public String logEmpty() {
		return redisUtils.logEmpty();
	}
	//获取当前数据库中key的数量
	public Map<String,Object> getKeysSize() {
		long dbSize = redisUtils.dbSize();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("create_time", new Date().getTime());
		map.put("dbSize", dbSize);
		return map;
	}
	
	//获取当前redis使用内存大小情况
	public Map<String,Object> getMemeryInfo() {
		String[] strs = redisUtils.getRedisInfo().split("\n");
		Map<String, Object> map = null;
		for (int i = 0; i < strs.length; i++) {
			String s = strs[i];
			String[] detail = s.split(":");
			if (detail[0].equals("used_memory")) {
				map = new HashMap<String, Object>();
				map.put("used_memory",detail[1].substring(0, detail[1].length() - 1));
				map.put("create_time", new Date().getTime());
				break;
			}
		}
		return map;
	}
	private String getDateStr(long timeStmp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date(timeStmp));
	}
}
