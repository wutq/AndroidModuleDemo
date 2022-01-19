package com.wss.amd.note.designpattern.visitor.company;

import com.wss.amd.note.designpattern.visitor.material.Copper;
import com.wss.amd.note.designpattern.visitor.material.Paper;

/**
 * Describe：定义公司接口，分别可以制造纸制品 和铜制品
 * Created by 吴天强 on 2022/1/19.
 */
public interface Company {

    String create(Copper copper);

    String create(Paper paper);

}
