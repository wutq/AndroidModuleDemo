package com.wss.common.widget.scaleImg;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Describe：耗时逻辑处理线程
 * Created by 吴天强 on 2018年11月16日
 */
class IOThread {

    private static ExecutorService singleThread;

    static Executor getSingleThread() {
        if (singleThread == null) {
            singleThread = Executors.newSingleThreadExecutor();
        }
        return singleThread;
    }

}