package 삼성알고리즘특강.구간트리;

import java.io.InputStreamReader;
import java.io.*;
import java.util.*;

/**
 * 범위를 만족하는 i의 개수가 홀수일 경우 al - al+1 + al+2  - ... - ar-2 + ar-1 를 출력하고
 *                    범위를 만족하는 i의 개수가 짝수일 경우 al - al+1 + al+2  - ... + ar-2 - ar-1 를 출력하라.
 *
 * 어려웠던 점 : sum 타입을 int로 했는데 하나의 값이 억이므로 합을 계산할 때 오버 플로우 발생 가능하므로 트리를 int에서 long으로 변경
 */

public class segmentTree연습2 {

    public static final int CHANGE = 0;
    public static final int PRINT = 1;
    public static long[] tree;
    public static long[] secTree;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++){
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            int arrNum = Integer.parseInt(st.nextToken());
            int queryNum = Integer.parseInt(st.nextToken());
            int[] number = new int[arrNum];
            int[] secNumber = new int[arrNum];
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < arrNum; i++){
                int num = Integer.parseInt(st.nextToken());
                if(i%2 != 0){// 혹수일 경우
                    number[i] = (-1)*num;
                    secNumber[i] = num;
                }else{
                    number[i] = num;
                    secNumber[i] = (-1)*num;
                }

            }

            int k = (int) Math.ceil(Math.log(number.length) / Math.log(2));
            int height = k + 1;
            int treeSize =(int) Math.pow(2, height);
            tree = new long[treeSize];
            secTree = new long[treeSize];
            makeTree(number, 1, 0, number.length-1);
            makeSecTree(secNumber, 1, 0, number.length-1);
            while(queryNum-- > 0){
                st = new StringTokenizer(br.readLine());
                int query = Integer.parseInt(st.nextToken());
                if(query == CHANGE){
                    int index = Integer.parseInt(st.nextToken());
                    int newValue = Integer.parseInt(st.nextToken());
                    change(1, 0, number.length-1,index, newValue);
                }
                else if(query == PRINT)
                {
                    int start = Integer.parseInt(st.nextToken());
                    int end = Integer.parseInt(st.nextToken());
                    printSum(sb, number.length-1, start, end-1);
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    // 첫 인덱스가 +로 시작하는 합 트리 생성
    public static void makeTree(int[] number, int node,int start, int end){
        if(start == end){
            tree[node] = number[start];
            return;
        }else{
            int mid = (start + end)/2;
            makeTree(number, node*2, start, mid);
            makeTree(number, node*2+1, mid+1, end);

            tree[node] = tree[node*2] + tree[node*2+1];

        }
    }

    // 첫 인덱스가 - 으로 시작하는 합 트리 생성
    public static void makeSecTree(int[] secNumber, int node,int start, int end){
        if(start == end){
            secTree[node] = secNumber[start];
            return;
        }else{
            int mid = (start + end)/2;
            makeSecTree(secNumber, node*2, start, mid);
            makeSecTree(secNumber, node*2+1, mid+1, end);

            secTree[node] = secTree[node*2] + secTree[node*2+1];

        }
    }

    public static void change(int node, int start, int end, int index, int newValue){
        if(start == index && end == index){
            if(index%2 !=  0){
                tree[node] = (-1)* newValue;
                secTree[node] = newValue;
            }else{
                tree[node] = newValue;
                secTree[node] = (-1)* newValue;

            }
            return;
        }
        if(start > index || end < index) return;
        int mid = (start+end)/2;
        change(node*2, start, mid, index, newValue);
        change(node*2+1, mid+1, end, index, newValue);
        tree[node] = tree[node*2] + tree[node*2+1];
        secTree[node] = secTree[node*2] + secTree[node*2+1];
    }

    public static void printSum(StringBuilder sb, int arrSize, int queryStart, int queryEnd){

        if(queryStart%2 != 0){
            sb.append(findSum(secTree, 1, 0, arrSize, queryStart, queryEnd)).append(" ");
        }else{
            sb.append(findSum(tree, 1, 0, arrSize, queryStart, queryEnd)).append(" ");
        }

    }
    public static long findSum(long[] target, int node, int start, int end, int queryStart, int queryEnd){
        //범위 안에 있으면 구간합 구하는 대상이 된다.
        if(start >= queryStart && end <= queryEnd){
            return target[node];
        }
        //  범위 밖에 있으면 구간합 대상이 아님
        if(start > queryEnd || queryStart > end){
            return 0;
        }
        int mid = (start + end)/2;
        long rightSum = findSum(target, node*2, start, mid, queryStart, queryEnd);
        long leftSum = findSum(target, node*2+1, mid+1, end, queryStart, queryEnd);
        return rightSum + leftSum;
    }
}
