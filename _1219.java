import java.util.Arrays;

/**
 * 1219. Path with Maximum Gold
 */
public class _1219 {
    // Time: O(3^mn)
    class Solution1_Backtrack {
        private int maxGold;
        // private boolean[][] onPath;
        public int getMaximumGold(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            for (int r = 0 ; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] > 0) {
                        dfs(grid, r, c, 0);
                    }
                }
            }
            return maxGold;
        }

        private void dfs(int[][] grid, int r, int c, int currGold) {
            int m = grid.length, n = grid[0].length;
            if (r < 0 || c < 0 || r >= m || c >= n || grid[r][c] == 0) {
                maxGold = Math.max(maxGold, currGold);
                return;
            }
            int gridGold = grid[r][c];
            grid[r][c] = 0; // use grid[][] as onPath[][]
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                dfs(grid, r + dir[0], c + dir[1], currGold + gridGold);
            }
            grid[r][c] = gridGold;
        }
    }

    class Solution2_Backtrack_Without_Global_Variable {
        public int getMaximumGold(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            int maxGold = 0;
            for (int r = 0 ; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] > 0) {
                        maxGold = Math.max(maxGold, dfs(grid, r, c));
                    }
                }
            }
            return maxGold;
        }

        private int dfs(int[][] grid, int r, int c) {
            int m = grid.length, n = grid[0].length;
            if (r < 0 || c < 0 || r >= m || c >= n || grid[r][c] == 0) {
                return 0;
            }

            int gridGold = grid[r][c];
            grid[r][c] = 0;
            int bestNext = 0;

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                bestNext = Math.max(bestNext, dfs(grid, r + dir[0], c + dir[1]));
            }

            grid[r][c] = gridGold;
            return gridGold + bestNext;
        }
    }

    // think about why memo won't work here -- take example [ [1, 2, 3], [4, 0, 5] ] as example
    // notice how we mark the cell as onPath by setting their value to 0
    // this way leads to the wrong cached value for cells because the available cells changed for them
    class Wrong_Solution_Memo {
        private int[][] memo;
        // private boolean[][] onPath;
        public int getMaximumGold(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            memo = new int[m][n];
            for (int i = 0; i < m; i++) {
                Arrays.fill(memo[i], -1);
            }

            for (int r = 0 ; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] > 0) {
                        dfs(grid, r, c);
                    }
                }
            }

            int maxGold = 0;
            for (int r = 0 ; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (memo[r][c] > 0) {
                        maxGold = Math.max(maxGold, memo[r][c]);
                    }
                }
            }
            return maxGold;
        }

        private int dfs(int[][] grid, int r, int c) {
            int m = grid.length, n = grid[0].length;
            if (r < 0 || c < 0 || r >= m || c >= n || grid[r][c] == 0) {
                return 0;
            }
            if (memo[r][c] >= 0) {
                return memo[r][c];
            }

            int gridGold = grid[r][c];
            grid[r][c] = 0; // use grid[][] as onPath[][]
            int maxGold = 0;

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                maxGold = Math.max(maxGold, dfs(grid, r + dir[0], c + dir[1]));
            }

            grid[r][c] = gridGold;
            memo[r][c] = gridGold + maxGold;
            return memo[r][c];
        }
    }
}
