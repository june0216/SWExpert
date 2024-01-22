package 삼성알고리즘특강.heap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 중앙값구하기 {
    private static final int MOD = 20171109;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        PriorityQueue<Integer> maxHeap; //중간값보다 작거나 같은 숫자들 -> minHeap의 크기보다 1개 크도록 유리
        PriorityQueue<Integer> minHeap; //중간값보다 큰 숫자들
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            int totalNum = Integer.parseInt(st.nextToken());
            int initialNum = Integer.parseInt(st.nextToken());
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            minHeap = new PriorityQueue<>();

            maxHeap.add(initialNum);

            long result = 0;
            for(int i = 0; i < totalNum; i++){
                st = new StringTokenizer(br.readLine());
                int num1 = Integer.parseInt(st.nextToken());
                int num2 = Integer.parseInt(st.nextToken());


                // 배열에 넣기
                //왜 num1, num2따로 따로 하는 게 아니라 경우를 나눠서 하는 지 -> 계산 줄이기 위해서
                if(num1 < maxHeap.peek() && num2 < maxHeap.peek()){ // 둘다 중간값보다 작으면
                    maxHeap.add(num1); // 둘다 작은값을 모아두는 Heap에 저장
                    maxHeap.add(num2);
                    minHeap.add(maxHeap.poll()); // 제일 큰 수 (중간값보다 큰 수)를 큰 값들을 모아주는 Heap에 저장
                }
                else if(num1 > maxHeap.peek() && num2 > maxHeap.peek()){ // 둘 다 중간값보다 크면
                    minHeap.add(num1); // 둘 다 큰 값을 모아두는 heap에 저장
                    minHeap.add(num2);
                    maxHeap.add(minHeap.poll()); // 최신 업데이트 된 중간값을 옮겨주기
                }else{
                    if(num1 > num2){
                        minHeap.add(num1);
                        maxHeap.add(num2);
                    }else{
                        minHeap.add(num2);
                        maxHeap.add(num1);
                    }
                }

                // 중간값 찾기
                result = (result + maxHeap.peek())%MOD;
            }
            sb.append(result).append("\n");
        }
        System.out.println(sb);
    }
}
