import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 542. 01 Matrix
 * 
 * - S0 BF BFS: O((mn)^2), O(mn)
 * - S1 Reverse BFS O(mn), O(mn)
 */
public class _542 {
    // BFS on each cell with value 1 to find nearest 0
    class Solution0_BF_BFS_TLE {
        public int[][] updateMatrix(int[][] mat) {
            int rows = mat.length;
            int cols = mat[0].length;
            int[][] result = new int[rows][cols];
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (mat[i][j] == 1) {
                        result[i][j] = bfsToNearestZero(mat, i, j);
                    }
                    // If mat[i][j] == 0, result[i][j] = 0 (default)
                }
            }
            
            return result;
        }
        
        private int bfsToNearestZero(int[][] mat, int startRow, int startCol) {
            int rows = mat.length;
            int cols = mat[0].length;
            
            Queue<int[]> queue = new LinkedList<>();
            boolean[][] visited = new boolean[rows][cols];
            
            queue.offer(new int[]{startRow, startCol, 0});  // row, col, distance
            visited[startRow][startCol] = true;
            
            int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
            
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int row = curr[0];
                int col = curr[1];
                int dist = curr[2];
                
                // Found a 0
                if (mat[row][col] == 0) {
                    return dist;
                }
                
                // Explore neighbors
                for (int[] dir : directions) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    
                    if (newRow >= 0 && newRow < rows && 
                        newCol >= 0 && newCol < cols && 
                        !visited[newRow][newCol]) {
                        
                        visited[newRow][newCol] = true;
                        queue.offer(new int[]{newRow, newCol, dist + 1});
                    }
                }
            }
            
            return -1;  // Should never reach here
        }
    }
    
    // Instead of: "For each 1, find nearest 0"
    // Think:      "From all 0s, spread outward to reach all 1s"
    // Time: O(m × n)
    // Space: O(m × n)
    class Solution1_ReverseBFS {
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

    class Solution1_ReverseBFS_InPlaceVisited {
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length, n = mat[0].length;
            Queue<int[]> q = new ArrayDeque<>();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] == 0) {
                        q.offer(new int[]{i, j});
                    } else {
                        mat[i][j] = Integer.MAX_VALUE; // mark unvisited
                    }
                }
            }

            int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            while (!q.isEmpty()) {
                int[] cell = q.poll();
                int row = cell[0], col = cell[1];
                for (int[] dir : dirs) {
                    int nextRow = row + dir[0], nextCol = col + dir[1];
                    if (nextRow >= 0 && nextRow < m && nextCol >= 0 && nextCol < n && mat[nextRow][nextCol] > mat[row][col] + 1) {
                        mat[nextRow][nextCol] = mat[row][col] + 1;
                        q.offer(new int[]{nextRow, nextCol});
                    }
                }
            }
            return mat;
        }
    }

    class Solution2_DFS_Memo {
        private static final int INF = 1_000_000_000;
        private static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};
        private int m, n;
        private int[][] mat, memo;
        private boolean[][] visiting;

        public int[][] updateMatrix(int[][] mat) {
            this.mat = mat;
            m = mat.length; n = mat[0].length;
            memo = new int[m][n];
            visiting = new boolean[m][n];

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    memo[r][c] = -1;
                }
            }

            int[][] ans = new int[m][n];
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    ans[r][c] = distToZero(r, c);
                }
            }
            return ans;
        }

        private int distToZero(int r, int c) {
            if (mat[r][c] == 0) return 0;
            if (memo[r][c] != -1) return memo[r][c];
            if (visiting[r][c]) return INF; // cycle guard

            visiting[r][c] = true;
            int best = INF;
            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    int cand = distToZero(nr, nc);
                    if (cand + 1 < best) best = cand + 1;
                }
            }
            visiting[r][c] = false;
            memo[r][c] = best;
            return best;
        }
    }
}
