/**
 * 261. Graph Valid Tree
 */
public class _261 {
    class Solution1_Union_Find {
        // a valid tree: no cycle + all nodes connected
        // T: O(n + e)
        // S: O(n)
        public boolean validTree(int n, int[][] edges) {
            int connectedComponents = n;
            int[] parent = new int[n];
            for (int node = 0; node < n; node++) {
                parent[node] = node;
            }
    
            for (int[] edge : edges) {
                int n1 = edge[0];
                int n2 = edge[1];
                int p1 = findParent(parent, n1);
                int p2 = findParent(parent, n2);
                if (p1 == p2) return false;
                parent[p1] = p2;
                connectedComponents--;
            }
    
            return connectedComponents == 1;
        }
    
        private int findParent(int[] parent, int node) {
            if (parent[node] != node) {
                parent[node] = findParent(parent, parent[node]);
            }
            return parent[node];
        }
    }
}
