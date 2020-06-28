package com.princeframework.jlightspeed.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.alibaba.fastjson.JSONObject;


public class ObjectHelper {

	@SuppressWarnings("rawtypes")
	public static Map convertBean(Object obj) throws Exception {
		Class type = obj.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(obj, new Object[0]);
				if (!isEmpty(result)) {
					returnMap.put(propertyName, result);
				}
			}
		}
		return returnMap;
	}
	
	public HashMap<String, String> objectToMap(Object obj){
        HashMap<String, String> map = new HashMap<String, String>();
        try{
            if(obj == null){return null;}
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields){
                field.setAccessible(true);
                if(field.get(obj) instanceof String){
                    if(field.get(obj)!=null&&!field.get(obj).equals("")){
                        map.put(field.getName(), field.get(obj).toString());
                    }
                }else if(field.get(obj) instanceof Long){
                    if(field.get(obj)!=null){
                        map.put(field.getName(), String.valueOf(field.get(obj)));
                    }
                }else if(field.get(obj) instanceof Integer){
                    if(field.get(obj)!=null){
                        map.put(field.getName(), String.valueOf(field.get(obj)));
                    }
                }else if(field.get(obj) instanceof Double){
                    if(field.get(obj)!=null){
                        map.put(field.getName(), String.valueOf(field.get(obj)));
                    }
                }else if(field.get(obj) instanceof Float){
                    if(field.get(obj)!=null){
                        map.put(field.getName(), String.valueOf(field.get(obj)));
                    }
                }else if(field.get(obj) instanceof BigInteger){
                    if(field.get(obj)!=null){
                        map.put(field.getName(), String.valueOf(field.get(obj)));
                    }
                }else if(field.get(obj) instanceof Short){
                    if(field.get(obj)!=null){
                        map.put(field.getName(), String.valueOf(field.get(obj)));
                    }
                }else{
                    if(field.get(obj)!=null){
                        map.put(field.getName(), field.get(obj).toString());
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return map;
    }
	
	/**
	 * 
	 * @param patterns
	 * @return true 里面有为空的数据
	 */
	public static boolean isEmpty(String... patterns) {
		for (int x = 0; x < patterns.length; x++) {
			String str = patterns[x];
			if (null == str || "null".equals(str) || "".equals(str)) {
				// 判断string 类型为空
				return true;
			}
		}
		return false;
	}
	/**
	 * 验证数据是否为空，支持String 集合 Map 数组为空的验证。。。 数组 集合 如果里面的没数据同样返回true
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		boolean empty = false;
		if (null == obj) {
			empty = true;
		} else if (obj instanceof String || obj instanceof StringBuffer || obj instanceof StringBuilder) {
			// 特殊情况
			if ("null".equals(obj.toString().toLowerCase()) || "undefined".equals(obj.toString().toLowerCase())) {
				empty = true;
			} else {
				empty = "".equals(String.valueOf(obj));
			}
		} // Collection集合
		else if (obj instanceof Collection) {
			Collection conn = (Collection) obj;
			empty = conn.isEmpty();
		} // Map集合
		else if (obj instanceof Map) {
			Map map = (Map) obj;
			empty = map.isEmpty();
		} // 数组
		else if (obj.getClass().isArray()) {
			empty = Array.getLength(obj) < 1;
		}

		return empty;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> convertBeanEmptyBlank(Object obj) throws Exception {
		Class type = obj.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(obj, new Object[0]);
				if (!isEmpty(result)) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "  ");
				}
			}
		}
		return returnMap;
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 遍历request中所有参数
	 * 
	 * @param request
	 * @return Map<String,String>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> getRequestParameMap(HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		Enumeration<String> paraNames = request.getParameterNames();
		for (Enumeration e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			if (isNotEmpty(thisValue)) {
				resultMap.put(thisName, thisValue);
			}
		}

		return resultMap;
	}
	/**
	 * 截取数字和大写1-9从第二位开始判断，
	 * @param str
	 * @return
	 */
	public static String editPlate(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < str.length(); i++) {
			String s = String.valueOf(str.charAt(i));
			//String regex = "[\u4E00-\u9FA5]$[\0030-0039]";
		//	\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u96f6\u4e5d
			String regex = "[\u002d]|[\u4e00]|[\u4e8c]|[\u4e09]|[\u56db]|[\u4e94]|[\u516d]|[\u4e03]|[\u516b]|[\u96f6]|[\u4e5d]|[0-9]";
			if (!s.matches(regex)) {
				break;
			}
			sb.append(str.charAt(i));
		}
		return str.charAt(0) + sb.toString();

		/*
		 * if(str.length()>1){ //从1开始计算 第一位不计算 后面的只能为数字 String numbers =
		 * str.substring(1, str.length()); String regEx="[^0-9|-]"; Pattern p =
		 * Pattern.compile(regEx); Matcher m = p.matcher(numbers); str =
		 * str.substring(0,1)+ m.replaceAll("").trim(); }
		 */

	}

	/**
	 * 判断数组是否存在某个值
	 * 
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean arryHasVlu(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/**
	 * 根据指定字段排序
	 * 
	 * @param value1
	 *            字段1
	 * @param value2
	 *            字段2
	 * @return 1表示大于，0表示等于，-1表示小于
	 */
	public static int sort(String value1, String value2) {
		int sort = value1.compareTo(value2);
		if (sort > 0) {
			return 1;
		} else if (sort < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 替换样式 style width width
	 * 
	 * @param content
	 * @return
	 */
	public static String replaceStyle(String content) {
		if (isNotEmpty(content)) {
			content = content.replaceAll("style=\"(.*?)\".*?(.*?)", "").replaceAll("width=\"(.*?)\".*?(.*?)", "")
					.replaceAll("height=\"(.*?)\".*?(.*?)", "");
		}
		return content;
	}

	/**
	 * Integer是空返回0
	 * 
	 * @param val
	 * @return
	 */
	public static Integer nullInt0(Integer val) {
		if (isEmpty(val)) {
			return 0;
		}
		return val;
	}
	
	/**
	 * 空字符串返回空
	 * 
	 * @param val
	 * @return
	 */
	public static Object nullEmpty(Object val) {
		if (isEmpty(val)) {
			return "";
		}
		return val;
	}

	/**
	 * Integer是空 或者 小于0 返回0
	 * 
	 * @param val
	 * @return
	 */
	public static Integer lessInt0(Integer val) {
		if (isEmpty(val) || val < 0) {
			return 0;
		}
		return val;
	}

	/**
	 * BigDecimal是空返回0
	 * 
	 * @param val
	 * @return
	 */
	public static BigDecimal nullDecim0(BigDecimal val) {
		if (isEmpty(val)) {
			return BigDecimal.ZERO;
		}
		return val;
	}

	public static String toString(Object obj) {
		return isEmpty(obj) ? "" : obj.toString();
	}

	
	/**
	 * 将一些数据放到ShiroSession中,
	 * Controller,使用时直接用httpSession.getAttribute(key)就可以取到
	 */
	public static void setSession(String key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (isNotEmpty(session)) {
				session.setAttribute(key, value);
			}
		}
	}
	
	/**
	 * 将一些数据从ShiroSession中取出,
	 * Controller,使用时直接用httpSession.getAttribute(key)就可以取到
	 */
	public static Object getSession(String key) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (isNotEmpty(session) && isNotEmpty(session.getAttribute(key))) {
				return session.getAttribute(key);
			}
		}
		
		return null;
	}
	
	/**
	 * json 大写 转 小写
	 * @param json
	 * @return
	 */
	public static JSONObject jsonToLowerCase(JSONObject json){
		JSONObject json2 = new JSONObject();
	    for (Object key : json.keySet()) {
	    	json2.put(key.toString().toLowerCase(), json.getString(key.toString()));
	    }
	    return json2;
	}
	
}
