/**
 * 547. Number of Provinces
 */
public class _547 {
    class Solution1_Union_Find {
        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            int connected = n;
            int[] parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
    
            for (int r = 0; r < n; r++) {
                for (int c = r + 1; c < n; c++) {
                    if (isConnected[r][c] == 1) {
                        if (!connected(parent, r, c)) {
                            union(parent, r, c);
                            connected--;
                        }
                    }
                }
            }
    
            return connected;
        }
    
        private boolean connected(int[] parent, int n1, int n2) {
            return findParent(parent, n1) == findParent(parent, n2);
        }
    
        private void union(int[] parent, int n1, int n2) {
            int p1 = findParent(parent, n1);
            int p2 = findParent(parent, n2);
            if (p1 != p2) {
                parent[p1] = p2;
            }
        }
    
        private int findParent(int[] parent, int node) {
            if (parent[node] != node) {
                parent[node] = findParent(parent, parent[node]);
            }
            return parent[node];
        }
    }
}
