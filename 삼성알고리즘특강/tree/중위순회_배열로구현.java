package 삼성알고리즘특강.tree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 중위순회_배열로구현 {
    //완전이진트리이므로 트리를 굳이 구현하지 않아도 된다.
    private static void inorder(char[] tree, int index,int totalNum,StringBuilder sb){
        if(index > totalNum) return; // 인덱스가 트리의 크기를 넘어가면 반환
        inorder(tree, 2 * index, totalNum, sb); // 왼쪽 자식 노드 방문
        sb.append(tree[index]); // 현재 노드 처리
        inorder(tree, 2 * index + 1, totalNum, sb); // 오른쪽 자식 노드 방문
    }
    public static void main(String args[]) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = 10; // 문제에서 테스트 케이스 10개로 고정
        StringBuilder sb; // 결과를 하나의 문자열로 만들기 위해 선언
        StringTokenizer st;
        for(int testCase = 1; testCase <= T; testCase++) {

            sb = new StringBuilder(); // 각 케이스마다 만들 문자열
            sb.append("#").append(testCase).append(" ");
            int totalNum = Integer.parseInt(br.readLine()); // 노드가 몇개인지
            char[] tree = new char[totalNum+1];

            for(int i = 1; i <= totalNum; i++){
                st = new StringTokenizer(br.readLine()); // 하나씩 노드에 삽입하기
                int index = Integer.parseInt(st.nextToken()); // index인 값 입력받기
                char data = st.nextToken().charAt(0);
                tree[index] = data;
                // 나머지 토큰을 쓸모 없는 값이라 무시

            }
            inorder(tree, 1,totalNum,sb);
            System.out.println(sb);

        }

    }
}
