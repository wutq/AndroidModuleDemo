package com.wss.amd.note.designpattern.command.command;

import com.wss.amd.note.designpattern.command.receiver.RiceCookReceiver;

/**
 * Describe：做米饭具体命令
 * Created by 吴天强 on 2022/1/19.
 */
public class RiceCommand extends AbsCommand {

    public RiceCommand() {
        this.receiver = new RiceCookReceiver();
    }

    @Override
    public void execute() {
        receiver.cooking();
    }
}
