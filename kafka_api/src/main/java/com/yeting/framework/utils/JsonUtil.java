package com.yeting.framework.utils;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 * JSON工具类
 * </pre>
 * 
 * @author jackpyf
 *
 */
public class JsonUtil {

	private static Logger logger = Logger.getLogger(JsonUtil.class);
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Object --> JSON String
	 * 
	 * @param value
	 * @return
	 */
	public static String serialize(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * JSON String --> Object
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T deserialize(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> deserializeList(String json, Class<T> clazz) {
		JavaType javaType = TypeFactory.collectionType(List.class, clazz);
		try {
			return objectMapper.readValue(json, javaType);
		} catch (IOException e) {
			logger.error(e);
		}
		return new ArrayList<T>();
	}
}
