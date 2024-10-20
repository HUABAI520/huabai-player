package com.ithe.huabaiplayer.suanf;

import lombok.Data;

import java.beans.IntrospectionException;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IntrospectionException {
        Random r = new Random(12345);
        for (int i = 0; i < 10; i++) {
            System.out.println(r.nextInt(100));
        }
    }
}

@Data
class Score {
    private final int[] scores;
    private int sum;

    public Score(int[] scores) {
        // 深拷贝
        this.scores = new int[scores.length];
        System.arraycopy(scores, 0, this.scores, 0, scores.length);
        this.sum = Arrays.stream(scores).sum();

    }

    public Score add(int other) {
        this.sum += other;
        return this;
    }

    public void printScores() {
        System.out.println(Arrays.toString(this.scores));
    }

    public void printSum() {
        System.out.println(this.sum);
    }
}

enum Color {
    RED,
    GREEN,
    BLUE
}

// 记录类
record Point(int x, int y) {
    Point {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Invalid point");
        }
        System.out.println("Point created");
    }
}