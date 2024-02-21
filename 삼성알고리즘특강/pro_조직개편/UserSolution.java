package 삼성알고리즘특강.pro_조직개편;

import com.sun.source.tree.Tree;
import java.util.*;

class UserSolution {

    class TreeNode{
        public TreeNode parent;
        public List<TreeNode> childNodList = new ArrayList<>();
        public int peopleNum;
        public int totalSum;
        public int idx;
        public TreeNode(int idx, TreeNode node, int peopleNum){
            this.idx = idx;
            this.parent = node;
            this.peopleNum = peopleNum;
            this.totalSum = peopleNum;
        }

    }

    public HashMap<Integer, TreeNode> hashMap;
    public int rootIdx;
    public void init(int mId, int mNum) {
        hashMap = new HashMap<>();
        hashMap.put(mId, new TreeNode(mId, null, mNum));
        rootIdx = mId;
    }

    public void updateSum(TreeNode parent, int changeValue){
        while(parent != null){
            parent.totalSum += changeValue;
            parent = parent.parent;
        }
    }

    /**
     * mId 부서를 mParent 부서의 하위 부서로 추가
     * @param mId
     * @param mNum
     * @param mParent
     * @return 추가에 성공할 경우, mParent 부서를 포함하여 그 아래 모든 부서의 인원 수 합을 반환
     */
    public int add(int mId, int mNum, int mParent) {
        TreeNode parent = hashMap.get(mParent);
        if(parent.childNodList.size() == 2) return -1;
        TreeNode newTreeNode = new TreeNode(mId, parent, mNum);
        parent.childNodList.add(newTreeNode);
        hashMap.put(mId, newTreeNode);
        updateSum(parent, mNum);
        return parent.totalSum;
    }

    public void removeChild(TreeNode node){
        for(TreeNode child: node.childNodList){
            removeChild(child);
        }
        hashMap.remove(node.idx);
    }

    /**
     * ID가 mId인 부서를 삭제한다. mId 부서 아래 모든 부서도 함께 삭제된다.
     * @param mId
     * @return
     */
    public int remove(int mId) {
        if(!hashMap.containsKey(mId)) return -1;
        TreeNode removedNode = hashMap.get(mId);
        int returnValue = removedNode.totalSum;
        removedNode.parent.childNodList.remove(removedNode);
        removeChild(removedNode);
        updateSum(removedNode.parent, -returnValue);
/*        removedNode.childNodList.clear();
        hashMap.remove(mId);*/
        return returnValue;
    }

    public int divide(TreeNode node){
        if(node.peopleNum > groupLimit || totalGroup > targetGroupNum ){
            totalGroup = Integer.MAX_VALUE;
            return 0;
        }
        int rightNum = 0;
        int leftNum = 0;
        // case0) 각각 둘다 target보다 큰 경우

        if(node.childNodList.size() ==1) leftNum = divide(node.childNodList.get(0));
        if(node.childNodList.size() ==2) rightNum = divide(node.childNodList.get(1));
        if(totalGroup <= targetGroupNum){
            if(node.peopleNum + leftNum > groupLimit){
                totalGroup++; // 하나의 노드만 그룹이 된다.
                leftNum = 0; // 왼쪽은 포함될 수 없으니 0으로
            }
            if(node.peopleNum + rightNum > groupLimit){
                totalGroup++; // 하나의 노드만 그룹이 된다.
                rightNum = 0; // 왼쪽은 포함될 수 없으니 0으로
            }
            if(node.peopleNum + rightNum + leftNum > groupLimit){
                totalGroup++;
                if(leftNum > rightNum) leftNum = 0;
                else rightNum = 0;
            }
            return node.peopleNum + leftNum + rightNum;


        }

        return 0;

    }

    int totalGroup = 0;
    int targetGroupNum = 0;
    int groupLimit = 0;
    public int reorganize(int M, int K) {
        targetGroupNum = M;
        groupLimit = K;
        TreeNode root = hashMap.get(rootIdx);
        divide(root);
        if(totalGroup <= targetGroupNum) return 1; // 가능한 경우
        return 0;
    }
}
