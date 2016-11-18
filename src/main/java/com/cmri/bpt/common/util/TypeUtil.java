package com.cmri.bpt.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cmri.bpt.common.base.OrdinalMap;

/**
 * 
 * @author Hu Changwei
 * @date 2013-12-15
 * 
 */
public class TypeUtil {
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<T> elemType, int length) {
		return (T[]) Array.newInstance(elemType, length);
	}

	public static <T> List<T> newList(Class<T> elemType) {
		return new ArrayList<T>();
	}

	public static <T> List<T> arrayToList(T[] array) {
		if (array == null) {
			return null;
		}
		return Arrays.asList(array);
	}

	public static <T> T[] listToArray(List<T> list, Class<T> elemType) {
		if (list == null) {
			return null;
		}
		T[] array = newArray(elemType, list.size());
		return list.toArray(array);
	}

	public static List<Object> toObjectList(List<?> anyList) {
		List<Object> retList = new ArrayList<Object>();
		for (int i = 0, j = anyList.size(); i < j; i++) {
			retList.add(anyList.get(i));
		}
		return retList;
	}

	//
	@SuppressWarnings("rawtypes")
	public static Map<String, String> enumAsMapList(Class<? extends Enum> enumClass) {
		OrdinalMap<String, String> retMap = new OrdinalMap<String, String>();
		Enum[] enumElems = enumClass.getEnumConstants();
		Field textField = null;
		try {
			textField = enumClass.getDeclaredField("text");
		} catch (Exception ex) {
			try {
				textField = enumClass.getDeclaredField("value");
			} catch (Exception exx) {
				exx.printStackTrace();
			}
		}
		for (int i = 0; i < enumElems.length; i++) {
			Enum enumElem = enumElems[i];
			String name = enumElem.name();
			String text = name;
			if (textField != null) {
				try {
					textField.setAccessible(true);
					text = String.valueOf(textField.get(enumElem));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			retMap.put(name, text);
		}
		return retMap;
	}

	// ===============================================================================

	@SuppressWarnings("rawtypes")
	public static boolean areEqual(Object arrA, Object arrB) {
		if (arrA == arrB) {
			return true;
		}
		if (arrA == null && arrB != null || arrA != null && arrB == null) {
			return false;
		}
		Class clsA = arrA.getClass();
		Class clsB = arrB.getClass();
		if (clsA.isArray()) {
			if (!clsB.isArray()) {
				return false;
			}
		} else {
			if (clsB.isArray()) {
				return false;
			} else {
				return arrA.equals(arrB);
			}
		}
		int len = Array.getLength(arrA);
		if (Array.getLength(arrB) != len) {
			return false;
		}
		for (int i = 0; i < len; i++) {
			if (!areEqual(Array.get(arrA, i), Array.get(arrB, i))) {
				return false;
			}
		}
		return true;
	}

	// ===============================================================================
	public static String getFieldPropertyName(Field field) {
		JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
		if (jsonProperty != null) {
			return jsonProperty.value();
		}
		return field.getName();
	}

	public static Object getObjectFieldValue(Object object, String fieldName) {
		try {
			Class<?> objClass = object.getClass();
			// 优先使用属性
			try {
				String getterName = StrUtil.toGetterMethodName(fieldName);
				Method getter = objClass.getMethod(getterName);
				getter.setAccessible(true);
				return getter.invoke(object);
			} catch (Exception ex) {
				//
			}
			// 其次使用字段
			Field field = objClass.getField(fieldName);
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static void setObjectFieldValue(Object object, String fieldName, Object fieldValue) {
		try {
			Class<?> objClass = object.getClass();
			// 优先使用属性
			try {
				String setterName = StrUtil.toSetterMethodName(fieldName);
				Method setter = objClass.getMethod(setterName, fieldValue.getClass());
				setter.setAccessible(true);
				setter.invoke(object, fieldValue);
				return;
			} catch (Exception ex) {
				//
			}
			// 其次使用字段
			Field field = objClass.getField(fieldName);
			field.setAccessible(true);
			field.set(object, fieldValue);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public static List<String> getFieldNames(Class<?> clazz, List<String> subFieldNames) {
		if (clazz == null || clazz == Object.class) {
			return new ArrayList<String>();
		} else {
			//
			Field[] clazzFields = clazz.getDeclaredFields();
			List<String> clazzFieldNames = new ArrayList<String>();
			for (Field field : clazzFields) {
				String fieldName = getFieldPropertyName(field);
				if (subFieldNames == null || !subFieldNames.contains(fieldName)) {
					if (fieldName.indexOf('$') == -1) {
						clazzFieldNames.add(fieldName);
					}
				}
			}
			//
			Class<?> parentClazz = clazz.getSuperclass();
			List<String> allFieldNames = getFieldNames(parentClazz, clazzFieldNames);
			allFieldNames.addAll(clazzFieldNames);
			//
			return allFieldNames;
		}
	}

	public static void copyFields(Object src, Object target, String... fieldNames) {
		if (src == null) {
			return;
		}
		if (fieldNames.length < 1) {
			List<String> fieldNameList = getFieldNames(src.getClass(), null);
			fieldNames = listToArray(fieldNameList, String.class);
		}
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldName = fieldNames[i];
			try {
				Object fieldValue = getObjectFieldValue(src, fieldName);
				if (fieldValue != null) {
					setObjectFieldValue(target, fieldName, fieldValue);
				}
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
		}
	}

	public static void copyFieldsExcept(Object src, Object target, String... excludeFieldNames) {
		if (src == null) {
			return;
		}
		List<String> excludeFieldNameList = arrayToList(excludeFieldNames);
		List<String> rawNameList = getFieldNames(src.getClass(), null);
		List<String> fieldNameList = new ArrayList<String>();
		for (String fieldName : rawNameList) {
			if (!excludeFieldNameList.contains(fieldName)) {
				fieldNameList.add(fieldName);
			}
		}
		String[] fieldNames = listToArray(fieldNameList, String.class);
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldName = fieldNames[i];
			try {
				Object fieldValue = getObjectFieldValue(src, fieldName);
				if (fieldValue != null) {
					setObjectFieldValue(target, fieldName, fieldValue);
				}
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
		}
	}

	public static Class<?> getWrapperType(Class<?> type) {
		if (type.isPrimitive()) {
			// boolean, byte, char, short, int, long, float, and double
			if (type == boolean.class) {
				return Boolean.class;
			} else if (type == byte.class) {
				return Byte.class;
			} else if (type == char.class) {
				return Character.class;
			} else if (type == short.class) {
				return Short.class;
			} else if (type == int.class) {
				return Integer.class;
			} else if (type == long.class) {
				return Float.class;
			} else if (type == float.class) {
				return Float.class;
			} else if (type == double.class) {
				return Double.class;
			} else if (type == void.class) {
				return Void.class;
			}
		}
		return type;
	}

	private static final List<Class<?>> simpleTypes;

	private static final Map<String, Class<?>> aliasToTypeMap;

	static {
		aliasToTypeMap = new HashMap<String, Class<?>>();
		simpleTypes = new ArrayList<Class<?>>();
		aliasToTypeMap.put("string", String.class);
		simpleTypes.add(String.class);
		aliasToTypeMap.put("String", String.class);
		aliasToTypeMap.put("bool", Boolean.class);
		simpleTypes.add(Boolean.class);
		aliasToTypeMap.put("boolean", Boolean.class);
		aliasToTypeMap.put("Boolean", Boolean.class);
		aliasToTypeMap.put("date", java.util.Date.class);
		simpleTypes.add(java.util.Date.class);
		aliasToTypeMap.put("Date", java.util.Date.class);
		aliasToTypeMap.put("int", Integer.class);
		simpleTypes.add(Integer.class);
		aliasToTypeMap.put("integer", Integer.class);
		aliasToTypeMap.put("Integer", Integer.class);
		aliasToTypeMap.put("long", Long.class);
		simpleTypes.add(Long.class);
		aliasToTypeMap.put("Long", Long.class);
		aliasToTypeMap.put("short", Short.class);
		simpleTypes.add(Short.class);
		aliasToTypeMap.put("Short", Short.class);
		aliasToTypeMap.put("float", Float.class);
		simpleTypes.add(Float.class);
		aliasToTypeMap.put("Float", Float.class);
		aliasToTypeMap.put("double", Double.class);
		simpleTypes.add(Double.class);
		aliasToTypeMap.put("Double", Double.class);
		aliasToTypeMap.put("char", Character.class);
		simpleTypes.add(Character.class);
		aliasToTypeMap.put("Character", Character.class);
		aliasToTypeMap.put("bigInt", java.math.BigInteger.class);
		simpleTypes.add(java.math.BigInteger.class);
		aliasToTypeMap.put("bigInteger", java.math.BigInteger.class);
		aliasToTypeMap.put("BigInteger", java.math.BigInteger.class);
		aliasToTypeMap.put("bigDec", java.math.BigDecimal.class);
		simpleTypes.add(java.math.BigDecimal.class);
		aliasToTypeMap.put("bigDecimal", java.math.BigDecimal.class);
		aliasToTypeMap.put("BigDecimal", java.math.BigDecimal.class);
		/*
		 * for (String typeName : aliasToTypeMap.keySet()) { Class<?> clazz =
		 * aliasToTypeMap.get(typeName); System.out.println(typeName + " => " +
		 * clazz.getCanonicalName()); }
		 */
	}

	public static boolean isSimpleType(Class<?> type) {
		return type.isPrimitive() || simpleTypes.contains(type);
	}

	public static Class<?> getTypeForAlias(String typeAlias) {
		return aliasToTypeMap.get(typeAlias);
	}

	public static Object convertToType(String strVal, Class<?> type) {
		if (strVal == null) {
			return null;
		}
		if (type == null || type == String.class) {
			return strVal;
		}
		Class<?> typex = TypeUtil.getWrapperType(type);
		if (typex == Boolean.class) {
			return Boolean.valueOf(strVal);
		} else if (typex == Date.class) {
			try {
				return DateUtil.fromStdDateTimeXStr(strVal);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		} else if (typex == Integer.class) {
			return Integer.valueOf(strVal);
		} else if (typex == Long.class) {
			return Long.valueOf(strVal);
		} else if (typex == Short.class) {
			return Short.valueOf(strVal);
		} else if (typex == Float.class) {
			return Float.valueOf(strVal);
		} else if (typex == Double.class) {
			return Double.valueOf(strVal);
		} else if (typex == Character.class) {
			return Character.valueOf(strVal.charAt(0));
		} else if (typex == BigInteger.class) {
			return new BigInteger(strVal);
		} else if (typex == BigDecimal.class) {
			return new BigDecimal(strVal);
		} else {
			return type.cast(strVal);
		}
	}
}
