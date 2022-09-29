import java.util.*;

/**
 * 1584. Min Cost to Connect All Points.
 * You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [xi, yi].
 *
 * The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them: |xi - xj| + |yi - yj|,
 * where |val| denotes the absolute value of val.
 *
 * Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path
 * between any two points.
 */
public class _1584 {
    class Solution1_Kruskal {
        /**
         * Time: O(eloge) = O(n^2*log(n^2))
         * Space: O(n + e) = O(n^2)
         *
         * @param points given points, need to construct edges (from, to, weight) by ourselves
         * @return weight sum of the mst
         */
        public int minCostConnectPoints(int[][] points) {
            int n = points.length;
            // 1 - build the edges, edges num = n - 1 + n - 2 + ... + 1 = n(n-1)/2
            List<int[]> edges = new ArrayList<>(); // edge: int[]{from, to, weight}
            for (int n1 = 0; n1 < n - 1; n1++) { // think each point as a node // time: O(e) = O(n^2)
                int x1 = points[n1][0], y1 = points[n1][1]; // point 1
                for (int n2 = n1 + 1; n2 < n; n2++) {
                    int x2 = points[n2][0], y2 = points[n2][1]; // point 2
                    int weight = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                    edges.add(new int[]{n1, n2, weight});
                }
            }

            // 2 - sort the edges based on asc weight
            Collections.sort(edges, (a, b) -> Integer.compare(a[2], b[2])); // time: O(eloge) = O(n^2*log(n^2)), space: O(loge) = O(log(n^2))

            // 3 - union-find to add the edges from small weight large to ensure the built tree is acyclic
            UnionFind uf = new UnionFind(n); // time: O(n), space: O(n)
            int weightSum = 0;

            for (int[] edge : edges) { // O(e) = O(n^2)
                int from = edge[0], to = edge[1], weight = edge[2];
                if (uf.union(from, to)) {
                    weightSum += weight;
                }
            }
            return uf.count() == 1 ? weightSum : -1;
        }

        private class UnionFind {
            private int count;
            private int[] parent;

            public UnionFind(int n) {
                count = n;
                parent = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                }
            }

            public boolean union(int n1, int n2) {
                int p1 = findParent(n1), p2 = findParent(n2);
                if (p1 == p2) return false;
                parent[p1] = p2;
                count--;
                return true;
            }

            private int findParent(int node) {
                if (parent[node] == node) return node;
                parent[node] = findParent(parent[node]);
                return parent[node];
            }

