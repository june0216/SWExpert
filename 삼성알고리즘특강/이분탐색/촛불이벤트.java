package 삼성알고리즘특강.이분탐색;

import java.io.BufferedReader;
import java.io.InputStreamReader;
 /*
 왜 이분탐색인지 모르겠음 -> DP정도?로만 생각했는데 아무리 생각해도 아이디어가 떠오르지 않음
 이분 탐색 -> 어떤 걸 찾는 것 == 지금 식이 주어져있고 미지수를 찾으면 되는 것임

  */

public class 촛불이벤트 {

    public static long find(long target, long start, long end){

        if(start > end){
            return -1;
        }
        long mid = (start + end)/2;
        long cal = (mid+1)*mid/2;
        if(cal > target){
            return find(target, start, mid-1);
        }else if(cal < target){
            return find(target, mid+1, end);
        }else{
            return mid;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        long result;
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            long N = Long.parseLong(br.readLine());
            // 삼각형 만들기
            // 2N = k*(k+1)을 만족하는 k를 찾기
            result = find(N, 1, (long)Math.sqrt(N*2)); // 범위 -> k^2 ≈ 2N이 되므로 k의 최대값은 sqrt(2N) 근처에 있을 것으로 예상
            sb.append(result).append("\n");
        }
        System.out.print(sb); // 이 부분에서 println이 아니라 print로 해야 정답 처리
    }

}
