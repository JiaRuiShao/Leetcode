import java.util.ArrayList;
import java.util.List;

/**
 * 2858. Minimum Edge Reversals So Every Node Is Reachable
 */
public class _2858 {
    // Time: O(E+N) = O(N) since E = N - 1
    // Space: O(E+N) = O(N)
    class Solution1_DFS_TreeReRoot {
        // if it's a tree, there should be only one valid path from node i to node j
        // connected with no cycle
        public int[] minEdgeReversals(int n, int[][] edges) {
            // build undirected graph
            List<int[]>[] graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int[] edge : edges) {
                int from = edge[0], to = edge[1];
                graph[from].add(new int[]{to, 0});
                graph[to].add(new int[]{from, 1});
            }

            // traverse from node 0 to build the path
            int[] minEdges = new int[n];
            minEdges[0] = dfs(graph, 0, -1);
            reRoot(graph, 0, -1, minEdges);
            return minEdges;
        }

        // cost[0] = min(cost[1..n-1])
        private int dfs(List<int[]>[] graph, int node, int parent) {
            int minCost = 0;
            for (int[] edge : graph[node]) {
                int nbr = edge[0], cost = edge[1];
                if (nbr == parent) {
                    continue;
                }
                minCost += cost + dfs(graph, nbr, node);
            }
            return minCost;
        }

        // nodes in tree are always connected and one path only
        private void reRoot(List<int[]>[] graph, int node, int parent, int[] minEdges) {
            for (int[] edge : graph[node]) {
                int nbr = edge[0], cost = edge[1];
                if (nbr == parent) {
                    continue;
                }
                if (cost == 0) { // reverse the edge
                    minEdges[nbr] = minEdges[node] + 1;
                } else { // no need to reverse
                    minEdges[nbr] = minEdges[node] - 1;
                }
                // recurse to all children
                reRoot(graph, nbr, node, minEdges);
            }
        }
    }
}
