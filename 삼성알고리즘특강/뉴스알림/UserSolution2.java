package 삼성알고리즘특강.뉴스알림;

import java.util.*;

class UserSolution2 {

    private static class News implements Comparable< News > {
        int newsId;
        int channelId;
        int time;
        boolean isDeleted;

        public News(int _newsId, int _channelId, int _time) {
            newsId = _newsId;
            channelId = _channelId;
            time = _time;
            isDeleted = false;
        }

        // time이 낮을수록, newId가 낮을수록 우선순위 큼
        @Override
        public int compareTo(News o) {
            if (this.time != o.time) return this.time - o.time;
            return this.newsId - o.newsId;
        }
    }

    Map< Integer, List< Integer >> channelMap;    // key: 채널ID, value: 해당 채널을 구독하고 있는 유저ID리스트
    Map< Integer, List< News >> userMap;          // key: 유저ID, value: 해당 유저가 받은 뉴스들
    Map< Integer, News > newsMap;                // key: 뉴스ID, value: 해당 뉴스 저장
    PriorityQueue< News > notiPQ;                // 보낼 뉴스들 저장(시간 낮은 뉴스 우선)

    // 초기화
    void init(int _N, int _K) {
        channelMap = new HashMap<>();
        userMap = new HashMap<>();
        newsMap = new HashMap<>();
        notiPQ = new PriorityQueue<>();
    }

    // time까지 보내야할 뉴스들 보내기
    void sendNews(int time) {
        while (!notiPQ.isEmpty()) {
            if (notiPQ.peek().time > time) return;

            News news = notiPQ.poll();

            // 삭제된 뉴스인 경우
            if (news.isDeleted) continue;

            // 해당 채널을 구독하고 있는 유저들에게 뉴스 보내기
            List< Integer > userList = channelMap.get(news.channelId);
            for (int i : userList) {
                userMap.get(i).add(news);
            }
        }
    }

    // 유저가 채널을 구독
    void registerUser(int mTime, int mUID, int mNum, int mChannelIDs[]) {
        sendNews(mTime);

        if (userMap.get(mUID) == null) userMap.put(mUID, new ArrayList<>());

        // 각 채널 구독자 목록에 해당 유저 추가
        for (int i = 0; i < mNum; i++) {
            int channelId = mChannelIDs[i];

            if (channelMap.get(channelId) == null) channelMap.put(channelId, new ArrayList<>());
            channelMap.get(channelId).add(mUID);
        }
    }

    // 뉴스 등록
    int offerNews(int mTime, int mNewsID, int mDelay, int mChannelID) {
        // 뉴스 생성 후 뉴스 등록
        News newNews = new News(mNewsID, mChannelID, mTime + mDelay);
        notiPQ.add(newNews);
        newsMap.put(mNewsID, newNews);

        return channelMap.get(mChannelID).size();
    }

    // 뉴스 취소
    void cancelNews(int mTime, int mNewsID) {
        sendNews(mTime);

        News news = newsMap.get(mNewsID);

        // 삭제 표시
        news.isDeleted = true;
    }

    // 유저가 받은 뉴스 확인
    int checkUser(int mTime, int mUID, int mRetIDs[]) {
        sendNews(mTime);

        // list의 뒤에서부터는 최신 뉴스가 쌓여있다
        List< News > list = userMap.get(mUID);

        int total = list.size();
        int cnt = 0;    // 받은 뉴스 카운트

        while (total > 0) {
            News news = list.get(total - 1);

            // 삭제된 뉴스인 경우
            if (news.isDeleted) {
                total--;
                continue;
            }

            // 최신 뉴스 최대 3개 저장
            if (cnt < 3) mRetIDs[cnt] = news.newsId;

            total--;
            cnt++;
        }

        // 유저가 받았던 뉴스 초기화
        userMap.get(mUID).clear();
        return cnt;
    }
}
