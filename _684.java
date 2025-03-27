/**
 * 684. Redundant Connection
 */
public class _684 {
    class Solution1_Union_Find {
        // brute force: remove each edge and check hasCycle, time: O(E * (V + E))
        // this solution: union-find, time: O(V + E)
        public int[] findRedundantConnection(int[][] edges) {
            int numNode = edges.length;
            int[] res = new int[2];

            int[] parents = new int[numNode]; // 0-based index
            for (int i = 0; i < numNode; i++) {
                parents[i] = i;
            }

            for (int[] edge : edges) {
                // here we use 0 index based parents[], so we need to convert 1 index based nodes to 0 based
                // the simpler way is to use a 1-indexed parents[]
                int n1 = edge[0] - 1;
                int n2 = edge[1] - 1;
                int p1 = findParent(parents, n1);
                int p2 = findParent(parents, n2);
                if (p1 == p2) {
                    res = edge;
                } else {
                    parents[p1] = p2;
                }
            }

            return res;
        }

        private int findParent(int[] parents, int curr) {
            if (parents[curr] != curr) {
                parents[curr] = findParent(parents, parents[curr]);
            }
            return parents[curr];
        }
    }
}
