package 삼성알고리즘특강.pro_국가행정;

import java.util.PriorityQueue;

/**
 * 정말 정말 오래 걸렸던 문제
 * 노드와 노드사이의 거리를 각각 관리해주어야 하기 때문에 살짝 헷갈렸다.
 */
class UserSolution
{

    class Node{
        int id;
        int peopleNum;

        public Node(int id, int peopleNum) {
            this.id = id;
            this.peopleNum = peopleNum;

        }
    }
    class Road implements Comparable<Road>{
        public int roadNum;
        public Node from;
        public Node to;
        public int roadCnt;
        public int time;
        public Road(Node from, Node to, int roadNum, int roadCnt, int time){
            this.from = from;
            this.to = to;
            this.roadNum = roadNum;
            this.roadCnt = roadCnt;
            this.time = time;
        }

        @Override
        public int compareTo(Road road){
            if(this.time == road.time){
                return this.roadNum - road.roadNum;
            }
            return road.time - this.time;
        }



    }

    public static Node[] nodeList;
    public PriorityQueue<Road> roadPq;

    public int[] tree;
    public int[] population;


    /**
     * N개 도시의 고유번호는 왼쪽에서부터 0 ~ (N – 1)이며, 그 순서대로 도시의 인구가 주어진다.
     * @param N
     * @param mPopulation
     */
    void init(int N, int mPopulation[])
    {
        population = mPopulation;
        nodeList = new Node[N];
        roadPq = new PriorityQueue<>();
        tree = new int[N*4];

        for(int i = 0 ; i < N-1; i++){
            if(i == 0) nodeList[i] = new Node(i, 0);
            int time = mPopulation[i]+ mPopulation[i+1];
            nodeList[i+1] = new Node(i+1, time);
            roadPq.add(new Road(nodeList[i], nodeList[i+1], i+1, 1,time));



        }


        makeTree(1, 0, N-1);

    }
    void makeTree(int node, int start, int end){
        if(start == end){
            tree[node] = nodeList[start].peopleNum;
            return;
        }

            int mid = (start+end)/2;
            makeTree(node*2, start, mid);
            makeTree(node*2+1, mid+1, end);
            tree[node] = tree[node*2] + tree[node*2+1];

    }

    void update(int node, int start, int end, int fromId, int toId, int value){
            if(start > toId || end < toId){
                return;
            }
            if(start == end){
                tree[node] = value;
                return;
            }

                int mid = (start+end)/2;
                update(node*2, start, mid, fromId, toId, value);
                update(node*2+1, mid+1, end, fromId, toId, value);
                tree[node] = tree[node*2] + tree[node*2+1];

    }


    /**
     * 이동 시간이 가장 긴 도로를 한 차선 확장하기를 M번 반복
     * @param M 도로 확장의 횟수
     * @return 마지막으로 확장한 도로의 확장 후 이동 시간.
     */
    int expand(int M)
    {
        int result = 0;
        while(M-- > 0){
            if(!roadPq.isEmpty()){
                Road road = roadPq.poll();
                road.roadCnt++;
                result= ( population[road.from.id]+ population[road.to.id])/road.roadCnt;
                road.time = result;
                roadPq.add(road);
                update(1, 0, nodeList.length-1, road.from.id, road.to.id, result);
            }
        }

        return result;
    }


    int getSum(int node, int start, int end, int queryStart, int queryEnd){
        if(start >= queryStart && end <= queryEnd){
            return tree[node];
        }
        if(start > queryEnd || end < queryStart){
            return 0;
        }
        int mid = (start + end) /2;
        int left = getSum(node*2, start, mid, queryStart, queryEnd);
        int right = getSum(node*2+1, mid+1, end, queryStart, queryEnd);
        return left + right;

    }

    /**
     * 고유번호가 mFrom인 도시에서 고유번호가 mTo인 도시까지 이동하는 데 걸리는 시간
     * @param mFrom
     * @param mTo
     * @return 이동 시간이 가장 긴 도로를 한 차선 확장하기를 M번 반복
     */
    int calculate(int mFrom, int mTo)
    {
        if(mFrom> mTo){
            int temp = mFrom;
            mFrom = mTo;
            mTo = temp;
        }
        int result = getSum(1, 0, nodeList.length-1, mFrom+1, mTo);
        return result;
    }

    /**
     * mFrom부터 mTo까지의 도시에 대해서 K개의 선거구를 결정한다.
     *     1. 하나의 도시는 여러 선거구에 분할되지 않는다.
     *
     *     2. 하나의 선거구에 포함되는 도시들은 서로 연속한다.
     *
     *     3. 가장 인구가 많은 선거구의 인구를 최소로 한다.
     * 가장 인구 수가 많은 선거구의 인구 수를 반환한다.
     *
     * @param mFrom
     * @param mTo
     * @param K
     * @return
     */
    int divide(int mFrom, int mTo, int K)
    {
        int start = 1;
        int end = Integer.MAX_VALUE;
        while(start < end){
            int totalNum = 0;

            int mid = ((end+start)/2);
            int i = mFrom;
            while(i<=mTo && totalNum <= K){
                int peopleNum = 0;

                int j = i;

                while(j <= mTo && peopleNum + population[j] <= mid){
                    peopleNum += population[j++];
                }
                totalNum++;
                i = j; // 다음 선거구
            }
            if(totalNum <= K){
                end = mid;
            }else{
                start = mid+1;
            }

        }

        return end;
    }
}
