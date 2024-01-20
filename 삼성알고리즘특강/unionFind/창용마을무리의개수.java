package 삼성알고리즘특강.unionFind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//건너건너 아는 사람
public class 창용마을무리의개수 {
    private static class Node{ // 각 사람을 의미 , 관계를 유니온 파인드로
        private Node parent = null;

        public void merge(Node node) {
            Node root1 = this.root();
            Node root2 = node.root();

            if (root1 == root2) {
                return; // 이미 같은 루트를 가지고 있으므로 병합할 필요 없음
            }

            root2.parent = root1; // 한 노드의 루트를 다른 노드의 루트에 연결
        }

        private Node root(){
            if(parent == null) return this; // 자신이 루트일 경우 자신을 반환
            return parent = parent.root(); // 아니라면 부모의 root를 반환
        }

    }

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder(); // 결과를 하나의 문자열로 만들기 위해 선언
        StringTokenizer st;
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            int peopleNum = Integer.parseInt(st.nextToken());
            int relationNum = Integer.parseInt(st.nextToken());
            Node[] people = new Node[peopleNum+1];
            for(int i = 1; i <= peopleNum; i++ ){
                people[i] = new Node();
            }
            while(relationNum--> 0){
                st = new StringTokenizer(br.readLine());
                int index1 = Integer.parseInt(st.nextToken());
                int index2 = Integer.parseInt(st.nextToken());
                people[index1].merge(people[index2]);
            }

            int goupNum = 0;
            for(int i = 1; i <=peopleNum; i++ ){
                if(people[i].root() == people[i]){
                    goupNum++;
                }
            }
            sb.append(goupNum).append("\n");

        }
        System.out.println(sb);
    }
}
// 5개 맞음
