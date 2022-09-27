/**
 * 261. Graph Valid Tree.
 *
 * You have a graph of n nodes labeled from 0 to n - 1. You are given an integer n and a list of edges where
 * edges[i] = [ai, bi] indicates that there is an undirected edge between nodes ai and bi in the graph.
 *
 * Return true if the edges of the given graph make up a valid tree, and false otherwise.
 */
public class _261 {
    class Solution1_Union_Find {
        /**
         * Time: O(N + E) where n is num of nodes in the given graph, and e is edge num, that is edges.length
         * Space: O(N)
         *
         * @param n total num of nodes in the given graph
         * @param edges edges of the given graph
         * @return true if the edges can construct a tree; vice versa
         */
        boolean validTree(int n, int[][] edges) {
            // initialize the UF graph, from 0...n-1 in total n nodes
            UF uf = new UF(n);
            // traverse all the edges, and connect them together using union-find
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                // cycle detected if two nodes are already connected
                if (uf.connected(u, v)) {
                    return false;
                }
                // add this edge to the tree if two nodes are not connected
                uf.union(u, v);
            }
            // make sure all nodes are connected, aka there's only one connected component left
            return uf.count() == 1;
        }

        private class UF {
            int count; // num of connected components
            int[] parent;

            public UF(int n) { // n is num of nodes in the graph
                count = n; // n is connected components num in teh beginning
                parent = new int[n];
                for (int i = 0; i < n; i++) { // set parent node as themselves
                    parent[i] = i;
                }
            }

            public void union(int n1, int n2) { // connect two nodes together as one component
                // find the parent node / root node of two given nodes
                int p1 = findParent(n1), p2 = findParent(n2);
                // n1 & n2 are already connected
                if (p1 == p2) return;
                // set one as the other's parent
                parent[p1] = p2;
                // decrease the total num of connected components
                count--;
            }

            public boolean connected(int n1, int n2) { // whether two nodes are connected
                return findParent(n1) == findParent(n2);
            }

            private int findParent(int node) { // find the parent/root of given node, set root as the parent of all nodes in path from given node to root
                if (node == parent[node]) return node; // root
                parent[node] = findParent(parent[node]);
                return parent[node];
            }

            public int count() {
                return count;
            }
        }
    }
}
