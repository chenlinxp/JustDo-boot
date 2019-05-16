package com.justdo.common.domain;

import static com.justdo.common.utils.StringUtils.sqlValidate;

/**
 * Created by chenlin on 2019/5/14.
 */
public class AjaxRequestBean {

	public int pagenum=1;
	public int pagesize=10;
	public int caltotal=0;
	//public int groupscount;
	public String sortdatafield;
	public String sortorder;
	public String sortByColumn="0";
	public String defaultsortdatafield;
	//public boolean inited
	public boolean sortByColCell;
	public String sortByColVal="";
	public Object extObj;

	// public String sortorder; //asc ,desc

	public String genOrderLimit(){
		String w_sortfield = sortdatafield;
		if (w_sortfield.equals("")&&defaultsortdatafield!=null){
			w_sortfield=defaultsortdatafield;
		}
		if (sortorder==null||sortorder.equals("")){
			sortorder="asc";
		}
		if (sqlValidate(w_sortfield)||sqlValidate(sortorder)){
			return "";
		}
		return (w_sortfield.equals("")?" " : " order by "+w_sortfield+(sortorder.equals("")?"":" "+sortorder))+" limit "+
				String.valueOf(pagesize)+" offset "+String.valueOf((pagenum * pagesize));
	}

	public String getOrderLimit1(){
		return genOrderLimit1();
	}
	public String genOrderLimit1(){
		String w_sortfield = sortdatafield;
		if (w_sortfield.equals("")&&defaultsortdatafield!=null){
			w_sortfield=defaultsortdatafield;
		}
		if (sortorder==null||sortorder.equals("")){
			sortorder="asc";
		}
		if (sqlValidate(w_sortfield)||sqlValidate(sortorder)){
			return "";
		}
		return (w_sortfield.equals("")?" " : " order by "+w_sortfield+(sortorder.equals("")?"":" "+sortorder)
		)+" limit "+
				String.valueOf(pagesize)+" offset "+String.valueOf(((pagenum-1) * pagesize));
	}
	public String genOrder(){
		String w_sortfield = sortdatafield;
		if (w_sortfield.equals("")&&defaultsortdatafield!=null){
			w_sortfield=defaultsortdatafield;
		}
		if (w_sortfield.equals("")) return "";
		if (sortorder==null||sortorder.equals("")){
			sortorder="asc";
		}
		String[] lvT=w_sortfield.split(",");
		lvT[0]=lvT[0] +" "+sortorder;
		return "ORDER BY "+ lvT[0] ;//Util.toString(",", lvT);
	}
	/***返回不带order by **/
	public String genOrder1(){
		String w_sortfield = sortdatafield;
		if (w_sortfield.equals("")&&defaultsortdatafield!=null){
			w_sortfield=defaultsortdatafield;
		}
		if (w_sortfield.equals("")) return "";
		if (sortorder==null||sortorder.equals("")){
			sortorder="asc";
		}
		String[] lvT=w_sortfield.split(",");
		lvT[0]=lvT[0] +" "+sortorder;
		return lvT[0]; //Util.toString(",", lvT);
	}
}

