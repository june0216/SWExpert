package 삼성알고리즘특강.코드배틀;
import java.util.ArrayList;
import java.util.List;

class UserSolution {
    public static List<Node> people;
    class Node{
        private Node parent;
        private int id;
        private int score;
        private int lazy = 0;

        public Node(int id) {
            this.id = id;
            this.score = 0;
            this.parent = this;
        }

        private Node root() {
            Node node = this;
            while (node.parent != node) {
                node = node.parent;
            }
            return node;
        }
        public void merge(Node node) {
            Node root1 = this.root();
            Node root2 = node.root();

            if (root1 == root2) {
                return; // 이미 같은 루트를 가지고 있으므로 병합할 필요 없음
            }
            node.propagate();
            this.propagate();
            root2.parent = root1;

        }

        public void addScore(int scoreDiff) {
            Node root = this.root();
            root.lazy += scoreDiff;
        }

        public int getScore() {
            //propagate();
            return this.parent.lazy + score;
        }

        private void propagate() {
            Node node = this;
            int accumulatedScore = 0;
            while (node.parent != node) {
                accumulatedScore += node.lazy;
                node = node.parent;
            }
            accumulatedScore += node.lazy; // 루트 노드의 지연 점수까지 포함
            node.lazy = 0;

            node = this;
            while (node.parent != node) {
                node.score += accumulatedScore; // 각 노드에 점수 적용
                accumulatedScore -= node.lazy;
                node = node.parent;
            }
            node.score += accumulatedScore; // 루트 노드에 점수 적용
        }

    }
    public void init(int N) {
        people = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            people.add(new Node(i));
        }
    }

    public void updateScore(int mWinnerID, int mLoserID, int mScore) {
        people.get(mWinnerID - 1).addScore(mScore);
        people.get(mLoserID - 1).addScore(-mScore);
    }

    public void unionTeam(int mPlayerA, int mPlayerB) {
        people.get(mPlayerA - 1).merge(people.get(mPlayerB - 1));
    }

    public int getScore(int mID) {
        return people.get(mID - 1).getScore();
    }
}


