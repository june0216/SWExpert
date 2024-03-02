package 삼성알고리즘특강.pro_문자열관리프로그램;
import java.nio.channels.DatagramChannel;
import java.util.*;
class UserSolution
{
    public Deque<Character> que; // 실제로 뒤집으면 150,000 * 30,000 -> 시간초과 => 뒤집혀있는지 여부를 변수로 관리하고 그 여부에 따라 앞에 추가할지 뒤에 추가할지 결정
    public boolean isReversed;
    public Map<Integer, Integer> hashMap;
    public static final char END = '\0';

    /**
     * 해당 테스트 케이스에서 초기 문자열은 mStr 이다.
     * @param mStr
     */
    void init(char mStr[])
    {
        que = new ArrayDeque<>();
        isReversed = false;
        hashMap = new HashMap<>();
        appendWord(mStr);


    }

    void appendWord(char mWord[])
    {
        if(!isReversed){
            for(char c : mWord){
                if(c == END) break;
                que.addLast(c);
                getHashValue(1);
            }
        }
        else{
            for(char c : mWord){
                if(c == END) break;
                que.addFirst(c);
                getReverseHashValue(1);
            }
        }

    }

    void getReverseHashValue(int num){
        int hashValue = 0;
        int power = 1;
        Iterator<Character> iter = que.iterator();

        for (int i = 0; i < 4 && iter.hasNext(); ++i){

            hashValue += power * (iter.next() - 'a'+1);
            power <<=5;
            hashMap.put(hashValue, hashMap.getOrDefault(hashValue, 0)+num);

        }
    }
    void getHashValue(int num){
        int hashValue = 0;
        Iterator<Character> iter = que.descendingIterator();
        for (int i = 0; i < 4 && iter.hasNext(); ++i){
            hashValue <<=5;
            hashValue += (iter.next() - 'a'+1);
            hashMap.put(hashValue, hashMap.getOrDefault(hashValue, 0)+num);

        }
    }



    void cut(int k)
    {
        if(!isReversed){
            for(int i = 0 ; i < k; ++i){
                getHashValue(-1);
                que.removeLast();
            }
        }
        else{
            for(int i = 0 ; i < k; ++i){
                getReverseHashValue(-1);
                que.removeFirst();
            }
        }
    }

    void reverse()
    {
        isReversed = !isReversed;
    }

    int countOccurrence(char mWord[])
    {
        int hashValue = 0;
        if(!isReversed){
            int power = 1;
            for(char c : mWord){
                if(c == END) break;
                hashValue += power*(c-'a' +1);
                power <<= 5;
            }
        }else{
            for(char c : mWord){
                if(c == END) break;
                hashValue <<= 5;
                hashValue += (c-'a' +1);

            }
        }
        if(hashMap.get(hashValue) == null)
            return 0;
        return hashMap.get(hashValue);
    }
}
