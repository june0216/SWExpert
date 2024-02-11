package 삼성알고리즘특강.구간트리.출근길라디오;

class UserSolution
{
    class Road{
        public int value;
        public int type;

        public Road(int value, int type) {
            this.value = value;
            this.type = type;
        }
    }

    public Road[] roadList;
    public int[] tree;
    public int[] typeList;
    /**
     *
     * @param N 지점의 개수 , (간선== N-1)
     * @param M 도로 종류의 개수
     * @param mType 각 종류
     * @param mTime 각 시간
     */
    void init(int N, int M, int mType[], int mTime[])
    {
        roadList = new Road[N-1];
        typeList = new int[M];
        for(int i = 0 ; i < M; i++){
            typeList[i] = i;
        }
        int k = (int) Math.ceil(Math.log(N-1) / Math.log(2));
        int height = k + 1;
        int treeSize =(int) Math.pow(2, height);
        tree = new int[treeSize];
        for(int i = 0 ; i < N-1; i++){
            roadList[i] = new Road(mTime[i], mType[i]);
        }
        makeTree(1, 0, N-2);

    }

    void makeTree(int node, int start, int end){
        if(start == end){
            tree[node] = roadList[start].value;
        }else{
            int mid = (start+end)/2;
            makeTree(node*2, start, mid);
            makeTree(node*2+1, mid+1, end);
            tree[node] = tree[node*2]+ tree[node*2+1];
        }
    }

    void destroy()
    {

    }
    void updateTree(int node, int start, int end, int index, int newValue){
        if(start > index || end < index){
            return;
        }
        if(start == index && end == index){
            tree[node] = newValue;
            roadList[index].value = newValue;
        }
        int mid = (start + end) /2;
        updateTree(node*2, start, mid, index, newValue);
        updateTree(node*2+1, mid+1, end, index, newValue);
        tree[node] = tree[node*2] + tree[node*2+1];
    }
    void update(int mID, int mNewTime)
    {
        updateTree(1, 0, roadList.length-1, mID, mNewTime);
    }

    int updateByType(int mTypeID, int mRatio256)
    {
        for(int i = 0 ; i < roadList.length-1; i++){
            if(roadList[i].type == typeList[mTypeID]){
                update(i, roadList[i].value*mRatio256/256);
            }
        }
        return getSum(1, 0, roadList.length-1, 0, roadList.length-1);
    }
    int getSum(int node, int start, int end, int queryStart, int queryEnd){
        if(start > queryEnd || end < queryStart){
            return 0;
        }
        if(start>= queryStart && end <= queryEnd){
            return tree[node];
        }
        int mid = (start+end)/2;
        return getSum(node*2 , start, mid, queryStart, queryEnd)+
        getSum(node*2+1, mid+1, end, queryStart, queryEnd);
    }

    int calculate(int mA, int mB)
    {
        if(mA > mB) {
            int temp = mA;
            mA = mB;
            mB = temp;
        }
        return getSum(1, 0, roadList.length-1, mA, mB-1);
    }
}
