package com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces;

/**
 * Describe：电脑接口
 * Created by 吴天强 on 2022/1/14.
 */
public interface IComputerFactory {

    ICPU createCPU();

    IHardDisk createHardDisk();

    IMainBoard createMainBoard();
}
