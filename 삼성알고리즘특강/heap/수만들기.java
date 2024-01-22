package 삼성알고리즘특강.heap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
처음에는 더하는 거만 고려하여 거스름돈 문제와 비슷하게 느껴짐 -> DP로 생각함 -> 하지만 곱하는 것도 고려해야함
우선순위 큐 생각
하지만 문제를 잘못읽어서 D=1인 경우를 고려하지 않음
 */

public class 수만들기 { // 거스름돈 문제와 비슷하게 느껴짐 -> DP, 하지만 곱하기 연산으로 인해 DP보다는 우선순위 큐
    static class Node implements Comparable<Node>{
        int count;
        int value;

        public Node(int count, int value) {
            this.count = count;
            this.value = value;
        }

        @Override
        public int compareTo(Node node){
            return this.count - node.count;
        }

    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        PriorityQueue<Node> pq;
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            int N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            List<Integer> arr = new ArrayList<>();

            for(int i = 0; i < N; i++){
                arr.add(Integer.parseInt(st.nextToken()));
            }
            int target = Integer.parseInt(br.readLine());
            int count = 0;
            pq = new PriorityQueue<>();
            pq.add(new Node(0, target));
            while(!pq.isEmpty()){
                Node n = pq.poll();  // 우선순위 큐에서 count가 가장 작은 Node를 꺼냄

                if(n.value == 0){ // 목표 달성 (X가 K와 같아짐)
                    count = n.count;
                    break;
                }
                pq.add(new Node(n.count+n.value, 0)); // 최악의 경우 1을 계속 더해야한다.

                for(int i = 0; i < N; i++){
                    pq.add(new Node(n.count+n.value%arr.get(i), n.value / arr.get(i)));
                }
            }
            sb.append(count).append("\n");
        }
        System.out.println(sb);
    }
}
