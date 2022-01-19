package com.wss.amd.note.designpattern.visitor.material;

import com.wss.amd.note.designpattern.visitor.company.Company;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public interface Material {

    String accept(Company visitor);
}
