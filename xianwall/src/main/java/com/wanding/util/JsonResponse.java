/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanding.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.wanding.comm.Constant;

/**
 *
 * @author sunshine
 */
public class JsonResponse {

    public static Map<String, Object> success(String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subCode", Constant.SCCUESS_CODE);
        map.put("subMsg", message);
        return map;
    }
    public static Map<String, Object> success(String message,Object data) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("subCode", Constant.SCCUESS_CODE);
    	map.put("subMsg", message);
    	map.put("data", data);
    	return map;
    }
    

   

    public static Map<String, Object> failure(String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subCode", Constant.ERROR_CODE);
        map.put("subMsg", message);
        return map;
    }


    public static String  timeOut(String message) {
        JSONObject json = new JSONObject();
        json.put("subCode", Constant.TIME_OUT_CODE);
        json.put("subMsg", message);
        return json.toJSONString();
    }

    
    public static Map<String, Object> abnormal(String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subCode", Constant.ABNORMAL_CODE);
        map.put("subMsg", message);
        return map;
    }

}
