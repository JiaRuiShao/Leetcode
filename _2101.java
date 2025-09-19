import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 2101. Detonate the Maximum Bombs
 */
public class _2101 {
    class Solution1_BFS {
        // directed graph: find the max nodes visited onPath
        public int maximumDetonation(int[][] bombs) {
            int n = bombs.length;
            List<Integer>[] graph = new ArrayList[n];
            for (int node = 0; node < n; node++) {
                graph[node] = new ArrayList<>();
            }
            for (int n1 = 0; n1 < n - 1; n1++) {
                int a = bombs[n1][0], b = bombs[n1][1], r1 = bombs[n1][2];
                for (int n2 = n1 + 1; n2 < n; n2++) {
                    int x = bombs[n2][0], y = bombs[n2][1], r2 = bombs[n2][2];
                    if (withinRadius(a, b, x, y, r1)) {
                        graph[n1].add(n2);
                    }
                    if (withinRadius(a, b, x, y, r2)) {
                        graph[n2].add(n1);
                    }
                }
            }
            int maxBomb = 0;
            for (int start = 0; start < n; start++) {
                maxBomb = Math.max(maxBomb, bfs(graph, start));
            }
            return maxBomb;
        }

        private int bfs(List<Integer>[] graph, int start) {
            int n = graph.length, bomb = 0;
            boolean[] visited = new boolean[n];
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(start);
            visited[start] = true;
            while (!q.isEmpty()) {
                int size = q.size();
                bomb += size;
                for (int i = 0; i < size; i++) {
                    int curr = q.poll();
                    for (int nbr : graph[curr]) {
                        if (!visited[nbr]) {
                            q.offer(nbr);
                            visited[nbr] = true;
                        }
                    }
                }
            }
            return bomb;
        }

        private boolean withinRadius(int a, int b, int x, int y, int radius) {
            long distSquare = (long)(a - x) * (a - x) + (long)(b - y) * (b - y); // Euclidean Distance
            return distSquare <= (long) radius * radius;
        }
    }

    // Time: O(n^2 + VE) where V is n and E is n^2 for dense graph
    class Solution1_DFS {
        public int maximumDetonation(int[][] bombs) {
            int n = bombs.length;
            List<Integer>[] graph = new ArrayList[n];
            for (int node = 0; node < n; node++) {
                graph[node] = new ArrayList<>();
            }
            for (int n1 = 0; n1 < n - 1; n1++) {
                int a = bombs[n1][0], b = bombs[n1][1], r1 = bombs[n1][2];
                for (int n2 = n1 + 1; n2 < n; n2++) {
                    int x = bombs[n2][0], y = bombs[n2][1], r2 = bombs[n2][2];
                    if (withinRadius(a, b, x, y, r1)) {
                        graph[n1].add(n2);
                    }
                    if (withinRadius(a, b, x, y, r2)) {
                        graph[n2].add(n1);
                    }
                }
            }
            int maxBomb = 1;
            for (int start = 0; start < n; start++) {
                maxBomb = Math.max(maxBomb, dfs(graph, new boolean[n], start));
            }
            return maxBomb;
        }

        private int dfs(List<Integer>[] graph, boolean[] visited, int node) {
            int bomb = 1;
            visited[node] = true;
            for (int nbr : graph[node]) {
                if (!visited[nbr]) {
                    bomb += dfs(graph, visited, nbr);
                }
            }
            return bomb;
        }

        private boolean withinRadius(int a, int b, int x, int y, int radius) {
            long distSquare = (long)(a - x) * (a - x) + (long)(b - y) * (b - y);
            return distSquare <= (long) radius * radius;
        }
    }
}
