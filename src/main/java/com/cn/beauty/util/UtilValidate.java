package com.cn.beauty.util;

import java.util.Collection;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class UtilValidate {

	public static final String module = UtilValidate.class.getName();

	public static boolean areEqual(Object obj, Object obj2) {
		if (obj == null) {
			return obj2 == null;
		} else {
			return obj.equals(obj2);
		}
	}
	
	/**
	 * 检查输入的字符串是否为null
	 * 或者去掉左右两边的空格之后是否是空字符串
	 * @since 2012-9-3 下午04:54:21
	 * @param src 要检查的字符串
	 * @return
	 */
	public static boolean isNullOrEmpty(String src) {
		return null == src || src.matches("\\s*");
	}
	
	/**
	 * Check whether an object is empty, will see if it is a String, Map, Collection, etc.
	 */
	public static boolean isEmpty(Object o) {
		return ObjectType.isEmpty(o);
	}

	/**
	 * Check whether an object is NOT empty, will see if it is a String, Map, Collection, etc.
	 */
	public static boolean isNotEmpty(Object o) {
		return !ObjectType.isEmpty(o);
	}
	
	public static boolean isNotNull(JSONObject json, String key){
		if(!json.containsKey(key)){
			return false;
		}
		if(json.getString(key).equals("null")){
			return false;
		}
		return true;
	}

	/**
	 * Check whether string s is empty.
	 */
	public static boolean isEmpty(String s) {
		return ((s == null) || (s.trim().length() == 0));
	}

	/**
	 * Check whether collection c is empty.
	 */
	public static <E> boolean isEmpty(Collection<E> c) {
		return ((c == null) || (c.size() == 0));
	}

	/**
	 * Check whether map m is empty.
	 */
	public static <K, E> boolean isEmpty(Map<K, E> m) {
		return ((m == null) || (m.size() == 0));
	}

	/**
	 * Check whether charsequence c is empty.
	 */
	public static <E> boolean isEmpty(CharSequence c) {
		return ((c == null) || (c.length() == 0));
	}

	/**
	 * Check whether string s is NOT empty.
	 */
	public static boolean isNotEmpty(String s) {
		return ((s != null) && (s.length() > 0));
	}

	/**
	 * Check whether collection c is NOT empty.
	 */
	public static <E> boolean isNotEmpty(Collection<E> c) {
		return ((c != null) && (c.size() > 0));
	}

	/**
	 * Check whether charsequence c is NOT empty.
	 */
	public static <E> boolean isNotEmpty(CharSequence c) {
		return ((c != null) && (c.length() > 0));
	}

	public static boolean isString(Object obj) {
		return ((obj != null) && (obj instanceof java.lang.String));
	}
	
	public static boolean isAlpha(String s){
		boolean is = true;
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			int num = (int) c;
			if ((num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				//
			} else {
				is = false;
				break;
			}
		}
		return is;
	}
	
	
}
