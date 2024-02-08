package 삼성알고리즘특강.뉴스알림;

import java.io.*;
import java.util.*;
/*
삽질 1 : compareTo 에서 변수명 잘못 사용
삽질 2 : 반복문을 돌릴 때 최대한 인덱스로 돌리자 -> registerUser(int mTime, int mUID, int mNum, int mChannelIDs[]) 이 부분에서 mNum을 사용하지 않고 향상된 for문을 사용했더니 fail이었다.
 */
class UserSolution {

    class Channel{
        public int uid;
        List<News> newsList = new ArrayList<>();
        Set<User> userList = new HashSet<>();
        public Channel(int uid){
            this.uid = uid;
        }


    }
    class News implements Comparable<News>{ // 유저별 뉴스 정보

        public int newsUid;
        public boolean isDeleted = false;
        public int time;

        public int channelId;
        @Override
        public int compareTo(News news){ // 이 부분이 문제였음
            if (this.time != news.time) return this.time - news.time;
            return this.newsUid - news.newsUid;
        }

        public News(int newsUid, int time, int channelId){
            this.newsUid= newsUid;
            this.time = time;
            this.channelId = channelId;
        }

    }

    class User{
        public int uid;
        List<News> newsList = new ArrayList<>();

        public User(int uid) {
            this.uid = uid;
        }
    }

    public Map<Integer, User> users;
    public PriorityQueue<News> readyForAlarm;
    public Map<Integer, News> newsMap;
    public Map<Integer, Channel> channels;

    void init(int N, int K)
    {
        users = new HashMap<>();
        channels = new HashMap<>();
        readyForAlarm = new PriorityQueue<>();
        newsMap = new HashMap<>();
    }

    void sendAlarm(int time){
        //System.out.println(readyForAlarm.size());
        while(!readyForAlarm.isEmpty()){
            //  지금 시간이 해당 뉴스의 알림 시간보다 이르다면 Pass
            if(readyForAlarm.peek().time > time) {
                return;
            }
            News news = readyForAlarm.poll();
            // 해당 뉴스가 삭제 된 것이라면 다음 우선순위의 뉴스를 꺼내기
            if(news.isDeleted){
                continue;
            }


            for(User user : channels.get(news.channelId).userList){
                user.newsList.add(news); // 알림 보내기

            }

        }
    }

    /**
     * mTime 시각에 유저에게 보내지는 뉴스 알림이 있는 경우 먼저 알림을 보낸 후, mUID 유저를 뉴스 채널에 등록
     * @param mTime
     * @param mUID
     * @param mNum
     * @param mChannelIDs
     */
    void registerUser(int mTime, int mUID, int mNum, int mChannelIDs[])
    {
        sendAlarm(mTime);
        if(!users.containsKey(mUID)) users.put(mUID, new User(mUID));
        // 알림 보내기

        User user = users.get(mUID);


        for(int i = 0; i < mNum; i++){
            int channelUid = mChannelIDs[i];
            if(!channels.containsKey(channelUid)) channels.put(channelUid, new Channel(channelUid));
            Channel channel = channels.get(channelUid);
            // 유저를 뉴스 채널에 등록
            channel.userList.add(user);


        }

    }

    /**
     * mTime 시각에 mDelay 시간이 있는 mNewsID 뉴스가 mChannelID 뉴스 채널에 제공되고, mChannelID 뉴스 채널에 알림을 등록한 유저의 수를 반환
     * @param mTime
     * @param mNewsID
     * @param mDelay
     * @param mChannelID
     * @return
     */
    int offerNews(int mTime, int mNewsID, int mDelay, int mChannelID)
    {
        // 고민 -> 채널과 뉴스를 양방향으로 저장할 것인지, 혹은 뉴스에서 채널을 일반적으로 저장할지
        News news = new News(mNewsID, mTime+mDelay, mChannelID);
        newsMap.put(mNewsID, news);

        readyForAlarm.add(news);

        Channel channel = channels.get(mChannelID);
        channel.newsList.add(news);
        return channel.userList.size();
    }

    /**
     * 뉴스 알림 삭제
     * @param mTime
     * @param mNewsID
     */

    void cancelNews(int mTime, int mNewsID)
    {
        sendAlarm(mTime);
        // 뉴스 삭제
        News deletedNews = newsMap.get(mNewsID);
        deletedNews.isDeleted = true; // 아예 객체를 삭제하는 것이 아니라 변수명을 통해 삭제 여부 파악하는 것이 더 효과적

    }

    /**
     * 유저의 알림을 확인하고 삭제
     * @param mTime
     * @param mUID
     * @param mRetIDs
     * @return 알림 개수
     */

    int checkUser(int mTime, int mUID, int mRetIDs[])
    {
        sendAlarm(mTime);
        //mUID 유저가 받은 뉴스 알림의 개수를 반환
        User user = users.get(mUID);
        int count = 0;
        for(int i = user.newsList.size()-1; i >= 0; i--){ // 사용자가 알림을 받을 때 최신순으로 누적되어서 받기 떄문에 (스택 구조처럼) 뒤에서 부터 꺼내면 최신순으로 정렬된 알림으로 구성할 수 있다.
            // 여기서 알림을 받은 리스트들을 우선순위 큐로 해서 넣을 때마다 정렬을 수행하는 것보다 List로 쌓는 것이 더 시간복잡도가 덜하다 -> 어차피 알림을 받을 때 순서대로 오기 때문(이게 최적화 포인트이다)
            News news = user.newsList.get(i);
            if(news.isDeleted) continue;
            //mUID 유저가 받은 뉴스의 ID 를 최신 받은 순서대로 최대 3개 mRetIDs[] 에 저장
            if(count < 3){
                mRetIDs[count] = news.newsUid;
            }

            count++;
        }
        user.newsList.clear();


        //함수 호출 후, mUID 유저가 받은 뉴스 알림은 모두 삭제되어 알림의 개수는 0 이 된다.
        return count;
    }
}
