package 삼성알고리즘특강.pro_물류허브;

import java.io.*;
import java.util.*;
class UserSolution {
    static class City implements Comparable<City>{
        public int cost;
        public int to; // 도착지의 index 저장

        public City(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
        @Override
        public int compareTo(City city){
            return this.cost - city.cost;
        }

    }

    Map<Integer, Integer> cityInfo; // city의 고유 번호가 아닌 index로 접근하기 위해 고유 번호와 배열에 저장된 인덱스를 매핑하여 정보를 가지고 있음
    public List<City> mapping[];

    public List<City> reverseMapping[];
    public static int MAX_CITY_NUM = 601;
    public int totalCityNum = 0;
    /**
     * 각 테스트 케이스의 처음에 호출된다.
     *
     * N개의 도로 정보가 주어진다. 각 도로의 출발 도시와 도착 도시, 운송 비용이 주어진다.
     *
     * 도로 정보로 주어지는 도시의 총 개수를 반환한다.
     *
     * 단방향 도로이기 때문에 출발 도시에서 도착 도시로만 갈 수 있다.
     *
     * 출발 도시와 도착 도시의 순서쌍이 동일한 도로는 없다.
     *
     * 출발 도시와 도착 도시가 서로 같은 경우는 없다.
     * @param N
     * @param sCity 출발 도시
     * @param eCity 도착 도시
     * @param mCost
     * @return
     */
    public int init(int N, int sCity[], int eCity[], int mCost[]) {
        mapping = new ArrayList[MAX_CITY_NUM];
        reverseMapping = new ArrayList[MAX_CITY_NUM];
        cityInfo = new HashMap<>();

        for(int i = 0 ; i < MAX_CITY_NUM; i++){
            mapping[i] = new ArrayList<>();
            reverseMapping[i] = new ArrayList<>();
        }

        for(int i = 0 ; i < N; i++){
            int from = sCity[i];
            int to = eCity[i];
            if(!cityInfo.containsKey(sCity[i])){
                cityInfo.put(sCity[i], cityInfo.size());
            }
            if(!cityInfo.containsKey(eCity[i])){
                cityInfo.put(eCity[i], cityInfo.size());
            }

            add(sCity[i], eCity[i], mCost[i]);
        }
        totalCityNum = cityInfo.size();

        return totalCityNum;
    }

    /**
     *
     * @param sCity
     * @param eCity
     * @param mCost
     */

    public void add(int sCity, int eCity, int mCost) {
        mapping[cityInfo.get(sCity)].add(new City(cityInfo.get(eCity), mCost));
        reverseMapping[cityInfo.get(eCity)].add(new City(cityInfo.get(sCity), mCost));
    }

    public int cost(int mHub) {
        return getMinSum(mapping, mHub) + getMinSum(reverseMapping, mHub);
    }

    public int getMinSum(List<City> info[], int mHub){
        PriorityQueue<City> pq = new PriorityQueue<>();
        pq.add(new City(cityInfo.get(mHub), 0));
        int[] cost = new int[totalCityNum];
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[cityInfo.get(mHub)] = 0;
        while(!pq.isEmpty()){
            City newCity = pq.poll();
            if(cost[newCity.to] < newCity.cost) continue;
            for(City nearCity : info[newCity.to]){
                if(cost[nearCity.to] > cost[newCity.to]+nearCity.cost){
                    cost[nearCity.to] = cost[newCity.to]+nearCity.cost;
                    pq.add(new City(nearCity.to, cost[nearCity.to]));
                }
            }
        }

        return Arrays.stream(cost).sum();
    }
}
