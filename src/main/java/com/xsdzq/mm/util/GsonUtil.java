package com.xsdzq.mm.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.xsdzq.mm.model.Pagination;

public class GsonUtil {

	public static Gson gson = new Gson();

	public static String build(int code, String message) {
		return build(code, message, null, null);
	}

	public static String build(int code, String message, Object result) {
		return build(code, message, result, null);
	}

	public static String build(int code, String message, Object result, Pagination pagination) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("resCode", code);
		map.put("resMsg", message);
		if (result != null) {
			map.put("result", result);
		}
		if (pagination != null) {
			map.put("pagination", pagination);
		}
		return gson.toJson(map);
	}

	public static Map<String, Object> buildMap(int code, String message, Object result) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("resCode", code);
		map.put("resMsg", message);
		if (result != null) {
			map.put("result", result);
		}
		return map;
	}

	public static Map<String, Object> buildMap(int code, String message, Object result, Pagination pagination) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("resCode", code);
		map.put("resMsg", message);
		if (result != null) {
			map.put("result", result);
		}
		if (pagination != null) {
			map.put("pagination", pagination);
		}
		return map;
	}

}
