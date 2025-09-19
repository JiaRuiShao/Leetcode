import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 542. 01 Matrix
 */
public class _542 {
    class Solution1_BFS {
        // notice starting from 1 would lead to O((mn)^2) time whereas starting from 0
        // is only O(mn) time
        int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length, n = mat[0].length;
            Queue<int[]> q = new ArrayDeque<>();
            boolean[][] visited = new boolean[m][n];
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (mat[r][c] == 0) {
                        q.offer(new int[] { r, c });
                    }
                }
            }
            int step = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                step++;
                for (int i = 0; i < size; i++) {
                    int[] cell = q.poll();
                    int r = cell[0], c = cell[1];
                    for (int[] dir : dirs) {
                        int nr = r + dir[0], nc = c + dir[1];
                        if (nr >= 0 && nr < m && nc >= 0 && nc < n && mat[nr][nc] == 1 && !visited[nr][nc]) {
                            q.offer(new int[] { nr, nc });
                            mat[nr][nc] = step;
                            visited[nr][nc] = true;
                        }
                    }
                }
            }
            return mat;
        }
    }

    class Solution0_DFS_Memo_TLE {
        int[][] directions = new int[][] { { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
        boolean[][] visited;
        public int[][] updateMatrix(int[][] mat) {
            int rows = mat.length, cols = mat[0].length;
            visited = new boolean[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (mat[i][j] == 0) {
                        dfs(mat, i, j, rows, cols, 0);
                    }
                }
            }
            return mat;
        }

        // level denotes the distance between the cell with 0 we are currently processing and 1
        private void dfs(int[][] mat, int i, int j, int rows, int cols, int step) {
            if (i < 0 || i >= rows || j < 0 || j >= cols || (mat[i][j] == 0 && step != 0)) {
                return;
            }
            if (visited[i][j] && mat[i][j] <= step) {
                return;
            }
            mat[i][j] = step;
            visited[i][j] = true;
            for (int[] dir : directions) {
                dfs(mat, i + dir[0], j + dir[1], rows, cols, step + 1);
            }
        }
    }
}
