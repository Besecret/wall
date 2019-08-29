/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanding.util;

import java.util.HashMap;
import java.util.Map;

public class JsonResponseGenerator {

    public static Map<String, Object> success(String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("message", message);
        return map;
    }
    public static Map<String, Object> success(Object object) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("success", true);
    	map.put("message", object);
    	return map;
    }

    public static Map<String, Object> success(String message, Map<String, Object> data){
        Map<String, Object> map = (Map<String, Object>)JsonResponseGenerator.success(message);
        for (Map.Entry<String, Object> entrySet : data.entrySet()) {
            String key = entrySet.getKey();
            Object value = entrySet.getValue();
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, Object> fail(String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("message", message);
        return map;
    }
    
   

}
