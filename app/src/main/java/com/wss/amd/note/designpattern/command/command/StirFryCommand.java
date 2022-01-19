package com.wss.amd.note.designpattern.command.command;

import com.wss.amd.note.designpattern.command.receiver.StirFryReceiver;

/**
 * Describe：炒菜命令
 * Created by 吴天强 on 2022/1/19.
 */
public class StirFryCommand extends AbsCommand {
    public StirFryCommand() {
        this.receiver = new StirFryReceiver();
    }

    @Override
    public void execute() {
        receiver.cooking();
    }
}
