
package 삼성알고리즘특강.그래프다익스트라;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/**
 * 여기서 경로가 중요
 * 처음엔 최적의 노드를 찾는 것으로 했는데 경로로 해야할듯
 * 경로 찾기(다익스트라) + 계산
 */


public class 가장짧은길청소하기 {

    static class Node implements Comparable<Node> {
        int num;
        long weight;
        boolean visited = false;

        public Node(int num, long weight) {
            this.num = num;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node node) {
            if(this.weight - node.weight < 0) return -1;
            return 1;
            // 여기서 원래 (int) this.weight-node.weight로 했다. -> 결과를 fail 왜 그럴까 고민하다가 weight가 long 타입이라 오버플로우가 될 수 있다는 것을 간과했던 것 같다.
        }
    }

    public static long[] minDis;
    public static int[] route;
    public static List<Node>[] nodes;
    public static int buildingNum;
    public static final long INF = Long.MAX_VALUE; // 최댓값

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            buildingNum = Integer.parseInt(st.nextToken());
            int roadNum = Integer.parseInt(st.nextToken());


            minDis = new long[buildingNum + 1];
            route = new int[buildingNum + 1];
            Arrays.fill(minDis, INF); // 최단 거리 저장용 배열
            Arrays.fill(route, -1); // 최단 거리를 갖는 경로 저장 배열

            nodes = new List[buildingNum+1];
            for (int i = 0; i <= buildingNum; i++) {
                nodes[i] = new ArrayList<>();
            }
            for (int i = 0; i < roadNum; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());
                nodes[a].add(new Node(b, weight));
                nodes[b].add(new Node(a, weight));
            }
            dijkstra();
            sb.append(calculateDis()).append("\n");

        }
        System.out.println(sb);
    }

    public static void dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[buildingNum + 1];
        pq.add(new Node(1,0));
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            if(visited[now.num]) continue;
            visited[now.num] = true; // 꺼낼 때 방문 처리
            for(Node next : nodes[now.num]){
                if(minDis[next.num] >= minDis[now.num]+next.weight){
                    minDis[next.num] = minDis[now.num]+next.weight; //거리 업데이트
                    if(route[next.num] == -1){ // 처음 방문한 경우라면 바로 루트 업데이트
                        route[next.num] = now.num;
                    }
                    else{ // 재방문했을 경우
                        long currentDist = findDist(next.num, route[next.num]); // 지금 연결 가중치 구하기
                        long newDist = findDist(now.num,next.num ); // 새로운 연결 가중치 구하기
                        if(currentDist > newDist) route[next.num] = now.num; // 새로운 연결 가중치가 더 작으면 업데이트
                        else continue;
                    }
                    pq.add(new Node(next.num, minDis[next.num]));
                }
            }

        }
    }

    // 거리 계산하기
    public static long calculateDis(){
        int idx = 1;
        long answer = 0;
        while (idx <= buildingNum){ // 건물 하나씩 돌면서 해당 건물까지의 거리를 합치기
            int nodeB = route[idx]; // 자기 자신과 연결된 최단 건물
            Node road = findRoad(idx,nodeB);; // 연결된 건물을 찾고

            if(road != null && !road.visited) { // 연결된 건물이 있으며 아직 결과 계산에 반영이 안된 경우
                road.visited = true; // 일단 계산했다고 표시하고
                Node reverseNode = findRoad(road.num,idx);
                reverseNode.visited=true; //양측 다 계산했다고 표시하고
                answer += road.weight; // 결과에 반영
            }
            idx +=1; // 건물 하나 계산 처리했다고 표시
        }
        return answer;

    }


    // 두 노드에 대한 길 찾기
    public static Node findRoad(int nodeA, int nodeB){
        Node result = null;
        List<Node> distList=nodes[nodeA];
        for(Node node : distList){
            if(node.num == nodeB){
                result = node;
                break;
            }
        }
        return result;
    }

    //두 노드에 대한 길을 찾고 가중치 찾기
    public static long findDist(int nodeA, int nodeB){
        List<Node> distList=nodes[nodeA];
        for(Node node : distList){
            if(node.num == nodeB){
                return node.weight;
            }
        }
        return INF;
    }



}
