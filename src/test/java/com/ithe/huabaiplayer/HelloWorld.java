package com.ithe.huabaiplayer;

/**
 * @ClassName HelloWorld
 * @Author hua bai
 * @Date 2024/10/10 16:58
 **/
public class HelloWorld {
    public static void main(String[] args) {
        long limit = 1000000000000L; // 1 trillion
        long sum = 0;

        long start = System.currentTimeMillis();

        for (long i = 1; i <= limit; ++i) {
            sum += i;
        }

        long end = System.currentTimeMillis();

        System.out.println("Sum: " + sum);
        System.out.println("Time taken: " + (end - start) + " milliseconds");
    }
}
