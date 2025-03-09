package com.ithe.huabaiplayer.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Trie
 * @Author hua bai
 * @Date 2024/11/12 13:36
 **/
public class Trie {
    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word, int position) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.isEnd = true;
        if (node.positions == null) {
            node.positions = new ArrayList<>();
        }
        node.positions.add(position);
    }

    public List<Integer> search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return new ArrayList<>();
            }
        }
        return node.isEnd ? node.positions : new ArrayList<>();
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        Trie trie = new Trie();
        var text = "The quick brown fox jumps over the lazy dog dog2 喜欢 ";
        var words = text.split("\\s"); // 分割单词
        System.out.println(Arrays.toString(words));
        for (int i = 0; i < words.length; i++) {
            trie.insert(words[i], i);
        }
        System.out.println(trie.search("fox"));
        System.out.println(trie.search("dog"));
        System.out.println(trie.search("cat"));
        System.out.println(trie.search("dog2"));
        System.out.println(trie.search("喜欢"));
        for (int i = 0; i < 10000000; i++) {
            trie.search("fox");
            trie.search("dog");
            trie.search("cat");
            trie.search("dog2");
            trie.search("喜欢");
        }
        System.out.println("Time taken: " + (System.currentTimeMillis() - l) + " ms");
    }

    @Override
    public String toString() {
        return "Trie{" +
                "root=" + root +
                '}';
    }
}
