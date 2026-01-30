import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 827. Making A Large Island
 */
public class _827 {
    // Time: O(n^2)
    // Space: O(n^2)
    class Solution1_DFS {
        // BF DFS/BFS: flip every 0 and count O(n^4), O(n^2)
        // Label each island and precompute their size, then flip 0
        public int largestIsland(int[][] grid) {
            int n = grid.length, maxIsland = 0;
            int id = 2;
            Map<Integer, Integer> islandSize = new HashMap<>();
            // label the island & precompute size for each island
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == 1) {
                        islandSize.put(id, labelDFS(grid, r, c, id));
                        maxIsland = Math.max(maxIsland, islandSize.get(id)); // input could be all 1s
                        id++;
                    }
                }
            }
            // flip each 0
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == 0) {
                        grid[r][c] = 1;
                        int size = count(grid, r, c, islandSize, new HashSet<>());
                        maxIsland = Math.max(maxIsland, size); // input could be all 0s
                        grid[r][c] = 0;
                    }
                }
            }
            return maxIsland;
        }

        private int count(int[][] grid, int r, int c, Map<Integer, Integer> map, Set<Integer> visited) {
            int n = grid.length;
            int count = 1;
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr < 0 || nc < 0 || nr >= n || nc >= n || grid[nr][nc] == 0 || visited.contains(grid[nr][nc])) {
                    continue;
                }
                visited.add(grid[nr][nc]);
                count += map.getOrDefault(grid[nr][nc], 0);
            }
            return count;
        }

        private int labelDFS(int[][] grid, int r, int c, int id) {
            int n = grid.length;
            if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] != 1) { // cannot check grid[r][c] == 0 here because we would visit a cell multiple times (no revisit tracking!)
                return 0;
            }
            int count = 1;
            grid[r][c] = id;
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                count += labelDFS(grid, nr, nc, id);
            }
            return count;
        }
    }
}