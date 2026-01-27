import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 947. Most Stones Removed with Same Row or Column
 * 
 * - S1: UF with stone as node O(n) [PREFERRED]
 * - S2: UF with coordinate as node O(n)
 * - S3: DFS O(n^2)
 * 
 * Clarification:
 * - No duplicate points in input?
 * - Range of x, y?
 */
public class _947 {
    // Each stone is a node
    // Stones in the same row or column belong to the same connected component
    // Final answer = totalStones - numberOfComponents
    // Time: O(n × α(n)) ≈ O(n) where α is inverse Ackermann (nearly constant)
    // Space: O(n)
    class Solution1_UnionFind_StoneAsNode {
        // 1 - encode stone coordinates (x, y) into one index (here no integer overflow because x, y <= 10^4)
        class EncodeCoordinates {
            int components;
            Map<Integer, Integer> parent;

            public int removeStones(int[][] stones) {
                int n = stones.length;
                components = n;
                parent = new HashMap<>();
                Map<Integer, List<Integer>> rows = new HashMap<>();
                Map<Integer, List<Integer>> cols = new HashMap<>();

                for (int[] stone : stones) {
                    int index = stone[0] * 10_000 + stone[1];
                    parent.put(index, index);
                    rows.computeIfAbsent(stone[0], k -> new ArrayList<>()).add(index);
                    cols.computeIfAbsent(stone[1], k -> new ArrayList<>()).add(index);
                }

                for (List<Integer> row : rows.values()) {
                    for (int i = 1; i < row.size(); i++) {
                        union(row.get(0), row.get(i));
                    }
                }

                for (List<Integer> col : cols.values()) {
                    for (int i = 1; i < col.size(); i++) {
                        union(col.get(0), col.get(i));
                    }
                }
                return n - components;
            }

            void union(int n1, int n2) {
                int p1 = findParent(n1);
                int p2 = findParent(n2);
                if (p1 == p2) {
                    return;
                }
                // size optimization pos
                parent.put(p1, p2);
                components--;
            }

            int findParent(int node) {
                if (parent.get(node) != node) {
                    parent.put(node, findParent(parent.get(node)));
                }
                return parent.get(node);
            }
        }
    
        // 2 - use helper class pair to 
        class HelperPairClass {
            class Pair {
                int x, y;
                Pair(int x, int y) {
                    this.x = x;
                    this.y = y;
                }
            }

            int components;
            Map<Pair, Pair> parent;

            public int removeStones(int[][] stones) {
                int n = stones.length;
                components = n;
                parent = new HashMap<>();
                Map<Integer, List<Pair>> rows = new HashMap<>();
                Map<Integer, List<Pair>> cols = new HashMap<>();

                for (int[] stone : stones) {
                    Pair p = new Pair(stone[0], stone[1]);
                    parent.put(p, p);
                    rows.computeIfAbsent(stone[0], k -> new ArrayList<>()).add(p);
                    cols.computeIfAbsent(stone[1], k -> new ArrayList<>()).add(p);
                }

                for (List<Pair> row : rows.values()) {
                    for (int i = 1; i < row.size(); i++) {
                        union(row.get(0), row.get(i));
                    }
                }

                for (List<Pair> col : cols.values()) {
                    for (int i = 1; i < col.size(); i++) {
                        union(col.get(0), col.get(i));
                    }
                }
                return n - components;
            }

            void union(Pair n1, Pair n2) {
                Pair p1 = findParent(n1);
                Pair p2 = findParent(n2);
                if (p1 == p2) {
                    return;
                }
                // size optimization pos
                parent.put(p1, p2);
                components--;
            }

            Pair findParent(Pair node) {
                if (parent.get(node) != node) {
                    parent.put(node, findParent(parent.get(node)));
                }
                return parent.get(node);
            }
        }
    
        // ** 3 - use stone index **
        class StoneIndex {
            int[] parent;
            int[] size;
            int components;

            public int removeStones(int[][] stones) {
                int n = stones.length;
                parent = new int[n];
                size = new int[n];
                components = n;
                
                // Initialize
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
                
                // Group stones by row/col
                Map<Integer, List<Integer>> rows = new HashMap<>();
                Map<Integer, List<Integer>> cols = new HashMap<>();
                
                for (int i = 0; i < n; i++) {
                    rows.computeIfAbsent(stones[i][0], k -> new ArrayList<>()).add(i);
                    cols.computeIfAbsent(stones[i][1], k -> new ArrayList<>()).add(i);
                }
                
                // Union stones in same row
                for (List<Integer> row : rows.values()) {
                    for (int i = 1; i < row.size(); i++) {
                        union(row.get(0), row.get(i));
                    }
                }
                
                // Union stones in same column
                for (List<Integer> col : cols.values()) {
                    for (int i = 1; i < col.size(); i++) {
                        union(col.get(0), col.get(i));
                    }
                }
                
                return n - components;
            }

