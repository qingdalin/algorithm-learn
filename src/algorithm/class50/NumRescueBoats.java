package algorithm.class50;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-01 20:36
 * https://leetcode.cn/problems/boats-to-save-people/
 */
public class NumRescueBoats {
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int l = 0;
        int r = people.length - 1;
        int ans = 0;
        int sum = 0;
        while (l <= r) {
            sum = people[r] + people[l];
            if (sum <= limit) {
                l++;
            }
            r--;
            ans++;
        }
        return ans;
    }
}
