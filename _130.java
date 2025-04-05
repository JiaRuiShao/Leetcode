import java.util.LinkedList;
import java.util.Queue;

/**
 * 130. Surrounded Regions
 */
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

    class Solution3_Union_Find {
        /**
         * Union-Find implementation to connect edge Os with a dummy node, connect Os with their neighbors; then flip Os to X if not connected to the dummy node.
         * Time: O(mn)
         * Space: O(mn)
         * @param board
         */
        public void solve(char[][] board) {
            int m = board.length, n = board[0].length;
            UnionFind uf = new UnionFind(m * n + 1);
            int dummy = m * n;
            char target = 'O';
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (board[r][c] != target) {
                        continue;
                    }
                    int node = r * n + c;
                    if (r == 0 || r == m - 1 || c == 0 || c == n - 1) {
                        uf.union(node, dummy);
                    }
    
                    for (int[] direction : directions) {
                        int row = r + direction[0];
                        int col = c + direction[1];
                        if (row < 0 || row >= m || col < 0 || col >= n || board[row][col] != target) {
                            continue;
                        }
                        uf.union(row * n + col, node);
                    }
                }
            }
    
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (board[r][c] == target && !uf.connected(r * n + c, dummy)) {
                        board[r][c] = 'X';
                    }
                }
            }
        }
    
        class UnionFind {
            private int[] parent;
            public UnionFind(int size) {
                parent = new int[size];
                for (int i = 0; i < size; i++) {
                    parent[i] = i;
                }
            }
    
            private int findParent(int node) {
                if (parent[node] != node) {
                    parent[node] = findParent(parent[node]);
                }
                return parent[node];
            }
    
            public void union(int n1, int n2) {
                int p1 = findParent(n1);
                int p2 = findParent(n2);
                if (p1 != p2) {
                    parent[p1] = p2;
                }
            }
    
            public boolean connected(int n1, int n2) {
                return findParent(n1) == findParent(n2);
            }
        }
    }
}
