/**
 * 323. Number of Connected Components in an Undirected Graph
 */
public class _323 {
    /**
     * Pros of using Union-Find:
     * 1 - space complexity is better compared to DFS & BFS
     * 2 - duplicate edges and self-cycle are automatically ignored, need to build Set<>[] graph if there's duplicate edges
     * However this question assumption declares that there's no self cycle or duplicate edges; if not, we do need to consider that
     */
    class Solution1_Union_Find {
        // solution 1: Union-Find, time: O(V + E · α(V)) ≈ O(V + E); space: O(V) int[] parents
        // solution 2: BFS, time: O(V + VE) if not build graph; if build graph O(V + E); space: O(V + E) visited[] O(V), graph O(V + E), queue O(V)
        // solution 3: DFS (build graph, then DFS), time: O(V + E); space: O(V + E) visited[] O(V), graph O(V + E), queue O(V)
        public int countComponents(int n, int[][] edges) {
            int[] parents = new int[n];
            int count = n;
            setParents(parents, n);
            for (int[] edge : edges) {
                int p1 = findParent(parents, edge[0]);
                int p2 = findParent(parents, edge[1]);
                if (p1 != p2) {
                    parents[p1] = p2;
                    count--;
                }
            }
            return count;
        }

        private void setParents(int[] parents, int n) {
            for (int node = 0; node < n; node++) {
                parents[node] = node;
            }
        }

        private int findParent(int[] parents, int curr) {
            if (parents[curr] != curr) {
                parents[curr] = findParent(parents, parents[curr]);
            }
            return parents[curr];
        }
    }

    class Solution2_Union_Find_Balanced {
        public int countComponents(int n, int[][] edges) {
            int[] parents = new int[n];
            int[] size = new int[n];
            int count = n;
            setParents(parents, n);
            for (int[] edge : edges) {
                int p1 = findParent(parents, edge[0]);
                int p2 = findParent(parents, edge[1]);
                if (p1 != p2) {
                    // instead of arbitrary assign parent, we assign subtree with less node to the subtree with more nodes
                    if (size[p1] > size[p2]) {
                        parents[p2] = p1;
                        size[p1] += size[p2];
                    } else {
                        parents[p1] = p2;
                        size[p2] += size[p1];
                    }
                    count--;
                }
            }
            return count;
        }

        private void setParents(int[] parents, int n) {
            for (int node = 0; node < n; node++) {
                parents[node] = node;
            }
        }

        private int findParent(int[] parents, int curr) {
            if (parents[curr] != curr) {
                parents[curr] = findParent(parents, parents[curr]);
            }
            return parents[curr];
        }
    }

    public static void main(String[] args) {
        _323.Solution1_Union_Find uf = new _323().new Solution1_Union_Find();
        int[][] edges = {{0, 1}, {1, 0}, {0, 0}};
        int count = uf.countComponents(2, edges);
        System.out.println(count);
    }
}
