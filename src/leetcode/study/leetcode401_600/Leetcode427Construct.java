package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/10 17:12
 * https://leetcode.cn/problems/construct-quad-tree/
 */
public class Leetcode427Construct {
    public Node construct(int[][] grid) {
        int n = grid.length;
        return f(0, 0, n, n, grid);
    }
    //   0 1 2 3
    // 0
    // 1
    // 2
    // 3

    private Node f(int a, int b, int c, int d, int[][] grid) {
        boolean same = true;
        for (int i = a; i < c; i++) {
            for (int j = b; j < d; j++) {
                if (grid[i][j] != grid[a][b]) {
                    same = false;
                    break;
                }
            }
            if (!same) {
                break;
            }
        }
        if (same) {
            return new Node(grid[a][b] == 1, true);
        }
        Node leftUp = f(a, b, (a + c) / 2, (b + d) / 2, grid);
        Node rightUp = f(a, (b + d) / 2, (a + c) / 2, d, grid);
        Node leftDown = f((a + c) / 2, b, c, (b + d) / 2, grid);
        Node rightDown = f((a + c) / 2, (b + d) / 2, c, d, grid);
        return new Node(true, false, leftUp, rightUp, leftDown, rightDown);
    }

    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;


        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }
}
