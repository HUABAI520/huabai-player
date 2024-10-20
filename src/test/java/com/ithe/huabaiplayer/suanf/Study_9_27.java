package com.ithe.huabaiplayer.suanf;

/**
 * @ClassName Study_9_26
 * @Author hua bai
 * @Date 2024/9/26 19:16
 **/
public class Study_9_27 extends Study_9_26 {
    private static int a = 1;


    public static void main(String[] args) {
        String a = "hello";
    }

    static {
        System.out.println("hello world2");
    }

    static {
        System.out.println("hello world2");
    }

    public Study_9_27(String name) {
        super(name);
        System.out.println("hello2");
    }
}
