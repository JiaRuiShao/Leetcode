import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 787. Cheapest Flights Within K Stops
 */
public class _787 {
    // Dijkstra with state management
    // Time: O(N+E+EKlogEK)
    // Space: O(N+E+NK+EK)
    class Solution1_Dijkstra {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            List<int[]>[] graph = new ArrayList[n];
            for (int city = 0; city < n; city++) {
                graph[city] = new ArrayList<>();
            }

            for (int[] flight : flights) {
                graph[flight[0]].add(new int[]{flight[1], flight[2]});
            }

            int[][] minCost = new int[n][k + 2]; // at most k+1 stops (excluding start)
            for (int i = 0; i < n; i++) {
                Arrays.fill(minCost[i], Integer.MAX_VALUE);
            }
            minCost[src][0] = 0;

            PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
            minHeap.offer(new int[]{src, 0, 0}); // [to, cost, stops]

            while (!minHeap.isEmpty()) {
                int[] flight = minHeap.poll();
                int from = flight[0], cost = flight[1], stop = flight[2];
                if (from == dst) {
                    return cost;
                }
                if (stop == k + 1) { // already k+1 stops
                    continue;
                }
                for (int[] next : graph[from]) {
                    int to = next[0], nextCost = cost + next[1];
                    if (nextCost < minCost[to][stop + 1]) {
                        minHeap.offer(new int[]{to, nextCost, stop + 1});
                        minCost[to][stop + 1] = nextCost;
                    }
                }
            }
            return -1;
        }
    }

    // Constrained Bellman Ford -- K+1 instead of V-1 iterations
    // Time: O(KE)
    // Space: O(N)
    class Solution2_BellmanFord {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            int[] prev = new int[n];
            Arrays.fill(prev, Integer.MAX_VALUE);
            prev[src] = 0;
            
            // K+1 iterations (K stops = K+1 flights)
            for (int i = 0; i <= k; i++) {
                int[] curr = Arrays.copyOf(prev, n); // ** IMPORTANT: use previous iteration **
                
                for (int[] flight : flights) {
                    int from = flight[0];
                    int to = flight[1];
                    int price = flight[2];
                    
                    if (prev[from] != Integer.MAX_VALUE) {
                        curr[to] = Math.min(curr[to], prev[from] + price);
                    }
                }
                
                prev = curr;
            }
            
            return prev[dst] == Integer.MAX_VALUE ? -1 : prev[dst];
        }
    }
}
