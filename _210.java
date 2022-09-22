import java.util.*;

/**
 * 210. Course Schedule II.
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
 * You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that
 * * you must take course bi first if you want to take course ai.
 *
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * Return the ordering of courses you should take to finish all courses.
 * If there are many valid answers, return any of them.
 * If it is impossible to finish all courses, return an empty array.
 */
public class _210 {
    /**
     * DFS Post-order Traversal.
     * Time: O(V + E)
     * Space: O(V + E)
     */
    class Solution1_DFS {
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
            res.add(node);
        }
    }

    /**
     * BFS Traverse.
     * Time: O(V + E)
     * Space: O(V + E)
     */
    class Solution2_BFS {
        public int[] findOrder(int numCourses, int[][] prerequisites) {
            // 1 - build the directed graph
            List<Integer>[] g = buildGraph(numCourses, prerequisites);

            // 2 - create an in-degree helper arr
            int[] inDegree = new int[numCourses];
            for (int[] edge : prerequisites) {
                inDegree[edge[0]]++; // edge[0] depends on edge[1], direction: edge[1] -> edge[0]
            }

            // 3 - find starting nodes whose in-degree is zero, offer them into the BFS queue
            Queue<Integer> q = new LinkedList<>();
            for (int node = 0; node < numCourses; node++) {
                if (inDegree[node] == 0) q.offer(node);
            }

            // create the res list, along with a counter
            int count = 0;
            int[] res = new int[numCourses];

            // 4 - pop the node out, decrease in-degree of the nodes who depends on this node, offer the nodes into the queue if the in-degree is zero
            while (!q.isEmpty()) {
                int node = q.poll();
                res[count++] = node;
                for (int next : g[node]) {
                    inDegree[next]--;
                    if (inDegree[next] == 0) q.offer(next);
                }
            }

            // 5 - continue util the BFS queue is empty
            // 6 - return the res if the res size == n
            return count == numCourses ? res : new int[]{};
        }

        private List<Integer>[] buildGraph(int n, int[][] edges) {
            List<Integer>[] g = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new LinkedList<>();
            }
            for (int[] edge : edges) {
                g[edge[1]].add(edge[0]); // edge[0] depends on edge[1], edge[1] -> edge[0]
            }
            return g;
        }

    }
}
