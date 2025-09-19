import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 994. Rotting Oranges
 */
public class _994 {
    class Solution1_BFS {
        public int orangesRotting(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            Queue<int[]> q = new ArrayDeque<>();
            // enqueue all rotten oranges
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == 2) {
                        q.offer(new int[]{r, c});
                    }
                }
            }
            // bfs until there're no orange left to become rotten
            int time = 0;
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            while (!q.isEmpty()) {
                int size = q.size(), orangesTurned = 0;
                for (int i = 0; i < size; i++) {
                    int[] curr = q.poll();
                    int r = curr[0], c = curr[1];
                    for (int[] dir : dirs) {
                        int nr = r + dir[0], nc = c + dir[1];
                        if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                            q.offer(new int[]{nr, nc});
                            grid[nr][nc] = 2;
                            orangesTurned++;
                        }
                    }
                }
                if (orangesTurned > 0) time++;
            }
            // traverse again to see if there's any fresh oranges left
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == 1) {
                        return -1;
                    }
                }
            }
            return time;
        }
    }
}
