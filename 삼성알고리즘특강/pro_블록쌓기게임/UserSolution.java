package 삼성알고리즘특강.pro_블록쌓기게임;

public class UserSolution {

    int c;
    long count;
    int size;
    int[] tree;
    int[] minTree;
    int[] maxTree;

    void init(int C) {

        this.c = C;
        int height = (int)Math.ceil(Math.log(C) / Math.log(2));


        size = (int)Math.pow(2, height + 1);


        tree = new int[size];
        minTree = new int[size];
        maxTree = new int[size];
        this.count = 0;
    }
    void update(int node, int s, int e, int l, int r, int h) {

        if(l <= s && e <= r) {
            tree[node] += h;
            minTree[node] += h;
            maxTree[node] += h;
            return;
        }

        int mid = (s + e) / 2;

        if(s <= r && l <= mid) update(node * 2, s, mid, l, r, h);
        if(mid + 1 <= r && l <= e) update(node * 2 + 1, mid + 1, e, l, r, h);

        minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]) + tree[node];
        maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]) + tree[node];
    }

    Solution.Result dropBlocks(int mCol, int mHeight, int mLength) {

        count += mHeight * mLength;
        update(1, 0, c - 1, mCol, mCol + mLength - 1, mHeight);

        Solution.Result ret = new Solution.Result();
        ret.top = maxTree[1] - minTree[1];
        ret.count = (int)((count - ((long)c * minTree[1])) % 1_000_000);

        return ret;
    }
}
