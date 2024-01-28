package 삼성알고리즘특강.hash;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;
public class 문자열교집합 {

    private static HashSet<String> alphabet;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            alphabet = new HashSet<>();
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            int firstNum = Integer.parseInt(st.nextToken());
            int secondNum = Integer.parseInt(st.nextToken());

            sb.append(count(br)).append("\n");

        }
        System.out.println(sb);
    }

    public static int count(BufferedReader br) throws Exception{
        int result = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        while(st.hasMoreTokens()){ // 첫번째 집합 입력 받기
            String word = st.nextToken();
            alphabet.add(word); // hashset에 저장
        }
        st = new StringTokenizer(br.readLine());
        while(st.hasMoreTokens()){ // 두 번째 집합 입력받기
            String word = st.nextToken();
            if(alphabet.contains(word)) result +=1; // 앞에서 세팅된 값 중에 있으면 count
        }
        return result;

    }

}
