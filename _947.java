import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 947. Most Stones Removed with Same Row or Column
 */
public class _947 {
    class Solution1_Union_Find {
        public int removeStones(int[][] stones) {
            int n = stones.length;

            // Map encoded coordinates to Union-Find IDs
            Map<Integer, Integer> stoneToId = new HashMap<>();
            for (int i = 0; i < n; i++) {
                stoneToId.put(encode(stones[i]), i);
            }

            // Group encoded stones by row and column
            Map<Integer, List<Integer>> rows = new HashMap<>();
            Map<Integer, List<Integer>> cols = new HashMap<>();
            for (int[] stone : stones) {
                int row = stone[0], col = stone[1];
                int encoded = encode(stone);

                rows.computeIfAbsent(row, k -> new ArrayList<>()).add(encoded);
                cols.computeIfAbsent(col, k -> new ArrayList<>()).add(encoded);
            }

            UF uf = new UF(n); // Union-Find for stone decoded ids

            // Union stones in the same column
            for (List<Integer> colGroup : cols.values()) {
                int baseId = stoneToId.get(colGroup.get(0));
                for (int i = 1; i < colGroup.size(); i++) {
                    int otherId = stoneToId.get(colGroup.get(i));
                    uf.union(baseId, otherId);
                }
            }

            // Union stones in the same row
            for (List<Integer> rowGroup : rows.values()) {
                int baseId = stoneToId.get(rowGroup.get(0));
                for (int i = 1; i < rowGroup.size(); i++) {
                    int otherId = stoneToId.get(rowGroup.get(i));
                    uf.union(baseId, otherId);
                }
            }

            // Max stones removable = total stones - number of connected components
            return n - uf.count();
        }

        // Encode a 2D coordinate into a unique integer
        private int encode(int[] point) {
            return point[0] * 10000 + point[1]; // assuming 0 <= x, y <= 9999
        }

        class UF {
            private int[] parent;
            private int count;
        
            public UF(int n) {
                parent = new int[n];
                count = n;
                for (int i = 0; i < n; i++) parent[i] = i;
            }
        
            public int find(int x) {
                if (parent[x] != x) parent[x] = find(parent[x]); // path compression
                return parent[x];
            }
        
            public void union(int x, int y) {
                int rootX = find(x), rootY = find(y);
                if (rootX != rootY) {
                    parent[rootY] = rootX;
                    count--;
                }
            }
        
            public boolean connected(int x, int y) {
                return find(x) == find(y);
            }
        
            public int count() {
                return count;
            }
        }
    }

    /**
     * Union row and (col + OFFSET) for each stone, use a map instead of array to record parents (this avoids mapping index number to unique node id)
     */
    class Solution2_Union_Find_Improved {
        public int removeStones(int[][] stones) {
            UF uf = new UF();
            for (int[] stone : stones) {
                int row = stone[0];
                int col = stone[1] + 10000; // offset to separate row/col IDs
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

    class Solution3_Union_Find_Improved_Bitwise {
        public class Solution {
            public int removeStones(int[][] stones) {
                UnionFind uf = new UnionFind();
        
                for (int[] stone : stones) {
                    int x = stone[0];
                    int y = ~stone[1]; // bitwise NOT to differentiate y from x
                    uf.union(x, y);
                }
        
                // number of connected components = number of unique parents
                // answer = total stones - number of components
                return stones.length - uf.getComponentCount();
            }
        
            class UnionFind {
                Map<Integer, Integer> parent = new HashMap<>();
                int components = 0;
        
                public int find(int x) {
                    if (!parent.containsKey(x)) {
                        parent.put(x, x);
                        components++;
                    }
                    if (parent.get(x) != x) {
                        parent.put(x, find(parent.get(x))); // path compression
                    }
                    return parent.get(x);
                }
        
                public void union(int x, int y) {
                    int px = find(x);
                    int py = find(y);
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
    }
}
