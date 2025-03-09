package com.ithe.huabaiplayer.jichu;

/**
 * @ClassName Jichu1
 * @Author hua bai
 * @Date 2024/10/21 15:39
 **/
public class Jichu1 {
    public abstract class A {
        abstract public void show();
    }

    public static void main(String[] args) {
        StringBuffer a = new StringBuffer("One");
        StringBuffer b = new StringBuffer("Two");
        swap(a, b);
        System.out.println("a is " + a + " b is " + b);
        System.out.println(a.toString().toUpperCase());
        String c = "123";
        String d = "2";
        System.out.println(c.indexOf(d));
    }

    static void swap(StringBuffer a, StringBuffer b) {
        a.append(" more");
        b = a;
        System.out.println(b);
    }
}
