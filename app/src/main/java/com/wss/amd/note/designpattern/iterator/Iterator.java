package com.wss.amd.note.designpattern.iterator;

/**
 * Describe：迭代器接口
 * Created by 吴天强 on 2022/1/19.
 */
public interface Iterator<E> {

    E first();

    E next();

    boolean hasNext();
}
