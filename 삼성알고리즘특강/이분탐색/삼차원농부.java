package 삼성알고리즘특강.이분탐색;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 개인적으로 너무 어려워서 다시 풀어볼 문제
 * 이분탐색으로 풀어야하는지 전혀 감을 못잡은 문제
 * 특히 이분 탐색 반복문 돌고 양옆의 요소 한 번 더 확인해줘야 하는 부분이 이해하기 조금 어려웠음
 */

public class 삼차원농부 {

    public static long MIN_VALUE, COUNT;
    public static long c1, c2;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            MIN_VALUE = Long.MAX_VALUE;
            COUNT = 0;
            int horseNum = Integer.parseInt(st.nextToken());
            int cowNum = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            c1 = Long.parseLong(st.nextToken());
            c2 = Long.parseLong(st.nextToken());

            st = new StringTokenizer(br.readLine());

            long[] horse = new long[horseNum]; // 말을 저장하기 위한 배열

            for(int i = 0; i < horseNum; i++){
                horse[i] = Long.parseLong(st.nextToken());

            }
            Arrays.sort(horse); // 이분 탐색을 위한 정렬

            st = new StringTokenizer(br.readLine());

            long[] cow = new long[cowNum]; // 소를 저장하기 위한 배열
            for(int i = 0; i < cowNum; i++){
                cow[i] = Long.parseLong(st.nextToken());

            }
            Arrays.sort(cow); // 이분 탐색을 위한 정렬
            for(int i = 0; i < cowNum; i++){
                binarySearch(horse, cow[i]); // 이분 탐색을 통해 가까운 거리 찾기
            }
            sb.append(MIN_VALUE).append(" ").append(COUNT).append("\n");

        }
        System.out.println(sb);
    }

    public static void binarySearch(long[] horses, long cow){
        int min = 0;
        int max = horses.length-1;
        int mid = 0;
        while(min <= max){
            mid = min + ((max-min)/2);// min, max가 충분히 커서 오버플로우를 방지하기 위함
            if(horses[mid] == cow){
                max = mid; // 같으면 그 값 반환한다. -> 이때, 아예 같은 값을 발견할 수 있고 min<=max 으로 나갈 수 있기 때문에 max에 대입
                break;
            }
            else if(cow > horses[mid]) min = mid+1;
            else max = mid-1;
        }
        for(int i = max-1; i <= max+1;i++){ // min <= max 조건에 의해 끝날 경우 양옆으로 같거나 더 작은 값이 있을
            if(i < 0) continue;
            if(i > horses.length-1) continue;
            long temp = Math.abs(c1-c2)+Math.abs(horses[i]-cow);
            if(temp < MIN_VALUE){
                MIN_VALUE = temp;
                COUNT = 1;
            }else if(temp==MIN_VALUE){
                COUNT+=1;
            }
        }

    }
}
