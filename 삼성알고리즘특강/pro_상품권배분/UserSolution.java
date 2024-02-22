package 삼성알고리즘특강.pro_상품권배분;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class UserSolution {
    class Tree{
        Tree parent;
        int idx;
        int total;
        int sum;
        List<Tree> subTree = new ArrayList<>();
        public Tree(int idx, Tree parent, int total){
            this.idx = idx;
            this.parent = parent;
            this.total = total;
            this.sum = total;
        }

    }
    public List<Integer> parent;
    public HashMap<Integer, Tree> treeMap;
    public void init(int N, int mId[], int mNum[]) {
        parent = new ArrayList<>();
        treeMap = new HashMap<>();
        for(int i = 0;  i < N; i++){
            treeMap.put(mId[i], new Tree(mId[i], null, mNum[i]));
            parent.add(mId[i]);
        }
    }

    public void updateSum(Tree tree, int change){ // 부모에게 알리기
        while(tree != null){
            tree.sum += change;
            tree = tree.parent;
        }
    }

    /**
     * mId 부서를 mParent 부서의 하위 부서로 추가
     * @param mId
     * @param mNum
     * @param mParent
     * @return 추가에 성공할 경우, mParent 부서를 포함하여 그 아래 모든 부서의 인원 수 합을 반환한다. 다시 말해, mParent가 루트 노드인 서브 트리의 인원 수 합을 반환한다.
     *  추가에 실패할 경우, -1을 반환한다.
     */

    public int add(int mId, int mNum, int mParent) {
        Tree parent = treeMap.get(mParent);
        if(parent.subTree.size() >= 3){
            return -1;
        }
        Tree newTree = new Tree(mId, parent, mNum);
        parent.subTree.add(newTree);
        treeMap.put(mId, newTree);
        updateSum(parent, mNum);

        return parent.sum;
    }

    public void removeSum(Tree node){
        for(Tree child : node.subTree){
            removeSum(child);
        }
        treeMap.remove(node.idx);
    }

    public int remove(int mId) {
        if(!treeMap.containsKey(mId)) return -1;
        Tree removedTree = treeMap.get(mId);
        removeSum(removedTree);
        removedTree.parent.subTree.remove(removedTree);
        int result = removedTree.sum;

        updateSum(removedTree.parent, -removedTree.sum);
        return result;
    }

    public int checking(int K, int max){
        int start = 0;

        int end = Math.min(K, max);
        while(start < end){
            int mid = (start + end+1)/2;
            int distributeNum = 0;
            for(int parentId : parent){
                distributeNum += Math.min(mid, treeMap.get(parentId).sum);
            }
            if(distributeNum > K){
                end = mid-1;
            }else{
                start = mid;
            }
        }

        return start;

    }

    public int distribute(int K) {
        int max = 0;
        int total = 0;
        for(int parentId : parent){
            int parentSum = treeMap.get(parentId).sum;
            if(max < parentSum) max = parentSum;
            total +=  parentSum;
        }
        if(total <= K) return max;
        return checking(K, max);
    }
}
