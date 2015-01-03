package com.upbest.utils;

import org.codehaus.jackson.map.ObjectMapper;

public class CommonUtils {
    public static String toJson(Object obj) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
