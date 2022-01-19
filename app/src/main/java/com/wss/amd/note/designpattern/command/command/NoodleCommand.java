package com.wss.amd.note.designpattern.command.command;

import com.wss.amd.note.designpattern.command.receiver.NoodleCookeReceiver;

/**
 * Describe：面条命令
 * Created by 吴天强 on 2022/1/19.
 */
public class NoodleCommand extends AbsCommand {
    public NoodleCommand() {
        this.receiver = new NoodleCookeReceiver();
    }

    @Override
    public void execute() {
        receiver.cooking();
    }
}
