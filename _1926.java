import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * 1926. Nearest Exit from Entrance in Maze
 */
public class _1926 {
    class Solution1_BFS {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int nearestExit(char[][] maze, int[] entrance) {
            int steps = 0, m = maze.length, n = maze[0].length;
            boolean[][] visited = new boolean[m][n];
            Queue<int[]> q = new ArrayDeque<>();
            q.offer(entrance);
            visited[entrance[0]][entrance[1]] = true;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    int[] curr = q.poll();
                    int r = curr[0], c = curr[1];
                    if (isExit(r, c, m, n) && (r != entrance[0] || c != entrance[1])) return steps;
                    for (int[] dir : dirs) {
                        int nr = r + dir[0], nc = c + dir[1];
                        if (isInValid(nr, nc, m, n)) continue;
                        if (!visited[nr][nc] && maze[nr][nc] == '.') {
                            q.offer(new int[]{nr, nc});
                            visited[nr][nc] = true;
                        }
                    }
                }
                steps++;
            }
            return -1;
        }

        private boolean isExit(int r, int c, int m, int n) {
            return r == 0 || r == m - 1 || c == 0 || c == n - 1;
        }

        private boolean isInValid(int r, int c, int m, int n) {
            return r < 0 || r >= m || c < 0 || c >= n;
        }
    }

    class Solution1_BFS_SpaceOptimized {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        char wall = '+';

        public int nearestExit(char[][] maze, int[] entrance) {
            int steps = 0, m = maze.length, n = maze[0].length;
            Queue<int[]> q = new ArrayDeque<>();
            q.offer(entrance);
            maze[entrance[0]][entrance[1]] = wall;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    int[] curr = q.poll();
                    int r = curr[0], c = curr[1];
                    if (isExit(r, c, m, n) && (r != entrance[0] || c != entrance[1])) return steps;
                    for (int[] dir : dirs) {
                        int nr = r + dir[0], nc = c + dir[1];
                        if (!isInValid(nr, nc, m, n) && maze[nr][nc] != wall) {
                            q.offer(new int[]{nr, nc});
                            maze[nr][nc] = wall;
                        }
                    }
                }
                steps++;
            }
            return -1;
        }

        private boolean isExit(int r, int c, int m, int n) {
            return r == 0 || r == m - 1 || c == 0 || c == n - 1;
        }

        private boolean isInValid(int r, int c, int m, int n) {
            return r < 0 || r >= m || c < 0 || c >= n;
        }
    }

    class Solution0_Backtrack_TLE {
        int minStep;
        public int nearestExit(char[][] maze, int[] entrance) {
            minStep = Integer.MAX_VALUE;
            dfs(maze, entrance, entrance[0], entrance[1], 0);
            return minStep == Integer.MAX_VALUE ? -1 : minStep;
        }

        private void dfs(char[][] maze, int[] entrance, int r, int c, int step) {
            int m = maze.length, n = maze[0].length;
            if (r < 0 || c < 0 || r >= m || c >= n || maze[r][c] == '+' || step >= minStep) {
                return;
            }
            if ((r == 0 || r == m - 1 || c == 0 || c == n - 1) && (r != entrance[0] || c != entrance[1])) {
                minStep = Math.min(minStep, step);
                return;
            }
            step++;
            maze[r][c] = '+';
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                dfs(maze, entrance, r + dir[0], c + dir[1], step);
            }
            maze[r][c] = '.';
        }
    }

    // this solution is not correct because we block and unblock the wall when running, which leads to different wall and path env for the same cache index
    class WrongSolution_DFS_Memo {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int nearestExit(char[][] maze, int[] entrance) {
            Map<Integer, Integer> memo = new HashMap<>();
            return dfs(maze, entrance, entrance[0], entrance[1], memo);
        }

        private int dfs(char[][] maze, int[] entrance, int r, int c, Map<Integer, Integer> memo) {
            int m = maze.length, n = maze[0].length;
            if (r < 0 || c < 0 || r >= m || c >= n || maze[r][c] == '+') {
                return -1;
            }
            int index = r * n + c;
            if (memo.containsKey(index)) {
                return memo.get(index);
            }
            if ((r == 0 || r == m - 1 || c == 0 || c == n - 1) && (r != entrance[0] || c != entrance[1])) {
                return 0; // this is a valid exit
            }
            int minSteps = Integer.MAX_VALUE;
            maze[r][c] = '+';
            for (int[] dir : dirs) {
                int step = dfs(maze, entrance, r + dir[0], c + dir[1], memo);
                if (step != -1) {
                    minSteps = Math.min(minSteps, step + 1);
                }
            }
            maze[r][c] = '.';
            minSteps = (minSteps == Integer.MAX_VALUE) ? -1 : minSteps;
            memo.put(index, minSteps);
            return minSteps;
        }
    }

    public static void main(String[] args) {
        WrongSolution_DFS_Memo solution = new _1926().new WrongSolution_DFS_Memo();
        char[][] maze = {{'+','+','.','+'},{'.','.','.','+'},{'+','+','+','.'}};
        int[] entrance = {1,2};
        System.out.println(solution.nearestExit(maze, entrance)); // 1
    }
}
