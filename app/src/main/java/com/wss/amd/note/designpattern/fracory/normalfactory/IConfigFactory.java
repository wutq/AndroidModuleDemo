package com.wss.amd.note.designpattern.fracory.normalfactory;


import com.wss.amd.note.designpattern.fracory.simplefactory.config.IConfig;

/**
 * Describe：定义创建环境工厂接口
 * Created by 吴天强 on 2022/1/14.
 */
public interface IConfigFactory {
    IConfig createConfig();
}
