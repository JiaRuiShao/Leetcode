import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 1091. Shortest Path in Binary Matrix
 */
public class _1091 {
    class Solution1_BFS {
        class Cell {
            int r, c, dist;
            public Cell(int r, int c, int dist) {
                this.r = r;
                this.c = c;
                this.dist = dist;
            }
        }

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        public int shortestPathBinaryMatrix(int[][] grid) {
            if (grid[0][0] == 1) return -1;
            int m = grid.length, n = grid[0].length;
            Cell start = new Cell(0, 0, 1);
            Queue<Cell> q = new ArrayDeque<>();
            q.offer(start);
            grid[start.r][start.c] = 1;
            while (!q.isEmpty()) {
                Cell curr = q.poll();
                if (curr.r == m - 1 && curr.c == n - 1) {
                    return curr.dist;
                }
                for (int[] dir : dirs) {
                    int nr = curr.r + dir[0], nc = curr.c + dir[1];
                    if (nr < 0 || nc < 0 || nr >= m || nc >= n || grid[nr][nc] == 1) {
                        continue;
                    }
                    q.offer(new Cell(nr, nc, curr.dist + 1));
                    grid[nr][nc] = 1;
                }
            }
            return -1;
        }
    }
}
