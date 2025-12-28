package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/23 20:38
 * https://leetcode.cn/problems/peeking-iterator/description/
 */
public class Leetcode284PeekingIterator implements Iterator<Integer> {
    List<Integer> list;
    int size;
    int i;
    public Leetcode284PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        size = list.size();
        i = 0;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return list.get(i);
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        return list.get(i++);
    }

    @Override
    public boolean hasNext() {
        return i < size;
    }
}
