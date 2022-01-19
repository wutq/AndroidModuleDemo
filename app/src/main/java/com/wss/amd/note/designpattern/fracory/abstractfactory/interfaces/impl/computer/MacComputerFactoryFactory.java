package com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.computer;

import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.ICPU;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IComputerFactory;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IHardDisk;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IMainBoard;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.cpu.AmdCPU;

/**
 * Describe：MAC电脑工厂
 * Created by 吴天强 on 2022/1/14.
 */
public class MacComputerFactoryFactory implements IComputerFactory {

    @Override
    public ICPU createCPU() {
        return new AmdCPU();
    }

    @Override
    public IHardDisk createHardDisk() {
        return null;
    }

    @Override
    public IMainBoard createMainBoard() {
        return null;
    }
}
