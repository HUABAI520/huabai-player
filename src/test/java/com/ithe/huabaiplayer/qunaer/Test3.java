package com.ithe.huabaiplayer.qunaer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @ClassName Test3
 * @Author hua bai
 * @Date 2024/10/10 15:24
 **/
public class Test3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();//字符串长度
        int q = in.nextInt();// 操作次数
        String s = in.next();// 字符串
        char[] sCharArray = s.toCharArray();
        List<Character> characters = new ArrayList<>();
        for (char c : sCharArray) {
            characters.add(c);
        }
        Map<Integer, Character> map = new HashMap<>();
        in.nextLine();
        int san = 0;
        for (int i = 0; i < q; i++) {
            String s1 = in.nextLine();
            String[] split = s1.split(" ");
            int op = Integer.parseInt(split[0]);
            if (op == 1) {
                int g = Integer.parseInt(split[1]);
                // 将第g位的字符取出来
                System.out.println(g - 1 - san);
                char c = characters.get(g - 1 - san);
                map.put(g - 1, c);
                characters.remove(g - 1 - san);
                san++;
            } else {
                int size = characters.size();
                // 右移
                ArrayList<Character> newChar = new ArrayList<>();
                for (int i1 = 0; i1 < size; i1++) {
                    newChar.add(characters.get((i1 + 1) % size));
                }
                characters = newChar;
            }
        }
        int x = 0;
        for (int i = 0; i < n; i++) {
            Character c = map.get(i);
            if (c != null) {
                System.out.print(c);
            } else {
                System.out.print(characters.get(x));
                x++;
            }
        }
    }
}
