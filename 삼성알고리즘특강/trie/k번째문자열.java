package 삼성알고리즘특강.trie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class k번째문자열 {
    static BufferedReader br;
    static int T;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        sb = new StringBuilder();
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            int index = Integer.parseInt(br.readLine());
            String word = br.readLine();
            Set<String> wordSet = new HashSet<>();

            for (int i = 0; i < word.length(); i++) {
                for (int j = i + 1; j <= word.length(); j++) {
                    wordSet.add(word.substring(i, j));
                }
            }
            List<String> sortedWord = wordSet.stream()
                    .sorted()
                    .collect(Collectors.toCollection(ArrayList::new));
            String result = "none";
            if(sortedWord.size() >= index) result =sortedWord.get(index-1);
            sb.append(result).append("\n");

        }
        System.out.println(sb);

    }
}
