package com.wss.amd.note.designpattern.combination;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class Employee {
    private String name;
    private String dept;
    private List<Employee> subordinates = new ArrayList<>();

    public Employee(String name, String dept) {
        this.name = name;
        this.dept = dept;
    }


    public void add(Employee e) {
        subordinates.add(e);
    }

    public void remove(Employee e) {
        subordinates.remove(e);
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", subordinates=" + subordinates +
                '}';
    }
}
