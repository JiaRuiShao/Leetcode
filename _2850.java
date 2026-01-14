import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2850. Minimum Moves to Spread Stones Over Grid
 */
public class _2850 {
    // Time: O((ek)^k) where e is num of cells with spare stones & k is num of empty cells in the grid
    // notice this is a looser bound because some branches are early pruned; a tighter bound would be O(e^k*k!)
    // Space: max(O(k), O(e))
    class Solution1_Backtrack {
        private int minMoves;
        public int minimumMoves(int[][] grid) {
            minMoves = Integer.MAX_VALUE;
            int n = grid.length; // 3
            List<int[]> extra = new ArrayList<>();
            List<int[]> empty = new ArrayList<>();
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == 0) empty.add(new int[]{r, c});
                    else if (grid[r][c] > 1) extra.add(new int[]{r, c});
                }
            }
            backtrack(grid, extra, empty, empty.size(), 0);
            return minMoves;
        }

        private void backtrack(int[][] grid, List<int[]> extra, List<int[]> empty, int count, int currMoves) {
            if (count == 0) {
                minMoves = Math.min(minMoves, currMoves);
                return;
            }
            for (int[] from : extra) {
                if (grid[from[0]][from[1]] == 1) continue; // no spare stone
                for (int[] to : empty) {
                    if (grid[to[0]][to[1]] == 1) continue; // already have stone filled
                    int cost = getManhattanDist(from, to);
                    grid[from[0]][from[1]]--;
                    grid[to[0]][to[1]]++;
                    backtrack(grid, extra, empty, count - 1, currMoves + cost);
                    grid[from[0]][from[1]]++;
                    grid[to[0]][to[1]]--;
                }
            }
        }

        private int getManhattanDist(int[] from, int[] to) {
            return Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
        }
    }

    // Time: O((sk)^k) where s is source count and k is target count
    // Space: O(k) for recursion stack
    class Solution1_Backtrack_WithoutGlobalVar {
        public int minimumMoves(int[][] grid) {
            int n = grid.length; // 3
            List<int[]> sources = new ArrayList<>();
            List<int[]> targets = new ArrayList<>();
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == 0) targets.add(new int[]{r, c});
                    else if (grid[r][c] > 1) sources.add(new int[]{r, c});
                }
            }
            return backtrack(grid, sources, targets, targets.size());
        }

        private int backtrack(int[][] grid, List<int[]> sources, List<int[]> targets, int count) {
            if (count == 0) return 0;
            int minMoves = Integer.MAX_VALUE;
            for (int[] from : sources) {
                if (grid[from[0]][from[1]] == 1) continue; // no spare stone
                for (int[] to : targets) {
                    if (grid[to[0]][to[1]] == 1) continue; // already have stone filled
                    int distance = Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
                    grid[from[0]][from[1]]--;
                    grid[to[0]][to[1]]++;
                    int otherDistance = backtrack(grid, sources, targets, count - 1);
                    if (otherDistance < Integer.MAX_VALUE) {
                        minMoves = Math.min(minMoves, distance + otherDistance);
                    }
                    grid[from[0]][from[1]]++;
                    grid[to[0]][to[1]]--;
                }
            }
            return minMoves;
        }
    }

    // Time: O(n*2^n)
    class Solution2_DP_BottomUp {
        public int minimumMoves(int[][] grid) {
            List<int[]> sources = new ArrayList<>();
            List<int[]> targets = new ArrayList<>();
            
            // Collect sources and targets
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i][j] > 1) {
                        for (int k = 1; k < grid[i][j]; k++) {
                            sources.add(new int[]{i, j});
                        }
                    } else if (grid[i][j] == 0) {
                        targets.add(new int[]{i, j});
                    }
                }
            }
            
            int n = targets.size();
            
            // Precompute distances, sources.size usually <= targets.size
            int[][] dist = new int[n][n];
            for (int i = 0; i < sources.size(); i++) {
                for (int j = 0; j < targets.size(); j++) {
                    dist[i][j] = Math.abs(sources.get(i)[0] - targets.get(j)[0]) 
                            + Math.abs(sources.get(i)[1] - targets.get(j)[1]);
                }
            }
            
            // DP table: dp[mask][i] = min cost to assign targets in mask, last assigned target is i
            // Actually, simpler: dp[sourceIdx][mask] = min cost using first sourceIdx sources to fill mask targets
            int[][] dp = new int[n + 1][1 << n];
            
            for (int[] row : dp) {
                Arrays.fill(row, Integer.MAX_VALUE / 2);
            }
            dp[0][0] = 0;
            
            // Fill DP table
            for (int srcIdx = 0; srcIdx < n; srcIdx++) {
                for (int mask = 0; mask < (1 << n); mask++) {
                    if (dp[srcIdx][mask] == Integer.MAX_VALUE / 2) continue;
                    
                    // Try assigning source srcIdx to each unassigned target
                    for (int tgtIdx = 0; tgtIdx < n; tgtIdx++) {
                        if ((mask & (1 << tgtIdx)) == 0) {  // Target not assigned yet
                            int newMask = mask | (1 << tgtIdx);
                            dp[srcIdx + 1][newMask] = Math.min(
                                dp[srcIdx + 1][newMask],
                                dp[srcIdx][mask] + dist[srcIdx][tgtIdx]
                            );
                        }
                    }
                }
            }
            
            return dp[n][(1 << n) - 1];
        }
    }
}
