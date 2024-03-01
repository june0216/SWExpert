package 삼성알고리즘특강.pro_기초partialsort연습;

public class UserSolution2 {
    static final int MAX = 100000;
    private User[] heap;
    private int last = 0;

    public static class User {
        int uID;
        int income;

        public User(int uID, int income) {
            this.uID = uID;
            this.income = income;
        }
    }

    public void init() {
        heap = new User[MAX + 1];
        last = 0;
    }

    public void addUser(int uID, int income) {
        heap[++last] = new User(uID, income);

        int idx = last;
        while (idx / 2 > 0) {
            if (compare(idx / 2, idx) > 0) {
                swap(idx / 2, idx);
            } else break;

            idx /= 2;
        }
    }

    private void swap(int i, int j) {
        User temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int compare(int i, int j) {
        if(j == -1) return 0;
        if (heap[i].income > heap[j].income || (heap[i].income == heap[j].income && heap[i].uID < heap[j].uID)) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getTop10(int[] result) {
        int count = 0;
        User[] tmp = new User[10];

        for (int i = 0; i < 10 && last > 0; i++) {
            tmp[i] = heap[1];
            result[i] = heap[1].uID;
            count++;

            heap[1] = heap[last];
            last--;

            int parent = 1;
            while (parent * 2 <= last) {
                int left = parent * 2;
                int right = left + 1;
                int min = left;
                right = right <= last ? right : -1;


                if (compare(left, right) ==1 ) {
                    min = right;
                }

                if (compare(parent, min) == 0) {
                    break;
                }

                swap(parent, min);
                parent = min;
            }
        }

        for (User user : tmp) {
            if (user != null) addUser(user.uID, user.income);
        }

        return count;
    }
}
