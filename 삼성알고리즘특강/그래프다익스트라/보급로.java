package 삼성알고리즘특강.그래프다익스트라;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 가중치 있는 최단 경로 -> 다익스트라
 */

public class 보급로 {
    public static int[][] route;
    public static int N;
    public static int[] dx = {-1,1, 0, 0};
    public static int[] dy = {0, 0, -1, 1};
    public static boolean[][] visited;

    static class Node implements Comparable<Node>{
        int x;
        int y;
        int weight;

        public Node(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node node){
            return this.weight - node.weight; // 가중치에 따른 우선순위
        }

    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            N = Integer.parseInt(br.readLine());
            route = new int[N][N];

            for(int i = 0; i < N; i++){
                String input = br.readLine();
                for(int j = 0; j < N; j++){
                    route[i][j] = input.charAt(j) - '0';
                }
            }
            sb.append(dijkstra()).append("\n");
        }
        System.out.println(sb);

    }
    public static int dijkstra(){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, 0, route[0][0]));
        visited = new boolean[N][N];
        visited[0][0] = true;
        while(!pq.isEmpty()){
            Node now = pq.poll();
            if(now.x == N-1 && now.y == N-1){
                return now.weight;
            }
            for(int i = 0; i < 4; i++){
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];
                if(0 <= ny && ny < N && 0 <= nx && nx < N && !visited[ny][nx]){
                    pq.add(new Node(nx, ny, now.weight+route[ny][nx]));
                    visited[ny][nx] = true;
                }
            }
        }
        return -1;
    }

}
