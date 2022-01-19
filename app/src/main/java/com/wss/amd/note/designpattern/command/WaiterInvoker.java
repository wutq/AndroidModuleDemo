package com.wss.amd.note.designpattern.command;

import com.wss.amd.note.designpattern.command.command.AbsCommand;

/**
 * Describe：具体调度者
 * Created by 吴天强 on 2022/1/19.
 */
public class WaiterInvoker {
    private AbsCommand riceCommand;
    private AbsCommand noodleCommand;
    private AbsCommand stirFryCommand;

    public WaiterInvoker(AbsCommand riceCommand, AbsCommand noodleCommand, AbsCommand stirFryCommand) {
        this.riceCommand = riceCommand;
        this.noodleCommand = noodleCommand;
        this.stirFryCommand = stirFryCommand;
    }


    public void chooseRice() {
        riceCommand.execute();
    }

    public void chooseNoodle() {
        noodleCommand.execute();
    }

    public void chooseStirFry() {
        stirFryCommand.execute();
    }


}

