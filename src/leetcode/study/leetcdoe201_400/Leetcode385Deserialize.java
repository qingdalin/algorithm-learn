package leetcode.study.leetcdoe201_400;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 8:26
 * https://leetcode.cn/problems/mini-parser/
 */
public class Leetcode385Deserialize {
    public NestedInteger deserialize(String str) {
        if (str.charAt(0) != '[') {
            return new NestedInteger(Integer.parseInt(str));
        }
        // [123,[456,[789]]]
        char[] s = str.toCharArray();
        int n = s.length;
        Deque<NestedInteger> stack = new ArrayDeque<>();
        int num = 0;
        boolean negative = false;
        for (int i = 0; i < n; i++) {
            if (s[i] == '-') {
                negative = true;
            } else if (Character.isDigit(s[i])) {
                num = num * 10 + s[i] - '0';
            } else if (s[i] == '[') {
                stack.push(new NestedInteger());
            } else if (s[i] == ',' || s[i] == ']') {
                if (Character.isDigit(s[i - 1])) {
                    if (negative) {
                        num *= -1;
                    }
                    stack.peek().add(new NestedInteger(num));
                }
                num = 0;
                negative = false;
                if (s[i] == ']' && stack.size() > 1) {
                    NestedInteger pop = stack.pop();
                    stack.peek().add(pop);
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String s = "[123,[456,[789]]]";
        Leetcode385Deserialize a = new Leetcode385Deserialize();
        System.out.println(a.deserialize(s));
    }


     // This is the interface that allows for creating nested lists.
     // You should not implement it, or speculate about its implementation
     public class NestedInteger {
         public NestedInteger() {

         }

         // Constructor initializes a single integer.
         public NestedInteger(int value) {

         }

         // @return true if this NestedInteger holds a single integer, rather than a nested list.
         public boolean isInteger(){
             return true;
         }


         // @return the single integer that this NestedInteger holds, if it holds a single integer
         // Return null if this NestedInteger holds a nested list
         public Integer getInteger() {
             return 1;
         }

         // Set this NestedInteger to hold a single integer.
         public void setInteger(int value) {

         }

         // Set this NestedInteger to hold a nested list and adds a nested integer to it.
         public void add(NestedInteger ni){

         }

         // @return the nested list that this NestedInteger holds, if it holds a nested list
         // Return empty list if this NestedInteger holds a single integer
         public List<NestedInteger> getList(){
             return null;
         }
     }
}
