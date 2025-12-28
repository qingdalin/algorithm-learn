package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/19 20:27
 * https://leetcode.cn/problems/design-spreadsheet/?envType=daily-question&envId=2025-09-19
 */
public class Spreadsheet {
    public int[][] arr;
    public int n, m;
    public Spreadsheet(int rows) {
        arr = new int[rows][27];
        n = rows;
        m = 26;
    }

    public void setCell(String cell, int value) {
        int[] res = getRowAndColNum(cell);
        int row = res[0];
        int col = res[1];
        arr[row][col] = value;
    }

    public int[] getRowAndColNum(String str) {
        int col = 0, row = 0;
        int len = str.length();
        for (int i = 0; i < len;) {
            while (i < len && Character.isLetter(str.charAt(i))) {
                col = col * 26 + str.charAt(i) - 'A' + 1;
                i++;
            }
            while (i < len && Character.isDigit(str.charAt(i))) {
                row = row * 10 + str.charAt(i) - '0';
                i++;
            }
        }
        return new int[] {row, col};
    }

    public void resetCell(String cell) {
        int[] res = getRowAndColNum(cell);
        int row = res[0];
        int col = res[1];
        arr[row][col] = 0;
    }

    public int getValue(String formula) {
        String str = formula.substring(1);
        String[] split = str.split("\\+");
        return getNum(split[0]) + getNum(split[1]);
    }

    public int getNum (String str) {
        if (isNumber(str)) {
            return Integer.parseInt(str);
        } else {
            int[] rowAndColNum = getRowAndColNum(str);
            int row = rowAndColNum[0];
            int col = rowAndColNum[1];
            return arr[row][col];
        }
    }

    public boolean isNumber(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
