import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 694. Number of Distinct Islands
 */
public class _694 {
    class Solution_Matrix_Serialization_DFS {
        // serialize each island traversal process
        public int numDistinctIslands(int[][] grid) {
            int m = grid.length, n = grid[0].length, land = 1;
            Set<String> islands = new HashSet<>();
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == land) {
                        StringBuilder sb = new StringBuilder();
                        dfs(grid, r, c, 0, sb);
                        islands.add(sb.toString());
                    }
                }
            }
            return islands.size();
        }

        private void dfs(int[][] grid, int r, int c, int dir, StringBuilder sb) {
            int m = grid.length, n = grid[0].length, water = 0;
            if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == water) {
                return;
            }
            grid[r][c] = water;
            sb.append(dir);
            dfs(grid, r - 1, c, 1, sb);
            dfs(grid, r + 1, c, 2, sb);
            dfs(grid, r, c - 1, 3, sb);
            dfs(grid, r, c + 1, 4, sb);
            sb.append(-dir);
        }
    }

    class Solution2_BFS {
        public int numDistinctIslands(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            Set<String> islands = new HashSet<>();

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == 1) {
                        islands.add(bfs(grid, r, c));
                    }
                }
            }

            return islands.size();
        }

        private String bfs(int[][] grid, int startR, int startC) {
            int m = grid.length, n = grid[0].length;
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{startR, startC});
            grid[startR][startC] = 0;

            StringBuilder sb = new StringBuilder();
            
            sb.append('^');
            int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                int r = cell[0], c = cell[1];

                for (int i = 0; i < directions.length; i++) {
                    int[] dir = directions[i];
                    int nr = r + dir[0];
                    int nc = c + dir[1];
                    sb.append(i);
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                        grid[nr][nc] = 0;
                        queue.offer(new int[]{nr, nc});
                        sb.append('#');
                    }
                }
            }
            sb.append('$');
            return sb.toString();
        }

    }
}
