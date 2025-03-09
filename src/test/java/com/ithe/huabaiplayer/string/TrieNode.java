package com.ithe.huabaiplayer.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TrieNode
 * @Author hua bai
 * @Date 2024/11/12 13:34
 **/

public class TrieNode {
    public boolean isEnd;
    public Map<Character, TrieNode> children;
    public List<Integer> positions;

    public TrieNode() {
        children = new HashMap<>();
        positions = new ArrayList<>();
        isEnd = false;
    }

    @Override
    public String toString() {
        return "TrieNode{" +
                "isEnd=" + isEnd +
                ", children=" + children +
                ", positions=" + positions +
                '}';
    }
}
