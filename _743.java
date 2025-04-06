import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 743. Network Delay Time
 */
public class _743 {
    class Solution1_Dijkstra_Without_AdjList {
        // convert to 0-based index
        // return -1 if not all nodes traversed
        // Time: O(ne + (n + e)logn)
        public int networkDelayTime(int[][] times, int n, int k) {
            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE);

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
            pq.offer(new int[]{k, 0});
            dist[k - 1] = 0;

            while (!pq.isEmpty()) {
                int[] pair = pq.poll();
                int curr = pair[0];
                int weight = pair[1];
                if (weight > dist[curr - 1]) continue;
                for (int[] time : times) {
                    if (time[0] != curr) {
                        continue;
                    }
                    int nbr = time[1];
                    int nbrDist = weight + time[2];
                    if (nbrDist < dist[nbr - 1]) {
                        pq.offer(new int[]{nbr, nbrDist});
                        dist[nbr - 1] = nbrDist;
                    }
                }
            }

            int maxTime = 0;
            for (int time : dist) {
                maxTime = Math.max(maxTime, time);
            }
            return maxTime == Integer.MAX_VALUE ? -1 : maxTime;
        }
    }

    class Solution2_Dijkstra_With_AdjList {
        // Time: O(e + (n+e)logn)
        public int networkDelayTime(int[][] times, int n, int k) {
            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE);

            List<int[]>[] graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
        
            for (int[] time : times) {
                int from = time[0] - 1;
                int to = time[1] - 1;
                int weight = time[2];
                graph[from].add(new int[]{to, weight});
            }

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1])); // {next, dist}
            k--;
            pq.offer(new int[]{k, 0});
            dist[k] = 0;

            while (!pq.isEmpty()) {
                int[] pair = pq.poll();
                int curr = pair[0];
                int weight = pair[1];
                if (weight > dist[curr]) continue;
                for (int[] nbrPair : graph[curr]) {
                    int nbr = nbrPair[0];
                    int nbrDist = weight + nbrPair[1];
                    if (nbrDist < dist[nbr]) {
                        pq.offer(new int[]{nbr, nbrDist});
                        dist[nbr] = nbrDist;
                    }
                }
            }

            int maxTime = 0;
            for (int time : dist) {
                maxTime = Math.max(maxTime, time);
            }
            return maxTime == Integer.MAX_VALUE ? -1 : maxTime;
        }
    }
}
