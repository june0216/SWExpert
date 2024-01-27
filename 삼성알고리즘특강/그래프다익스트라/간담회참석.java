package 삼성알고리즘특강.그래프다익스트라;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 간담회참석 {
/*
느낀점 : 처음 시도 했을 때 글을 잘못 이해한 부분이 "일반 통행" 단어를 보고 길이 주어지면 상황에 맞게 방향을 선택하고 선택한 방향대로만 갈 수 있다고 생각했다.
하지만 입력 부분을 다시 읽어보면 "강의실이 시작되는"이라는 점에서 방향이 정해져있다는 것을 알 수 있다.

적용점:  a -> target -> a 이 과정을 거쳐야 하는 것이다. target -> a 이 방향은 다익스트라를 돌리면 각 노드에 대해 최단거리를 구하므로 바로 n logn으로 구할 수 있다.
여기서 a-> target 부분도 구해주어야 하므로 다익스트라로 얻은 최단 간선을 제외한 모든 노드에 대해 각각 다익스트라로 구해야 해야한다. 따라서 arget -> a에 대한 다익스트라를 적용한 시간복잡도는  O(E log E)이고
반대 방향에 대해서 나머지 노드에 대해 다익스트라를 하는 시간복잡도는  O(V * E log E) 이므로 TLE 발생
=> 따라서  애초에 노드 정보를 반대로 하여 다익스트라를 적용하면 된다.


 */
    static boolean[] visited;
    static int totalNum;
    static int roadNum;

    static int meetRoom;

    static int[] startDistance;
    static int[] backDistance;

    static StringBuilder sb;
    static List<Node>[] startNodeList;
    static List<Node>[] backNodeList;
    final static int INF = Integer.MAX_VALUE;


    static class Node implements Comparable<Node>{
        private int from;
        private int time;

        public Node(int from, int time) {
            this.from = from;
            this.time = time;
        }

        @Override
        public int compareTo(Node node){
            return this.time-node.time;
        }
    }

    public static void main(String[] args) throws Exception {
        sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());

            totalNum = Integer.parseInt(st.nextToken());
            roadNum = Integer.parseInt(st.nextToken());
            meetRoom = Integer.parseInt(st.nextToken());
            startNodeList = new List[totalNum+1];
            backNodeList = new List[totalNum+1];
            for(int i =0 ; i <= totalNum; i++){
                startNodeList[i] = new ArrayList<>();
                backNodeList[i] = new ArrayList<>();
            }
            startNodeList[0] = new ArrayList<>();
            backNodeList[0] = new ArrayList<>();
            for(int i = 1; i<= roadNum; i++){
                st = new StringTokenizer(br.readLine());
                int startNode = Integer.parseInt(st.nextToken());
                int endNode = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());
                startNodeList[startNode].add(new Node(endNode, time)); // target -> node 의 정보
                backNodeList[endNode].add(new Node(startNode, time)); // (시간복잡도를 고려하여 간선 정보를 반대로 하고 다익스트라를 돌리기 위함 )a -> target 의 정보
            }

            startDistance = new int[totalNum+1]; // 회의실 돌아가는 거리
            backDistance = new int[totalNum+1]; // 회의실 가는 거리
            Arrays.fill(startDistance, INF);
            Arrays.fill(backDistance, INF);



            dijkstra(startNodeList, startDistance);
            dijkstra(backNodeList, backDistance);
            int result = 0;
            for(int i = 0 ; i < startDistance.length; i++){
                int start = startDistance[i];
                int back = backDistance[i];
                if( start == INF || back == INF) continue; // 정점이 연결되지 않은 경우 초기값 그대로이므로 pass
                result = Math.max(start+back, result);

            }
            sb.append(result).append("\n");

        }
        System.out.println(sb);
    }

    public static void dijkstra(List<Node>[] nodes, int[] distance) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(meetRoom, 0)); // 역으로 도착인 회의실 부터 각 노드별로 최단 거리 구하기
        visited = new boolean[totalNum + 1];
        distance[meetRoom] = 0;
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            if (visited[now.from]) continue;
            visited[now.from] = true; // pop할 때 방문 처리
            for (Node next : nodes[now.from]) {
                if (distance[next.from] > next.time + distance[now.from]) {
                    distance[next.from] = next.time + distance[now.from];
                    pq.add(new Node(next.from, distance[next.from]));
                }
            }
        }
    }

}
