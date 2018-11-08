package com.wss.common.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Describe：Lis分组
 * Created by 吴天强 on 2018-05-31 08:45
 **/
public class GroupUtils {
    public static final String TAG = GroupUtils.class.getSimpleName();

    /**
     * 分组依据接口，用于集合分组时，获取分组依据
     */
    public interface GroupBy<T> {
        T groupBy(Object obj);
    }

    public static <T extends Comparable<T>, D> Map<T, List<D>> group(Collection<D> colls, GroupBy<T> gb) {
        if (colls == null || colls.isEmpty()) {
            Log.e(TAG, "分组集合不能为空");
            return null;
        }
        if (gb == null) {
            Log.e(TAG, " 分组依据接口不能为Null！ ");
            return null;
        }
        Iterator<D> iter = colls.iterator();
        Map<T, List<D>> map = new HashMap<>();
        while (iter.hasNext()) {
            D d = iter.next();
            T t = gb.groupBy(d);
            if (map.containsKey(t)) {
                map.get(t).add(d);
            } else {
                List<D> list = new ArrayList<>();
                list.add(d);
                map.put(t, list);
            }
        }
        return map;
    }

    /**
     * 将List<V>按照V的methodName方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
     *
     * @param list       待分组的列表
     * @param map        存放分组后的map
     * @param clazz      泛型V的类型
     * @param methodName 方法名
     */
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Class<V> clazz, String methodName) {
        // 入参非法行校验
        if (null == list || null == map || null == clazz || TextUtils.isEmpty(methodName)) {
            return;
        }

        // 获取方法
        Method method = getMethodByName(clazz, methodName);
        // 非空判断
        if (null == method) {
            return;
        }
        // 正式分组
        listGroup2Map(list, map, method);
    }

    /**
     * 根据类和方法名，获取方法对象
     *
     * @param clazz      class
     * @param methodName 风机组方法名
     * @return Method
     */
    @Nullable
    private static Method getMethodByName(Class<?> clazz, String methodName) {
        Method method = null;
        // 入参不能为空
        if (null == clazz || TextUtils.isEmpty(methodName)) {
            Log.e(TAG, "GroupUtils.getMethodByName 入参错误，clazz：" + clazz + " ；methodName：" + methodName);
            return null;
        }
        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (Exception e) {
            Log.e(TAG, "类获取方法失败");
        }

        return method;
    }

    /**
     * 将List<V>按照V的某个方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
     *
     * @param list   待分组的列表
     * @param map    存放分组后的map
     * @param method 方法
     */
    @SuppressWarnings("unchecked")
    private static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Method method) {
        // 入参非法行校验
        if (null == list || null == map || null == method) {
            Log.e(TAG, "GroupUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map + " ；method：" + method);
            return;
        }
        try {
            // 开始分组
            Object key;
            List<V> listTmp;
            for (V val : list) {
                key = method.invoke(val);
                listTmp = map.get(key);
                if (null == listTmp) {
                    listTmp = new ArrayList<>();
                    map.put((K) key, listTmp);
                }
                listTmp.add(val);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "数据分组异常" + e.getMessage());
        }
    }

}

    
