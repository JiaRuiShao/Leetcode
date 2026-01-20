import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1192. Critical Connections in a Network
 */
public class _1192 {
    class Solution0_BruteForce_TLE {
        // undirected graph
        // find all edges whose removal increases the number of connected components
    }

    // Time: O(V+E)
    // Space: O(V+E)
    class Solution1_TarjanAlgo {
        private int time = 0; // global counter

        public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
            List<Integer>[] graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }

            // Build undirected graph
            for (List<Integer> conn : connections) {
                int u = conn.get(0), v = conn.get(1);
                graph[u].add(v);
                graph[v].add(u);
            }

            int[] disc = new int[n]; // discovery time of node i
            int[] low = new int[n]; // lowest discovery time of subtree rooted as i
            Arrays.fill(disc, -1); // function as boolean[] visited

            List<List<Integer>> res = new ArrayList<>();
            dfs(0, -1, graph, disc, low, res);
            return res;
        }

        private void dfs(int u, int parent, List<Integer>[] graph, int[] disc, int[] low, List<List<Integer>> res) {
            disc[u] = low[u] = time++;
            for (int v : graph[u]) {
                if (v == parent) continue;

                if (disc[v] == -1) { // tree edge: v hasn't been visited
                    dfs(v, u, graph, disc, low, res);
                    low[u] = Math.min(low[u], low[v]);
                    // check if (u, v) is a bridge
                    if (low[v] > disc[u]) {
                        res.add(List.of(u, v));
                    }
                } else { // back edge: update low[u] with disc[v]
                    low[u] = Math.min(low[u], disc[v]);
                }
            }
        }
    }
}
