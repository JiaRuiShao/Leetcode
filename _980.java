/**
 * 980. Unique Paths III
 */
public class _980 {
    // Time: O(3^mn) -- the reason it's not 4^mn is because we never go back to where we're from thanks to visited[][]
    // Space: O(mn)
    class Solution1_Backtrack_Permutation_ElementsUsedOnce {
        private int paths;

        public int uniquePathsIII(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            paths = 0;
            int startR = 0, startC = 0, emptySquares = 0;
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == 1) {
                        startR = r;
                        startC = c;
                    }
                    if (grid[r][c] == 0 || grid[r][c] == 1) {
                        emptySquares++;
                    }
                }
            }
            dfs(grid, startR, startC, emptySquares, new boolean[m][n]);
            return paths;
        }

        private void dfs(int[][] grid, int row, int col, int need, boolean[][] visited) {
            int m = grid.length, n = grid[0].length;
            if (row < 0 || row >= m || col < 0 || col >= n || visited[row][col] || grid[row][col] == -1
                    || (grid[row][col] == 2 && need > 0)) {
                return;
            }
            if (need == 0) {
                if (grid[row][col] == 2) {
                    paths++;
                }
                return;
            }
            visited[row][col] = true;
            need--;
            dfs(grid, row - 1, col, need, visited);
            dfs(grid, row + 1, col, need, visited);
            dfs(grid, row, col - 1, need, visited);
            dfs(grid, row, col + 1, need, visited);
            need++;
            visited[row][col] = false;
        }
    }

}
