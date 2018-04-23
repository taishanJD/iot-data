package com.quarkdata.data.util;

/**
 * 此类用于Java和Cassandra数据类型转换
 * @author luohl
 *
 */
public class TypeUtil {
	public static String typeConversion(String type){
		 switch (type) {
         case "0":
             type = "float"; //float
             break;
         case "1":
             type = "double"; //double
             break;
         case "2":
             type = "smallint"; //short 16位有符号长
             break;
         case "3":
             type = "int";//int 32位有符号长
             break;
         case "4":
             type = "bigint";//long 64位有符号长
             break;
         case "5":
             type = "boolean"; // boolean
             break;
         case "6":
         case "7":
             type = "text"; // String : UTF8编码字符串
             break;
		 }
		 return type;
	}
}
