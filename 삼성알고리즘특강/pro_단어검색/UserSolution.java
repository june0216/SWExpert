package 삼성알고리즘특강.pro_단어검색;

class UserSolution {
    public static final int MAX_SIZE = 26;
    class Node{
        Node[] child = new Node[MAX_SIZE];
        int childCount;

    }

    public Node root;
    public int totalCount;
    public boolean isRemove;
    void init() {
       root = new Node(); // 루트 생성
    }

    int add(char str[]) {
        Node currentNode = root;
        for(char alphabet : str){
            if(alphabet == '\0') break;
            int index = alphabet - 'a'; // 알파벳별로 0부터 시작하게 함
            if(currentNode.child[index] == null){ // 처음 추가되는 경우
                currentNode.child[index] = new Node();
            }
            currentNode = currentNode.child[index]; // 현재노드가 자식으로 넘어감
        }
        currentNode.childCount+=1; // 단어 마지막 위치에 count 를 1 갱신한다.
        return currentNode.childCount;
    }

    int remove(char str[]) {
        totalCount = 0;
        isRemove = true;
        rootCheck(str);
        return totalCount;
    }
    public void rootCheck(char[] str){
        if(str[0] == '?'){
            for(int i = 0; i < MAX_SIZE; i++){
                update(str, root.child[i], 0);

            }
        }else
        {
            update(str, root.child[str[0] - 'a'], 0);
        }
    }
    private void update(char[] str, Node node, int index){
        if(node == null) return;
        char nextChar = str[index+1];
        if(nextChar == '\0'){
            totalCount += node.childCount;
            if(isRemove) node.childCount = 0;
        }
        else if(nextChar == '?'){
            for(int i = 0 ; i < MAX_SIZE; i++){
                Node startNode= node.child[i];
                update(str, startNode, index+1);
            }
        }else{
            update(str, node.child[nextChar - 'a'],index+1);
        }
    }


    int search(char str[]) {
        totalCount = 0;
        isRemove = false;
        rootCheck(str);
        return totalCount;
    }
}
