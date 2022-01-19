package com.wss.amd.note.designpattern.command.command;

import com.wss.amd.note.designpattern.command.receiver.CookReceiver;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public abstract class AbsCommand {

    protected CookReceiver receiver;

    public abstract void execute();
}
