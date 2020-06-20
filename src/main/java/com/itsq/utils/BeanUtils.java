package com.itsq.utils;

import com.itsq.common.constant.APIException;
import org.springframework.cglib.beans.BeanMap;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {
    public BeanUtils() {
    }
    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
    private static PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] pds, PropertyDescriptor ref, boolean isStrict) {
        if (ref.getDisplayName().equals("class")) {
            return null;
        } else {
            PropertyDescriptor[] var3 = pds;
            int var4 = pds.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                PropertyDescriptor pd = var3[var5];
                if (isStrict) {
                    if (pd.equals(ref)) {
                        return pd;
                    }
                } else if (ref.getPropertyType().equals(pd.getPropertyType()) && pd.getName().equals(ref.getName())) {
                    return pd;
                }
            }

            return null;
        }
    }

    public static void copyProperties(Object fromObj, Object toObj) throws APIException {
        copyProperties(fromObj, toObj, true);
    }

    public static void copyProperties(Object fromObj, Object toObj, boolean ignoreNull) throws APIException {
        if (fromObj != null && toObj != null) {
            Class<? extends Object> fromClass = fromObj.getClass();
            Class<? extends Object> toClass = toObj.getClass();
            boolean isStrict = fromClass == toClass;

            try {
                BeanInfo fromBean = Introspector.getBeanInfo(fromClass);
                BeanInfo toBean = Introspector.getBeanInfo(toClass);
                PropertyDescriptor[] toPds = toBean.getPropertyDescriptors();
                PropertyDescriptor[] fromPds = fromBean.getPropertyDescriptors();
                PropertyDescriptor[] var10 = toPds;
                int var11 = toPds.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    PropertyDescriptor toPd = var10[var12];
                    PropertyDescriptor fromPd = getPropertyDescriptor(fromPds, toPd, isStrict);
                    if (fromPd != null && fromPd.getDisplayName().equals(toPd.getDisplayName())) {
                        Method readMethod = fromPd.getReadMethod();
                        Method writeMethod = writeMethod(toClass, toPd);
                        if (writeMethod != null && readMethod != null) {
                            Object param = readMethod.invoke(fromObj, (Object[])null);
                            if (!ignoreNull || param != null) {
                                writeMethod.invoke(toObj, param);
                            }
                        }
                    }
                }

            } catch (Exception var18) {
                throw new APIException(var18);
            }
        }
    }

    public static <T> T copyProperties(Object from, Class<T> toClass) throws APIException {
        if (from == null) {
            return null;
        } else {
            try {
                T to = toClass.newInstance();
                copyProperties(from, to);
                return to;
            } catch (IllegalAccessException | InstantiationException var4) {
                throw new APIException(var4);
            }
        }
    }

    public static <T> List<T> copyListProperties(Collection<? extends Object> fromList, Class<T> toClass) throws APIException {
        if (fromList == null) {
            return null;
        } else {
            List<T> result = new ArrayList(fromList.size());
            Iterator var3 = fromList.iterator();

            while(var3.hasNext()) {
                Object from = var3.next();
                T to = copyProperties(from, toClass);
                result.add(to);
            }

            return result;
        }
    }

    public static Method writeMethod(Class<?> clz, PropertyDescriptor property) {
        String firstByte = property.getDisplayName().substring(0, 1).toUpperCase();
        String setMethodStr = "set" + firstByte + property.getDisplayName().substring(1);
        Method method = null;

        try {
            method = clz.getMethod(setMethodStr, property.getPropertyType());
        } catch (NoSuchMethodException var6) {
        }

        return method;
    }
}
