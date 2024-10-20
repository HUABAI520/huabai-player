package com.ithe.huabaiplayer.jihe;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 * @ClassName Jihe
 * @Author hua bai
 * @Date 2024/10/10 18:10
 **/
public class Jihe {
    @Test
    public void test1() {
        List<String> list = List.of("apple", "pear", "banana");
        for (String s : list) {
            System.out.println(s);
        }
        Map<Person, Integer> map = new TreeMap<>(Comparator.comparing(p -> p.name));
        Set<Object> objects = new HashSet<>();
        objects.add(1);
        objects.add(null);
        objects.add(1);
        objects.forEach(System.out::println);
        Queue<String> linkedList = new LinkedList<>();
    }
}

class Person {
    public String name;

    Person(String name) {
        this.name = name;
    }

    public String toString() {
        return "{Person: " + name + "}";
    }
}