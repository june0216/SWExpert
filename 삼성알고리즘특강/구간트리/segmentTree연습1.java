package 삼성알고리즘특강.구간트리;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class segmentTree연습1 {
/*
어려웠던 점 1) 세그먼트 트리는 처음이라 구간합을 구하는 세그먼트 트리와 최소최대값을 구하는 세그먼트 트리는 다르게 구성해야하는지 모르고 시간이 좀 걸렸음
어려웠던 점 2) 재귀함수를 돌릴 때 파라미터로 tree를 넘김 -> update 부분에서 값이 바뀌면 즉각적으로 바뀌어야 하므로 적합하지 않음 -> 전역 변수로 선언하는 것으로 바꾸었더니 정답
 */
    public static final int CHANGE = 0;
    public static final int PRINT = 1;

    public static int[] maxTree;
    public static int[] minTree;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            int totalNum = Integer.parseInt(st.nextToken());
            int queryNum = Integer.parseInt(st.nextToken());
            int[] number = new int[totalNum];
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < totalNum; i++){
                number[i] = Integer.parseInt(st.nextToken());
            }
            int k = (int) Math.ceil(Math.log(number.length) / Math.log(2));
            int height = k + 1;
            int treeSize =(int) Math.pow(2, height);
            minTree = new int[treeSize];
            maxTree = new int[treeSize];
            initMinTree(number, 1, 0, number.length-1); // 루트노드 1로 하는 이유 -> 자식 노드 인덱스를 쉽게 구하기 위해
            initMaxTree(number, 1, 0, number.length-1);
            for(int j = 0; j < queryNum; j++){
                st = new StringTokenizer(br.readLine());
                int query = Integer.parseInt(st.nextToken());

                if(query == CHANGE){
                    int index = Integer.parseInt(st.nextToken());
                    int target = Integer.parseInt(st.nextToken());
                    update(1, 0, number.length-1,  index, target);
                }else if(query == PRINT){
                    // max(al, al+1, ⋯, ar-1) - min(al, al+1, ⋯, ar-1)를 출력
                    int start = Integer.parseInt(st.nextToken());
                    int end = Integer.parseInt(st.nextToken());
                    printDiff(sb,start, end, number.length );

                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }


    public static void printDiff(StringBuilder sb, int queryStart, int queryEnd, int nodeSize){
        sb.append(max( 1, 0, nodeSize-1, queryStart, queryEnd-1)- min( 1, 0, nodeSize-1, queryStart, queryEnd-1)).append(" ");
    }
    // 예시: 최대값 트리 초기화 함수
    public static int initMaxTree(int[] number,  int node, int start, int end) {
        if (start == end) {
            return maxTree[node] = number[start];
        }
        int mid = (start + end) / 2;
        initMaxTree(number, node * 2, start, mid);
        initMaxTree(number, node * 2 + 1, mid + 1, end);
        return maxTree[node] = Math.max(maxTree[node*2+1], maxTree[node*2]);
    }

    // 예시: 최소값 트리 초기화 함수
    public static int initMinTree(int[] number,  int node, int start, int end) {
        if (start == end) {
            return minTree[node] = number[start];
        }
        int mid = (start + end) / 2;
        initMinTree(number,  node * 2 + 1, mid + 1, end);
        initMinTree(number, node * 2, start, mid);
        return minTree[node] = Math.min(minTree[node*2], minTree[node*2+1]);
    }

    // 예시: 값 변경 함수
    public static void update( int node, int start, int end, int index, int newValue) {
        if (index < start || index > end) {
            return;
        }
        if (start == index && end == index) {
            maxTree[node] = newValue;
            minTree[node] = newValue;
            return;
        }
        int mid = (start + end) / 2;
        update( node * 2, start, mid, index, newValue);
        update(node * 2 + 1, mid + 1, end, index, newValue);
        maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
        minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
    }

    public static int max( int nodeIdx, int start, int end , int queryStart, int queryEnd){
        if (queryStart > end || queryEnd < start) { // 범위에 벗어난 경우{ // 쿼리 범위에 없으면 의미 없는 min 값을 반환
            return Integer.MIN_VALUE;
        }
        if(queryStart <= start && queryEnd >= end){ // 쿼리 범위 안에 있는 값이면 해당 값꺼내기
            return maxTree[nodeIdx];
        }
        int mid = ((start + end)/2);
        int leftMaxValue = max( nodeIdx*2, start, mid, queryStart, queryEnd);
        int rightMaxValue = max(nodeIdx*2+1, mid+1, end, queryStart, queryEnd);
        return Math.max(leftMaxValue, rightMaxValue);
    }

    public static int min(int nodeIdx, int start, int end, int queryStart, int queryEnd){
        if (queryStart > end || queryEnd < start) { // 범위에 벗어난 경우
            return Integer.MAX_VALUE;
        }

        if(queryStart <= start && queryEnd >= end){ // 쿼리 범위 안에 있는 값이면 해당 값꺼내기
            return minTree[nodeIdx];
        }
        int mid = ((start + end)/2);
        int leftMinValue = min(nodeIdx*2, start, mid, queryStart, queryEnd);
        int rightMinValue = min(nodeIdx*2+1, mid+1, end, queryStart, queryEnd);
        return Math.min(leftMinValue, rightMinValue);

    }



}
