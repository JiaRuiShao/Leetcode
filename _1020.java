/**
 * 1020. Number of Enclaves
 */
public class _1020 {
    class Solution1_DFS {
        public int numEnclaves(int[][] grid) {
            int m = grid.length, n = grid[0].length, land = 1;
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
                        islands++;
                    }
                }
            }
            return islands;
        }
    
        private void dfs(int[][] grid, int r, int c) {
            int m = grid.length, n = grid[0].length, water = 0;
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
