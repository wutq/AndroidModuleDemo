package com.wss.amd.note.designpattern.iterator;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class UserIterator<E> implements Iterator<E> {
    private List<E> list = null;
    private int index = -1;

    public UserIterator(List<E> list) {
        this.list = list;
    }

    @Override
    public E first() {
        index = 0;
        return list.get(index);
    }

    @Override
    public E next() {
        E obj = null;
        if (this.hasNext()) {
            obj = list.get(++index);
        }
        return obj;
    }

    @Override
    public boolean hasNext() {
        return index < list.size() - 1;
    }
}
