package com.grape.supermarket.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;

/**
 * JSON转换工具
 * 
 * @author lbh
 * 
 * 2016年3月23日
 */
public class JsonUtils {
	private static final ObjectMapper objectMapper;
	private static final ObjectMapper objectMapperNonNull;
	static{
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapperNonNull = new ObjectMapper();
        objectMapperNonNull.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapperNonNull.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public static <T> T convertMapToObject(Map<String,Object> map,Class<T> clazz){
		if(map==null||clazz==null) return null;
		try {
			return objectMapper.convertValue(map, clazz);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static <T> T convertMap(Object obj,TypeReference<T> t){
		if(obj==null||t==null) return null;
		try {
			return objectMapper.convertValue(obj, t);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * java 对象转换为json 存入流中
	 */
	public static String toJson(Object obj) {
		if (obj == null)
			return null;
		String s = null;
		try {
			s = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return s;
	}

	/**
	 * java 对象转换为json 存入流中
	 */
	public static String toJsonNonNull(Object obj) {
		if (obj == null)
			return null;
		String s = null;
		try {
			s = objectMapperNonNull.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return s;
	}

	/**
	 * json 转为java对象
	 *
	 */
	public static <T> T fromJson(String json, Class<T> valueType) {
		if (json == null || "".equals(json))
			return null;
		T obj = null;
		try {
			obj = objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
	/**
	 * 将jsonstring 转化成 jsonNode
	 */
	public static JsonNode readTree(String content){
		try {
			return objectMapper.readTree(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static <T> T fromNode(JsonNode node,Class<T> clazz){
		try {
			return objectMapper.convertValue(node, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static <T> T fromNode(JsonNode node,TypeReference<T> typeReference){
		try {
			return objectMapper.convertValue(node, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * json 转为java对象
	 */
	public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
		if (json == null || "".equals(json))
			return null;
		T obj = null;
		try {
			obj = objectMapper.readValue(json, valueTypeRef);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
}