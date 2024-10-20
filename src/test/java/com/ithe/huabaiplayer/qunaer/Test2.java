package com.ithe.huabaiplayer.qunaer;

import java.util.Scanner;

/**
 * @ClassName Test2
 * @Author hua bai
 * @Date 2024/10/10 15:20
 **/
public class Test2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        int l = in.nextInt();
        int r = in.nextInt();
        String ks = String.valueOf(k);
        for (int i = l; i <= r; i++) {
            if (i % k == 0) {
                System.out.print(i + " ");
            } else {
                String is = String.valueOf(i);
                if (is.contains(ks)) {
                    System.out.print(i + " ");
                }
            }
        }
    }
}
