package algorithm;

import java.util.Arrays;

public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {2,3,5,1,4,6,8,7};
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j >= 1; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = 0;
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
