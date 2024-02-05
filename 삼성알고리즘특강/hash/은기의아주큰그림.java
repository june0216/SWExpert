package 삼성알고리즘특강.hash;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * 처음에 KMP를 생각했는데 2차원 배열이라서?
 * 라빈카프 -> 그런데 2차원이라 어려웠음 -> 각 행마다 해싱 돌려 배열로 만들고 그걸 다시 각 열마다 다시 해싱한다.
 * 비슷한 백준 문제 = https://www.acmicpc.net/problem/10538
 */
public class 은기의아주큰그림 {
    private static final int MOD = 1000000007;
    private static final int BASE = 31;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            st = new StringTokenizer(br.readLine());
            int patternHeight = Integer.parseInt(st.nextToken());
            int patternWidth = Integer.parseInt(st.nextToken());
            int targetHeight = Integer.parseInt(st.nextToken());
            int targetnWidth = Integer.parseInt(st.nextToken());
            char[][] pattern = new char[patternHeight][patternWidth];
            for(int i = 0; i < patternHeight; i++){
                pattern[i] = br.readLine().toCharArray();

            }

            char[][] target = new char[targetHeight][targetnWidth];
            for(int i = 0; i < targetHeight; i++){
                target[i] = br.readLine().toCharArray();
            }
            int result = 0;
            result = countPattern(pattern, target, patternHeight, patternWidth);
            sb.append(result).append("\n");

        }
        System.out.println(sb);
    }

    public static int countPattern(char[][] pattern, char[][] target, int patternHeight, int patternWidth) {
        int[][] patternHash = getPatternHash(pattern, patternHeight, patternWidth);
        int[][] targetHash = getPatternHash(target, target.length, patternWidth);
        int count = 0;

        for (int row = 0; row <= target.length - patternHeight; row++) {
            for (int col = 0; col <= target[0].length - patternWidth; col++) {
                if (comparePattern(pattern, target, patternHash, targetHash, patternHeight, patternWidth, row, col)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static int[][] getPatternHash(char[][] pattern, int rows, int cols) {
        int[][] hash = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            int rowHash = 0;
            for (int j = 0; j < cols; j++) { //먼저 행에 대한 해시값을 구한다.
                rowHash = (rowHash * BASE+ pattern[i][j]) % Integer.MAX_VALUE;
            }
            hash[i][0] = rowHash;

            for (int j = 1; j < cols; j++) { // 그 다음에 행에 대한 해시값에다가 전체 해시값을 계산한다.
                rowHash = (rowHash - pattern[i][0] * BASE + pattern[i][j]) % Integer.MAX_VALUE;
                hash[i][j] = rowHash;
            }
        }

        return hash;
    }

    private static boolean comparePattern(char[][] pattern, char[][] target, int[][] patternHash, int[][] targetHash, int patternHeight, int patternWidth, int row, int col) {


        for (int i = 0; i < patternHeight; i++) {
            int patternRowHash = patternHash[i][0];
            int targetRowHash = targetHash[row + i][col];

            if (patternRowHash != targetRowHash) {
                return false;
            }

            for (int j = 1; j < patternWidth; j++) {
                patternRowHash = (patternRowHash * BASE+ pattern[i][j]) % Integer.MAX_VALUE;
                targetRowHash = (targetRowHash * BASE + target[row + i][col + j]) % Integer.MAX_VALUE;

                if (patternRowHash != targetRowHash) {
                    return false;
                }
            }
        }

        return true;
    }


}
