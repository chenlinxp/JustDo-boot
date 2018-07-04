package com.justdo.common.utils;

import java.sql.Clob;
import java.sql.SQLException;


public class OracleSql {
	
 /**
    *
    * Description:将Clob对象转换为String对象,Blob处理方式与此相同
    *
    * @param clob
    * @return
    * @throws Exception
    * @since：Oct 1, 2008 7:19:57 PM
    */
   public static String oracleClob2Str(Clob clob){
       try {
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
   }

}
