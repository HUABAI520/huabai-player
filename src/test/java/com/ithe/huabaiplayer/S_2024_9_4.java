package com.ithe.huabaiplayer;

import com.ithe.huabaiplayer.common.utils.HuaUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @ClassName S_2024_9_4
 * @Author hua bai
 * @Date 2024/9/4 15:20
 **/
public class S_2024_9_4 {
    @Test
    public void test() {
        int[] nums = {2, 4, 1, 1, 4};
        System.out.println(hIndex(nums));
    }

    public boolean canJump(int[] nums) {
        int start = nums[0];
        int len = nums.length;
        if (len == 1) {
            return true;
        }
        if (start == 0) {
            return false;
        }
        int need = len - 1;
        int max = 0;
        for (int i = 0; i < len; i++) {
            if (i <= max) {
                max = Math.max(max, i + nums[i]);
                if (max >= need) {
                    return true;
                }
            }
        }
        return false;
    }

    public int jump(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            maxPosition = Math.max(maxPosition, nums[i] + i);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }

    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int max = 0;
        for (int i = citations.length - 1; i >= 0; i--) {
            if (max < citations[i]) {
                max++;
            }
        }
        return max;
    }
    @Test
    public void test2() {
        String pinYin = HuaUtils.getFirstPinYin("啊啊啊啊");
        System.out.println(pinYin);
    }
}
