package com.smok.web.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smok on 2015/12/28.
 */
public class JsonUtil {
    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    public static Map<String, String> parseJson2Map(String text) {
        if (!StringUtils.isEmpty(text))
            return JSON.parseObject(text, new TypeReference<Map<String, String>>() {
            });
        return new HashMap<String, String>();
    }

    public static JSONArray parseArray(String text) {
        return JSON.parseArray(text);
    }

    public static JSONArray parseArray(Object obj) {
        return JSON.parseArray(toJSONString(obj));
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public static JSONObject parseObject(String text) {
        return JSON.parseObject(text);
    }
}
