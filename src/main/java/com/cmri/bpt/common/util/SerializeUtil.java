package com.cmri.bpt.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializeUtil {
	public interface ObjectBytesConverter {
		byte[] toBytes(Object object);

		Object fromBytes(byte[] bytes);
	}

	private static Map<Class<?>, ObjectBytesConverter> classObjectBytesConverters = new ConcurrentHashMap<Class<?>, ObjectBytesConverter>();

	public static void registerObjectBytesConverter(Class<?> clazz, ObjectBytesConverter objectBytesConverter) {
		classObjectBytesConverters.put(clazz, objectBytesConverter);
	}

	// 序列化
	public static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}
		ObjectBytesConverter objectBytesConverter = classObjectBytesConverters.get(object.getClass());
		if (objectBytesConverter != null) {
			return objectBytesConverter.toBytes(object);
		}
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.close();
			return bos.toByteArray();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	// 反序列化
	public static Object deseialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object object = ois.readObject();
			ois.close();
			return object;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T deseialize(byte[] bytes, Class<T> targetType) {
		ObjectBytesConverter objectBytesConverter = classObjectBytesConverters.get(targetType);
		if (objectBytesConverter != null) {
			return (T) objectBytesConverter.fromBytes(bytes);
		}
		return (T) deseialize(bytes);
	}
}
