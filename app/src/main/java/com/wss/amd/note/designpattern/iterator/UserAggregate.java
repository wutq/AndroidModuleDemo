package com.wss.amd.note.designpattern.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class UserAggregate<E> implements Aggregate<E> {
    private List<E> list = new ArrayList<>();

    @Override
    public void add(E object) {
        list.add(object);
    }

    @Override
    public void remove(E object) {
        list.remove(object);
    }

    public Iterator iterator() {
        return new UserIterator<E>(list);
    }

}