            public int count() {
                return count;
            }
        }
    }

    /**
     * Step:
     * 1 - build the graph using edges
     * 2 - use min-heap to store the edges
     * 3 - poll from min-heap until the all nodes are added
     * 4 - if the polled node hasn't been added to the mst set, add it & offer all edges starting from it to the pq
     */
    class Solution2_Prim {

        /**
         * Time: O(n^2 + eloge) = O(eloge) = O(n^2*log(n^2))
         * Space: O(n^2 + log(n^2) + n^2 + n) = O(n^2)
         *
         * @param points
         * @return
         */
        public int minCostConnectPoints(int[][] points) {
            int n = points.length;
            int[][][] g = buildGraph(n, points); // store the edges using adjacency list, edge: int[]{to, weight}
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            // start from any node, here use node 0
            q.offer(new int[]{0, 0}); // add a dummy edge where start & end node are both node 0 and its weight is 0

            int weightSum = 0, edgeAdd = 0;
            boolean[] mst = new boolean[n];// store the added nodes
            while (!q.isEmpty() || edgeAdd < n) { // O(eloge)
                int[] edge = q.poll();
                int end = edge[0], weight = edge[1];
                if (!mst[end]) {
                    // add this end node to the mst set, & update the weight sum
                    mst[end] = true;
                    weightSum += weight;
                    edgeAdd++;
                    // add the edges whose start node is this end node to the queue
                    for (int[] nextEdge : g[end]) {
                        q.offer(nextEdge); // O(loge)
                    }
                }
            }
            return edgeAdd == n ? weightSum : -1;
        }

        /**
         * Graph Explanation:
         * an array of list, each array bucket contains a list of int array, that int[] is an edge whose starting node is this array bucket index
         * [List<int[]>]
         * [List<int[]>]
         * [List<int[]>]
         *
         * @return the graph
         */
        private int[][][] buildGraph(int n, int[][] points) {
            int[][][] g = new int[n][n][2];
            for (int node = 0; node < n - 1; node++) {
                int x1 = points[node][0], y1 = points[node][1];
                for (int end = node + 1; end < n; end++) {
                    int x2 = points[end][0], y2 = points[end][1];
                    int weight = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                    g[node][end] = new int[]{end, weight};
                    g[end][node] = new int[]{node, weight};
                }
            }
            return g;
        }

        /**
         * Instead of building a graph, we calculated the edges when needed.
         *
         * @param points
         * @return
         */
        public int minCostConnectPointsImproved(int[][] points) {
            int n = points.length;
            // int[][][] g = buildGraph(n, points); // store the edges using adjacency list, edge: int[]{to, weight}
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            // start from any node, here use node 0
            q.offer(new int[]{0, 0}); // add a dummy edge where start & end node are both node 0 and its weight is 0

            int weightSum = 0, edgeAdd = 0;
            boolean[] mst = new boolean[n];// store the added nodes
            while (!q.isEmpty() || edgeAdd < n) { // O(eloge)
                int[] edge = q.poll();
                int end = edge[0], weight = edge[1];
                if (!mst[end]) {
                    // add this end node to the mst set, & update the weight sum
                    mst[end] = true;
                    weightSum += weight;
                    edgeAdd++;
                    // add the edges whose start node is this end node to the queue
                    // for (int[] nextEdge : g[end]) {
                    //     q.offer(nextEdge); // O(loge)
                    // }
                    for (int next = 0; next < n; next++) {
                        if (next == end) continue;
                        int nextWeight = Math.abs(points[end][0] - points[next][0]) + Math.abs(points[end][1] - points[next][1]);
                        q.offer(new int[]{next, nextWeight});
                    }
                }
            }
            return edgeAdd == n ? weightSum : -1;
        }
    }

    /**
     * In this approach, we use one minDist array, where minDist[i] stores the weight of the smallest weighted edge
     * to reach the ith node from any node in the current tree. We will iterate over the minDist array and greedily pick
     * the node that is not in the MST and has the smallest edge weight. We will add this node to the MST, and for all of its
     * neighbors, we will try to update the value in minDist.
     *
     * Repeat this process until all nodes are part of the MST.
     */
    class Solution3_Prim_Advance {

        /**
         * Time: O(n+n^2) = O(n^2)
         * Space: O(n)
         *
         * @param points
         * @return
         */
        public int minCostConnectPoints(int[][] points) {
            int n = points.length;
            int mstCost = 0;
            int edgesUsed = 0;

            // track nodes which are visited
            boolean[] inMST = new boolean[n];

            // min distance to this point/node
            int[] minDist = new int[n];
            minDist[0] = 0;
            for (int i = 1; i < n; ++i) {
                minDist[i] = Integer.MAX_VALUE; // set default distance as Integer.MAX_VALUE
            }

            while (edgesUsed < n) {
                int currWeight = Integer.MAX_VALUE;
                int currNode = -1;

                // pick the least weight node which is not in MST
                for (int node = 0; node < n; ++node) {
                    if (!inMST[node] && minDist[node] < currWeight) {
                        currWeight = minDist[node];
                        currNode = node;
                    }
                }

                // add this node to mst
                mstCost += currWeight;
                edgesUsed++;
                inMST[currNode] = true;

                // update distance of adjacent nodes of current node
                for (int nextNode = 0; nextNode < n; nextNode++) {
                    int weight = Math.abs(points[currNode][0] - points[nextNode][0]) +
                            Math.abs(points[currNode][1] - points[nextNode][1]);

                    if (!inMST[nextNode] && minDist[nextNode] > weight) {
                        minDist[nextNode] = weight;
                    }
                }
            }

            return mstCost;
        }
    }
}
