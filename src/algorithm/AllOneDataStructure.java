package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-06 14:56
 */
public class AllOneDataStructure {
    class AllOne {
        class Bucket {
            public int cnt;
            public HashSet<String> set;
            public Bucket last;
            public Bucket next;

            public Bucket(String s, int cnt) {
                set = new HashSet<>();
                set.add(s);
                this.cnt = cnt;
            }
        }

        public void insert(Bucket cur, Bucket post) {
            cur.next.last = post;
            post.next = cur.next;
            cur.next = post;
            post.last = cur;
        }

        public void remove(Bucket cur) {
            cur.last.next = cur.next;
            cur.next.last = cur.last;
        }

        Bucket head;
        Bucket tail;
        Map<String, Bucket> map;
        public AllOne() {
            head = new Bucket("", 0);
            tail = new Bucket("", Integer.MAX_VALUE);
            head.next = tail;
            tail.last = head;
            map = new HashMap<>();
        }

        public void inc(String key) {
            if (!map.containsKey(key)) {
                if (head.next.cnt == 1) {
                    map.put(key, head.next);
                    head.next.set.add(key);
                } else {
                    Bucket bucket = new Bucket(key, 1);
                    map.put(key, bucket);
                    insert(head, bucket);
                }
            } else  {
                Bucket bucket = map.get(key);
                if (bucket.cnt + 1 == bucket.next.cnt) {
                    map.put(key, bucket.next);
                    bucket.next.set.add(key);

                } else {
                    Bucket newBucket = new Bucket(key, bucket.cnt + 1);
                    map.put(key, newBucket);
                    insert(bucket, newBucket);
                }
                bucket.set.remove(key);
                if (bucket.set.isEmpty()) {
                    remove(bucket);
                }
            }
        }

        public void dec(String key) {
            Bucket bucket = map.get(key);
            if (bucket.cnt == 1) {
                map.remove(key);
            } else {
                if (bucket.last.cnt == bucket.cnt - 1) {
                    map.put(key, bucket.last);
                    bucket.last.set.add(key);
                } else {
                    Bucket newBucket = new Bucket(key, bucket.cnt - 1);
                    map.put(key, newBucket);
                    insert(bucket.last, newBucket);
                }
            }
            bucket.set.remove(key);
            if (bucket.set.isEmpty()) {
                remove(bucket);
            }
        }

        public String getMaxKey() {
            return tail.last.set.iterator().next();
        }

        public String getMinKey() {
            return head.next.set.iterator().next();
        }
    }
}
