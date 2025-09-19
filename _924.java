import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 924. Minimize Malware Spread
 */
public class _924 {
    // BFS/DFS to find component count & number of bad node in that component for each bad node in initial
    // When the bad node count is 1, which means removing this node from initial infected array would save all other nodes in that component
    class Solution1_BFS {
        // Time: O(n^2)
        // Space: O(n)
        public int minMalwareSpread(int[][] graph, int[] initial) {
            int n = graph.length;
            HashSet<Integer> initialSet = new HashSet<>();
            for (int node : initial) {
                initialSet.add(node);
            }
            boolean[] visited = new boolean[n];

            // Because the answer must be the smallest index on ties, sort `initial`
            // so the first qualifying node we find is the smallest.
            Arrays.sort(initial);

            // We can delete at least one node, so initialize the candidate to initial[0].
            int targetNode = initial[0];
            int reduceCount = 0;

            for (int badNode : initial) {
                if (visited[badNode]) {
                    continue;
                }
                // Run BFS to find all nodes in this infected component.
                int[] count = bfs(graph, badNode, visited, initialSet);
                if (count[1] == 1) {
                    // If this component has exactly one initially infected node,
                    // then this node is a removable unique seed.
                    if (count[0] > reduceCount) {
                        reduceCount = count[0];
                        targetNode = badNode;
                    }
                }
            }
            return targetNode;
        }

        // Perform BFS and return two values:
        // [0] the total number of nodes reached from `badNode` (size of the component),
        // [1] the number of nodes encountered that are in `initial`
        //     (the count of initially infected nodes in this component).
        int[] bfs(int[][] graph, int badNode, boolean[] visited, HashSet<Integer> initialSet) {
            int n = graph.length;
            visited[badNode] = true;

            // Track how many nodes encountered during this BFS are in `initial`.
            int nodeCount = 0;
            int badCount = 0;

            // BFS skeleton
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(badNode);
            while (!queue.isEmpty()) {
                int node = queue.poll();
                nodeCount++;
                if (initialSet.contains(node)) {
                    badCount++;
                }
                // Spread to all neighboring nodes.
                for (int neighborNode = 0; neighborNode < n; neighborNode++) {
                    if (graph[node][neighborNode] == 1 && !visited[neighborNode]) {
                        queue.offer(neighborNode);
                        visited[neighborNode] = true;
                    }
                }
            }
            return new int[]{nodeCount, badCount};
        }
    }

    class Solution2_UF {
        public int minMalwareSpread(int[][] graph, int[] initial) {
            int n = graph.length;
            DSU dsu = new DSU(n);

            // 1) Build connected components.
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (graph[i][j] == 1) dsu.union(i, j);
                }
            }

            // 2) Component sizes.
            int[] componentSize = new int[n];
            for (int node = 0; node < n; node++) {
                int root = dsu.find(node);
                componentSize[root]++;
            }

            // 3) Count initially infected nodes per component.
            int[] infectedInComponent = new int[n];
            for (int node : initial) {
                infectedInComponent[dsu.find(node)]++;
            }

            // 4) Choose the node to remove.
            Arrays.sort(initial); // ensures smallest index on ties
            int bestNode = initial[0];
            int bestSaved = -1;

            for (int node : initial) {
                int root = dsu.find(node);
                int saved = (infectedInComponent[root] == 1) ? componentSize[root] : 0;

                if (saved > bestSaved || (saved == bestSaved && node < bestNode)) {
                    bestSaved = saved;
                    bestNode = node;
                }
            }

            return bestNode;
        }

        // Union-Find with path compression and union by size
        class DSU {
            int[] parent, size;
            DSU(int n) {
                parent = new int[n];
                size = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
            }
            int find(int x) {
                if (parent[x] != x) parent[x] = find(parent[x]);
                return parent[x];
            }
            void union(int a, int b) {
                int ra = find(a), rb = find(b);
                if (ra == rb) return;
                if (size[ra] < size[rb]) { int t = ra; ra = rb; rb = t; }
                parent[rb] = ra;
                size[ra] += size[rb];
            }
        }
    }
}
