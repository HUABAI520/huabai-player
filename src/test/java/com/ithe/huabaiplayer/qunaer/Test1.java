package com.ithe.huabaiplayer.qunaer;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName Test1
 * @Author hua bai
 * @Date 2024/10/10 14:27
 **/
public class Test1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        int l = in.nextInt();
        int r = in.nextInt();
        Set<Integer> count = new HashSet<>();
        // 先查询到l 到 r 的所有倍数
        int i = l / k;
        if (l % k != 0) {
            i++;
        }
        while (i * k <= r) {
            count.add(i * k);
            i++;
        }
        // 找到包含k的所有数
        String ks = String.valueOf(k);
        int ksl = ks.length();
        String ls = String.valueOf(l);
        int lsl = ls.length();
        String rs = String.valueOf(r);
        int rsl = rs.length();
        int kl = lsl - ksl;
        int kr = rsl - ksl;
        if (kl == 0) {
            if (k >= l && k <= r) {
                count.add(k);
            }
        }
        if (kr > 0) {
            for (int j = 1; j <= kr; j++) {
                // 先加尾部直到 > r
                int m = 0;
                int x = Integer.parseInt(ks + getStringBuilder(m, j));
                while (m <= Math.pow(10, j) - 1 && x <= r) {
                    if (x >= l) {
                        count.add(x);
                    }
                    m++;
                    x = Integer.parseInt(ks + getStringBuilder(m, j));
                }
                // 前放加
                int n = 1;
                int y = Integer.parseInt(n + ks);
                while (n <= Math.pow(10, j) - 1 && y <= r) {
                    if (y >= l) {
                        count.add(y);
                    }
                    n++;
                    y = Integer.parseInt(n + ks);
                }
            }
        }
        List<Integer> list = count.stream().sorted(Comparator.comparingInt(a -> a))
                .collect(Collectors.toList());
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }
        System.out.println(list.size());
    }

    private static StringBuilder getStringBuilder(int m, int j) {
        StringBuilder ms = new StringBuilder(String.valueOf(m));
        int c = j - ms.length();
        if (c > 0) {
            for (int i = 0; i < c; i++) {
                ms.append("0");
            }
        }
        return ms;
    }

    @Test
    public void test() {
        System.out.println(Math.pow(2, 3));
    }
}
