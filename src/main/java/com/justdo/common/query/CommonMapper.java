package com.justdo.common.query;

import com.justdo.common.domain.CommonRequestBean;

/**
 * Created by chenlin on 2019/5/14.
 */
public interface CommonMapper {
	/*@Select("select count(*) as cc from ${tablename} ")*/
	public long getTableRecordTotal(CommonRequestBean pvInq);
}



