import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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

    // Time: O(n^2 * α(n) + klogk) = O(n^2) where n is nodes in graph and k is number of infected nodes in initial
    // Space: O(n)
    class Solution2_UF {
        class UF {
            int[] parent;
            int[] size;

            UF (int n) {
                parent = new int[n];
                size = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
            }

            int find(int node) {
                if (parent[node] != node) {
                    parent[node] = find(parent[node]);
                }
                return parent[node];
            }

            void union(int n1, int n2) {
                int r1 = find(n1), r2 = find(n2);
                if (r1 != r2) {
                    if (size[r1] >= size[r2]) {
                        parent[r2] = r1;
                        size[r1] += size[r2];
                    } else {
                        parent[r1] = r2;
                        size[r2] += size[r1];
                    }
                }
            }
        }

        public int minMalwareSpread(int[][] graph, int[] initial) {
            int n = graph.length;
            UF uf = new UF(n);
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (graph[i][j] == 1) {
                        uf.union(i, j);
                    }
                }
            }

            // Count component sizes
            int[] components = new int[n];
            for (int node = 0; node < n; node++) {
                int root = uf.find(node);
                components[root]++;
            }

            // Count infected per component
            int[] infected = new int[n];
            for (int infect : initial) {
                infected[uf.find(infect)]++;
            }

            Arrays.sort(initial);
            int nodeToRem = initial[0];
            int maxComponent = 0;
            for (int infect : initial) {
                int root = uf.find(infect);
                if (infected[root] == 1 && components[root] > maxComponent) {
                    maxComponent = components[root];
                    nodeToRem = infect;
                }
            }
            return nodeToRem;
        }
    }

    class Solution3_BF_Simulation {
        public int minMalwareSpread(int[][] graph, int[] initial) {
            int n = graph.length;
            int minInfected = n + 1;
            int result = Integer.MAX_VALUE;
            
            Arrays.sort(initial); // For tiebreaker
            
            // Try removing each initially infected node
            for (int toRemove : initial) {
                // Count infected if we remove this node
                Set<Integer> infected = new HashSet<>();
                
                for (int start : initial) {
                    if (start != toRemove) {
                        bfs(graph, start, infected);
                    }
                }
                
                int count = infected.size();
                
                if (count < minInfected || (count == minInfected && toRemove < result)) {
                    minInfected = count;
                    result = toRemove;
                }
            }
            
            return result;
        }
        
        private void bfs(int[][] graph, int start, Set<Integer> infected) {
            if (infected.contains(start)) return;
            
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(start);
            infected.add(start);
            
            while (!queue.isEmpty()) {
                int node = queue.poll();
                
                for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                    if (graph[node][neighbor] == 1 && !infected.contains(neighbor)) {
                        infected.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }
    }
}
