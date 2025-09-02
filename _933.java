import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

/**
 * 933. Number of Recent Calls
 */
public class _933 {
    // use TreeMap to record ping time and num of requests up until that time
    // Time: O(logn) where n is total number of requests
    // Space: O(n)
    class RecentCounter1 {
        int requests;
        TreeMap<Integer, Integer> pings;

        public RecentCounter1() {
            requests = 0;
            pings = new TreeMap<>();
            pings.put(0, 0);
        }
        
        public int ping(int t) {
            requests++;
            Integer lowerKey = pings.lowerKey(t - 3000); // find last ping time < t - 3000
            int lastRequests = lowerKey == null ? 0 : pings.get(lowerKey);
            pings.put(t, requests);
            return requests - lastRequests;
        }
    }

    class RecentCounter {
        Queue<Integer> pings;

        public RecentCounter() {
            pings = new LinkedList<>();
        }
        
        public int ping(int t) {
            int threshold = t - 3000;
            while(!pings.isEmpty() && pings.peek() < threshold) {
                pings.poll();
            }
            pings.offer(t);
            return pings.size();
        }
    }
}
