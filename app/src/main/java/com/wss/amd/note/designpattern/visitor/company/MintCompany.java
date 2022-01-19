package com.wss.amd.note.designpattern.visitor.company;

import com.wss.amd.note.designpattern.visitor.material.Copper;
import com.wss.amd.note.designpattern.visitor.material.Paper;

/**
 * Describe：具体访问者：造币公司
 * Created by 吴天强 on 2022/1/19.
 */
public class MintCompany implements Company {
    @Override
    public String create(Copper copper) {
        return "纸币";
    }

    @Override
    public String create(Paper paper) {
        return "铜币";
    }
}
