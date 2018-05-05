package com.yeting.framework.utils;

import org.apache.ibatis.javassist.Modifier;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanUtil {
    public BeanUtil() {
    }

    /**
     * 拷贝单个对象
     *
     * @param source
     * @param target
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object copyProperties(Object source, Object target, boolean notNull) {
        if (source == null || target == null)
            return null;
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);
        PropertyDescriptor arr$[] = wrapper.getPropertyDescriptors();
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            PropertyDescriptor descriptor = arr$[i$];
            Method readMethod = descriptor.getReadMethod();
            if (readMethod == null)
                continue;
            Class targetPropertyType = targetWrapper.getPropertyType(descriptor.getName());
            if (targetPropertyType == null)
                continue;
            PropertyDescriptor propertyDescriptor = targetWrapper.getPropertyDescriptor(descriptor.getName());
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod == null)
                continue;
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers()))
                writeMethod.setAccessible(true);
            Class propertyType = descriptor.getPropertyType();
            if (propertyType.isAssignableFrom(List.class)) {
                List list = (List) wrapper.getPropertyValue(descriptor.getName());
                if (list.size() == 0 || list == null)
                    continue;
                Class genericClass = null;
                List targetList = new ArrayList();
                try {
                    Field field = target.getClass().getDeclaredField(descriptor.getName());
                    ParameterizedType type = (ParameterizedType) field.getGenericType();
                    genericClass = (Class) type.getActualTypeArguments()[0];
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                if (genericClass == null)
                    continue;
                try {
                    for (Iterator i$1 = list.iterator(); i$1.hasNext(); ) {
                        Object object = i$1.next();
                        targetList.add(object);
                    }
                    writeMethod.invoke(target, new Object[]{targetList});
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }
            try {
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()))
                    readMethod.setAccessible(true);
                Object object = readMethod.invoke(source, new Object[0]);
                if (notNull) {
                    if (object == null)
                        continue;
                }
                writeMethod.invoke(target, new Object[]{readMethod.invoke(source, new Object[0])});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return target;
    }

    /**
     * 拷贝单个对象
     *
     * @param source
     * @param target
     * @return
     */
    public static Object copyProperties(Object source, Object target) {
        return copyProperties(source, target, false);
    }

    /**
     * 拷贝list
     *
     * @param source
     * @param target
     * @param targetValueClass
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List copyListProperties(List source, List target, Class targetValueClass) {
        if (source == null || target == null)
            return null;
        try {
            Object obj;
            for (Iterator i$ = source.iterator(); i$.hasNext(); target
                    .add(copyProperties(obj, targetValueClass.newInstance())))
                obj = i$.next();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return target;
    }


    public static byte[] ObjectToBytes(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bo != null) {
                    bo.close();
                }
                if (oo != null) {
                    oo.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public static Object BytesToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bi != null) {
                    bi.close();
                }
                if (oi != null) {
                    oi.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return obj;
    }
}















































































































































































