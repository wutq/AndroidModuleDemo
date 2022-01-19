package com.wss.amd.note.designpattern.visitor;

import com.wss.amd.note.designpattern.visitor.company.Company;
import com.wss.amd.note.designpattern.visitor.material.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：对象结构角色：材料集
 * Created by 吴天强 on 2022/1/19.
 */
public class SetMaterial {

    private List<Material> list = new ArrayList<>();


    public String accept(Company visitor) {
        StringBuilder sb = new StringBuilder();
        for (Material m : list) {
            sb.append(m.accept(visitor)).append(",");
        }
        return sb.toString();
    }

    public void add(Material element) {
        list.add(element);
    }

    public void remove(Material element) {
        list.remove(element);
    }
}
