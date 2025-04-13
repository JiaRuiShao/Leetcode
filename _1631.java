import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 1631. Path With Minimum Effort
 */
public class _1631 {
    // Time: O(mnlog(mn))
    // Space: O(mn)
    class Solution1_Dijkstra {
        public int minimumEffortPath(int[][] heights) {
            int m = heights.length, n = heights[0].length;
            int[][] effort = new int[m][n];
            for (int i = 0; i < m; i++) {
                Arrays.fill(effort[i], Integer.MAX_VALUE);
            }

            // {i, j, effortFromStart}
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
            effort[0][0] = 0;
            pq.offer(new int[]{0, 0, 0});

            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            while (!pq.isEmpty()) {
                int[] pair = pq.poll();
                int r = pair[0];
                int c = pair[1];
                int e = pair[2];
                if (r == m - 1 && c == n - 1) {
                    return e;
                }
                if (e > effort[r][c]) {
                    continue;
                }
                for (int[] dir : directions) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];
                    if (nr < 0 || nc < 0 || nr >= m || nc >= n) {
                        continue;
                    }
                    int nh = Math.abs(heights[nr][nc] - heights[r][c]);
                    int ne = Math.max(nh, e);
                    if (ne < effort[nr][nc]) {
                        pq.offer(new int[]{nr, nc, ne});
                        effort[nr][nc] = ne;
                    }
                }
            }

            return -1;
        }
    }

    class Solution2_Binary_Search_BFS {
        // Time: O(log(10^6)mn)
        public int minimumEffortPath(int[][] heights) {
            int m = heights.length, n = heights[0].length;
            int left = 0, right = 1_000_000;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (bfs(heights, mid, m, n)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }

        // Can we reach bottom-right with max effort ≤ limit?
        private boolean bfs(int[][] heights, int limit, int m, int n) {
            Queue<int[]> queue = new LinkedList<>();
            boolean[][] visited = new boolean[m][n];
            queue.offer(new int[]{0, 0});
            visited[0][0] = true;

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int r = curr[0], c = curr[1];
                if (r == m - 1 && c == n - 1) return true;

                for (int[] dir : dirs) {
                    int nr = r + dir[0], nc = c + dir[1];
                    if (nr < 0 || nc < 0 || nr >= m || nc >= n || visited[nr][nc]) continue;

                    int effort = Math.abs(heights[nr][nc] - heights[r][c]);
                    if (effort <= limit) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            return false;
        }
    }

    class Solution2_Binary_Search_DFS {
        // Time: O(log(10^6)mn)
        public int minimumEffortPath(int[][] heights) {
            int m = heights.length, n = heights[0].length;
            int left = 0, right = 1_000_000;
        
            while (left <= right) {
                int mid = left + (right - left) / 2;
                boolean[][] visited = new boolean[m][n];
                if (dfs(heights, 0, 0, mid, visited)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        
            return left;
        }
        
        private boolean dfs(int[][] heights, int r, int c, int limit, boolean[][] visited) {
            int m = heights.length, n = heights[0].length;
            if (r == m - 1 && c == n - 1) return true;
            visited[r][c] = true;
        
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr < 0 || nc < 0 || nr >= m || nc >= n || visited[nr][nc]) continue;
        
                int effort = Math.abs(heights[nr][nc] - heights[r][c]);
                if (effort <= limit && dfs(heights, nr, nc, limit, visited)) {
                    return true;
                }
            }
            return false;
        }        
    }

    public class Solution4_Kruskal {
        // Union-Find implementation
        class UF {
            int[] parent;

            UF(int n) {
                parent = new int[n];
                for (int i = 0; i < n; i++) parent[i] = i;
            }

            int find(int x) {
                if (x != parent[x]) parent[x] = find(parent[x]);
                return parent[x];
            }

            void union(int x, int y) {
                int px = find(x), py = find(y);
                if (px != py) parent[px] = py;
            }

            boolean connected(int x, int y) {
                return find(x) == find(y);
            }
        }

        public int minimumEffortPath(int[][] heights) {
            int m = heights.length, n = heights[0].length;
            int total = m * n;

            // Step 1: Build edges to adjacent cells (go down/right)
            List<int[]> edges = new ArrayList<>();
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    int id = r * n + c;
                    if (r + 1 < m) {
                        int diff = Math.abs(heights[r][c] - heights[r + 1][c]);
                        edges.add(new int[]{id, (r + 1) * n + c, diff});
                    }
                    if (c + 1 < n) {
                        int diff = Math.abs(heights[r][c] - heights[r][c + 1]);
                        edges.add(new int[]{id, r * n + (c + 1), diff});
                    }
                }
            }

            // Step 2: Sort edges by effort
            edges.sort(Comparator.comparingInt(a -> a[2]));

            // Step 3: Use Union-Find to connect components
            UF uf = new UF(total);
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], w = edge[2];
                uf.union(u, v);
                if (uf.connected(0, total - 1)) return w; // Start and end connected
            }

            return 0; // start == end
        }
    }

}
