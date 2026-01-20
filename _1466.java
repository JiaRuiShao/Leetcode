import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 1466. Reorder Routes to Make All Paths Lead to the City Zero
 */
public class _1466 {
    // build directed graph with costs -- original edge 0 cost; reverse edges 1 cost
    // find max cost for nodes [1..n-1] to node 0
    // however this takes O(n^2) time
    // Optimized: build reversed cost graph and find max cost needed for node 0 to all other nodes
    // this takes O(n) time -- it's a tree so E = V - 1
    class Solution1_DFS {
        class Edge {
            int node, cost;
            Edge(int node, int cost) {
                this.node = node;
                this.cost = cost;
            }
        }

        public int minReorder(int n, int[][] connections) {
            List<Edge>[] graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int[] conn : connections) {
                graph[conn[0]].add(new Edge(conn[1], 1)); // original edge with cost 1
                graph[conn[1]].add(new Edge(conn[0], 0)); // reverse edge with cost 0
            }

            boolean[] visited = new boolean[n];
            return dfs(graph, 0, visited);
        }

        private int dfs(List<Edge>[] graph, int node, boolean[] visited) {
            visited[node] = true;
            int cost = 0;
            for (Edge edge : graph[node]) {
                if (!visited[edge.node]) {
                    cost += edge.cost;
                    cost += dfs(graph, edge.node, visited);
                }
            }
            return cost;
        }
    }

    class Solution2_BFS {
        public int minReorder(int n, int[][] connections) {
            List<List<int[]>> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
            }
            
            for (int[] conn : connections) {
                graph.get(conn[0]).add(new int[]{conn[1], 1}); // original
                graph.get(conn[1]).add(new int[]{conn[0], 0}); // reverse
            }
            
            Queue<Integer> queue = new LinkedList<>();
            boolean[] visited = new boolean[n];
            queue.offer(0);
            visited[0] = true;
            
            int changes = 0;
            
            while (!queue.isEmpty()) {
                int city = queue.poll();
                
                for (int[] edge : graph.get(city)) {
                    int neighbor = edge[0];
                    int needsReversal = edge[1];
                    
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        changes += needsReversal;
                        queue.offer(neighbor);
                    }
                }
            }
            
            return changes;
        }
    }
}
