/**
 * 695. Max Area of Island
 */
public class _695 {
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        int m = grid.length, n = grid[0].length, land = 1;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == land) {
                    maxArea = Math.max(maxArea, dfs(grid, r, c));
                }
            }
        }
        return maxArea;
    }
    
    private int dfs(int[][] grid, int r, int c) {
        int m = grid.length, n = grid[0].length, water = 0, lands = 1;
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == water) {
            return 0;
        }
        grid[r][c] = water;
        lands += dfs(grid, r - 1, c);
        lands += dfs(grid, r + 1, c);
        lands += dfs(grid, r, c - 1);
        lands += dfs(grid, r, c + 1);
        return lands;
    }
}
