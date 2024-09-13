package com.ithe.huabaiplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @ClassName MiHayou
 * @Author hua bai
 * @Date 2024/9/7 16:15
 **/
public class MiHayou2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] c = new int[m]; //对应英的额外奖励
        for (int i = 0; i < m; i++) {
            c[i] = in.nextInt();
        }
        // 每个关卡 中 奖励与对应英灵的映射
        List<Map<Integer, Integer>> guanka = new ArrayList<>();
        in.nextLine();
        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> map = new HashMap<>();
            String s1 = in.nextLine();
            String s2 = in.nextLine();
            String[] split1 = s1.split(" ");
            String[] split2 = s2.split(" ");
            for (int j = 0; j < 3; j++) {
                map.put(Integer.valueOf(split1[j]), Integer.valueOf(split2[j]));
            }
            guanka.add(map);
        }
        // 找出超过三个英

    }


}
