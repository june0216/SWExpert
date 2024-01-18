package 삼성알고리즘특강;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class 사칙연산 {
    public static Queue<String> que = new LinkedList<>();
    public Integer result = 0;
    public static void main(String args[]) throws Exception {
        class Node {
            private String value;
            private Node left;
            private Node right;
            private int index;

            public Node(int index) {
                this.index = index;
            }
        }
        class BinaryTree {
            Node root;


            public void add(int index, int leftNum, int rightNum, String value) {
                if (root == null) {
                    root = new Node(index);
                    root.value = value;
                    if (leftNum != 0) {
                        root.left = new Node(leftNum);
                    }
                    if (rightNum != 0) {
                        root.right = new Node(rightNum);
                    }
                } else { // 부모부터 하나씩 내려가보면서 들어갈 위치를 찾는다. -> 단말 노드를 찾아서 만든다.
                    search(root, index, leftNum, rightNum, value);
                }
            }

            public void search(Node parent, int index, int leftNum, int rightNum, String value) {
                if (parent.index == index) { // 해당 인덱스를 찾은 경우
                    if (leftNum != 0) { // 입력값중에 왼쪽 숫자가 있으면 왼쪽 자식 노드 생성
                        parent.left = new Node(leftNum);
                    }
                    if (rightNum != 0) {
                        parent.right = new Node(rightNum);
                    }
                    parent.value = value;
                } else {
                    if (parent.left != null) {
                        search(parent.left, index, leftNum, rightNum, value);
                    }
                    if (parent.right != null) {
                        search(parent.right, index, leftNum, rightNum, value);
                    }
                }
            }

            public double inOrder(Node node) {

                double right = 0;
                double left = 0;

                if (node.left.left == null) { // 왼쪽 자식 노드가 단말 노드이면 바로 값으로 변경하여 계산하기 위해 변수에 대입
                    left = Double.parseDouble(node.left.value);
                } else {
                    left = inOrder(node.left); // 단말 노드가 아니라면 기준 노드 기준으로 하위에서 일어난 계산 결과를 left 노드에 담는다.
                }

                if (node.right.right == null) { // 오른쪽 자식 노드가 단말 노드이면 바로 값으로 변경하여 계산하기
                    right = Double.parseDouble(node.right.value);
                } else {
                    right = inOrder(node.right);// 단말노드가 아니라면 기준 노드 기준으로 하위에서 일어난 계산 결과를 right 노드에 담는다.
                }
                switch (node.value) { // 계산하기
                    case "+":
                        return left + right;
                    case "-":
                        return left - right;
                    case "*":
                        return left * right;
                    case "/":
                        return left / right;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + node.value);
                }
            }

        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = 10; // 문제에서 테스트 케이스 10개로 고정
        StringBuilder sb = new StringBuilder(); // 결과를 하나의 문자열로 만들기 위해 선언
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            int totalNum = Integer.parseInt(br.readLine());
            BinaryTree tree = new BinaryTree();
            sb.append("#").append(testCase).append(" ");
            while (totalNum-- > 0) {
                st = new StringTokenizer(br.readLine());
                int index = Integer.parseInt(st.nextToken()); // index
                String value = st.nextToken();
                int leftIndex = 0;
                int rightIndex = 0;
                if(st.countTokens() == 2){ // 단말노드가 아니라면
                    leftIndex = Integer.parseInt(st.nextToken());
                    rightIndex = Integer.parseInt(st.nextToken());
                }
                tree.add(index, leftIndex, rightIndex, value); // 트리 구성
            }

            sb.append((int)tree.inOrder(tree.root)).append("\n");
        }
        System.out.println(sb);
    }
}
