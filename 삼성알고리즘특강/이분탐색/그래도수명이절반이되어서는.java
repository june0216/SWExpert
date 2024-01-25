package 삼성알고리즘특강.이분탐색;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;

import java.util.StringTokenizer;

/**
 *  모든 경우를 따져서 최적의 해를 구하는 것은 어렵다.
 *  -> 반대로 해를 x라고 생각하고 x를 만족하는 경우가 있는지 확인해보자
 *  최대 값을 하나씩 정해서 찾은 다음 만족하면 그거보다 더 큰 값은 고려하지 않아도 된다.
 */

/**
 * 처음에 덩어리의 순서를 바꿀  수 있다고 생각해서 더 어렵게 풀었는데 문제를 꼼꼼하게 보자!
 */

public class 그래도수명이절반이되어서는 {

    public static List<Integer> dataSet;
    public static List<Integer> block;
    private static int MAX_SIZE = 200000;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            int blockNum = Integer.parseInt(st.nextToken());
            int dataSetNum = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            block = new ArrayList<>();
            for(int i = 0; i < blockNum; i++) {
                int num = Integer.parseInt(st.nextToken());
                block.add(num);

            }
            dataSet = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < dataSetNum; i++){
                dataSet.add(Integer.parseInt(st.nextToken()));
            }

            sb.append(binarySearch()).append("\n");

        }
        System.out.println(sb);
    }

    public static long binarySearch(){
        int start = 1;
        int end = MAX_SIZE;
        long answer = -1;

        while(start <= end){
            int mid = start+ ((end-start)/2);
            if(isAvailableCase(mid)){
                answer = mid;
                end = mid-1;
            }else{
                start = mid+1;
            }
        }
        return answer;

    }

    public static boolean isAvailableCase(int mid){
        int dataIndex = 0;

        for(int i = 0; i < block.size(); i++){ // block 전체를 탐색한다.
            if(block.get(i) > mid) continue;
            int searchEnd = i+dataSet.get(dataIndex);
            for(int j = i; j < searchEnd && j < block.size(); j++){
                if(block.get(j) > mid){ // 블록의 weave level 조건을 만족하지 않는 경우
                    i=j; // 만족하지 않는 부분 이후부터 탐색할 수 있게 조절
                    break;
                }

                if(j == searchEnd-1){ // 덩어리가 다 조건에 맞는 경우
                    i = j;
                    dataIndex++;
                }

            }
            if(dataIndex == dataSet.size()){ // 모든 덩어리가 다 조건을 만족하는 경우
                return true;
            }
        }
        return false;

    }


}
