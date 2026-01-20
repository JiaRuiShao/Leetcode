import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 1615. Maximal Network Rank
 */
public class _1615 {
    // Time: O(V^2)
    // Space: O(V)
    class Solution1_BF {
        // undirected graph
        // degree[i] + degree[j] (- 1 if edge from i to j)
        public int maximalNetworkRank(int n, int[][] roads) {
            Map<Integer, Set<Integer>> graph = new HashMap<>();
            for (int[] road : roads) {
                graph.computeIfAbsent(road[0], k -> new HashSet<>()).add(road[1]);
                graph.computeIfAbsent(road[1], k -> new HashSet<>()).add(road[0]);
            }

            int maxRank = 0;
            for (int i = 0; i < n - 1; i++) {
                int degreeI = graph.get(i) == null ? 0 : graph.get(i).size();
                for (int j = i + 1; j < n; j++) {
                    int degreeJ = graph.get(j) == null ? 0 : graph.get(j).size();
                    int rank = degreeI + degreeJ;
                    if (graph.get(i) != null && graph.get(i).contains(j)) {
                        rank--;
                    }
                    maxRank = Math.max(maxRank, rank);
                }
            }
            return maxRank;
        }
    }
}
