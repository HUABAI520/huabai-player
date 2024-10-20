package com.ithe.huabaiplayer.xiaomi;

import java.util.Scanner;

/**
 * @ClassName Test2
 * @Author hua bai
 * @Date 2024/9/19 17:17
 **/
public class Test2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();
            int[] a = new int[n];
            int[] b = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = sc.nextInt();
            }
            for (int j = 0; j < n; j++) {
                b[j] = sc.nextInt();
            }
            System.out.println(isSort(a, b));
        }
        sc.close();
    }

    private static String isSort(int[] a, int[] b) {
        
        return "NO";
    }
}
