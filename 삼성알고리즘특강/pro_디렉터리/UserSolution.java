package 삼성알고리즘특강.pro_디렉터리;

import java.io.*;
import java.util.*;
class UserSolution {

    private final static int NAME_MAXLEN	= 6;
    private final static int PATH_MAXLEN	= 1999;



//	 The below commented methods are for your reference. If you want
//	 to use it, uncomment these methods.
//
//	int mstrcmp(char[] a, char[] b) {
//		int i;
//		for (i = 0; a[i] != '\0'; i++) {
//			if (a[i] != b[i])
//				return a[i] - b[i];
//		}
//		return a[i] - b[i];
//	}
//
//	int mstrncmp(char[] a, char[] b, int len) {
//		for (int i = 0; i < len; i++) {
//			if (a[i] != b[i])
//				return a[i] - b[i];
//		}
//		return 0;
//	}
//
//	int mstrlen(char[] a) {
//		int len = 0;
//
//		while (a[len] != '\0')
//			len++;
//
//		return len;
//	}
//
//	void mstrcpy(char[] dest, char[] src) {
//		int i = 0;
//		while (src[i] != '\0') {
//			dest[i] = src[i];
//			i++;
//		}
//		dest[i] = src[i];
//	}
//
//	void mstrncpy(char[] dest, char[] src, int len) {
//		for (int i = 0; i < len; i++) {
//			dest[i] = src[i];
//		}
//		dest[len] = '\0';
//	}

    class Node{
        Node parent;
        List<Node> child = new ArrayList<>();
        int childCnt;
        String name;

        public Node(String name){
            this.name = name;
            this.childCnt = 1;
        }
    }

    public Node tree;
    public HashMap<String, Node> treeMap = new HashMap<>();
    void init(int n) {
        // root 생성
        tree = new Node("/");

    }

    /**
     * path[] 디렉터리의 하위에 name[] 이름을 가진 새로운 디렉터리를 생성한다.
     * @param path
     * @param name
     */
    void cmd_mkdir(char[] path, char[] name) {
        Node parent = getNode(path);
        Node newNode = new Node(charToString(name));
        update(parent, newNode);
    }
    private void update(Node parent, Node newNode){
        parent.child.add(newNode);
        newNode.parent = parent;

        // 자식 카운트 갱신
        while(parent != null){
            parent.childCnt += newNode.childCnt;
            parent = parent.parent;

        }


    }

    private String charToString(char[] path){
        // 배열 전체를 String으로 변환
        return String.valueOf(path, 0, path.length-1);
    }




    void removeUpdate(Node removeNode){
        Node node = removeNode;
        while(node.parent != null){
            node.parent.childCnt -= removeNode.childCnt;
            node = node.parent;
        }
    }

    /**
     * path[] 디렉터리와 그 모든 하위 디렉터리를 삭제한다.
     * @param
     */

    void cmd_rm(char[] path) {
        Node removeNode = getNode(path);
        removeUpdate(removeNode);
        removeNode.parent.child.remove(removeNode);
    }

    /**
     * dstPath[] 디렉터리의 하위에 srcPath[] 의 디렉터리와 그 모든 하위 디렉터리를 복사한다.
     * @param srcPath
     * @param dstPath
     */
    void cmd_cp(char[] srcPath, char[] dstPath) {
        Node targetNode = getNode(srcPath);;
        Node copyNode = getNode(dstPath);
        Node newNode = new Node(targetNode.name);
        copyTree(targetNode, newNode);
        update(copyNode, newNode);

    }
    void copyTree(Node parent, Node copyNode) {
        copyNode.childCnt = parent.childCnt;

        for (Node child : parent.child) {
            Node node = new Node(child.name);
            node.parent = copyNode;
            copyNode.child.add(node);
            copyTree(child, node);
        }
    }

    /**
     * dstPath[] 디렉터리의 하위에 srcPath[] 의 디렉터리와 그 모든 하위 디렉터리를 이동한다
     * @param srcPath
     * @param dstPath
     */
    void cmd_mv(char[] srcPath, char[] dstPath) {
        Node targetNode = getNode(dstPath);;
        Node moveNode = getNode(srcPath);
        moveNode.parent.child.remove(moveNode);


        Node node = moveNode.parent;
        while(node!= null){
            node.childCnt -= moveNode.childCnt;
            node = node.parent;
        }
        update(targetNode, moveNode);
    }

    /**
     * path[] 디렉터리의 모든 하위 디렉터리 개수를 반환한다.
     * @param path
     * @return
     */
    int cmd_find(char[] path) {
        Node node = getNode(path);
        return node.childCnt-1;
    }

    private Node getNode(char[] path){
        StringTokenizer st = new StringTokenizer(charToString(path), "/");
        Node node = tree;
        while(st.hasMoreTokens()){
            String targetName = st.nextToken();
            for(Node child : node.child){
                if(child.name.equals(targetName)){
                    node = child;
                    break;
                }
            }
        }
        return node;
    }
}
