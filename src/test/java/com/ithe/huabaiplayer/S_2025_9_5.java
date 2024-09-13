package com.ithe.huabaiplayer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * @ClassName S_2025_9_5
 * @Author hua bai
 * @Date 2024/9/5 15:33
 **/
public class S_2025_9_5 {

    @Test
    public void test1() {
        RandomizedSet randomizedSet = new RandomizedSet();
        System.out.println(randomizedSet.insert(1)); // 向集合中插入 1 。返回 true 表示 1 被成功地插入。
        System.out.println(randomizedSet.remove(2)); // 返回 false ，表示集合中不存在 2 。
        System.out.println(randomizedSet.insert(2)); // 向集合中插入 2 。返回 true 。集合现在包含 [1,2] 。
        System.out.println(randomizedSet.getRandom()); // getRandom 应随机返回 1 或 2 。
        System.out.println(randomizedSet.remove(1)); // 从集合中移除 1 ，返回 true 。集合现在包含 [2] 。
        System.out.println(randomizedSet.insert(2)); // 2 已在集合中，所以返回 false 。
        System.out.println(randomizedSet.getRandom()); // 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
        System.out.println(randomizedSet.getRandom()); // 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
        System.out.println(randomizedSet.getRandom()); // 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
        System.out.println(randomizedSet.getRandom()); // 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
    }

    static class RandomizedSet {

        private final Set<Integer> set;

        public RandomizedSet() {
            set = new HashSet<>();
        }

        public boolean insert(int val) {

            if (set.contains(val)) {
                return false;
            }
            return set.add(val);
        }

        public boolean remove(int val) {
            if (!set.contains(val)) {
                return false;
            }
            return set.remove(val);
        }

        public int getRandom() {
            // 随机返回 set 的元素
            int len = set.size();
            int index = (int) (Math.random() * len);
            return set.toArray(new Integer[len])[index];
        }
    }

    @Test
    public void test2() {
        int[] gas = new int[]{2};
//        int[] gas = new int[]{1, 2, 3, 4, 5};
        int[] cost = new int[]{2};
//        int[] cost = new int[]{3, 4, 5, 1, 2};
        System.out.println(canCompleteCircuit(gas, cost));
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        for (int i = 0; i < len; i++) {
            if (gas[i] >= cost[i]) {
                int j = i;
                j = (j + 1) % len;
                int have = gas[i] - cost[i];
                while (j != i && have >= 0) {
                    have = have + gas[j] - cost[j];
                    j = (j + 1) % len;
                }
                if (j == i && have >= 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int canCompleteCircuit2(int[] gas, int[] cost) {
        int sum = 0, max_g = 0, ans = 0;
        for (int i = gas.length - 1; i >= 0; i--) {
            sum += gas[i] - cost[i];
            max_g = Math.max(sum, max_g);
            if (sum >= max_g) ans = i;
        }
        return sum >= 0 ? ans : -1;
    }

//    public static void main(String[] args) {
//        TreeMap<Integer, Integer> map = new TreeMap<>();
//        map.put(1, 2);
//        map.put(2, 5);
//        map.put(5, 1);
//        map.put(10, 3);
//        map.put(20, 4);
//        map.put(50, 0);
//        map.put(100, 4);
//        Money money = new Money(map);
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        money.tanxin(n);
//        money.getResult().forEach((k, v) -> {
//            System.out.println(k + "元:" + v + "张");
//        });
//    }

    @Test
    public void test3() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1, 2);
        map.put(2, 5);
        map.put(5, 1);
        map.put(10, 3);
        map.put(20, 4);
        map.put(50, 0);
        map.put(100, 4);
        Money money = new Money(map);
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        money.tanxin(n);
        money.getResult().forEach((k, v) -> {
            System.out.println(k + "元:" + v + "张");
        });
    }

    // 贪心  计算出支付后的最小张数
    static class Money {
        // 面值：拥有的数量
        private final TreeMap<Integer, Integer> map;
        private final TreeMap<Integer, Integer> mapHua;

        public Money(TreeMap<Integer, Integer> m) {
            map = new TreeMap<>(Comparator.reverseOrder());
            mapHua = new TreeMap<>(Comparator.reverseOrder());
            map.putAll(m);
        }

        public boolean tanxin(int money) {
            for (Integer k : map.keySet()) {
                Integer v = map.get(k);
                Integer can = Math.min(v, money / k);
                map.put(k, v - can);
                mapHua.put(k, mapHua.getOrDefault(k, 0) + can);
                money = money - can * k;
            }
            return true;
        }

        public TreeMap<Integer, Integer> getResult() {
            return mapHua;
        }
    }

    @Test
    public void test4() {

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] map = new int[n][n];
        int[] dis = new int[n];
        Map<Integer, List<Integer>> road = new HashMap<>();
        for (int i = 1; i < n; ++i) {
            dis[i] = Short.MAX_VALUE;
            for (int j = 1; j < n; ++j) {
                map[i][j] = Short.MAX_VALUE;//开始时将每条边赋为最大值
            }
            road.put(i, new ArrayList<>());
        }
        for (int j = 1; j < m; ++j) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            if (c < map[a][b]) {
                map[a][b] = map[b][a] = c;
            }
        }
        System.out.println(Arrays.toString(djikstra(map, dis, n, new int[n])));
        System.out.println(Arrays.toString(djikstra(map, dis, road, n, new int[n])));
        System.out.println(road);
    }

    // 找出每个点到远点的最短路径
    static int[] djikstra(int[][] map, int[] dis, int n, int[] visit) {
        int pos = 1;
        if (n - 1 >= 0) System.arraycopy(map[1], 1, dis, 1, n - 1);
        visit[1] = 1;
        dis[1] = 0;
        for (int i = 1; i < n; i++) {
            int min = Short.MAX_VALUE;
            for (int j = 1; j < n; ++j) {
                // 找到距离起点最近的点且未走过的
                if (visit[j] == 0 && min > dis[j]) {
                    min = dis[j];
                    pos = j;
                }
            }
            visit[pos] = 1;
            // 更新dis
            for (int j = 1; j < n; j++) {
                if (visit[j] == 0 && dis[j] > dis[pos] + map[pos][j]) {
                    dis[j] = dis[pos] + map[pos][j];
                }
            }
        }
        return dis;
    }

    // 找出每个点到远点的最短距离和相应路径
    static int[] djikstra(int[][] map, int[] dis, Map<Integer, List<Integer>> road, int n, int[] visit) {
        int pos = 1;
        if (n - 1 >= 0) System.arraycopy(map[1], 1, dis, 1, n - 1);
        for (int i = 1; i < n; i++) {
            if (map[1][i] < Short.MAX_VALUE) {
                List<Integer> roadI = road.get(i);
                roadI.add(1);
                roadI.add(i);
            }
        }
        visit[1] = 1;
        dis[1] = 0;
        List<Integer> road1 = road.get(1);
        road1.add(1);
        for (int i = 1; i < n; i++) {
            int min = Short.MAX_VALUE;
            for (int j = 1; j < n; j++) {
                if (visit[j] == 0 && dis[j] < min) {
                    pos = j;
                    min = dis[j];
                }
            }
            visit[pos] = 1;
            // 更新dis
            for (int j = 1; j < n; j++) {
                if (visit[j] == 0 && dis[j] > dis[pos] + map[pos][j]) {
                    dis[j] = dis[pos] + map[pos][j];
                    List<Integer> roadJ = new ArrayList<>(road.get(pos));
                    roadJ.add(j);
                    road.put(j, roadJ);
                }
            }
        }
        return dis;
    }
}
