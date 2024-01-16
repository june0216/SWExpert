package 삼성알고리즘특강;

import java.util.*;
import java.io.*;


public class 중위순회_객체로구현 {

    public static void main(String args[]) throws Exception
    {
        class Node{
            private char data;
            private int index;
            // 이진 트리이므로 left, right
            private Node left;
            private Node right;
            private Node(int index){
                this.index = index;
            }

        }
        class BinaryTree {
            Node root;
            public void inorder(Node node, StringBuilder sb){
                if(node.left != null) inorder(node.left, sb);
                sb.append(node.data);
                if(node.right != null) inorder(node.right, sb);
            }
            public void add(int index, char data, int leftIndex, int rightIndex){
                if(root==null){ // root를 add할 경우
                    root = new Node(index);
                    root.data = data;
                    if(leftIndex != 0) root.left = new Node(leftIndex);
                    if(rightIndex != 0) root.right = new Node(rightIndex);
                }
                else{ // root가 이미 있는 경우 -> 해당 index를 갖는 부모 노드를 찾아야 한다
                    search(root, index, data, leftIndex, rightIndex);

                }
            }

            public void search(Node parent, int index, char data, int leftIndex, int rightIndex){
                if(parent.index == index){
                    if(leftIndex != 0) parent.left = new Node(leftIndex);
                    if(rightIndex != 0) parent.right = new Node(rightIndex);
                    parent.data = data;
                }
                else{
                    if(parent.left != null) search(parent.left, index, data, leftIndex, rightIndex);
                    if(parent.right != null) search(parent.right, index, data, leftIndex, rightIndex);
                }
            }

        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = 10; // 문제에서 테스트 케이스 10개로 고정
        StringBuilder sb; // 결과를 하나의 문자열로 만들기 위해 선언
        StringTokenizer st;
        for(int testCase = 1; testCase <= T; testCase++) {

            sb = new StringBuilder(); // 각 케이스마다 만들 문자열
            sb.append("#").append(testCase).append(" ");
            int totalNum = Integer.parseInt(br.readLine()); // 노드가 몇개인지
            BinaryTree tree = new BinaryTree(); // 객체로 이진트리를 만들기 위한 객체 생성

            for(int i = 1; i <= totalNum; i++){
                st = new StringTokenizer(br.readLine()); // 하나씩 노드에 삽입하기
                int index = Integer.parseInt(st.nextToken()); // index인 값 입력받기
                char data = st.nextToken().charAt(0);
                int leftIndex = 0;
                int rightIndex =0;

                if (st.countTokens() == 2) {
                    leftIndex = Integer.parseInt(st.nextToken());
                    rightIndex = Integer.parseInt(st.nextToken());
                }
                else if (st.countTokens() == 1){
                    leftIndex = Integer.parseInt(st.nextToken());
                }
                tree.add(index, data, leftIndex,rightIndex);
            }
            tree.inorder(tree.root, sb);
            System.out.println(sb);

        }

    }
}
