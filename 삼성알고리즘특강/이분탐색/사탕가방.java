package 삼성알고리즘특강.이분탐색;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 사탕가방 {
    /*
    이게 왜 이분탐색? 특정한 값을 찾는 게 아니지 않나? -> 그리디 아닌가? 라고 접근했다. 하지만 조건이 꽤 복잡해서 감이 잡히지 않았다.
    하지만 딱 하나의 특정값을 찾는 것이 아니라 범위를 줄여가며 조건에 맞는지 확인하면서 진행하는 것이 이분 탐색이라는 것을 알게 되었다.
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            long M = Long.parseLong(st.nextToken()); // 범위가 크기 때문에 long
            long[] arr = new long[N];

            st = new StringTokenizer(br.readLine());
            long max = Long.MIN_VALUE;
            long min = 1;  // 가방의 개수
            for(int i = 0; i < N; i++){
                arr[i] = Long.parseLong(st.nextToken());
                max = Math.max(arr[i], max);
            }

            long answer = 0L;
            while(min <= max){
                long mid = (min+max)/2;
                long candies = 0;
                for(int i = 0; i < N; i++){
                    candies += (arr[i] / mid); // 종류마다 가방 1개에 들어갈 수 있는 사탕 값을 계산
                }
                if(candies < M){ // 개수가 M을 넘지 않으면 가방을 줄이기
                    max = mid-1;
                }else{
                    answer = mid; // 개수가 넘거나 같으면 일단 정답으로 저장해두고 탐색해보기
                    min = mid+1;
                }
            }
            sb.append(answer).append("\n");
        }
        System.out.print(sb);
    }
}
