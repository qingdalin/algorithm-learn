package algorithm.class107;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/24 10:57
 * // 囚徒生存问题
 * // 有100个犯人被关在监狱，犯人编号0~99，监狱长准备了100个盒子，盒子编号0~99
 * // 这100个盒子排成一排，放在一个房间里面，盒子编号从左往右有序排列
 * // 最开始时，每个犯人的编号放在每个盒子里，两种编号一一对应，监狱长构思了一个处决犯人的计划
 * // 监狱长打开了很多盒子，并交换了盒子里犯人的编号
 * // 交换行为完全随机，但依然保持每个盒子都有一个犯人编号
 * // 监狱长规定，每个犯人单独进入房间，可以打开50个盒子，寻找自己的编号
 * // 该犯人全程无法和其他犯人进行任何交流，并且不能交换盒子中的编号，只能打开查看
 * // 寻找过程结束后把所有盒子关上，走出房间，然后下一个犯人再进入房间，重复上述过程
 * // 监狱长规定，每个犯人在尝试50次的过程中，都需要找到自己的编号
 * // 如果有任何一个犯人没有做到这一点，100个犯人全部处决
 * // 所有犯人在一起交谈的时机只能发生在游戏开始之前，游戏一旦开始直到最后一个人结束都无法交流
 * // 请尽量制定一个让所有犯人存活概率最大的策略
 * // 来自论文<The Cell Probe Complexity of Succinct Data Structures>
 * // 作者Anna Gal和Peter Bro Miltersen写于2007年
 * // 如今该题变成了流行题，还有大量科普视频
 */
public class PrisonerEscapeGame {

    public static double escape1(int people, int tryTimes, int testTimes) {
        int escape = 0;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArr(people);
            // 环的长度小于尝试次数，就一定生存
            if (maxCircle(arr) <= tryTimes) {
                escape++;
            }
        }
        return (double) escape / testTimes;
    }

    // 1 3 5 0 2 4 7 6
    // 0 1 2 3 4 5 6 7
    // i来到0 i != arr[0],交换0和arr[0] = 1位置 circle++ = 2
    // 3 1 5 0 2 4 7 6
    // i来到0 i != arr[0],交换0和arr[0] = 3位置 circle++ = 3
    // 0 1 5 3 2 4 7 6
    // i来到0 i == arr[0],交换0和arr[0] = 3位置 跳出循环
    private static int maxCircle(int[] arr) {
        int maxCircle = 1;
        for (int i = 0; i < arr.length; i++) {
            int curCircle = 1;
            while (i != arr[i]) {
                swap(arr, i, arr[i]);
                curCircle++;
            }
            maxCircle = Math.max(curCircle, maxCircle);
        }
        return maxCircle;
    }

    private static int[] randomArr(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        for (int i = n - 1; i > 0; i--) {
            swap(arr, i, (int) (Math.random() * (i + 1)));
        }
        return arr;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 公式
    // 环的长度大于等于51的概率,保证尝试次数大于人数一半
    // 从100个人里找出r个人(r>=51), 随机排，剩余的(100-r)随机排, 一共的排列数是100!
    // C(r, 100) * (r - 1)! * (100 -r)!, r从51开始算到100，就是死亡概率
    // (100! / (r! * (100 - r)!))  * (r - 1)! * (100 -r)! 简化就是1/r,从51累计到100
    public static double escape2(int people, int tryTimes) {
        double ans = 0;
        for (int r = tryTimes + 1; r <= people; r++) {
            ans += ((double) 1 / (double)r);
        }
        return 1 - ans;
    }

    public static void main(String[] args) {
        int people = 100;
        int tryTimes = 70;
        int testTimes = 10000;
        System.out.println("参与游戏的人数 : " + people);
        System.out.println("每人的尝试次数 : " + tryTimes);
        System.out.println("模拟实验的次数 : " + testTimes);
        System.out.println("通过模拟实验得到的概率为 : " + escape1(people, tryTimes, testTimes));
        System.out.println("通过公式计算得到的概率为 : " + escape2(people, tryTimes));
    }
}
