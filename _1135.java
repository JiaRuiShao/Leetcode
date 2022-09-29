import java.util.*;

/**
 * 1135. Connecting Cities With Minimum Cost.
 * There are n cities labeled from 1 to n. You are given the integer n and an array connections where
 * connections[i] = [xi, yi, costi] indicates that the cost of connecting city xi and city yi (bidirectional connection) is costi.
 *
 * Return the minimum cost to connect all the n cities such that there is at least one path between each pair of cities.
 * If it is impossible to connect all the n cities, return -1. The cost is the sum of the connections' costs used.
 */
public class _1135 {
    class Solution1_Prim {
        public int minimumCost(int n, int[][] connections) {
            Map<Integer, List<int[]>> graph = buildGraph(n, connections); // build a graph using edges
            PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // min-heap based on weight asc
            Set<Integer> visited = new HashSet<>(); // store the nodes that are added to mst
            int costs = 0;

            // add the 1st node into the mst. here index starts from 1
            heap.add(new int[]{1, 0});
            // traverse the edges
            while (!heap.isEmpty()) {
                int[] conn = heap.poll();
                int end = conn[0], cost = conn[1];
                // add edges starting from current end node if it hasn't been added to the mst
                if (!visited.contains(end)) {
                    costs += cost;
                    visited.add(end);
                    for (int[] edge : graph.get(end)) {
                        heap.add(new int[]{edge[0], edge[1]});
                    }
                }
            }

            return visited.size() == n ? costs : -1;
        }

        /**
         * In this built graph:
         * - key: starting node
         * - value: a list of int arr [endNode, weight]
         *
         * @param n
         * @param connections
         * @return
         */
        private Map<Integer, List<int[]>> buildGraph(int n, int[][] connections) {
            Map<Integer, List<int[]>> graph = new HashMap<>();
            for (int[] conn : connections) {
                int n1 = conn[0], n2 = conn[1], cost = conn[2];
                graph.computeIfAbsent(n1, k -> new ArrayList<>());
                graph.computeIfAbsent(n2, k -> new ArrayList<>());
                graph.get(n1).add(new int[]{n2, cost});
                graph.get(n2).add(new int[]{n1, cost});
            }
            return graph;
        }
    }

    /**
     * Basic Prim's Algorithm.
     * Time: O(eloge+n^2)
     * Space: (n+e)
     */
    class Solution2_Prim_Basic { // another implementation of Prim's algorithm
        public int minimumCost(int n, int[][] connections) {
            // build a graph using given edges and weights, implemented by adjacency list
            List<int[]>[] graph = buildGraph(n, connections);
            // prim's algorithm
            Prim prim = new Prim(graph);

            if (!prim.allConnected()) {
                return -1;
            }

            return prim.weightSum();
        }

        List<int[]>[] buildGraph(int n, int[][] connections) {
            // build a list with size of n
            List<int[]>[] graph = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new LinkedList<>();
            }

            for (int[] conn : connections) {
                // the starting index is 1 in this question, we need to decrease each node by 1 so that they start from 0
                int n1 = conn[0] - 1;
                int n2 = conn[1] - 1;
                int weight = conn[2];
                // for bidirectional graph, add the edge int[]{nextNode, weight}
                graph[n1].add(new int[]{n2, weight});
                graph[n2].add(new int[]{n1, weight});
            }

            return graph;
        }

        private class Prim {
            // use a min heap to store the edges with edge weight as queue order
            private PriorityQueue<int[]> pq;
            // record the nodes in the built MST, similar to visited
            private boolean[] inMST;
            // record the minimum sum of edges' weights
            private int weightSum = 0;
            // use adjacency list to implement the graph，where graph[i] records all the adjacent edges of node i
            // int[]{from, to, weight} represents the edge
            private List<int[]>[] graph;

            public Prim(List<int[]>[] graph) {
                this.graph = graph;
                this.pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
                // the graph has n vertices in total
                int n = graph.length;
                this.inMST = new boolean[n];

                // start with any vertex, here we start with vertex 0, city 1 with weight as 0
                pq.offer(new int[]{0, 0});

                // selecting next edge with min weight, time: O(eloge+en) = O(n^2*logn^2+n^3)
                while (!pq.isEmpty()) { // O(e)
                    int[] edge = pq.poll(); // O(loge) = O(logn^2)
                    int next = edge[0];
                    int weight = edge[1];
                    if (inMST[next]) continue; // ignore this edge if the connected node of this edge is already in the mst
                    // add this edge to the mst
                    weightSum += weight;
                    inMST[next] = true;
                    // add edges of next node to the pq
                    addEdges(next); // O(n)
                }
            }

            // add edges of node s into pq, time: O(n)
            private void addEdges(int s) {
                for (int[] edge : graph[s]) {
                    int next = edge[0];
                    if (inMST[next]) continue; // ignore this edge if the connected node of this edge is already in the mst
                    // add this edge to pq
                    pq.offer(edge);
                }
            }

            public int weightSum() {
                return weightSum;
            }

            // determine if all the nodes are added to the mst (marked as true in `inMst`)
            public boolean allConnected() {
                for (int i = 0; i < inMST.length; i++) {
                    if (!inMST[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    /**
     * Kruskal's Algorithm:
     * 1. sort the edges based on the weight from small to large
     * 2. add the edges ascending
     * 3. use union-find to add edges to ensure the built tree is acyclic
     */
    class Solution3_Kruskal {
        /**
         * Time: O(eloge + n + e) = O(eloge + n)
         * Space: O(loge + n + e) = O(n + e)
         * @param n num of nodes
         * @param connections input edges
         * @return weight sum of the built mst
         */
        public int minimumCost(int n, int[][] connections) {
            // 1 - sort the edges
            Arrays.sort(connections, Comparator.comparingInt(a -> a[2])); // sort the edges based on the edge weight
            // 2 - use union-find to add the edges to ensure there's no cycle
            UnionFind uf = new UnionFind(n);
            int weightSum = 0;
            // 3- add the edges from the smallest weight
            for (int[] edge : connections) {
                if (uf.count() == 1) break;
                int from = edge[0] - 1, to = edge[1] - 1, weight = edge[2]; // starting index change to 0, current is 1
                if (uf.union(from, to)) { // connect two nodes into one component
                    weightSum += weight;
                }
            }
            return uf.count() == 1 ? weightSum : -1;
        }

        private class UnionFind {
            int count; // record the connected components
            int[] parent; // record the node's parent

            public UnionFind(int n) {
                count = n;
                parent = new int[n];
                for (int i  = 0; i < n; i++) {
                    parent[i] = i; // default parent is themselves
                }
            }

            public boolean union(int n1, int n2) { // return false if two nodes are already connected
                int p1 = findParent(n1), p2 = findParent(n2);
                if (p1 == p2) return false; // already connected
                parent[p1] = p2; // parent[p2] = p1
                count--;
                return true;
            }

            private int findParent(int node) {
                if (parent[node] == node) return node; // root node of the tree we built
                parent[node] = findParent(parent[node]);
                return parent[node]; // always return root node of the built tree
            }

            public int count() {
                return count;
            }
        }
    }
}
