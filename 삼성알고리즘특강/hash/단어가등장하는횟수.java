package 삼성알고리즘특강.hash;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class 단어가등장하는횟수 {


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            String text = br.readLine();
            String pattern =br.readLine();
            int[] table = makeTable(pattern);
            sb.append(KMP(text, pattern, table)).append("\n");

        }
        System.out.println(sb);
    }

    public static int[] makeTable(String content){ // 실패 테이블 만들기
        int length = content.length();
        int[] table = new int[length];
        int compare = 0;
        for(int move = 1; move< length; move++){
            while(compare > 0 && content.charAt(compare) != content.charAt(move)){
                compare = table[compare-1];
            }
            if(content.charAt(compare) ==content.charAt(move)){
                table[move]= ++compare;
            }
        }
        return table;
    }

    public static int KMP(String text, String pattern, int[] table){
        int compare = 0;
        int result =0;
        for(int i = 0; i < text.length(); i++){
            while(compare > 0 && text.charAt(i) != pattern.charAt(compare)){
                compare = table[compare-1]; //비교를 다시 시작할 위치를 결정
            }
            if(text.charAt(i) == pattern.charAt(compare)){
                if(compare == pattern.length()-1){ // 패턴 모두 탐색 완료했다면
                    result++; // 단어 1개 일치한다고 발견
                    compare = table[compare];
                }else{ // 패턴 모두 탐색하지 않았다면 이동
                    compare++;
                }
            }
        }
        return result;
    }
}
