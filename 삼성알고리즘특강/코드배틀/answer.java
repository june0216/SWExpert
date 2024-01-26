package 삼성알고리즘특강.코드배틀;

// 논리만 있으면 풀 수 있는 문제
public class answer {
    static int[] parent;
    static int[] groupScore;

    public void init(int N){
        parent = new int[N+1];
        groupScore = new int[N+1];
        for(int i  = 0 ; i <= N; i++){
            parent[i] = i; // 처음에는 자기 자신이 부모
            groupScore[i] = 0;
        }
    }


    // 부모를 찾는 함수
    int find(int x) {
        // 내가 누군가의 부모라면
        if (x == parent[x])
            return x; // 나를 반환

        // 부모를 찾는 여정
        int p = find(parent[x]);

        // 부모의 부모가 바뀔 때 점수 계산을 해주기
        if (p != parent[x]){ // 방금 찾은 부모 ==p 이고 기존 부모
            // 현재 조에서 빠져나옴
            groupScore[x] += groupScore[parent[x]]; // 더하고 빠져나옴
        }

        parent[x] = p;
        return parent[x];
        //return parent[x] = find(parent[x]);
    }

    public void updateScore(int mWinnerID, int mLoserID, int mScore) {
        //이긴 조
        int A = find(mWinnerID);
        int B = find(mLoserID);

        // A조는 점수 플러스
        groupScore[A] += mScore;
        groupScore[B] -= mScore;

    }

    // 두 팀을 합침
    public void unionTeam(int mPlayerA, int mPlayerB) {
        // 두 조 준비
        int A = find(mPlayerA);
        int B = find(mPlayerB);

        // B조를 A합침
        parent[B] = A; // B의 부모를

        // B조 사람들은 A조로 들어가게 된다.
        // A조가 받을 점수를 어차피 받을거임
        // 받은 만큼 빼주자
        groupScore[B] -= groupScore[A];


    }

    // 선순의 점수 반환
    public int getScore(int mID){
        // 현재 조 (A)
        int A = find(mID); // 현재 부모를 찾기

        // 원래 조(B) -> 내가 처음에 있던 조
        int B = mID; // 처음에 자기자신이 부모였다.


        if(A != B) // 같은조라면 같은 값을 2번 더하게 된다.
            // 현재 조 점수 + 원래 조 점수
            return groupScore[A] + groupScore[B];
        else
            return groupScore[A];
    }
}
