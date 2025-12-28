package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 8:15
 * https://leetcode.cn/problems/shuffle-an-array/
 */
public class RandomArr {
    int[] randomArr;
    int n;
    int[] arr;
    public RandomArr(int[] nums) {
        n = nums.length;
        randomArr = new int[n];
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
            randomArr[i] = nums[i];
        }
    }

    public int[] reset() {
        int[] tmp = new int[n];
        for (int i = 0; i < n; i++) {
            tmp[i] = arr[i];
        }
        return tmp;
    }

    public int[] shuffle() {
        int[] tmp = new int[n];
        for (int i = 0; i < n; i++) {
            int index;
            do {
                index = (int) (Math.random() * n);
            } while (randomArr[index] == -1);
            tmp[i] = randomArr[index];
            randomArr[index] = -1;
        }
        for (int i = 0; i < n; i++) {
            randomArr[i] = arr[i];
        }
        return tmp;
    }
}
