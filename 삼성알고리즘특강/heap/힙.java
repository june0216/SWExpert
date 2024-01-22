package 삼성알고리즘특강.heap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 힙 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        PriorityQueue<Integer> pq;
        for(int testCase = 1; testCase <= T; testCase++){
            sb.append("#").append(testCase).append(" ");
            int calculateTimes = Integer.parseInt(br.readLine());
            pq = new PriorityQueue<>();
            while(calculateTimes-- > 0){
                st = new StringTokenizer(br.readLine());
                int command = Integer.parseInt(st.nextToken());
                if(command == 1){
                    int number = Integer.parseInt(st.nextToken());
                    // 힙에 넣기
                    pq.add(-1* number);
                }
                else if(command == 2){
                    // 힙 삭제
                    if(pq.isEmpty()) sb.append(-1).append(" ");
                    else sb.append(-1 * pq.poll()).append(" ");
                }
            }
            sb.append("\n");

        }
        System.out.println(sb);
    }
}
