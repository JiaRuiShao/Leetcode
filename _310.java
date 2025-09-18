import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import topics.BFS;

/**
 * 310. Minimum Height Trees
 */
public class _310 {
    class Solution1_Graph_TopoSort {
        public List<Integer> findMinHeightTrees(int n, int[][] edges) {
            // 1- build graph & inDegree[]
            List<Integer>[] graph = new ArrayList[n];
            int[] inDegree = new int[n];
            for (int node = 0; node < n; node++) {
                graph[node] = new ArrayList<>();
            }
            for (int[] edge : edges) {
                int v1 = edge[0], v2 = edge[1];
                graph[v1].add(v2);
                graph[v2].add(v1);
                inDegree[v1]++;
                inDegree[v2]++;
            }
            // 2 - find leaf node
            Queue<Integer> leafNodes = new ArrayDeque<>();
            for (int node = 0; node < n; node++) {
                if (inDegree[node] <= 1) { // here we use <= and not == to include the corner case where there's only one node and no edges; it's only valid under the constraint of all nodes are connected (no subgraph)
                    leafNodes.offer(node);
                }
            }
            // 3 - topological sort to find the node(s) with min distance to leaf nodes
            int remain = n;
            while (remain > 2) { 
                int size = leafNodes.size();
                remain -= size;
                for (int i = 0; i < size; i++) {
                    int curr = leafNodes.poll();
                    for (int nbr : graph[curr]) {
                        if (--inDegree[nbr] == 1) {
                            leafNodes.offer(nbr);
                        }
                    }
                }
            }
            return new ArrayList<>(leafNodes);
        }
    }

    // 1- Build the adjacency list
    // 2 - BFS from any node → farthest node A (this will be a leaf)
    // 3 - BFS from A → farthest node B and record parent[]
    // 4 - Construct the diameter path B → … → A (the diameter)
    // 5 - Return the middle one (odd length) or two (even length) nodes on that path → MHT roots
    class Solution2_BFS_DiameterCenter {
        public List<Integer> findMinHeightTrees(int n, int[][] edges) {
            if (n <= 2) {
                List<Integer> res = new ArrayList<>(n);
                for (int i = 0; i < n; i++) res.add(i);
                return res;
            }

            // Build adjacency list
            List<Integer>[] g = new ArrayList[n];
            for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
            for (int[] e : edges) {
                int a = e[0], b = e[1];
                g[a].add(b); g[b].add(a);
            }

            // 1) BFS from any node (0) to get farthest A
            Farthest fa = bfs(0, g);

            // 2) BFS from A to get farthest B and parent tree of A-rooted BFS
            Farthest fb = bfs(fa.node, g);

            // 3) Construct B->A diameter path
            List<Integer> path = new ArrayList<>();
            for (int cur = fb.node; cur != -1; cur = fb.parent[cur]) path.add(cur);
            // Collections.reverse(path); // now A -> ... -> B

            // 4) Middle node(s) of diameter path are MHT roots
            int m = path.size();
            if ((m & 1) == 1) {
                return List.of(path.get(m / 2));
            } else {
                return List.of(path.get(m / 2 - 1), path.get(m / 2));
            }
        }

        // BFS returning farthest node and parent[] used to reconstruct the path
        private Farthest bfs(int start, List<Integer>[] g) {
            int n = g.length;
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            boolean[] vis = new boolean[n];
            Deque<Integer> q = new ArrayDeque<>();
            vis[start] = true;
            q.offer(start);
            int last = start;

            while (!q.isEmpty()) {
                int u = q.poll();
                last = u;
                for (int v : g[u]) {
                    if (!vis[v]) {
                        vis[v] = true;
                        parent[v] = u;
                        q.offer(v);
                    }
                }
            }
            return new Farthest(last, parent);
        }

        class Farthest {
            final int node;
            final int[] parent;
            Farthest(int node, int[] parent) { this.node = node; this.parent = parent; }
        }
    }

}
