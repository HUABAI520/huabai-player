package com.ithe.huabaiplayer;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @ClassName Main
 * @Author hua bai
 * @Date 2024/10/12 11:01
 **/
public class Main {
    public static void main(String[] args) {
        System.out.println();
        Scanner sc = new Scanner(System.in);
        int i = Integer.parseInt("1");
        List<String> a= new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        for (String s : a) {
            System.out.println(s);
        }
        int s = 10;

        IntStream.rangeClosed(1, a.size()).forEach(d -> System.out.println(a.get(d-1)));
    }
}
