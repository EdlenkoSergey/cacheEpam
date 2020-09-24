package com.epam.cache;

import java.lang.reflect.Field;

@SuppressWarnings("unchecked")
public class ReflectionUtils {
    public static <K, V> K getPrivateField(String fieldName, Class<?> clazz, V instance)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (K) field.get(instance);
    }
}
