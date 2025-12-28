package algorithm.class107;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/23 20:56
 * 蓄水池采样问题
 * 假设有一个不停吐出球的机器，每次吐出1号球，2号球，3号球。。。
 * 有一个袋子只能装下10个球，每次机器吐出的球，要么放入袋子，要么永远扔掉
 * 如何做到机器吐出每个球之后，所有吐出的球都等概率被放进袋子
 * 拓展
 * 如何设计一个抽奖系统，一天内所有登录的用户都有均等的机会中奖，一共100个人
 * 维持一个100大小的容器，如果一个用户首次登录，进行抽奖，选择是否放进容器，否则剔除，时间点进行公布容器中的名单即可
 */
public class ResorvoirSmapling {
    static class Pool {
        int size;
        int[] bag;

        public Pool(int s) {
            this.size = s;
            this.bag = new int[s];
        }

        // 是否要当前球 m / i的概率要
        public boolean pick(int i) {
            return (int) (Math.random() * i) < size;
        }

        public int where() {
            return (int) (Math.random() * size);
        }

        public void enter(int i) {
            if (i <= size) {
                bag[i - 1] = i;
            } else {
                if (pick(i)) {
                    bag[where()] = i;
                }
            }
        }

        public int[] getBag() {
            return bag;
        }
     }

    public static void main(String[] args) {
        System.out.println("测试开始");
        int m = 10;
        int n = 100;
        int time = 10000;
        int[] cnt = new int[n + 1];
        for (int i = 0; i < time; i++) {
            Pool pool = new Pool(m);
            for (int j = 1; j <= n; j++) {
                pool.enter(j);
            }
            int[] bag = pool.getBag();
            for (int j = 0; j < 10; j++) {
                cnt[bag[j]]++;
            }
        }
        System.out.println("一共有" + n + "个球，袋子的大小为" + m + "，每个球被选中的概率为" + (double) m / n);
        for (int i = 1; i < cnt.length; i++) {
            System.out.println(i + "号球被选中的" + cnt[i] + "概率为" + (double) cnt[i] / time);
        }
    }
}
