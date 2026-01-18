import java.util.LinkedList;
import java.util.Queue;

/**
 * 547. Number of Provinces
 */
public class _547 {
    // Time: O(n^2*alpha(n))
    // Space: O(n)
    class Solution1_UnionFind {
        class UF {
            int[] parent;
            int[] size;
            int components;

            UF(int n) {
                parent = new int[n];
                size = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
                components = n;
            }

            void union(int n1, int n2) {
                int r1 = find(n1);
                int r2 = find(n2);
                if (r1 != r2) {
                    if (size[r1] >= size[r2]) {
                        parent[r2] = r1;
                        size[r1] += size[r2];
                    } else {
                        parent[r1] = r2;
                        size[r2] += size[r1];
                    }
                    components--;
                }
            }

            int find(int node) {
                if (parent[node] != node) {
                    parent[node] = find(parent[node]);
                }
                return parent[node];
            }
        }

        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            UF uf = new UF(n);
            for (int n1 = 0; n1 < n; n1++) {
                for (int n2 = n1 + 1; n2 < n; n2++) {
                    if (isConnected[n1][n2] == 1) {
                        uf.union(n1, n2);
                    }
                }
            }
            return uf.components;
        }
    }

    // connected components
    // undirected graph
    // Time: O(N^2)
    // Space: O(N)
    class Solution1_DFS {
        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            int province = 0;
            boolean[] visited = new boolean[n];
            for (int start = 0; start < n; start++) {
                if (!visited[start]) {
                    province++;
                    dfs(isConnected, start, -1, visited);
                }
            }
            return province;
        }

        private void dfs(int[][] graph, int node, int parent, boolean[] visited) {
            visited[node] = true;
            for (int nbr = 0; nbr < graph.length; nbr++) {
                if (nbr == node || nbr == parent || graph[node][nbr] == 0) {
                    continue;
                }
                if (!visited[nbr]) {
                    dfs(graph, nbr, node, visited);
                }
            }
        }
    }

    // same as DFS approach, use queue instead of recursion stack
    class Solution2_BFS {
        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            boolean[] visited = new boolean[n];
            int provinces = 0;
            
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    bfs(isConnected, visited, i);
                    provinces++;
                }
            }
            
            return provinces;
        }
        
        private void bfs(int[][] isConnected, boolean[] visited, int start) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(start);
            visited[start] = true;
            
            while (!queue.isEmpty()) {
                int city = queue.poll();
                
                for (int j = 0; j < isConnected.length; j++) {
                    if (isConnected[city][j] == 1 && !visited[j]) {
                        queue.offer(j);
                        visited[j] = true; // Mark when adding to queue
                    }
                }
            }
        }
    }
}
