package com.grape.supermarket.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 返回8位的当前时间
	 * author huanghuang
	 * 2017年5月27日 下午1:34:47
	 * @return
	 */
	public static String toEightDate(){
        Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        return sdf.format(d);
	}
	public static void main(String[] args) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		jsonArray.add(jsonObject);
		jsonArray.add(jsonObject2);
		jsonArray.discard(0);
		System.out.println(jsonArray.size());
	}
}
