package 삼성알고리즘특강.unionFind;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class 하나로 {
    static int[] parent;
    static class Edge implements Comparable<Edge>{
        int from, to;
        long cost;

        Edge(int from, int to, long cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Long.compare(this.cost, o.cost);
        }
    }

    static class Point{
        long x;
        long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    static int find(int edge){
        if(parent[edge] == edge) return edge;
        return parent[edge] = find(parent[edge]); // 경로 압축
    }

    static boolean isConnected(int fromParent, int toParent){
        return fromParent == toParent;
    }
    static boolean merge(int from, int to){
        int fromParent = find(from);
        int toParent = find(to);
        if(!isConnected(fromParent, toParent)){
            parent[toParent] = fromParent;
            return true;
        }
        return false;

    }

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder(); // 결과를 하나의 문자열로 만들기 위해 선언
        StringTokenizer st;
        List<Edge> edgeList;
        Point[] point;
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");

            int islandNum = Integer.parseInt(br.readLine());
            point = new Point[islandNum];

            st = new StringTokenizer(br.readLine());

            // X기준으로 정렬하기
            for(int i = 0; i < islandNum; i++){
                point[i] = new Point(0, 0);
                point[i].x = Long.parseLong(st.nextToken()); // 이 부분을 int가 아닌 Long으로 받은 이유는 거리를 구할 때 제곱을 해주어야 하는데 이때 int로 한다면 오버플로우가 발생할 수 있기 때문이다.
            }

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < islandNum; i++){
                point[i].y = Long.parseLong(st.nextToken());
            }

            double E = Double.parseDouble(br.readLine());

            parent = new int[islandNum];
            // parent
            for(int i = 0; i < islandNum; i++){
                parent[i] = i;
            }
            // 모든 간선에 대한 값을 저장

            edgeList = new ArrayList<>();
            for(int i = 0; i < islandNum; i++){
                for(int j = i+1; j < islandNum; j++){
                    long edgeX = (point[i].x - point[j].x) * (point[i].x - point[j].x);
                    long edgeY = (point[i].y - point[j].y) * (point[i].y - point[j].y);
                    edgeList.add(new Edge(i, j, edgeX+ edgeY));
                }
            }


            //거리 별로 오름차순으로 정렬
            Collections.sort(edgeList);
            long answer = 0;
            for(Edge edge : edgeList){
                if(merge(edge.from, edge.to)){
                    answer += edge.cost;
                }
            }
            sb.append(Math.round(E*answer)).append("\n");

        }
        System.out.println(sb);
    }
}
