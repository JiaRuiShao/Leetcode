import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 886. Possible Bipartition
 */
public class _886 {
    class Solution1_BFS {
        /**
         * BFS Solution
         * Key Points:
         * 1 - dislike pair [a, b] means a & b should be in different color, when construct graph, make them neighbors
         * 2 - we need to build a undirected/bidirectional graph since the dislike is mutual
         * 3 - node label is 1-based not 0-based in this question
         * @param n nodes
         * @param dislikes dislikes
         * @return true if it's a valid bipartite graph
         */
        public boolean possibleBipartition(int n, int[][] dislikes) {
            int[] color = new int[n]; // 1 , 2
            List<Integer>[] graph = new ArrayList[n];
            for (int node = 0; node < n; node++) {
                graph[node] = new ArrayList<>();
            }

            for (int[] edge : dislikes) {
                // convert the node from 1-based to 0-based
                graph[edge[0] - 1].add(edge[1] - 1);
                graph[edge[1] - 1].add(edge[0] - 1);
            }

            for (int node = 0; node < n; node++) {
                if (color[node] == 0) {
                    if (!bfs(node, color, graph)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean bfs(int node, int[] color, List<Integer>[] graph) {
            Queue<Integer> q = new LinkedList<>();
            color[node] = 1;
            q.offer(node);

            while (!q.isEmpty()) {
                int curr = q.poll();
                for (int neighbor : graph[curr]) {
                    if (color[neighbor] == 0) {
                        color[neighbor] = 3 - color[curr];
                        q.offer(neighbor);
                    } else if (color[neighbor] == color[curr]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    class Solution2_DFS {
        /**
         * dislike pair [a, b] means a & b should be in different color
         node label is 1-based
         */
        public boolean possibleBipartition(int n, int[][] dislikes) {
            int[] color = new int[n]; // 1 , 2
            List<Integer>[] graph = new ArrayList[n];
            for (int node = 0; node < n; node++) {
                graph[node] = new ArrayList<>();
            }

            for (int[] edge : dislikes) {
                // convert the node from 1-based to 0-based
                graph[edge[0] - 1].add(edge[1] - 1);
                graph[edge[1] - 1].add(edge[0] - 1);
            }

            for (int node = 0; node < n; node++) {
                if (color[node] == 0) {
                    if (!dfs(node, color, graph, 1)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean dfs(int node, int[] color, List<Integer>[] graph, int currColor) {
            if (color[node] != 0) {
                return color[node] == currColor;
            }

            color[node] = currColor;
            for (int neighbor : graph[node]) {
                if (!dfs(neighbor, color, graph, 3 - currColor)) {
                    return false;
                }
            }
            return true;
        }
    }
}
