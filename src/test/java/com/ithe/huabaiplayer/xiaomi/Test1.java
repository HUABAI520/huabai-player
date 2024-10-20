package com.ithe.huabaiplayer.xiaomi;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @ClassName Test1
 * @Author hua bai
 * @Date 2024/9/19 16:32
 **/
public class Test1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            int N = sc.nextInt();
            int n = sc.nextInt();
            int c = sc.nextInt();
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = sc.nextInt();
            }
            System.out.println(isCan(N, a, c));
        }
        sc.close();
    }


    private static String isCan(int n, int[] a, int c) {
        // 判断a[] 数组内的 东西 + c个1 能否恰好填满n
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        int len = a.length;
        boolean f = true;
        int index = -1;
        int nf = n;
        for (int i = len - 1; i >= 0; i--) {
            if (n >= a[i]) {
                if (f) {
                    index = i - 1;
                    f = false;
                }
                n -= a[i];
            }
            if (n == 0) {
                return "YES";
            }
        }
        if (n <= c) {
            return "YES";
        } else {
            while (index >= 0) {
                f = true;
                n = nf;
                int index2 = -1;
                for (int i = index; i >= 0; i--) {
                    if (n >= a[i]) {
                        if (f) {
                            index2 = i - 1;
                            f = false;
                        }
                        n -= a[i];
                    }
                    if (n == 0) {
                        return "YES";
                    }
                }
                if (n <= c) {
                    return "YES";
                }
                index = index2;
            }
            return "NO";
        }
    }

    private static String isCan2(int n, int[] a, int c) {
        // 判断a[] 数组内的 东西 + c个1 能否恰好填满n
        Arrays.sort(a);
        for (int j : a) {
            if (n >= j) {
                n -= j;
            } else {
                break;
            }
            if (n == 0) {
                return "YES";
            }
        }
        if (n <= c) {
            return "YES";
        } else {
            return "NO";
        }
    }
}
