package com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.computer;

import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.ICPU;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IComputerFactory;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IHardDisk;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IMainBoard;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.cpu.IntelCPU;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.harddisk.HpHardDisk;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.mainboard.MsiMainBoard;

/**
 * Describe：英特尔电脑工厂
 * Created by 吴天强 on 2022/1/14.
 */
public class IntelComputerFactoryFactory implements IComputerFactory {

    @Override
    public ICPU createCPU() {
        return new IntelCPU();
    }

    @Override
    public IHardDisk createHardDisk() {
        return new HpHardDisk();
    }

    @Override
    public IMainBoard createMainBoard() {
        return new MsiMainBoard();
    }
}
