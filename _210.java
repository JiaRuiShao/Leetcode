import java.util.*;

/**
 * 210. Course Schedule II
 */
public class _210 {
    /**
     * BFS Traverse.
     * Time: O(V + E), would be O(VE) if we choose not to build a graph
     * Space: O(V + E), would be O(V) if we choose not to build a graph
     */
    class Solution1_BFS {
        public int[] findOrder(int numCourses, int[][] prerequisites) {
            // Step 1: Build graph and in-degree array
            List<Integer>[] graph = new ArrayList[numCourses];
            int[] inDegree = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int[] prereq : prerequisites) {
                int next = prereq[0], pre = prereq[1];
                graph[pre].add(next);
                inDegree[next]++;
            }

            // Step 2: Add courses with 0 in-degree to queue
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < numCourses; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }

            // Step 3: BFS
            int[] order = new int[numCourses];
            int studied = 0;
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                order[studied++] = curr;
                for (int neighbor : graph[curr]) {
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
            return studied == numCourses ? order : new int[]{};
        }
    }

    /**
     * DFS Post-order Traversal.
     * Time: O(V + E)
     * Space: O(V + E)
     */
    class Solution2_DFS {
        boolean[] visited, onPath;
        boolean hasCycle = false;

        public int[] findOrder(int numCourses, int[][] prerequisites) {
            // 1 - build the directed graph
            visited = new boolean[numCourses];
            onPath = new boolean[numCourses];
            List<Integer>[] g = buildGraph(numCourses, prerequisites);
            // 2 - traverse the graph & store the post-order traverse result
            List<Integer> res = new ArrayList<>();
            for (int node = 0; node < numCourses; node++) {
                traverse(g, node, res);
            }

            Collections.reverse(res);
            return hasCycle ? new int[]{} : res.stream().mapToInt(Integer::intValue).toArray();
        }

        private List<Integer>[] buildGraph(int n, int[][] edges) {
            List<Integer>[] g = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new LinkedList<>();
            }

            for (int[] edge : edges) { // n0 depends on n1, n1 -> n0
                g[edge[1]].add(edge[0]);
            }

            return g;
        }

        private void traverse(List<Integer>[] g, int node, List<Integer> res) {
            if (onPath[node]) hasCycle = true;
            if (hasCycle || visited[node]) return;

            visited[node] = true;
            onPath[node] = true;

            for (int next : g[node]) {
                traverse(g, next, res);
            }

            onPath[node] = false;
            res.add(node); // post-order traversal
        }
    }

    /**
     * DFS Post-order Traversal without using global variables.
     * Time: O(V + E)
     * Space: O(V + E)
     */
    class Solution3_DFS_Improved {
        public int[] findOrder(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = buildGraph(numCourses, prerequisites);
            boolean[] visited = new boolean[numCourses];
            boolean[] onPath = new boolean[numCourses];
            List<Integer> order = new ArrayList<>();

            for (int course = 0; course < numCourses; course++) {
                if (!visited[course]) {
                    if (dfs(course, graph, visited, onPath, order)) {
                        return new int[0];
                    }
                }
            }

            // Reverse post-order to get topological order
            Collections.reverse(order);
            return order.stream().mapToInt(i -> i).toArray();
        }

        private List<Integer>[] buildGraph(int numNode, int[][] edges) {
            List<Integer>[] graph = new ArrayList[numNode];
            for (int i = 0; i < numNode; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int[] edge : edges) {
                int next = edge[0];
                int pre = edge[1];
                graph[pre].add(next);
            }
            return graph;
        }

        private boolean dfs(int course, List<Integer>[] graph, boolean[] visited, boolean[] onPath, List<Integer> order) {
            if (onPath[course]) {
                return true;
            }
            if (visited[course]) {
                return false;
            }

            visited[course] = true;
            onPath[course] = true;

            for (int neighbor : graph[course]) {
                if (dfs(neighbor, graph, visited, onPath, order)) {
                    return true;
                }
            }

            order.add(course);
            onPath[course] = false;
            return false;
        }
    }

}
