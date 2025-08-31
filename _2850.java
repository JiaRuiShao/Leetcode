import java.util.ArrayList;
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
}
