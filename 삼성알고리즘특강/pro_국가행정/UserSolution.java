package 삼성알고리즘특강.pro_국가행정;

import java.util.PriorityQueue;

class UserSolution
{

    class Node{
        int id;
        int peopleNum;
        int gu;

        public Node(int id, int peopleNum) {
            this.id = id;
            this.peopleNum = peopleNum;

        }
    }
    class Road implements Comparable<Road>{
        public int roadNum;
        public Node from;
        public Node to;
        public int time;
        public Road(Node from, Node to, int roadNum, int time){
            this.roadNum = roadNum;
            this.time = time;
        }

        @Override
        public int compareTo(Road road){
            if(this.time == road.time){
                return this.roadNum - road.roadNum;
            }
            return this.time - road.time;
        }



    }

    public static Node[] nodeList;
    public PriorityQueue<Road> roadPq;

    public int[] tree;


    public int getMoveTime(Road road){
        return (road.from.peopleNum + road.to.peopleNum)/road.roadNum;
    }

    /**
     * N개 도시의 고유번호는 왼쪽에서부터 0 ~ (N – 1)이며, 그 순서대로 도시의 인구가 주어진다.
     * @param N
     * @param mPopulation
     */
    void init(int N, int mPopulation[])
    {
        nodeList = new Node[N];
        roadPq = new PriorityQueue<>();
        for(int i = 0 ; i < N; i++){
            nodeList[i] = new Node(i, mPopulation[i]);
            if(i > 0){
                roadPq.add(new Road(nodeList[i-1], nodeList[i], 1, nodeList[i-1].peopleNum + nodeList[i].peopleNum));
            }
        }
        tree = new int[N*4];
        makeTree(1, 0, N-2);

    }
    int makeTree(int node, int start, int end){
        if(start == end){
            return tree[node] = 1;
        }
        else{
            int mid = (start+end)/2;
            makeTree(node*2, 0, mid);
            makeTree(node*2+1, mid+1, end);
            return tree[node] = tree[node*2] + tree[node*2+1];
        }
    }

    void update(int node, int start, int end, Road road){
            if(start > road.to.id || end < road.to.id){
                return;
            }
            if(road.to.id == start && road.to.id == end){
                tree[node] = road.time;
            }
            else{
                int mid = (start+end)/2;
                update(node*2, start, mid, road);
                update(node*2+1, mid+1, end, road);
                tree[node] = tree[node*2] + tree[node*2+1];
            }
    }


    /**
     * 이동 시간이 가장 긴 도로를 한 차선 확장하기를 M번 반복
     * @param M 도로 확장의 횟수
     * @return 마지막으로 확장한 도로의 확장 후 이동 시간.
     */
    int expand(int M)
    {
        Road road = roadPq.poll();
        while(M-- >1){
            if(road.time == roadPq.peek().time){
                road = roadPq.poll();
            }
            road.roadNum +=1;
            road.time = getMoveTime(road);
            update(1, 0, nodeList.length-2, road);
        }

        return road.time;
    }


    int getSum(int node, int start, int end, int queryStart, int queryEnd){
        if(start >= queryStart && end <= queryEnd){
            return tree[node];
        }
        if(start > queryEnd || end < queryStart){
            return 0;
        }
        int mid = (start + end) /2;
        return getSum(node*2, start, mid, queryStart, queryEnd)+
        getSum(node*2+1, mid+1, end, queryStart, queryEnd);

    }

    /**
     * 고유번호가 mFrom인 도시에서 고유번호가 mTo인 도시까지 이동하는 데 걸리는 시간
     * @param mFrom
     * @param mTo
     * @return 이동 시간이 가장 긴 도로를 한 차선 확장하기를 M번 반복
     */
    int calculate(int mFrom, int mTo)
    {
        return getSum(1, 0, tree.length-1, mFrom+1, mTo);
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
        int start = 0;
        int end = Integer.MAX_VALUE;
        while(start < end){
            int totalNum = 0;

            int mid = start + ((end-start)/2);
            int i = mFrom;
            while(i<=mTo && totalNum <= K){
                int peopleNum = 0;
                int j = i;
                while(j <= mTo && (nodeList[j].peopleNum + totalNum) <= mid){
                    peopleNum += nodeList[j++].peopleNum;
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
