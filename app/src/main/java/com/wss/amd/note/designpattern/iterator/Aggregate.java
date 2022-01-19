package com.wss.amd.note.designpattern.iterator;

/**
 * Describe：聚合接口
 * Created by 吴天强 on 2022/1/19.
 */
public interface Aggregate<E> {

    void add(E object);

    void remove(E object);
}
