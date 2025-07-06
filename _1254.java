/**
 * 1254. Number of Closed Islands
 */
public class _1254 {
    class Solution1_DFS {
        public int closedIsland(int[][] grid) {
            int m = grid.length, n = grid[0].length, land = 0;
            int islands = 0;
            for (int r = 0; r < m; r++) {
                dfs(grid, r, 0);
                dfs(grid, r, n - 1);
            }
            for (int c = 0; c < n; c++) {
                dfs(grid, 0, c);
                dfs(grid, m - 1, c);
            }
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == land) {
                        dfs(grid, r, c);
                        islands++;
                    }
                }
            }
            return islands;
        }
    
        private void dfs(int[][] grid, int r, int c) {
            int m = grid.length, n = grid[0].length, land = 0, water = 1;
            if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == water) {
                return;
            }
            grid[r][c] = water;
            dfs(grid, r - 1, c);
            dfs(grid, r + 1, c);
            dfs(grid, r, c - 1);
            dfs(grid, r, c + 1);
        }
    }
}
