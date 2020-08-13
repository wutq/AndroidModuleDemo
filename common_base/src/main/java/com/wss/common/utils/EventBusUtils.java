package com.wss.common.utils;


import com.wss.common.bean.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Describe：EventBus帮助类
 * Created by 吴天强 on 2018/10/22.
 */
public class EventBusUtils {

    /**
     * 注册事件
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 解除事件
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送普通事件
     */
    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送普通事件
     */
    public static void sendEvent(String action) {
        sendEvent(new Event<String>(action));
    }

    /**
     * 发送普通事件
     */
    public static void sendEvent(String action, Object data) {
        sendEvent(new Event<>(action, data));
    }
}
