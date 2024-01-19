package 삼성알고리즘특강.tree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 공통조상 {
    static class Node{
        int index;
        Node left;
        Node right;
        Node parent;
        public Node(int index, Node parent, Node leftNode, Node rightNode){
            this.index = index;
            this.parent = parent;
            this.left = leftNode;
            this.right = rightNode;
        }
    }

    // 모든 노드를 방문하면서 서브 트리의 크기를 반환한다.
    private static int findSubtreeSize(Node node) {
        if (node == null) {
            return 0;
        }
        // 아래로 내려가면서 하나씩
        return 1 + findSubtreeSize(node.left) + findSubtreeSize(node.right);
    }

    // 조상들을 하나의 리스트 안에 넣기
    private static List<Integer> findParents(Node node) {
        List<Integer> ancestors = new ArrayList<>();
        while (node != null) { // 루트 노드로 가기 전까지 반복
            ancestors.add(node.index); // 가까운 조상이 먼저 나온다.
            node = node.parent; // 그 다음 조상을 넣는다.
        }
        return ancestors;
    }

    //공통 조상을 찾는 메서드
    public static int findCommonParents(List<Integer> firstNode, List<Integer> secondNode){
        int commonParentIndex  = -1;
        for(int i : firstNode){
            if(secondNode.contains(i)){
                commonParentIndex = i; // 가까운 조상 순으로 배열에 넣었기 때문에 바로 처음 인덱스부터 돌면서 같은 인덱스를 발견하면 바로 멈춘다.
                break;
            }
        }

        return commonParentIndex;
    }



    public static void main(String[] args) throws Exception{
        //공통 조상 찾기
        // 서브 트리 높이 구하기


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++)
        {
            st = new StringTokenizer(br.readLine()); // 트리 Metadata
            int nodeNum = Integer.parseInt(st.nextToken());
            int edgeNum = Integer.parseInt(st.nextToken());
            int firstTarget = Integer.parseInt(st.nextToken());
            int secondTarget = Integer.parseInt(st.nextToken());


            st = new StringTokenizer(br.readLine()); // 트리
            Node[] tree = new Node[nodeNum+1]; // 조회가 많이 일어나기 때문에 배열이 적합
            sb.append("#").append(testCase).append(" ");
            while(st.hasMoreTokens()){
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                if(tree[parent] == null){
                    tree[parent] = new Node(parent, null, null, null);
                }
                if(tree[child] == null){
                    tree[child]  = new Node(child, tree[parent], null , null);
                }
                else{ // child가 전에 입력이 되었다면 지금 생성한 child 노드의 부모값에 부모를 넣는다.
                    tree[child].parent = tree[parent];
                }

                // 부모 노드 업데이트해야하는데 본인이 왼쪽 자식인지 오른쪽 자식인지 확인 후 넣기
                if(tree[parent].left == null){
                    tree[parent].left = tree[child];
                }else{
                    tree[parent].right= tree[child];
                }


            }

            // 공통 조상을 각각 저장한다.

            List<Integer> firstTargetParents = new ArrayList<>();
            List<Integer> secondTargetParents = new ArrayList<>();
            firstTargetParents = findParents(tree[firstTarget]);
            secondTargetParents = findParents(tree[secondTarget]);
            int commonParents = findCommonParents(firstTargetParents, secondTargetParents); // 공통 조상 찾기

            sb.append(commonParents).append(" ").append(findSubtreeSize(tree[commonParents])).append("\n");

        }
        System.out.println(sb);

    }
}
