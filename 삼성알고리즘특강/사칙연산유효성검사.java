package 삼성알고리즘특강;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 사칙연산유효성검사 {
    // 단순 순회와 다른 점은 한 번 돌고나서 계산한 결과값을 가지고 있어야한다.
    // 그런데 값을 구하라는 게 아니라 계산 가능성 여부를 알아보는 것이므로 가능성을 확인해보는 것으로 해도 될듯
    // 안되는 경우 중위 노드가 연산자가 아닌 경우, 왼쪽 혹은 오른쪽이 숫자가 아닌 경우
    // 단순하게 생각하자 -> 노드하나만 고려하면 되는데 자식 노드까지 다 같이 고민하려고 하니까 풀이가 느렸다.
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = 10; // 문제에서 테스트 케이스 10개로 고정
        StringBuilder sb = new StringBuilder(); // 결과를 하나의 문자열로 만들기 위해 선언
        StringTokenizer st;
        for (int testCase = 1; testCase <= T; testCase++) {
            int totalNum = Integer.parseInt(br.readLine());
            sb.append("#").append(testCase).append(" ");
            int result = 1;
            while(totalNum-- > 0){
                st = new StringTokenizer(br.readLine());
                st.nextToken(); // index

                String node = st.nextToken();

                if(!st.hasMoreTokens()){ //단말노드라면 -> 꼭 숫자여야 한다.
                    if(node.equals("+") || node.equals("-") || node.equals("*") || node.equals("/")){ // 숫자가 아닐 때 유효하지 않은 것으로 판정
                        result = 0;
                        //break;
                    }
                }else{ // 자식이 있는 노드라면 -> 꼭 연산자
                    if(!node.equals("+") && !node.equals("-") && !node.equals("*") && !node.equals("/")){ // 연산자가 아닐 때 유효하지 않은 것으로 판정
                        result = 0;
                        //break; 원래는 그냥 사칙 연산이 유효하지 않으면 조건문을 빠져나오고 다음 케이스를 검사하려고 했는데 그렇게 된다면 입력값을 다 읽지 못해 다음 케이스를 읽는 게 아니라 멈춘 곳 바로 다음을 읽어서 의도와 다르게 된다.
                    }
                }
            }
            sb.append(result).append("\n");

        }
        System.out.println(sb);
    }
}
