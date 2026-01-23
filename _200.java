import java.util.LinkedList;
import java.util.Queue;

/**
 * 200. Number of Islands
 */
public class _200 {
    // Time: O(mn)
    // Space: O(mn)
    class Solution_DFS {
        public int numIslands(char[][] grid) {
            int m = grid.length, n = grid[0].length;
            int islands = 0;
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == '1') {
                        islands++;
                        dfs(grid, r, c);
                    }
                }
            }
            return islands;
        }
    
        private void dfs(char[][] grid, int r, int c) {
            int m = grid.length, n = grid[0].length;
            if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == '0') {
                return;
            }
            grid[r][c] = '0';
            dfs(grid, r - 1, c);
            dfs(grid, r + 1, c);
            dfs(grid, r, c - 1);
            dfs(grid, r, c + 1);
        }
    }

    // Time: O(mn)
    // Space: O(min(m, n))
    public class Solution2_BFS {
        public int numIslands(char[][] grid) {
            int m = grid.length, n = grid[0].length;
            int islands = 0;
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == '1') {
                        islands++;
                        bfs(grid, r, c);
                    }
                }
            }
            return islands;
        }

        private void bfs(char[][] grid, int startR, int startC) {
            int m = grid.length, n = grid[0].length;
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{startR, startC});
            grid[startR][startC] = '0'; // mark as visited

            int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};

            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                int r = cell[0], c = cell[1];

                for (int[] dir : directions) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == '1') {
                        grid[nr][nc] = '0';
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
    }

    // Time: O(mn*alpha(mn)) = O(mn)
    // Space: O(mn)
    class Solution3_UF {
        class UnionFind {
            private int[] parent;
            private int[] rank;
            private int count;  // Number of distinct sets
            
            public UnionFind(char[][] grid) {
                int rows = grid.length;
                int cols = grid[0].length;
                parent = new int[rows * cols];
                rank = new int[rows * cols];
                count = 0;
                
                // Initialize: each cell is its own parent
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (grid[i][j] == '1') {
                            int id = i * cols + j;
                            parent[id] = id;
                            count++;
                        }
                    }
                }
            }
            
            public int find(int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]);  // Path compression
                }
                return parent[x];
            }
            
            public void union(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);
                
                if (rootX != rootY) {
                    // Union by rank
                    if (rank[rootX] > rank[rootY]) {
                        parent[rootY] = rootX;
                    } else if (rank[rootX] < rank[rootY]) {
                        parent[rootX] = rootY;
                    } else {
                        parent[rootY] = rootX;
                        rank[rootX]++;
                    }
                    count--;  // Merged two sets
                }
            }
            
            public int getCount() {
                return count;
            }
        }
        
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;
            
            int rows = grid.length;
            int cols = grid[0].length;
            UnionFind uf = new UnionFind(grid);
            
            int[][] directions = {{1, 0}, {0, 1}};  // Only check down and right
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == '1') {
                        int id1 = i * cols + j;
                        
                        // Check down and right neighbors
                        for (int[] dir : directions) {
                            int newRow = i + dir[0];
                            int newCol = j + dir[1];
                            
                            if (newRow < rows && newCol < cols && 
                                grid[newRow][newCol] == '1') {
                                int id2 = newRow * cols + newCol;
                                uf.union(id1, id2);
                            }
                        }
                    }
                }
            }
            
            return uf.getCount();
        }
    }
}
