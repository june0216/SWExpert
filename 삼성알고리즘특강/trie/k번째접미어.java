package 삼성알고리즘특강.trie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
처음에는 접미사 트리로 하려고 헀지만 k번째 단어를 찾아야하므로 접미사 배열이 적합할 것으로 생각함
 */

public class k번째접미어 {

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
            List<String> suffixArray = new ArrayList<>();
            for(int i = 0; i < word.length(); i++){
                suffixArray.add(word.substring(i, word.length()));
            }
            Collections.sort(suffixArray);
            sb.append(suffixArray.get(index-1)).append("\n");
        }
        System.out.println(sb);
    }


}
