package com.ithe.huabaiplayer.suanf;

import lombok.Data;

/**
 * @ClassName Study_9_26
 * @Author hua bai
 * @Date 2024/9/26 19:16
 **/
@Data
public class Study_9_26 implements Comparable<Study_9_26> {
    private String name;
    private Integer age;

    static {
        System.out.println("hello world1");
    }

    public Study_9_26(String name) {
        this.name = name;
        System.out.println("hello1");
    }

    @Override
    public int compareTo(Study_9_26 o) {
        return o.getAge() - this.getAge();
    }
}
