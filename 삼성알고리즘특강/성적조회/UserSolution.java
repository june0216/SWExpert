package 삼성알고리즘특강.성적조회;

import java.util.*;

class UserSolution {

    public static TreeSet<ScoreInfo>[][] scoreInfo;

    public static HashMap<Integer, StudentInfo> studentInfoSet;
    public final static int GRADE = 3;
    public final static int GENDER =2;

    public final static String FEMALE= "female";

    public final static int FEMALE_NUM = 1;
    public final static int MALE_NUM = 0;

    static class ScoreInfo implements Comparable<ScoreInfo>{

        private int ID;
        private int score;

        public ScoreInfo(int ID, int score) {
            this.ID = ID;
            this.score = score;
        }

        public int compareTo(ScoreInfo scoreInfo){ // 오름차순으로 정렬
            if(this.score != scoreInfo.score){
                return this.score - scoreInfo.score;
            }
            return this.ID - scoreInfo.ID;
        }
    }

    static class StudentInfo{
        private int score;
        private int grade;
        private int gender;

        public StudentInfo(int grade, int gender, int score) {
            this.score = score;
            this.grade = grade;
            this.gender = gender;
        }





    }
    public void init() {
        scoreInfo = new TreeSet[GRADE][GENDER];
        studentInfoSet = new HashMap<>();
        for(int i = 0; i < GRADE; i++){
            for(int j = 0; j < GENDER; j++){
                scoreInfo[i][j] = new TreeSet<>();
            }
        }
    }

    /**
     * @param mId id
     * @param mGrade  학년
     * @param mGender 성별
     * @param mScore 점수
     * @return 주어진 성별과 학년 집단 중에서 가장 높은 점수의 학생 id를 반환한다. (점수가 같다면 ID가 큰 학생을 반환한다)
     */
    public int add(int mId, int mGrade, char mGender[], int mScore) {
        int gender = MALE_NUM;
        if(mGender[0] == FEMALE.charAt(0)){
            gender = FEMALE_NUM;
        }
        scoreInfo[mGrade-1][gender].add(new ScoreInfo(mId, mScore));
        studentInfoSet.put(mId,new StudentInfo(mGrade, gender, mScore));
        return scoreInfo[mGrade-1][gender].last().ID;
    }

    /**
     *
     * @param mId 지우고자하는 학생의 id
     * @return 지워진 학생의 성별과 학년이 같은 집단 내에서 점수가 가장 낮은 학생의 점수 반환 (동일할 경우 ID가 가장 작은 값을 반환)
     */
    public int remove(int mId) {
        if(studentInfoSet.containsKey(mId) == false) return 0; // 기록되어 있지 않다면 0 반환
        StudentInfo removeStudent = studentInfoSet.get(mId);
        int grade = removeStudent.grade;
        int gender = removeStudent.gender;
        int result = 0;

        //삭제
        studentInfoSet.remove(mId);
        scoreInfo[grade-1][gender].remove(new ScoreInfo(mId, removeStudent.score));
        if(scoreInfo[grade-1][gender].size() >= 1){
            result = scoreInfo[grade-1][gender].first().ID;
        }
        return result;
    }

    /**
     *
     * @param mGradeCnt
     * @param mGrade
     * @param mGenderCnt
     * @param mGender
     * @param mScore
     * @return mGrade 학년 집합과 mGender 성별 집합에 속하면서, 점수가 mScore 이상인 학생 중에서 점수가 가장 낮은 학생의 ID를 반환
     */
    public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
        ScoreInfo compareInfo = new ScoreInfo(0, Integer.MAX_VALUE); // 이 값이 한 번도 바뀌지 않았다면 id를 0으로 반환하게 된다.
        for(int g = 0; g < mGenderCnt; g++){ // 이 부분에서 원래 for(char[] gender : mGender) 로 배열의 모든 값을 다 가져오도록 했는데 배열에 다른 값들이 들어간 것 같다. -> cnt만큼 받아오도록 했는데 성공
            int gender = MALE_NUM;
            if(mGender[g][0] == FEMALE.charAt(0)){
                gender = FEMALE_NUM;
            }
            for(int i = 0; i < mGradeCnt; i++){
                int grade = mGrade[i]-1;

                ScoreInfo candidate = scoreInfo[grade][gender].higher(new ScoreInfo(0, mScore)); // higher는 해당 값보다 바로 큰 값을 반환한다/
                if(candidate != null){
                    if(candidate.compareTo(compareInfo) < 0){ // 꺼내온 값이 더 작을 경우
                        compareInfo = candidate;
                    }
                }


            }
        }
        return compareInfo.ID;
    }
}
