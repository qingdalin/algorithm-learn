package algorithm.class66dp;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-15 20:31
 * 给定一个字符串 s，计算 s 的 不同非空子序列 的个数。因为结果可能很大，所以返回答案需要对 10^9 + 7 取余 。
 *
 * 字符串的 子序列 是经由原字符串删除一些（也可能不删除）字符但不改变剩余字符相对位置的一个新字符串。
 *
 * 例如，"ace" 是 "abcde" 的一个子序列，但 "aec" 不是。
 * https://leetcode.cn/problems/distinct-subsequences-ii/description/
 */
public class DistinctSubseqII {
    public static int distinctSubseqII(String s) {
        int mod = 1000000007;
        char[] chars = s.toCharArray();
        int[] cnts = new int[26];
        int all = 1, newAdd = 0;
        for (int x : chars) {
            // 纯新增 = all - 字符上次的记录数
            // 字符的记录数 += 纯新增
            // all += 纯新增
            newAdd = (all - cnts[x - 'a'] + mod) % mod;
            cnts[x - 'a'] = (cnts[x - 'a'] + newAdd) % mod;
            all = (all + newAdd) % mod;
        }
        return (all - 1 + mod) % mod;
    }

    public static void main(String[] args) {
        int i = distinctSubseqII("yezruvnatuipjeohsymapyxgfeczkevoxipckunlqjauvllfpwezhlzpbkfqazhexabomnlxkmoufneninbxxguuktv" +
            "upmpfspwxiouwlfalexmluwcsbeqrzkivrphtpcoxqsueuxsalopbsgkzaibkpfmsztkwommkvgjjdvvggnvtlwrllcafhfocprnrzfoyehqhr" +
            "vhpbbpxpsvomdpmksojckgkgkycoynbldkbnrlujegxotgmeyknpmpgajbgwmfftuphfzrywarqkpkfnwtzgdkdcyvwkqawwyjuskpvqomfchnlo" +
            "jmeltlwvqomucipcwxkgsktjxpwhujaexhejeflpctmjpuguslmzvpykbldcbxqnwgycpfccgeychkxfopixijeypzyryglutxweffyrqtkfrqlht" +
            "jweodttchnugybsmacpgperznunffrdavyqgilqlplebbkdopyyxcoamfxhpmdyrtutfxsejkwiyvdwggyhgsdpfxpznrccwdupfzlubkhppmasd" +
            "bqfzttbhfismeamenyukzqoupbzxashwuvfkmkosgevcjnlpfgxgzumktsexvwhylhiupwfwyxotwnxodttsrifgzkkedurayjgxlhxjzlxikcg" +
            "erptpufocymfrkyayvklsalgmtifpiczwnozmgowzchjiop");
        System.out.println("i = " + i);
    }
}
