package com.justdo.common.domain;

import com.justdo.common.utils.JSONUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chenlin on 2019/5/14.
 */


public class CommonRequestBean extends AjaxRequestBean {
	public static class ReqObj {
		public String compare;
		public String label;
		public String vFrom;
		public String vTo;
		public String[] toList(){
			return vFrom.split(",");
		}

		public String getCompare() {
			return compare;
		}

		public String getvFrom() {
			return vFrom;
		}

		public String getvTo() {
			return vTo;
		}
		public String toString(){
			String lvCompare=(compare==null?"=":compare);
			if (lvCompare.equals("between")){
				return lvCompare+" \""+vFrom+"\" and \""+vTo+"\"";
			}
			else{
				return lvCompare+" \""+vFrom+"\"";
			}
		}
	}
	public static void updateSortDataField(CommonRequestBean pvInq,List<String> pvFields){
		if (pvInq.sortdatafield==null||pvInq.sortdatafield.equals("")){
			pvInq.sortdatafield=pvFields.get(Integer.valueOf(pvInq.sortByColumn));
		}
	}
	public String toString() {
		return JSONUtils.beanToJson(this);
	}

	/***
	 * 用于显示下载Excel格式文档的底部查询条件集合显示
	 *
	 * @return
	 */
	public String toShowExcelCriteria() {
		if (conditions == null)
			return "";
		StringBuilder lvSb = new StringBuilder();
		for (String lvKey : conditions.keySet()) {
			ReqObj lvReq = conditions.get(lvKey);
			if (lvReq.label == null)
				continue;
			lvSb.append(lvReq.label + ": " + lvReq.vFrom
					+ (lvReq.compare.equals("Between")
					? " To "
					+ lvReq.vTo
					: "")
					+ "<br/>\n");
		}
		return lvSb.toString();
	}

	public HashMap<String, ReqObj> conditions; // 条件组合

	public CommonRequestBean() {
		conditions = new HashMap<String, ReqObj>();
	}

	/***
	 * 將輸入的關鍵字(多個的話用回車或“，”分隔）轉換成Postgresql的ANY關鍵字
	 *
	 * @param pvKeywords
	 * @return
	 */
	public static String keyWordsToAnyFormat(String pvKeywords) {
		pvKeywords = pvKeywords.trim().toUpperCase();
		if (pvKeywords.equals(""))
			return "";
		String[] lvItems = pvKeywords.replace("\n", ",").split(",");
		StringBuilder lvSb = new StringBuilder();
		int lvInc = 0;
		for (String item : lvItems) {
			if (item.trim().equals(""))
				continue;
			lvInc++;
			lvSb.append(String.format(",'%s'", item.trim()));
		}
		if (lvSb.length() > 1) {
			lvSb.delete(0, 1);
		} else {
			return "''";
		}
		if (lvInc == 1) {
			return lvSb.toString().trim();
		} else {
			return " ANY(array[" + lvSb.toString() + "]) ";
		}
	}

	public static class DateBetweenBean {
		public Date date1 = null;
		public Date date2 = null;
		public String compare = "=";
	}

