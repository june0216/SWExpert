/*
package 삼성알고리즘특강.pro_검색엔진2;
import java.util.*;


public class UserSolution {
    class WebPage implements Comparable<WebPage> {
        int views;
        String url;
        List<Integer> relatedPages;

        WebPage(int views, String url) {
            this.views = views;
            this.url = url;
            this.relatedPages = new ArrayList<>();
        }

        @Override
        public int compareTo(WebPage other) {
            if (this.views == other.views) {
                return this.url.compareTo(other.url);
            }
            return Integer.compare(other.views, this.views);
        }
    }


    Map<String, Integer> urlToId;
    Map<Integer, WebPage> pageData;
    Map<String, List<Integer>> prefixMap;
    int uniqueIdCounter;

    void init() {
        pageData = new HashMap<>();
        urlToId = new HashMap<>();
        prefixMap = new HashMap<>();
        uniqueIdCounter = 0;
    }

    String charArrayToString(char[] charArray) {
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            if (c == '\0') break;
            sb.append(c);
        }
        return sb.toString();
    }
    void search(char[] mStr, int mCount) {
        String keyword = charArrayToString(mStr);
        // 조회수를 증가시킬 검색어의 ID 찾기
        Integer keywordId = urlToId.get(keyword);
        if (keywordId == null) {
            // 검색어가 존재하지 않는 경우, 새로운 검색어로 추가
            keywordId = uniqueIdCounter++;
            WebPage newPage = new WebPage(mCount, keyword);
            pageData.put(keywordId, newPage);
            urlToId.put(keyword, keywordId);
            // 접두사 맵 업데이트
            updatePrefixMap(keyword, keywordId);
        } else {
            // 검색어가 이미 존재하는 경우, 조회수 증가
            WebPage page = pageData.get(keywordId);
            page.views += mCount;
            // 연관된 검색어들의 조회수도 증가
            for (Integer relatedId : page.relatedPages) {
                WebPage relatedPage = pageData.get(relatedId);
                relatedPage.views += mCount;
                pageData.put(relatedId, relatedPage);
            }
        }
    }
    void updatePrefixMap(String url, int id) {
        StringBuilder prefix = new StringBuilder();
        for (char c : url.toCharArray()) {
            prefix.append(c);
            prefixMap.computeIfAbsent(prefix.toString(), k -> new ArrayList<>()).add(id);
        }
    }


    Result recommend(char[] mStr) {
        String keyword = charArrayToString(mStr);
        List<WebPage> candidates = new ArrayList<>();
        if (prefixMap.containsKey(keyword)) {
            for (int id : prefixMap.get(keyword)) {
                candidates*/
/**//*
.add(pageData.get(id));
            }
        }*/
/**//*

        // 조회수 증가 로직
        if (urlToId.containsKey(keyword)) {
            WebPage page = pageData.get(urlToId.get(keyword));
            page.views++;
            pageData.put(urlToId.get(keyword), page);
        }
        Collections.sort(candidates);
        for (int i = 0; i < candidates.size(); i++) {
            if (candidates.get(i).url.equals(keyword)) {
                Result result = new Result();
                result.mOrder = keyword.length();
                result.mRank = i+1;
            }
        }
        return new Result();
    }

    int relate(char[] mStr1, char[] mStr2) {
        String keyword1 = charArrayToString(mStr1);
        String keyword2 = charArrayToString(mStr2);

        // 두 검색어의 ID를 찾음
        Integer id1 = urlToId.get(keyword1);
        Integer id2 = urlToId.get(keyword2);

        // 두 검색어가 이미 연관 검색어 관계에 있는지 확인 (이 부분은 문제에 따라 추가 구현 필요)
        // 이 예제에서는 이 단계를 생략하고 직접 연관 관계를 설정합니다.

        // 두 검색어의 WebPage 객체를 가져옴
        WebPage page1 = pageData.get(id1);
        WebPage page2 = pageData.get(id2);

        // 두 검색어를 서로의 연관 검색어 목록에 추가
        if (!page1.relatedPages.contains(id2)) {
            page1.relatedPages.add(id2);
        }
        if (!page2.relatedPages.contains(id1)) {
            page2.relatedPages.add(id1);
        }

        // 연관된 모든 검색어들의 조회수 합 계산
        int totalViews = page1.views + page2.views;
        for (Integer relatedId : page1.relatedPages) {
            totalViews += pageData.get(relatedId).views;
        }
        for (Integer relatedId : page2.relatedPages) {
            if (!page1.relatedPages.contains(relatedId)) {
                totalViews += pageData.get(relatedId).views;
            }
        }

        // 업데이트된 정보 저장
        pageData.put(id1, page1);
        pageData.put(id2, page2);

        return totalViews;
    }


    void rank(char[] mPrefix, int mRank, char[] mReturnStr) {
        String prefix = charArrayToString(mPrefix);
        List<WebPage> candidates = new ArrayList<>();
        if (prefixMap.containsKey(prefix)) {
            for (int id : prefixMap.get(prefix)) {
                candidates.add(pageData.get(id));
            }
        }
        Collections.sort(candidates);
        if (mRank - 1 < candidates.size()) {
            String result = candidates.get(mRank - 1).url;
            for (int i = 0; i < result.length(); i++) {
                mReturnStr[i] = result.charAt(i);
            }
            mReturnStr[result.length()] = '\0';
        }
    }

}
*/
