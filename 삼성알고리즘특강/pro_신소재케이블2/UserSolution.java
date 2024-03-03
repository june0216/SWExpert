package 삼성알고리즘특강.pro_신소재케이블2;
import java.util.*;
class UserSolution
{

    class Device implements Comparable<Device>{
        public int num;
        public int cost;

        public Device(int num, int cost){
            this.num = num;
            this.cost = cost;
        }
        @Override
        public int compareTo(Device device){
            return device.cost - this.cost;
        }
    }
    public Map<Integer, Integer> deviceInfo;
    public List<List<Device>> connectDevice;
    public int index;

    PriorityQueue<Device> pq;
    boolean[] visit;
    int temp[];

    /**
     * 초기에 장비 mDevice가 있다. mDevice는 장비 번호이다.
     * @param mDevice
     */
    public void init(int mDevice)
    {
        index = 0;
        deviceInfo = new HashMap<>();
        deviceInfo.put(mDevice, index);
        connectDevice = new ArrayList<>();
        connectDevice.add(new ArrayList<>());
        index++;

    }

    /**
     * 장비 mDevice1에서 장비 mDevice2로 신호를 전송했을 때 전송 시간을 반환한다.
     * @param mOldDevice
     * @param mNewDevice
     * @param mLatency
     */

    public void connect(int mOldDevice, int mNewDevice, int mLatency)
    {
        int newIndex = index;
        int oldIndex = deviceInfo.get(mOldDevice);
        deviceInfo.put(mNewDevice, newIndex );

        ArrayList<Device> newList = new ArrayList<>();

        connectDevice.get(oldIndex).add(new Device(newIndex, mLatency));
        newList.add(new Device(oldIndex, mLatency));
        connectDevice.add(newList);
        index++;



        return;
    }

    public int measure(int mDevice1, int mDevice2)
    {
        Queue<Integer[]> que = new LinkedList<>();
        boolean[] visited = new boolean[index];
        int from = deviceInfo.get(mDevice1);
        int to = deviceInfo.get(mDevice2);
        que.add(new Integer[]{from, 0});
        visited[from] = true;

        while(!que.isEmpty()){
            Integer[] now = que.poll();
            int nowIndex = now[0];
            int cost = now[1];
            for(Device next: connectDevice.get(nowIndex)){
                if(visited[next.num]) continue;
                if(next.num == to){
                    return cost+next.cost;
                }
                visited[next.num] = true;

                que.add(new Integer[]{next.num, cost+next.cost});
            }
        }
        return -1;
    }

    /**
     * 신호를 모니터링하는 장비를 장비 mDevice로 하고 테스트를 진행한다.
     * @param mDevice
     * @return 전송 시간이 최대가 되도록 보내는 장비와 받는 장비를 선택하고 이때의 전송 시간을 반환
     */
    public int test(int mDevice) {
        temp = new int[index];
        int start = deviceInfo.get(mDevice);
        pq = new PriorityQueue<>();
        visit = new boolean[index];

        bfs(mDevice);
        Device farthest = pq.poll();
        int firstMaxDistance = farthest.cost;
        int current = farthest.num;
        visit = new boolean[index];
        while (true) {
            if(current == deviceInfo.get(mDevice)) break;
            visit[current] = true;
            current = temp[current];
        }
       // markPathVisited(farthest.num, mDevice);

        pq.clear();
        bfs(mDevice);

        int secondMaxDistance = pq.isEmpty() ? 0 :pq.poll().cost;

        return firstMaxDistance + secondMaxDistance;
    }

    private void markPathVisited(int node, int target) {

        Integer current = node;
        while (true) {
            if(current == deviceInfo.get(target)) break;
            visit[current] = true;
            current = temp[current];
        }
    }

    private void bfs(int mDevice) {
        int start = deviceInfo.get(mDevice);
        Queue<Integer[]> queue = new LinkedList<>();
        queue.offer(new Integer[]{start, 0});
        visit[start] = true;

        while (!queue.isEmpty()) {
            Integer[] current = queue.poll();
            int currentIndex = current[0];
            int currentCost = current[1];

            for (Device nextDevice : connectDevice.get(currentIndex)) {
                if (visit[nextDevice.num]) continue;
                visit[nextDevice.num] = true;
                temp[nextDevice.num] = currentIndex;
                int nextCost = currentCost + nextDevice.cost;
                queue.add(new Integer[]{nextDevice.num, nextCost});
                pq.add(new Device(nextDevice.num, nextCost));
            }
        }
    }
}
