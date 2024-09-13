package com.ithe.huabaiplayer;

import java.util.Scanner;

/**
 * @ClassName MiHayou
 * @Author hua bai
 * @Date 2024/9/7 16:15
 **/
public class MiHayou {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int max = -1;
        int f = m;
        for (int i = m; i >= n; i--) {
            int number = getNumber(i);
            if (number > max) {
                f = i;
                max = number;
            }
        }
        System.out.println(f);
    }

    private static int getNumber(int num) {
        int x = 0;
        while (num / 10 > 0) {
            int b = num % 10;
            if (b == 4 || b == 6) {
                x++;
            }
            num /= 10;
        }
        if (num == 4 || num == 6) {
            x++;
        }
        return x;
    }
}
