/**
 * 1905. Count Sub Islands
 */
public class _1905 {
    class Solution1_DFS_Flood_Fill {
        public int countSubIslands(int[][] grid1, int[][] grid2) {
            int m = grid1.length, n = grid1[0].length, land = 1, water = 0;
            int subIslands = 0;
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    // eliminate islands that's not a subIsland
                    if (grid2[r][c] == land && grid1[r][c] == water) {
                        dfs(grid2, r, c);
                    }
                }
            }
    
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    // all remaining islands are validated subIslands
                    if (grid2[r][c] == land) {
                        dfs(grid2, r, c);
                        subIslands++;
                    }
                }
            }
            return subIslands;
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

    class Solution2_My_Original_DFS_Solution {
        public int countSubIslands(int[][] grid1, int[][] grid2) {
            int m = grid1.length, n = grid1[0].length, land = 1, water = 0;
            int subIslands = 0;
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid2[r][c] == land && dfs(grid1, grid2, r, c)) {
                        subIslands++;
                    }
                }
            }
            return subIslands;
        }
    
        private boolean dfs(int[][] g1, int[][] g2, int r, int c) {
            int m = g1.length, n = g1[0].length, land = 1, water = 0;
            if (r < 0 || r >= m || c < 0 || c >= n || g2[r][c] == water) {
                return true;
            }
            g2[r][c] = water;
            boolean isSubIsland = g1[r][c] == land;
            isSubIsland = dfs(g1, g2, r - 1, c) && isSubIsland;
            isSubIsland = dfs(g1, g2, r + 1, c) && isSubIsland;
            isSubIsland = dfs(g1, g2, r, c - 1) && isSubIsland;
            isSubIsland = dfs(g1, g2, r, c + 1) && isSubIsland;
            return isSubIsland;
        }
    }
}
