import java.util.*;

/**
 * 210. Course Schedule II
 */
public class _210 {
    // Time: O(V + E), would be O(VE) if we choose not to build a graph
    // Space: O(V + E), would be O(V) if we choose not to build a graph
    class Solution1_BFS_CycleDetectionForDirectedGraph {
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

    class FollowUpReturnAllPossibleOrders {
        public List<List<Integer>> findAllOrders(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = buildGraph(numCourses, prerequisites);
            int[] inDegree = new int[numCourses];
        
            for (int[] pre : prerequisites) {
                inDegree[pre[0]]++;
            }
        
            List<List<Integer>> result = new ArrayList<>();
            boolean[] visited = new boolean[numCourses];
            backtrack(numCourses, graph, inDegree, new ArrayList<>(), result, visited);
            return result;
        }
        
        private void backtrack(int n, List<Integer>[] graph, int[] inDegree, List<Integer> path, List<List<Integer>> result, boolean[] visited) {
            if (path.size() == n) {
                result.add(new ArrayList<>(path));
                return;
            }
        
            for (int node = 0; node < n; node++) {
                if (!visited[node] && inDegree[node] == 0) {
                    // choose
                    visited[node] = true;
                    path.add(node);
                    for (int neighbor : graph[node]) {
                        inDegree[neighbor]--;
                    }
        
                    // explore
                    backtrack(n, graph, inDegree, path, result, visited);
        
                    // un-choose
                    visited[node] = false;
                    path.remove(path.size() - 1);
                    for (int neighbor : graph[node]) {
                        inDegree[neighbor]++;
                    }
                }
            }
        }
        
        private List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = new ArrayList[numCourses];
            for (int i = 0; i < numCourses; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int[] pre : prerequisites) {
                graph[pre[1]].add(pre[0]);
            }
            return graph;
        }
    }

    // Time: O(V + E)
    // Space: O(V + E)
    class Solution2_DFS {
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

    class Solution2_DFS_CycleDetectionForDirectedGraph {
        public int[] findOrder(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = new ArrayList[numCourses];
            for (int i = 0; i < numCourses; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int[] prereq : prerequisites) {
                graph[prereq[1]].add(prereq[0]);
            }

            // 0 - unvisited
            // 1 - visiting
            // 2 - visited
            int[] state = new int[numCourses];
            List<Integer> reversed = new ArrayList<>();
            for (int start = 0; start < numCourses; start++) {
                if (state[start] == 0) {
                    if (cycleFound(graph, start, state, reversed)) {
                        return new int[0];
                    }
                }
            }
            
            int[] ordered = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                ordered[i] = reversed.get(numCourses - 1 - i);
            }
            return ordered;
        }

        private boolean cycleFound(List<Integer>[] graph, int node, int[] state, List<Integer> reversed) {
            if (state[node] == 1) {
                return true;
            }
            if (state[node] == 2) {
                return false;
            }

            state[node] = 1;
            for (int nbr : graph[node]) {
                if (cycleFound(graph, nbr, state, reversed)) {
                    return true;
                }
            }
            state[node] = 2;
            reversed.add(node);
            return false;
        }
    }
}