	/***
	 * 从查询条件里面解析出日期范围
	 * @throws Exception
	 */
	public static DateBetweenBean getDateFromReqObj(ReqObj pvKey) throws Exception {
		DateBetweenBean lvRet = new DateBetweenBean();
		lvRet.compare = pvKey.compare == null ? "=" : pvKey.compare;
		int lvDays=-1;
		try{
			lvDays=Integer.parseInt(pvKey.vFrom);
		}
		catch (Exception e){
		}
		if (lvDays>0){
			lvRet.date1 = DateUtils.addDays(new Date(), -lvDays);
			lvRet.date2 = new Date();
			lvRet.compare="between";
		}
		else{
			//lvRet.date1= DateUtils.parseDate()
			if (!pvKey.vFrom.equals("")&&lvRet.date1==null){
				throw new Exception("convert error.");
			}

			//lvRet.date2=pvKey.vTo.equals("")? null:Util.deformatDatetime(pvKey.vTo, Util.c_java_datefmt);
			if (lvRet.date2==null && pvKey.compare.equals("between")){
				lvRet.compare="=";
			}
			else if (lvRet.date2!=null){
				lvRet.compare="between";
			}
			if (!pvKey.vTo.equals("")&&lvRet.date2==null){
				throw new Exception("convert error.");
			}
		}
		return lvRet;
	}
	public static String valuesToArray(String pvVal){
		String lvRet="array['" + pvVal.split(",")+"']";
		return lvRet;
	}
	public static String genReqClauseWithValue(String pvFieldName, ReqObj pvReqObj) {
		if (pvReqObj == null)
			return null;
		String lvV1 = pvReqObj.vFrom.trim();
		String lvV2 = pvReqObj.vTo == lvV1 ||pvReqObj.vTo ==null? null : pvReqObj.vTo.trim();
		if (pvReqObj.compare == null || pvReqObj.compare.equals("=")) {
			return String.format(" %s='%s' ", pvFieldName, lvV1);
		} else if (pvReqObj.compare.equals(">=")) {
			return String.format(" %s>='%s' ", pvFieldName, lvV1);
		} else if (pvReqObj.compare.equals("<=")) {
			return String.format(" %s<='%s' ", pvFieldName, lvV1);
		} else if (pvReqObj.compare.equals("<")) {
			return String.format(" %s<'%s' ", pvFieldName, lvV1);
		}
		else if (pvReqObj.compare.toLowerCase().equals("like")) {
			return String.format(" %s like '%s' ", pvFieldName, "%" + lvV1 + "%");
		} else if (pvReqObj.compare.equals(">")) {
			return String.format(" %s>'%s' ", pvFieldName, lvV1);
		} else if (pvReqObj.compare.toLowerCase().equals("between")) {
			return String.format(" %s between '%s' and '%s' ", pvFieldName, lvV1, lvV2);
		}
		else if (pvReqObj.compare.toLowerCase().equals("in")) {
			String lvValues=CommonRequestBean.keyWordsToAnyFormat(pvReqObj.vFrom);
			return String.format(" %s=%s",pvFieldName,lvValues);
		}
		else if (pvReqObj.compare.toLowerCase().equals("includes")) {
			String lvValues=valuesToArray(pvReqObj.vFrom);
			return String.format(" %s && %s",pvFieldName,lvValues);
		}
		return null;
	}
	/*****
	 * 根据条件对象pvReqObj生成 SQL条件语句
	 * @param pvReqObj 比较符包括: >=,=,<=,>,<,between, in , includes ,其中,若为in ,则生成 fieldname=ANY(array[val1,val2,val3])格式<br/>
	 * 若为includes,则生成 fieldname && array[val1,val2,val3] ,表示是否有重叠,一般情况下fieldname这里亦需要是数组类型
	 */
	public static String genReqClause(String pvFieldName, ReqObj pvReqObj) {
		if (pvReqObj == null)
			return null;
		//String lvV1 = pvReqObj.vFrom.trim();
		//String lvV2 = pvReqObj.vTo == null ? null : pvReqObj.vTo.trim();
		if (pvReqObj.compare == null || pvReqObj.compare.equals("=")) {
			return String.format(" %s=:%s ", pvFieldName, pvFieldName);
		} else if (pvReqObj.compare.equals(">=")) {
			return String.format(" %s>=:%s ", pvFieldName, pvFieldName);
		} else if (pvReqObj.compare.equals("<=")) {
			return String.format(" %s<=:%s ", pvFieldName, pvFieldName);
		}
		else if (pvReqObj.compare.equals("like")) {
			return String.format(" %s like :%s ", pvFieldName, pvFieldName);
		}
		else if (pvReqObj.compare.equals("<")) {
			return String.format(" %s<:%s ", pvFieldName, pvFieldName);
		} else if (pvReqObj.compare.equals(">")) {
			return String.format(" %s>:%s ", pvFieldName, pvFieldName);
		} else if (pvReqObj.compare.equals("between")) {
			return String.format(" %s between :%s_1 and :%s_2 ", pvFieldName, pvFieldName, pvFieldName);
		}
		else if (pvReqObj.compare.equals("in")) {
			String lvValues=CommonRequestBean.keyWordsToAnyFormat(pvReqObj.vFrom);
			return String.format(" %s=%s",pvFieldName,lvValues);
		}
		else if (pvReqObj.compare.equals("includes")) {
			String lvValues=valuesToArray(pvReqObj.vFrom);
			return String.format(" %s && %s",pvFieldName,lvValues);
		}
		return null;
	}
	public static String genReqClauseByDate(String pvFieldName, DateBetweenBean pvReqObj) {
		if (pvReqObj == null)
			return null;
		//String lvV1 = pvReqObj.vFrom.trim();
		//String lvV2 = pvReqObj.vTo == null ? null : pvReqObj.vTo.trim();
		if (pvReqObj.compare == null || pvReqObj.compare.equals("=")) {
			return String.format(" %s=:%s ", pvFieldName, pvFieldName);
		} else if (pvReqObj.compare.equals(">=")) {
			return String.format(" %s>=:%s ", pvFieldName, pvFieldName);
		} else if (pvReqObj.compare.equals("<=")) {
			return String.format(" %s<=:%s ", pvFieldName, pvFieldName);
		}
		else if (pvReqObj.compare.equals("like")) {
			return String.format(" %s like :%s ", pvFieldName, pvFieldName);
		}
		else if (pvReqObj.compare.equals("<")) {
			return String.format(" %s<:%s ", pvFieldName, pvFieldName);
		} else if (pvReqObj.compare.equals(">")) {
			return String.format(" %s>:%s ", pvFieldName, pvFieldName);
		} else if (pvReqObj.compare.equals("between")) {
			return String.format(" %s between :%s_1 and :%s_2 ", pvFieldName, pvFieldName, pvFieldName);
		}
		return null;
	}
}

