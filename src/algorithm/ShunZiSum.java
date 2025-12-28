package algorithm;

import java.util.Scanner;

public class ShunZiSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        int m;
        int n;
        do {
            n = i / 2;
            i = n;

        }while (n % 2 == 0 && n > 2);

        if (i % 2 != 0 && i > 2) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }
}
