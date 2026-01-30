import java.util.ArrayList;
import java.util.List;

/**
 * 1971. Find if Path Exists in Graph
 */
public class _1971 {
    // Time: O(V+E)
    // Space: O(V)
    class Solution1_UF {
        class UF {
            int[] parent;
            int[] size;
            UF(int n) {
                parent = new int[n];
                size = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
            }

            void union(int n1, int n2) {
                int p1 = find(n1), p2 = find(n2);
                if (p1 != p2) {
                    if (size[p1] >= size[p2]) {
                        parent[p2] = p1;
                        size[p1] += size[p2];
                    } else {
                        parent[p1] = p2;
                        size[p2] += size[p1];
                    }
                }
            }

            int find(int node) {
                if (parent[node] != node) {
                    parent[node] = find(parent[node]);
                }
                return parent[node];
            }
        }

        public boolean validPath(int n, int[][] edges, int source, int destination) {
            UF uf = new UF(n);
            for (int[] edge : edges) {
                uf.union(edge[0], edge[1]);
            }

            int rs = uf.find(source);
            int rd = uf.find(destination);
            return rs == rd;
        }
    }

    class Solution2_DFS {
        public boolean validPath(int n, int[][] edges, int source, int destination) {
            List<Integer>[] graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int[] edge : edges) {
                graph[edge[0]].add(edge[1]);
                graph[edge[1]].add(edge[0]);
            }

            boolean[] visited = new boolean[n];
            return dfs(graph, source, destination, visited);
        }

        private boolean dfs(List<Integer>[] graph, int curr, int dst, boolean[] visited) {
            if (curr == dst) {
                return true;
            }

            visited[curr] = true;
            for (int next : graph[curr]) {
                if (!visited[next]) {
                    if (dfs(graph, next, dst, visited)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
