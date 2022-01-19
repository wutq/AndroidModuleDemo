package com.wss.amd.note.designpattern.fracory.abstractfactory;

import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.ICPU;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IHardDisk;
import com.wss.amd.note.designpattern.fracory.abstractfactory.interfaces.IMainBoard;

/**
 * Describe：组装电脑
 * Created by 吴天强 on 2022/1/14.
 */
public class AssembleComputer {

    private ICPU cpu;
    private IHardDisk hardDisk;
    private IMainBoard mainBoard;

    public AssembleComputer(ICPU cpu, IHardDisk hardDisk, IMainBoard mainBoard) {
        this.cpu = cpu;
        this.hardDisk = hardDisk;
        this.mainBoard = mainBoard;
    }
}

