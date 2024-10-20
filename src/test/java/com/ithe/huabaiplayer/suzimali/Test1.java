package com.ithe.huabaiplayer.suzimali;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @ClassName Test1
 * @Author hua bai
 * @Date 2024/9/24 19:11
 **/
public class Test1 {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String next = sc.next();
        char[] array = next.toCharArray();
        List<Character> xs = new ArrayList<>();
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            List<Integer> d = map.get(array[i]);
            if (d == null) {
                d = new ArrayList<>();
                map.put(array[i], d);
            } else if (d.size() == 1) {
                xs.add(array[i]);
            }
            d.add(i + 1);
        }
        for (Character k : xs) {
            List<Integer> v = map.get(k);
            for (Integer i : v) {
                System.out.print(k + ", " + i + "; ");
            }
        }
    }
}
