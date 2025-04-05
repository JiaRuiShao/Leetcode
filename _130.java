import java.util.LinkedList;
import java.util.Queue;

public class _130 {
    // solution 1: DFS/BFS (mark edge-connected 'O' with 'E', then flip remaining Os and revert Es back to Os)
    // solution 2: Union-Find (to connect edge Os together with a dummy root node, then traverse the matrix to flip the Os that're not connected)
    class Solution1_DFS {
        /**
         * DFS implementation to mark edge-connected 'O' with 'E'.
         * Time: O(mn)
         * Space: O(mn)
         * @param board
         */
        public void solve(char[][] board) {
            int m = board.length, n = board[0].length;
            char target = 'O';
            for (int c = 0; c < n; c++) {
                if (board[0][c] == target) {
                    dfs(board, 0, c);
                }
                if (board[m - 1][c] == target) {
                    dfs(board, m - 1, c);
                }
            }
    
            for (int r = 0; r < m; r++) {
                if (board[r][0] == target) {
                    dfs(board, r, 0);
                }
                if (board[r][n - 1] == target) {
                    dfs(board, r, n - 1);
                }
            }
    
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (board[r][c] == target) {
                        board[r][c] = 'X';
                    } else if (board[r][c] == 'E') {
                        board[r][c] = target;
                    }
                }
            }
        }
    
        private void dfs(char[][] board, int r, int c) {
            int m = board.length, n = board[0].length;
            if (r < 0 || r >= m || c < 0 || c >= n || board[r][c] != 'O') {
                return;
            }
    
            board[r][c] = 'E';
            dfs(board, r - 1, c);
            dfs(board, r + 1, c);
            dfs(board, r, c - 1);
            dfs(board, r, c + 1);
        }
    }

    class Solution2_BFS {
        /**
         * BFS implementation to mark edge-connected 'O' with 'E'.
         * Time: O(mn)
         * Space: O(mn)
         * @param board
         */        
        public void solve(char[][] board) {
            int m = board.length, n = board[0].length;
            char target = 'O';
            for (int c = 0; c < n; c++) {
                if (board[0][c] == target) {
                    bfs(board, 0, c);
                }
                if (board[m - 1][c] == target) {
                    bfs(board, m - 1, c);
                }
            }

            for (int r = 0; r < m; r++) {
                if (board[r][0] == target) {
                    bfs(board, r, 0);
                }
                if (board[r][n - 1] == target) {
                    bfs(board, r, n - 1);
                }
            }

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (board[r][c] == target) {
                        board[r][c] = 'X';
                    } else if (board[r][c] == 'E') {
                        board[r][c] = target;
                    }
                }
            }
        }

        private void bfs(char[][] board, int startRow, int startCol) {
            int m = board.length, n = board[0].length;
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{startRow, startCol});
            board[startRow][startCol] = 'E';

            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int row = curr[0];
                int col = curr[1];
                for (int[] direction : directions) {
                    int r = row + direction[0];
                    int c = col + direction[1];
                    if (r < 0 || r >= m || c < 0 || c >= n || board[r][c] != 'O') {
                        continue;
                    }
                    queue.offer(new int[]{r, c});
                    board[r][c] = 'E';
                }
            }
        }
    }
}
