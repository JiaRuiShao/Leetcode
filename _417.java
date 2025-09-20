import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 417. Pacific Atlantic Water Flow
 */
public class _417 {
    // This question can be solved by:
    // 1. check if curr cell can flow to Pacific & Atlantic ocean for each cell
    // time would be worse than exponential; if using memorization, time complexity is still quadratic O((mn)^2)
    // 2. we can also traverse from edge cells and get the cells that they can reach separately for two oceans
    // this way the time complexity is O(mn)
    class Solution1_DFS {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        public List<List<Integer>> pacificAtlantic(int[][] heights) {
            int m = heights.length, n = heights[0].length;

            boolean[][] pacific = new boolean[m][n];
            for (int c = 0; c < n; c++) {
                dfs(heights, pacific, 0, c);
            }
            for (int r = 0; r < m; r++) {
                dfs(heights, pacific, r, 0);
            }

            boolean[][] atlantic = new boolean[m][n];
            for (int c = 0; c < n; c++) {
                dfs(heights, atlantic, m - 1, c);
            }
            for (int r = 0; r < m; r++) {
                dfs(heights, atlantic, r, n - 1);
            }

            List<List<Integer>> res = new ArrayList<>();
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (pacific[r][c] && atlantic[r][c]) {
                        res.add(List.of(r, c));
                    }
                }
            }
            
            return res;
        }

        // we can use either dfs or bfs here
        private void dfs(int[][] heights, boolean[][] visited, int r, int c) {
            int m = heights.length, n = heights[0].length;
            visited[r][c] = true;
            int height = heights[r][c];
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && heights[nr][nc] >= height && !visited[nr][nc]) {
                    dfs(heights, visited, nr, nc);
                }
            }
        }
    }

    class Solution2_BFS {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        public List<List<Integer>> pacificAtlantic(int[][] heights) {
            int m = heights.length, n = heights[0].length;

            boolean[][] pacific = new boolean[m][n];
            Queue<int[]> pacificQ = new ArrayDeque<>();
            for (int c = 0; c < n; c++) {
                pacificQ.offer(new int[]{0, c});
                pacific[0][c] = true;
            }
            for (int r = 0; r < m; r++) {
                pacificQ.offer(new int[]{r, 0});
                pacific[r][0] = true;
            }
            bfs(heights, pacific, pacificQ);

            boolean[][] atlantic = new boolean[m][n];
            Queue<int[]> atlanticQ = new ArrayDeque<>();
            for (int c = 0; c < n; c++) {
                atlanticQ.offer(new int[]{m - 1, c});
                atlantic[m - 1][c] = true;
            }
            for (int r = 0; r < m; r++) {
                atlanticQ.offer(new int[]{r, n - 1});
                atlantic[r][n - 1] = true;
            }
            bfs(heights, atlantic, atlanticQ);

            List<List<Integer>> res = new ArrayList<>();
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (pacific[r][c] && atlantic[r][c]) {
                        res.add(List.of(r, c));
                    }
                }
            }
            
            return res;
        }

        private void bfs(int[][] heights, boolean[][] visited, Queue<int[]> q) {
            int m = heights.length, n = heights[0].length;
            while (!q.isEmpty()) {
                int[] curr = q.poll();
                int r = curr[0], c = curr[1], height = heights[r][c];
                for (int[] dir : dirs) {
                    int nr = r + dir[0], nc = c + dir[1];
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && heights[nr][nc] >= height && !visited[nr][nc]) {
                        q.offer(new int[]{nr, nc});
                        visited[nr][nc] = true;
                    }
                }
            }
        }
    }
}
