package com.ithe.huabaiplayer.suanf;

import com.ithe.huabaiplayer.common.utils.HuaUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @ClassName Study_2024_9_24
 * @Author hua bai
 * @Date 2024/9/24 14:08
 **/
public class Study_2024_9_24 {
    static class Student implements Comparable<Student> {
        int id;
        int score;

        public Student(int id, int score) {
            this.id = id;
            this.score = score;
        }

        @Override
        public int compareTo(Student o) {
            return -(this.score - o.score);
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", score=" + score +
                    '}';
        }
    }

    static class idSort implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.id - o2.id;
        }

    }

    @Test
    public void test1() {
        int a = 15;
        Assertions.assertEquals(a >> 1, a / 2);
        System.out.println(a >> 1);
        int b = 9;
        System.out.println(b << 1);

        // 随机生成数 1-100
//        for (int i = 0; i < 10; i++) {
//            num[i] = ((int) (Math.random() * 100));
//        }
//        sort(num, 5);
        int[] num = new int[]{1, 4, 2, 5, 3, 10, 6, 8, 7, 9, 10};
        Student[] students = new Student[10];
        for (int i = 0; i < num.length; i++) {
            students[i] = new Student(i, num[i]);
        }
        Arrays.sort(students);
        // num 倒排序
        System.out.println(Arrays.toString(students));
        Arrays.sort(students, new idSort());
        System.out.println(Arrays.toString(students));
        Arrays.sort(students, ((o1, o2) -> o1.score - o2.id));
        System.out.println(Arrays.toString(students));
    }

    @Test
    public void test2() {
        int[] num = new int[]{1, 4, 2, 5, 3, 10, 6, 8, 7, 9, 10};
        HuaUtils.radixSort(num,false);
        System.out.println(Arrays.toString(num));
    }

    public void sort(int[] nums, int k) {
        PriorityQueue<Integer> aa = new PriorityQueue<>();
        int index = 0;
        for (; index < Math.min(nums.length, k); index++) {
            aa.add(nums[index]);
        }
        System.out.println(index);

        int i = 0;
        for (; index < nums.length; i++, index++) {
            aa.add(nums[index]);
            nums[i] = aa.poll();
        }
        while (!aa.isEmpty()) {
            nums[i++] = aa.poll();
        }
        System.out.println(Arrays.toString(nums));
        System.out.println(aa);
    }
}
