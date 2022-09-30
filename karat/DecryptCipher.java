package karat;

import java.util.*;

public class DecryptCipher {
    public static void main(String[] args) {
        /**
         * Instead of catching up on the show your friends mounted a brute-force attack and cracked your code. They are complaining about spoilers again.
         * This time you choose an ambiguous arbitrary substitution cipher. Assume messages only contain letters A-Z. Each letter A-Z is mapped to some integer 1-26 (in any order), and you don't give the order to your friends.
         * Write a function that given an encrypted string and a list of known words returns all possible decryptions.
         * Examples:
         * dictionary1 = [ 'AXE', 'CAT', 'AT', 'OR', 'A', 'COO', 'CARD' ]
         * ciphertext1 = '123'
         * decrypt(dictionary1, ciphertext1) -> AXE, CAT, AT, OR
         * 123 can be parsed as:
         * 1 2 3 -> 3 distinct letters -> AXE, CAT
         * 12 3 -> 2 distinct letters -> AT, OR
         * 1 23 -> same
         * ciphertext2 = "122"
         * decrypt(dictionary1, ciphertext2) -> COO, AT, OR
         * 122 can be parsed as:
         * 1 2 2 -> COO
         * 12 2 -> AT, OR
         * 1 22 -> AT, OR
         * ciphertext3 = "102"
         * decrypt(dictionary1, ciphertext3) -> AT, OR
         */

        // Map记录字典每个串唯一字符个数
        // DFS切割cipher，有效数字范围1-26，不符合直接回溯换
        // 符合能切到尾，统计唯一数字个数，然后字典里查，，频率对得上的放res里
        // 102只能切 10 2, 2个唯一字符的串
        String[] dict1 = {"AXE", "CAT", "AT", "OR", "A", "COO", "CARD"};
        String cipher = "122";

        Map<Integer, List<String>> strToFreq = new HashMap<>();
        for (String each : dict1) {
            Set<Character> temp = new HashSet<>();
            for (char c : each.toCharArray()) {
                temp.add(c);
            }
            List<String> tempList = strToFreq.getOrDefault(temp.size(), new ArrayList<>());
            tempList.add(each);
            strToFreq.put(temp.size(), tempList);
        }
        Set<String> res = new HashSet<>();
        dfs(cipher, strToFreq, 0, new ArrayList<>(), res);
        System.out.println(res.toString());
    }

    public static void dfs(String cipher, Map<Integer, List<String>> freq, int idx, List<Integer> path, Set<String> res){
        if (idx >= cipher.length()) {//成功切割方案
            int cnt = path.size();// 要求长度
            Set<Integer> temp = new HashSet<>(path);
            int freqCnt = temp.size(); //唯一字符数
            List<String> candidate = freq.get(freqCnt);

            // 只select长度也为cnt的串
            for (String each : candidate) {
                if (each.length() == cnt) {
                    res.add(each);
                }
            }
            return;
        }

        for (int i = idx; i < cipher.length(); i++) {
            int curNum = Integer.parseInt(cipher.substring(idx, i+1));
            if (curNum<=0 || curNum>26) break;
            path.add(curNum);
            dfs(cipher, freq, i + 1, path, res);
            path.remove(path.size()-1);
        }
    }


}
