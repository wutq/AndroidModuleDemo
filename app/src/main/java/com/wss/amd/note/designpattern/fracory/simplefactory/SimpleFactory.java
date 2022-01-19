package com.wss.amd.note.designpattern.fracory.simplefactory;

import com.wss.amd.note.designpattern.fracory.simplefactory.config.DevConfig;
import com.wss.amd.note.designpattern.fracory.simplefactory.config.IConfig;
import com.wss.amd.note.designpattern.fracory.simplefactory.config.ProConfig;

/**
 * Describe：创建环境配置文件工厂
 * Created by 吴天强 on 2022/1/14.
 */
public class SimpleFactory {


    /**
     * 创建配置文件
     *
     * @param type 环境类型
     * @return 已创建环境
     */
    public static IConfig createConfig(int type) {
        switch (type) {
            case 0:
                return new ProConfig();
            case 1:
                return new DevConfig();
            default:
        }
        return null;
    }
}
