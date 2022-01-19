package com.wss.amd.note.designpattern.visitor.material;

import com.wss.amd.note.designpattern.visitor.company.Company;

import org.jetbrains.annotations.NotNull;

/**
 * Describe：具体元素：铜
 * Created by 吴天强 on 2022/1/19.
 */
public class Copper implements Material {
    @Override
    public String accept(@NotNull Company visitor) {
        return visitor.create(this);
    }
}
