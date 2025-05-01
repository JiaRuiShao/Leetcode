import java.util.LinkedList;
import java.util.Queue;

/**
 * 200. Number of Islands
 */
public class _200 {
    // Time: O(mn)
    // Space: O(mn)
    class Solution_DFS {
        public int numIslands(char[][] grid) {
            int m = grid.length, n = grid[0].length;
            int islands = 0;
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == '1') {
                        islands++;
                        dfs(grid, r, c);
                    }
                }
            }
            return islands;
        }
    
        private void dfs(char[][] grid, int r, int c) {
            int m = grid.length, n = grid[0].length;
            if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == '0') {
                return;
            }
            grid[r][c] = '0';
            dfs(grid, r - 1, c);
            dfs(grid, r + 1, c);
            dfs(grid, r, c - 1);
            dfs(grid, r, c + 1);
        }
    }

    public class Solution2_BFS {
        public int numIslands(char[][] grid) {
            int m = grid.length, n = grid[0].length;
            int islands = 0;
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == '1') {
                        islands++;
                        bfs(grid, r, c);
                    }
                }
            }
            return islands;
        }

        private void bfs(char[][] grid, int startR, int startC) {
            int m = grid.length, n = grid[0].length;
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{startR, startC});
            grid[startR][startC] = '0'; // mark as visited

            int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};

            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                int r = cell[0], c = cell[1];

                for (int[] dir : directions) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == '1') {
                        grid[nr][nc] = '0';
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
    }

}
