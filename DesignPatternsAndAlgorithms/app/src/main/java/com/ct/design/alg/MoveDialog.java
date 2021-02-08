package com.ct.design.alg;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 滑动窗口
 */
public class MoveDialog {
    public static void main(String[] args) {
        new MoveDialog().run();
    }

    private void run() {

        String result = minWindow("ADOBECODEBANC", "ABC");
        //System.out.println("最小子串:" + result);

        //System.out.println("包含:" + checkInclusion(Constance.s1, Constance.s2));

        System.out.println("不重复:" + lengthOfLongestSubstring("abcabcbb"));

    }

    /**
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。
     */
    public String minWindow(String s, String t) {

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        char[] chars = t.toCharArray();
        for (char ch : chars
        ) {
            Integer num = need.get(ch);
            if (num == null)
                num = 0;
            num += 1;
            need.put(ch, num);

        }


        int left = 0;
        int right = 0;
        int size = s.length();
        int valid = 0;
        int start = 0;
        int len = Integer.MAX_VALUE;
        while (right < size) {
            //开始滑动右边的窗口
            char ch = s.charAt(right);
            right++;

            //
            if (need.containsKey(ch)) {
                Integer num = window.get(ch);
                if (num == null)
                    num = 0;
                num += 1;
                window.put(ch, num);
                if (window.get(ch) == need.get(ch)) {
                    valid++;
                }

            }


            // 判断是否需要移动左窗口
            while (valid == need.size()) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                char move = s.charAt(left);
                left++;
                if (need.containsKey(move)) {
                    if (window.get(move) == need.get(move))
                        valid--;
                    Integer num = window.get(move);
                    num -= 1;
                    window.put(move, num);
                }


            }

        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);

    }


    /**
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
     * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
     */
    public boolean checkInclusion(String s1, String s2) {

        if (s1.length() > s2.length())
            return false;

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        char[] chars = s1.toCharArray();
        for (char ch : chars
        ) {
            add(need, ch);
        }

        int right = 0;
        int left = 0;
        int valid = 0;
        int size = s2.length();
        while (right < size) {
            char ch = s2.charAt(right);
            right++;
            if (need.containsKey(ch)) {
                add(window, ch);
                if (window.get(ch) == need.get(ch))
                    valid++;
            }

            while (right - left >= s1.length()) {
                System.out.println(s1.length() + "====>" + valid + "____" + need.size());
                if (valid == need.size())
                    return true;
                char move = s2.charAt(left);
                left++;
                if (need.containsKey(move)) {
                    if (need.get(move) == window.get(move))
                        valid--;
                    remove(window, move);
                }


            }
        }


        return false;

    }

    /**
     * 给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
     * 字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。
     * 说明：
     * 字母异位词指字母相同，但排列不同的字符串。
     * 不考虑答案输出的顺序。
     * <p>
     * 1、当移动right扩大窗口，即加入字符时，应该更新哪些数据？
     * 2、什么条件下，窗口应该暂停扩大，开始移动left缩小窗口？
     * 3、当移动left缩小窗口，即移出字符时，应该更新哪些数据？
     * 4、我们要的结果应该在扩大窗口时还是缩小窗口时进行更新？
     */
    public List<Integer> findAnagrams(String s, String p) {

        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        char[] chars = p.toCharArray();
        for (char ch : chars
        ) {
            add(need, ch);
        }

        int size = s.length();
        int left = 0;
        int right = 0;
        int valid = 0;

        while (right < size) {
            char ch = s.charAt(right);
            right++;
//            1、当移动right扩大窗口，即加入字符时，应该更新哪些数据？
            if (need.containsKey(ch)) {
                add(window, ch);
                if (need.get(ch) == window.get(ch))
                    valid++;
            }

//            2、什么条件下，窗口应该暂停扩大，开始移动left缩小窗口？
            while (right - left >= p.length()) {
//                4、我们要的结果应该在扩大窗口时还是缩小窗口时进行更新？
                if (valid == need.size()) {
                    result.add(left);
                }


                char move = s.charAt(left);
                left++;

//                3、当移动left缩小窗口，即移出字符时，应该更新哪些数据？
                if (need.containsKey(move)) {
                    if (window.get(move) == need.get(move))
                        valid--;
                    remove(window, move);
                }


            }


        }

        return result;
    }


    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();

        int left = 0;
        int right = 0;
        int valid = 0;
        int size = s.length();

        while (right < size) {
            char ch = s.charAt(right);
            right++;
            add(window, ch);
            while (window.get(ch) > 1) {
                char move = s.charAt(left);
                left++;
                remove(window, move);
            }
            valid = Math.max(right - left, valid);
        }


        return valid;

    }

    private void add(Map<Character, Integer> map, char key) {
        Integer num = map.get(key);
        if (num == null)
            num = 0;
        map.put(key, ++num);
    }

    private void remove(Map<Character, Integer> map, char key) {
        Integer num = map.get(key);
        map.put(key, --num);
    }

}
