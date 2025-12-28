package algorithm;

import java.util.*;



public class StringTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        String[] arr = new String[n];
        for(int i = 0; i < n; i++){
            arr[i] = in.next();
        }
        String x = in.next();
        int k = in.nextInt();
        List<String> list = new ArrayList<>();
        for(int i = 0; i < n; i++){
            String item = arr[i];
            if(item.equals(x) || item.length() != x.length()){
                continue;
            }
            int num = 0;
            for(int j = 0; j < item.length(); j++){
                if(x.contains(String.valueOf(item.charAt(j)))){
                    num++;
                }
            }
            if(num == item.length() && !list.contains(item)){
                list.add(item);
            }
        }

        String[] res = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
            res[i] = list.get(i);
        }
        Arrays.sort(res);
        System.out.println(list.size());
        System.out.println(res[k-1]);
    }

    private static void sail() {
        Map<Character,Integer> map = new HashMap<>();
        map.put('1',1);
        map.put('a',2);
        map.put('b',2);
        map.put('c',2);
        map.put('d',3);
        map.put('e',3);
        map.put('f',3);
        map.put('g',4);
        map.put('h',4);
        map.put('i',4);
        map.put('j',5);
        map.put('k',5);
        map.put('l',5);
        map.put('m',6);
        map.put('n',6);
        map.put('o',6);
        map.put('p',7);
        map.put('q',7);
        map.put('r',7);
        map.put('s',7);
        map.put('t',8);
        map.put('u',8);
        map.put('v',8);
        map.put('w',9);
        map.put('x',9);
        map.put('y',9);
        map.put('z',9);
        map.put('0',0);
        String str = "YUANzhi1987";
        char[] cArr = str.toCharArray();
        char[] res = new char[cArr.length];
        for(int i = 0; i < cArr.length; i++){
            if(cArr[i] >= 'A' && cArr[i] <= 'Z'){
                if(cArr[i] == 'Z'){
                    res[i] = 'a';
                } else{
                    char t = (char) (cArr[i] + 1);
                    res[i] = Character.toLowerCase(t);
                }
            } else if(cArr[i] >= 'a' && cArr[i] <= 'z'){
                int t = map.get(cArr[i]);
                res[i] =(char)(t + '0');
            }
        }
        System.out.println(new String(res));
    }

    private static void extracted2() {
        String str = "A10;S20;W10;D30;X;A1A;B10A11;;A10;";
        String[] arr = str.split(";");
        int x = 0, y = 0;
        for(int i = 0; i < arr.length; i++){
            String s =  arr[i];
            if(s.startsWith("W") ){
                if( s.substring(1).length() == 1 && s.charAt(1)>= 48 && s.charAt(1) <= 57){
                    y += (s.charAt(1) - 48);
                }
                if(s.substring(1).length() == 2 && s.charAt(2)>= 48 && s.charAt(2) <= 57
                        && s.charAt(1)>= 48 && s.charAt(1) <= 57){
                    y = y + (s.charAt(1) - 48) * 10 + (s.charAt(2) - 48);
                }
            } else if(s.startsWith("S")){
                if( s.substring(1).length() == 1 && s.charAt(1)>= 48 && s.charAt(1) <= 57){
                    y -= (s.charAt(1) - 48);
                }
                if(s.substring(1).length() == 2 && s.charAt(2)>= 48 && s.charAt(2) <= 57
                        && s.charAt(1)>= 48 && s.charAt(1) <= 57){
                    y = y - (s.charAt(1) - 48) * 10 - (s.charAt(2) - 48);
                }
            }else if(s.startsWith("A")){
                if( s.substring(1).length() == 1 && s.charAt(1)>= 48 && s.charAt(1) <= 57){
                    x -= (s.charAt(1) - 48);
                }
                if(s.substring(1).length() == 2 && s.charAt(2)>= 48 && s.charAt(2) <= 57
                        && s.charAt(1)>= 48 && s.charAt(1) <= 57){
                    x = x - (s.charAt(1) - 48) * 10 - (s.charAt(2) - 48);
                }
            }else if(s.startsWith("D")){
                if( s.substring(1).length() == 1 && s.charAt(1)>= 48 && s.charAt(1) <= 57){
                    x += (s.charAt(1) - 48);
                }
                if(s.substring(1).length() == 2 && s.charAt(2)>= 48 && s.charAt(2) <= 57
                        && s.charAt(1)>= 48 && s.charAt(1) <= 57){
                    x = x + (s.charAt(1) - 48) * 10 + (s.charAt(2) - 48);
                }
            }

        }
        System.out.println(x + "," + y);
    }

    private static void extracted1() {
        int[][] grid = {{1,2,3},{4,5,6},{7,8,9}};
//        int[][] grid = {
//                {-73,61,43,-48,-36},
//                {3,30,27,57,10},
//                {96,-76,84,59,-15},
//                {5,-49,76,31,-7},
//                {97,91,61,-46,67}};
        int[][] dp = new int[grid.length][grid.length];
        for(int m = 0; m < grid.length; m++){
            dp[0][m] = grid[0][m];
        }
        for(int i = 1; i < grid.length; i++){
            for(int j = 0; j < grid.length; j++){
                dp[i][j] = Integer.MAX_VALUE;
                for(int n = 0; n < grid.length; n++){
                    if(n != j){

                        dp[i][j] = Math.min(dp[i - 1][n] + grid[i][j], dp[i][j]);
                    }
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for(int n = 0; n < grid.length; n++){
            ans = Math.min(ans, dp[grid.length - 1][n]);
        }
        System.out.println(ans);
    }

    private static void longestCommonPrefix() {
        String[] strs = {"flower","flow","flight"};
        String s1 = strs[0];
        int i = 1;
        while(i < strs.length){
            int i1 = strs[i].indexOf(s1);
            while(strs[i].indexOf(s1) != 0){
                s1 = s1.substring(0, s1.length() - 1);
            }
            i++;
        }
        System.out.println(s1);
    }

    private static void compareStr() {
        // initialize
        String s1 = "Hello World";
        System.out.println("s1 is \"" + s1 + "\"");
        String s2 = s1;
        System.out.println("s2 is another reference to s1.");
        String s3 = new String(s1);
        System.out.println("s3 is a copy of s1.");
        // compare using '=='
        System.out.println("Compared by '==':");
        // true since string is immutable and s1 is binded to "Hello World"
        System.out.println("s1 and \"Hello World\": " + (s1 == "Hello World"));
        // true since s1 and s2 is the reference of the same object
        System.out.println("s1 and s2: " + (s1 == s2));
        // false since s3 is refered to another new object
        System.out.println("s1 and s3: " + (s1 == s3));
        // compare using 'equals'
        System.out.println("Compared by 'equals':");
        System.out.println("s1 and \"Hello World\": " + s1.equals("Hello World"));
        System.out.println("s1 and s2: " + s1.equals(s2));
        System.out.println("s1 and s3: " + s1.equals(s3));
        // compare using 'compareTo'
        System.out.println("Compared by 'compareTo':");
        System.out.println("s1 and \"Hello World\": " + (s1.compareTo("Hello World") == 0));
        System.out.println("s1 and s2: " + (s1.compareTo(s2) == 0));
        System.out.println("s1 and s3: " + (s1.compareTo(s3) == 0));
    }

    private static void setZeroes() {
        int[][]  matrix = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};

        boolean[][] arr = new boolean[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] == 0) {
                    arr[i][j] = true;
                }
            }
        }
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(arr[i][j]) {
                    for(int m = 0; m < matrix[0].length; m++){
                        matrix[i][m] = 0;
                    }
                    for(int n = 0; n < matrix.length; n++){
                        matrix[n][j] = 0;
                    }
                }
            }
        }
    }

    private static void pivotIndex() {
        int[] nums = {1, 7, 3, 6, 5, 6};
        int[] sums = new int[nums.length];
        sums[0] = 0;
        int s = nums[0];
        for(int i = 1; i < nums.length; i++){
            sums[i] = sums[i - 1] + nums[i - 1];
            s += nums[i];
        }
        int ans = Integer.MAX_VALUE;
        boolean flag = false;
        for(int j = 0; j< sums.length; j++){
            if(s - nums[j] == sums[j] * 2){
                ans = Math.min(ans, j);
                flag = true;
            }
        }
        System.out.println(flag ? ans : -1);
    }

    private static void trap() {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        int right = 0;
        int sum = 0;
        // while(right < height.length){
        for(int i = 0; i < height.length - 2; i++){
            for(int j = i + 2; j < height.length;j++){
                if(height[i] == 0 || i < right){
                    break;
                }
                if(height[i] <= height[j]){
                    int vTotal = (j - i) * height[i];
                    int vJian = 0;
                    for(int m = i; m < j; m++ ){
                        vJian = height[m] + vJian;
                    }
                    int vWater = vTotal - vJian;
                    sum = sum + vWater;
                    right = j;
                    break;
                }
            }
        }
        // }
        System.out.println(sum);
    }

    private static void maxPoints() {
        int[][] points = {{1,1},{3,2},{5,3},{4,1},{2,3},{1,4}};
        int n = 0;
        for(int i = 0; i < points.length; i++){
            n += i;
        }
        double[] arr = new double[n];
        int index = 0;
        for(int i = 0; i < points.length - 1; i++){
            int[] p1 = points[i];
            for(int j = i + 1; j < points.length; j++){
                int[] p2 = points[j];
                if(p2[1] - p1[1] != 0){
                    double k = (double)((p2[0] - p1[0]) / (p2[1] - p1[1]));
                    arr[index++] =k;
                }
            }
        }
        System.out.println();
    }


    public static String numToStr(int num){
        String s = "";
        // int shang = ;
        while(num / 10 != 0){
            int yu = num % 10;
            s = yu + s;
            num = num / 10;
        }
        return num + s;
    }

    private static void numSquares() {
        int n = 12;
        int[] dp = new int[n + 1];
        for(int i = 1; i <= n; i++){
            int min = Integer.MAX_VALUE;
            for(int j = 1; j * j <= i; j++){
                min = Math.min(min, dp[i - j * j]);
            }
            dp[i] = min + 1;
        }
        System.out.println(dp[n]);
    }

    private static void findMedianSortedArrays() {
        int[] nums1 ={1,2};
        int[] nums2 ={3,4};
        int m = nums1.length;
        int n = nums2.length;
        int[] temp = new int[m + n];
        int i = 0;
        int j = 0;
        int index = 0;
        while(i < m && j < n){
            if(nums1[i] < nums2[j]){
                temp[index++] = nums1[i];
                i++;
            } else{
                temp[index++] = nums2[j];
                j++;
            }
        }
        for(; i < m;){
            temp[index++] = nums1[i++];
        }
        for(; j < n;){
            temp[index++] = nums2[j++];
        }
        double mid = (double)(m + n - 1) / 2;
        double ind1 = Math.ceil(mid);
        double ind2 = Math.floor(mid);
        double res = (double)(temp[(int)ind1] + temp[(int)ind2]) / 2;
        System.out.println(res);
    }

    private static void jisuanqi() {
        String s = "3+2*2";
        Deque<Integer> stack = new ArrayDeque<>();
        int num = 0;
        char preSign = '+';
        int n = s.length();
        for(int i = 0; i < n; i++){
            if(Character.isDigit(s.charAt(i))){
                num = num * 10 + s.charAt(i) - '0';
            }
            if((!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ') || i == n - 1){
                switch(preSign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    default:
                        stack.push(stack.pop() / num);

                }
                preSign = s.charAt(i);
                num = 0;
            }
        }
        int res = 0;
        while(!stack.isEmpty()){
            res += stack.pop();
        }
        System.out.println(res);
    }

    private static void longestConsecutive() {
        int[] nums = {0,3,7,2,5,8,4,6,0,1};
        Arrays.sort(nums);
        int maxLong = 0;
        int temp = 0;
        for(int i = 0; i < nums.length - 1; i++){

            if(nums[i + 1] - nums[i] == 1){
                temp++;
            } else {
                temp = 0;
            }
            temp += 1;
            maxLong = Math.max(temp, maxLong);
        }
        System.out.println(maxLong);
    }

    private static void myPow() {
        double x = 2;
        int n = 10;
        double res = 1.0;
        for (int i = n; i != 0; i /= 2) {
            if (i % 2 != 0) {
                res *= x;
            }
            x *= x;
        }
        System.out.println(n < 0 ? 1 / res : res + "");
    }

    private static void titleToNumber() {
        String columnTitle = "AB";
        int length = columnTitle.length();
        int sum = 0;
        for(int i = 0; i < length; i++){
            sum += (columnTitle.charAt(i) - 64 ) * (Math.pow(26, length - 1));
        }
        System.out.println(sum);
    }

    private static void trailingZeroes() {
        int n = 30;
        long sum = 1;
        for(int i = n; i >= 1; i--){
            sum *= i;
        }
        long yu = sum % 10;
        int res = 0;
        while(yu == 0) {
            res++;
            sum = sum / 10;
            yu = sum % 10;
        }
        System.out.println(res);
    }

    private static void happyNumber() {
        int n = 19;
        Set<Integer> set = new HashSet<>();
        while (n != 1) {
            int temp = n;
            int sum = 0;
            while (temp >= 10) {
                int mod = temp % 10;
                temp /= 10;
                sum += mod * mod;
            }
            sum += temp * temp;
            if (!set.add(sum)) {
                System.out.println("return false;");
            }
            n = sum;
        }
        System.out.println("true");
    }

    private static void maxzixulie() {
        int[] nums = new int[]{10,9,2,5,3,7,101,18};
        int[] dp = new int[nums.length];

        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(dp[i], res);
        }
    }

    private static void canJump() {
        int[] nums = new int[]{2,0,0};
        int length = nums.length;
        if(length == 1){
            return;
        }
        if(nums[0] == 0) {
            return;
        }
        int[] dp = new int[length];
        dp[0] = Math.max(nums[0] - 1, nums[0]);

        for(int i = 1; i < length; i++){
            dp[i] = Math.max(dp[i - 1] - 1, nums[i]);
            if(dp[i] + i + 1 >= length){
                return;
            } else if(dp[i] == 0){
                return;
            }
        }
        return;
    }

    public static List<String> letterCombinations(String digits) {


        LinkedList<String> res = new LinkedList<>();
        //空判断
        if (digits == null || digits.isEmpty())
            return res;

        char[][] tab = {{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
                {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'},
                {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};
        res.add("");
        while (res.peek().length() != digits.length()) {
            String remove = res.poll();//出队
            int a = remove.length() - '2';
            char[] chars = tab[digits.charAt(remove.length()) - '2'];
            //相当于当前节点的所有子节点
            for (int i = 0; i < chars.length; i++) {
                res.add(remove + chars[i]);//入队
            }
        }
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        return res;
    }

    private static void extracted() {
        int n = 499979;
        if(n <= 2){
            System.out.println(0);
        }
        int count = 0;
        for(int i = 2; i < n; i++){
            if(isNum(i)){
                count++;
            }
        }
        System.out.println(count);
        String s = "";
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(1);
        Integer remove = objects.remove(1);
    }

    public static boolean isNum(int m){
        int count = 0;
        for(int i = 1; i <= m; i++){
            if(m % i == 0){
                count++;
            }
            if(count > 2){
                break;
            }

        }
        return count <= 2;
    }
}
