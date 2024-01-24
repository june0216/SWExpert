package 삼성알고리즘특강.이분탐색;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 영어공부 {
    public static int[] arr;
    public static int[] diff;
    public static int p;
    public static int n;
    public static long max;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int blankNum;

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            p = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            arr = new int[n];
            for(int i = 0; i < arr.length; i++){
                arr[i] = Integer.parseInt(st.nextToken());
            }
            diff = new int[arr.length];
            blankNum = 0;
            for(int i = 1; i < arr.length; i++){
                blankNum += (arr[i] - arr[i-1]-1);
                diff[i] = blankNum;
            }
            sb.append(binarySearch()).append("\n");
        }
        System.out.println(sb);
    }

    public static long binarySearch(){
        max = 0;
        // 모든 날짜를 각각 시작점으로 설정하고 각각 경우를 이진 탐색으로 구한다.
        for(int i = 0; i < arr.length; i++){
            long answer = 0; // 번호 계산 과정에서 오버플로우가 날 수 있으므로
            int start = i;
            int end = n-1;

            while(start <= end){
                int mid = start + ((end-start)/2);
                int blankDay = diff[mid] - diff[i]; // i~mid 범위의 날짜를 선택한다면 빈 공간의 개수
                if(blankDay > p){ // 빈칸이 여유분보다 크면 연속된 값이 아니므로 다시 해야한다.
                    end = mid-1;
                }else{ //빈칸이 여유분보다 작으면 더 시도해볼 수 있음
                    answer = arr[mid]-arr[i]+1+(p-blankDay); // 일단 정답값에 넣어놓고 탐색하기
                    start = mid+1;
                }
            }

            if (answer > max) { // 기준점마다 max를 갱신한다.
                max = answer;
            }

        }
        return max;
    }


}
