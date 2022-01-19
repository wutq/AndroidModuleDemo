package com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.computer;

import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.ICPU;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IComputerFactory;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IHardDisk;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IMainBoard;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.cpu.AmdCPU;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.harddisk.ToshibaHardDisk;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.impl.mainboard.AsusMainBoard;

/**
 * Describe：华为电脑工厂
 * Created by 吴天强 on 2022/1/14.
 */
public class HaweiComputerFactoryFactory implements IComputerFactory {

    @Override
    public ICPU createCPU() {
        return new AmdCPU();
    }

    @Override
    public IHardDisk createHardDisk() {
        return new ToshibaHardDisk();
    }

    @Override
    public IMainBoard createMainBoard() {
        return new AsusMainBoard();
    }
}
