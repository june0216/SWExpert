package 삼성알고리즘특강.list;

import java.io.*;
import java.io.InputStreamReader;
import java.util.*;

public class 수열편집 {
    private static void insert(List<String> arr, StringTokenizer st){
        int x = Integer.parseInt(st.nextToken());
        String value = st.nextToken();
        arr.add(x, value); // 인덱스 x에 value값 추가
    }
    private static void delete(List<String> arr, StringTokenizer st){
        int x = Integer.parseInt(st.nextToken());
        arr.remove(x); // 인덱스 x 값 삭제
    }
    private static void change(List<String> arr, StringTokenizer st){
        int x= Integer.parseInt(st.nextToken());
        String value = st.nextToken();
        arr.set(x, value); // 인덱스 x 값을 변경
    }

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        String result;
        for (int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            int arrLen = Integer.parseInt(st.nextToken()); // 배열의 길이
            int addCount = Integer.parseInt(st.nextToken()); // 몇번 명령어 입력할건지
            int resultIndex = Integer.parseInt(st.nextToken()); // 결과로 출력할 인덱스 번호
            List<String> arr = new LinkedList<>(); // 삽입 삭제가 자주 일어나므로 LinkedList 선택
            st = new StringTokenizer(br.readLine());
            while(st.hasMoreTokens()){
                arr.add(st.nextToken()); // 주어진 배열 입력
            }
            while(addCount-- >0){
                st = new StringTokenizer(br.readLine());
                switch (st.nextToken()){
                    case "I":
                        insert(arr, st);
                        break;
                    case "D":
                         delete(arr, st);
                         break;
                    case "C":
                        change(arr, st);
                        break;
                }
            }
            if(arr.size() > resultIndex){ // 결과로 출력할 인덱스의 값이 있다면 그대로 출력
                result = arr.get(resultIndex);
            }else{
                result = "-1"; //없다면 -1로 출력
            }
            sb.append("#").append(testCase).append(" ").append(result).append("\n");

        }
        System.out.println(sb);
    }
}
