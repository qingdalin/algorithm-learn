package algorithm;

import java.util.Scanner;

public class AvgWeight {
    public static void main(String[] args) {
//        String s = "w ha d";
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] array = s.split(" ");
        double div = array.length;
        double length = s.replaceAll(" ", "").length();
        double avg = length/div;
        /*BigDecimal bigDecimal = new BigDecimal(avg);
        avg = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
        System.out.println(avg);*/
        System.out.printf("%.2f%n", avg);
        // 6个任务
        // 2个缓存
        // 1  2  3  1  2  3
        // 0  1  2  3  4  5下标
        // 最少查询4次
        // 查询1，缓存没有，查询数据库，放入缓存[1]
        // 查询2，缓存没有，查询数据库，放入缓存[1,2]
        // 查询3，缓存没有，查询数据库，缓存已满，删除2，放入缓存[1,3]
        // 查询1，缓存有，直接返回
        // 查询2，缓存没有，查询数据库，缓存已满，删除1，放入缓存[2,3]
        // 查询3，缓存有，直接返回
    }
}
