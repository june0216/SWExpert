package 삼성알고리즘특강.구간트리.백준;
/*
segmentTree연습 1과 비슷한 문제
https://www.acmicpc.net/problem/14428
 */

import java.io.*;
import java.util.*;

public class 수열과쿼리16 {
    public static final int UPDATE = 1;
    public static final int PRINT = 2;

    public static int[] tree;
    public static int[] number;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int totalNum = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();


        int k = (int) Math.ceil(Math.log(totalNum) / Math.log(2));
        int height = k + 1;
        int treeSize =(int) Math.pow(2, height);

        number = new int[totalNum];
        for(int i = 0; i < totalNum; i++){
            number[i] = Integer.parseInt(st.nextToken());
        }
        tree = new int[treeSize];
        int  queryNum = Integer.parseInt(br.readLine());
        makeTree(1, 0, totalNum-1);
        for(int i = 0; i < queryNum; i++){
            st = new StringTokenizer(br.readLine());
            int query = Integer.parseInt(st.nextToken());

            if(query == UPDATE){
                int index = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                update(0, totalNum-1, 1, index-1, value);

            }else if(query == PRINT){
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                sb.append(min(1, 0, totalNum-1, start-1, end-1));
                sb.append("\n");
            }

        }
        System.out.println(sb);

    }

    public static int makeTree(int node, int start, int end){
        if(start == end){
            return tree[node] = number[start];
        }else {
            int mid = (start + end)/2;
            return tree[node] = Math.min(makeTree(node*2,start, mid),
            makeTree(node*2+1, mid+1, end));
        }

    }

    public static int min(int node, int start, int end, int queryStart, int queryEnd) {
        if (start > queryEnd || end < queryStart) {
            return 0;
        }
        if (start >= queryStart && end <= queryEnd) {
            return tree[node];
        }
        int mid = (start + end) / 2;
        return Math.min(min(node * 2, start, mid, queryStart, queryEnd),
                min(node * 2 + 1, mid + 1, end, queryStart, queryEnd));

    }

    public static int update(int start, int end, int node, int index, int value){
        if(end == index && start == index){
            return tree[node] = value;
        }
        if(start > index || end < index) return tree[node];
        int mid = (start+end)/2;
        int left = update(start, mid, node*2, index, value);
        int right =update(mid+1, end, node*2, index, value);
        return getMinIndex(left, right);
    }

    public static int getMinIndex(int left, int right) {
        if(tree[left] == tree[right]) return getMin(left, right);
        else if(tree[left] < tree[right]) return left;
        else return right;
    }

    public static int getMin(int left, int right) {
        if(left < right) {
            return left;
        }else return right;
    }


}
