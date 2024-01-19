package 삼성알고리즘특강.list;

import java.util.*;
import java.io.*;

public class 암호문3 {

    private static void delete(List<String> ciphertext, StringTokenizer st){
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        for(int i = 0; i < y; i++){
            ciphertext.remove(x);
        }
    }

    private static void add(List<String> ciphertext, StringTokenizer st){
        int y = Integer.parseInt(st.nextToken());
        for (int i = 0; i < y; i++){
            ciphertext.add(st.nextToken());
        }
    }

    private static void insert(List<String> ciphertext, StringTokenizer st){
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        for(int i = 0; i < y; i++){
            ciphertext.add(x+i, st.nextToken()); // 삽입되면 index 바뀌므로 i를 더해준다.
        }
    }
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = 10; // 문제에서 테스트 케이스 10개로 고정
        StringBuilder sb =new StringBuilder(); // 결과를 하나의 문자열로 만들기 위해 선언

        for(int test_case = 1; test_case <= T; test_case++)
        {
            int N = Integer.parseInt(br.readLine()); // 총 암호문 덩어리 개수
            List<String> ciphertext = new LinkedList<>(); // 추가, 삭제가 많아서 링크드 리스트로 선택
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i = 0; i < N; i++){
                ciphertext.add(st.nextToken()); // 암호문 LinkedList에 저장
            }
            int command = Integer.parseInt(br.readLine()); // 명령어 개수
            st = new StringTokenizer(br.readLine()); // 명령어 입력 받기

            while(st.hasMoreTokens()){ // 명령어가 있을 때까지 반복
                switch (st.nextToken()){ // 명령어 단위
                    case "A":
                        add(ciphertext, st);
                        break;
                    case "D":
                        delete(ciphertext, st);
                        break;
                    case "I":
                        insert(ciphertext, st);
                        break;
                }
            }
            sb.append("#").append(test_case).append(" ");
            for (int i = 0; i < 10; i++){
                sb.append(ciphertext.get(i)).append(" ");
            }
            sb.append("\n");

        }
        System.out.println(sb);
    }
}
