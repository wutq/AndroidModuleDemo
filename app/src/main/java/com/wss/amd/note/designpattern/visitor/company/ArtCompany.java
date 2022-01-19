package com.wss.amd.note.designpattern.visitor.company;

import com.wss.amd.note.designpattern.visitor.material.Copper;
import com.wss.amd.note.designpattern.visitor.material.Paper;

/**
 * Describe：具体访问者：艺术公司
 * Created by 吴天强 on 2022/1/19.
 */
public class ArtCompany implements Company {
    @Override
    public String create(Copper copper) {
        return "讲学图";
    }

    @Override
    public String create(Paper paper) {
        return "朱熹铜像";
    }
}
