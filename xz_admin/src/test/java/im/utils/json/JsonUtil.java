package com.xz.framework.utils.json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * JSON工具类
 * </pre>
 *
 * @author jack
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Object --> JSON String
     *
     * @param value
     * @return
     */
    public static String serialize(Object value) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSON String --> List
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> deserializeList(String json, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            list = objectMapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
