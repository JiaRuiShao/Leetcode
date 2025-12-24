package topics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
    static class Edge {
        String to;
        double weight;
        Edge(String node, double weight) {
            this.to = node;
            this.weight = weight;
        }
    }

    private Map<String, List<Edge>> buildDirectedWeightedGraph(String[][] edges, double[] weights) {
        Map<String, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            String[] edge = edges[i];
            String from = edge[0], to = edge[1];
            double weight = weights[i];
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(new Edge(to, weight)); // when traverse later, could have NPE, need to use: for (Edge nbr : graph.getOrDefault(node, new ArrayList<>())) for traversal
        }
        return graph;
    }

    private List<int[]>[] buildDirectedWeightedGraph(int n, int[][] edges) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1], weight = edge[2];
            graph[from].add(new int[]{to, weight});
        }
        return graph;
    }

    private List<Integer>[] buildDirectedGraph(int n, int[][] edges) {
        List<Integer>[] graph = new ArrayList[n];
        for (int node = 0; node < n; node++) {
            graph[node] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            graph[from].add(to);
        }
        return graph;
    }

    private Map<Integer, List<Integer>> buildDirectedGraphUsingMap(int n, int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        }
        return graph;
    }

    int countMaximumTraversedNode(int n, Map<Integer, List<Integer>> graph) {
        boolean[] visited = new boolean[n];
        int traversed = 0;
        for (int start = 0; start < n; start++) {
            if (!visited[start]) {
                traversed = Math.max(traversed, countTraversedNode(graph, visited, start));
            }
        }
        return traversed;
    }

    private int countTraversedNode(Map<Integer, List<Integer>> graph, boolean[] visited, int node) {
        int count = 1;
        visited[node] = true;
        for (int nbr : graph.getOrDefault(node, new ArrayList<>())) {
            if (visited[nbr]) continue;
            count += countTraversedNode(graph, visited, nbr);
        }
        return count;
    }

    boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n]; // 1 or 2
        for (int start = 0; start < n; start++) {
            if (colors[start] != 0) continue;
            if (!colorDFS(graph, colors, start, 1)) return false;
        }
        return true;
    }

    private boolean colorDFS(int[][] graph, int[] colors, int node, int color) {
        colors[node] = color;
        int nextColor = color == 1 ? 2 : 1;
        for (int nbr : graph[node] == null ? new int[0] : graph[node]) {
            if (colors[nbr] == nextColor) continue;
            if (colors[nbr] == color) return false;
            if (!colorDFS(graph, colors, nbr, nextColor)) return false;
        }
        return true;
    }

    boolean cycleDetectForDirectedGraph(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n]; // used for early stopping
        boolean[] onPath = new boolean[n]; // used for cycle detection
        for (int start = 0; start < n; start++) {
            if (!visited[start] && cycleFoundDFS(graph, visited, start, onPath)) return true;
        }
        return false;
    }

    boolean cycleFoundDFS(int[][] graph, boolean[] visited, int node, boolean[] onPath) {
        visited[node] = true;
        onPath[node] = true;
        for (int nbr : graph[node]) {
            if (onPath[nbr]) return true; // cycle found
            if (!visited[nbr] && cycleFoundDFS(graph, visited, nbr, onPath)) return true;
        }
        onPath[node] = false;
        return false; // no cycle found
    }

    boolean cycleFoundBFS(int[][] graph) {
        int n = graph.length;
        int[] inDegree = new int[n];
        for (int[] adjNode : graph) {
            for (int adj : adjNode) {
                inDegree[adj]++;
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int node = 0; node < n; node++) {
            if (inDegree[node] == 0) q.offer(node);
        }

        int visited = 0;
        while (!q.isEmpty()) {
            int curr = q.poll();
            visited++;
            for (int nbr : graph[curr]) {
                if (--inDegree[nbr] == 0) {
                    q.offer(nbr);
                }
            }
        }
        return visited == n;
    }

    boolean cycleDetectForUndirectedGraph(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        for (int start = 0; start < n; start++) {
            if (!visited[start] && cycleDetectUndirectedDFS(graph, visited, start, -1)) return true;
        }
        return false;
    }

    private boolean cycleDetectUndirectedDFS(int[][] graph, boolean[] visited, int node, int parent) {
        visited[node] = true;
        for (int nbr : graph[node]) {
            if (nbr == parent) continue;
            if (visited[nbr]) return true;
            if (cycleDetectUndirectedDFS(graph, visited, nbr, node)) return true;
        }
        return false;
    }

    int[] parents;
    int[] size;

    int mstKruskal(int[][] edges, int n) {
        this.parents = new int[n];
        this.size = new int[n];

        // prepare parent & size initial value
        for (int i = 0; i < n; i++) {
            parents[i] = i;
            size[i] = 1;
        }

        // sort & traverse edges
        Arrays.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));
        int connected = n, cost = 0; // cost could have integer overflow issue
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            if (union(u, v)) {
                cost += weight;
                if (--connected == 1) return cost;
            }
        }
        return cost; // single vertex graph
    }

    private boolean union(int u, int v) {
        int p1 = find(u), p2 = find(v);
        if (p1 == p2) return false;
        if (size[p1] < size[p2]) {
            parents[p1] = p2;
            size[p2] += size[p1];
        } else {
            parents[p2] = p1;
            size[p1] += size[p2];
        }
        return true;
    }

    private int find(int node) {
        if (parents[node] != node) {
            parents[node] = find(parents[node]);
        }
        return parents[node];
    }

    // Be aware of the possible integer overflow for weight
    int mstPrim(List<int[]>[] graph, int n) {
        boolean[] inMst = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e[1])); // [to, weight]
        int visited = 0, cost = 0, start = 0;
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int v = curr[0], weight = curr[1];
            if (inMst[v]) continue;
            inMst[v] = true;
            cost += weight;
            visited++;
            for (int[] edge : graph[v]) {
                int nbr = edge[0], nbrWeight = edge[1];
                if (!inMst[nbr]) {
                    pq.offer(new int[]{nbr, nbrWeight});
                }
            }
        }
        return visited == n ? cost : -1;
    }

    // Be aware of the possible integer overflow for distance
    int[] sspDijkstra(List<int[]>[] graph, int start) {
        int n = graph.length, cost = 0;
        int[] dist = new int[n]; // dist to start
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e[1])); // [vertex, distToStart]
        pq.offer(new int[]{start, cost});
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0], wu = curr[1];
            if (dist[u] <= wu)  continue;
            dist[u] = wu;
            for (int[] next : graph[u]) {
                int v = next[0], wv = wu + next[1];
                if (wv < dist[v]) {
                    pq.offer(new int[]{v, wv});
                }
            }
        }
        return dist;
    }
}