            void union(int n1, int n2) {
                int p1 = find(n1);
                int p2 = find(n2);
                
                if (p1 == p2) return;
                
                // optional optimization
                if (size[p1] >= size[p2]) {
                    parent[p2] = p1;
                    size[p1] += size[p2];
                } else {
                    parent[p1] = p2;
                    size[p2] += size[p1];
                }
                
                components--;
            }

            int find(int node) {
                if (parent[node] != node) {
                    parent[node] = find(parent[node]);
                }
                return parent[node];
            }
        }
    }

    // Treat rows and columns as nodes
    // No need to use helper class or encode 2D coordinates into 1D
    class Solution2_UnionFind_RowColAsNode {
        public int removeStones(int[][] stones) {
            UF uf = new UF();
            int offset = 10001; // r, c <= 10^4
            for (int[] stone : stones) {
                int row = stone[0];
                int col = stone[1] + offset; // offset to differentiate row/col
                uf.union(row, col);
            }
            return stones.length - uf.getComponentCount();
        }
        
        class UF {
            private Map<Integer, Integer> parent = new HashMap<>();
            private int components = 0;
        
            public int find(int x) {
                if (!parent.containsKey(x)) {
                    parent.put(x, x);
                    components++;
                }
                if (x != parent.get(x)) {
                    parent.put(x, find(parent.get(x)));
                }
                return parent.get(x);
            }
        
            public void union(int x, int y) {
                int px = find(x), py = find(y);
                if (px != py) {
                    parent.put(px, py);
                    components--;
                }
            }
        
            public int getComponentCount() {
                return components;
            }
        }
    }

    // Time: O(n^2)
    // Space: O(n)
    class Solution3_DFS {
        public int removeStones(int[][] stones) {
            int n = stones.length;
            boolean[] visited = new boolean[n];
            int components = 0;
        
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    dfs(stones, visited, i);
                    components++; // one new connected component found
                }
            }
        
            return n - components; // max stones that can be removed
        }
        
        private void dfs(int[][] stones, boolean[] visited, int curr) {
            visited[curr] = true;
        
            for (int i = 0; i < stones.length; i++) {
                if (!visited[i] && isConnected(stones[curr], stones[i])) {
                    dfs(stones, visited, i);
                }
            }
        }
        
        private boolean isConnected(int[] a, int[] b) {
            return a[0] == b[0] || a[1] == b[1]; // same row or same column
        }
    }

    // Time: O(n × k) where k is average connectivity
    // Space: O(n)
    class Solution3_DFS_Optimized {
        public int removeStones(int[][] stones) {
            // Map: row/column -> list of stone indices
            Map<Integer, List<Integer>> rowMap = new HashMap<>();
            Map<Integer, List<Integer>> colMap = new HashMap<>();
            
            for (int i = 0; i < stones.length; i++) {
                int row = stones[i][0];
                int col = stones[i][1];
                
                rowMap.computeIfAbsent(row, k -> new ArrayList<>()).add(i);
                colMap.computeIfAbsent(col, k -> new ArrayList<>()).add(i);
            }
            
            boolean[] visited = new boolean[stones.length];
            int components = 0;
            
            for (int i = 0; i < stones.length; i++) {
                if (!visited[i]) {
                    dfs(i, stones, rowMap, colMap, visited);
                    components++;
                }
            }
            
            return stones.length - components;
        }
        
        private void dfs(int idx, int[][] stones, 
                        Map<Integer, List<Integer>> rowMap,
                        Map<Integer, List<Integer>> colMap,
                        boolean[] visited) {
            visited[idx] = true;
            int row = stones[idx][0];
            int col = stones[idx][1];
            
            // Visit all stones in same row
            for (int neighbor : rowMap.get(row)) {
                if (!visited[neighbor]) {
                    dfs(neighbor, stones, rowMap, colMap, visited);
                }
            }
            
            // Visit all stones in same column
            for (int neighbor : colMap.get(col)) {
                if (!visited[neighbor]) {
                    dfs(neighbor, stones, rowMap, colMap, visited);
                }
            }
        }
    }
}
