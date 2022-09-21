import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 207. Course Schedule.
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
 * * You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that
 * * you must take course bi first if you want to take course ai.
 *
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * Return true if you can finish all courses. Otherwise, return false.
 */
public class _207 {
    class Solution1_DFS_Traverse {
        boolean hasCycle = false;
        /**
         * DFS Traversal.
         * 1) Build the directed graph by implementing adjacency list using input course pre-requisite arr
         * 2) Traverse the tree to detect if the graph is acyclic
         * Time: O(V + E) + O(V^2) (if we don't have visited) where O(V + E) is the time complexity for building teh directed graph,
         * * and the O(V^2) is for traversing the graph; (if we have visited) = O(V + E) + O(V) cuz we're traversing each node once
         * Space: O(V + E) + O(V)
         *
         * @param numCourses total num of courses
         * @param prerequisites int [2]: dependent, dependent_course
         * @return true if the built graph is acyclic, false if not
         */
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            List<Integer>[] g = buildGraph(numCourses, prerequisites);
            boolean[] visited = new boolean[numCourses];
            boolean[] onPath = new boolean[numCourses];

            for (int course = 0; course < numCourses; course++) {
                traverseGraph(g, visited, onPath, course);
            }

            return !hasCycle;
        }

        /**
         * Time: O(V + E) where V is the node/vertex number, and E is the edge num, which is prerequisites.length
         * Space: O(V + E)
         *
         * @param num numCourses
         * @param prerequisites int[][] dependencies input
         * @return the built graph implemented by adjacency list
         */
        private List<Integer>[] buildGraph(int num, int[][] prerequisites) {
            List<Integer>[] g = new LinkedList[num];
            for (int i = 0; i < num; i++) {
                g[i] = new LinkedList<>();
            }

            for (int[] edge : prerequisites) {
                g[edge[1]].add(edge[0]); // add the dependent to its dependent node, direction from edge[1] to edge[0]
            }

            return g;
        }

        /**
         * Time: O(V)
         * Space: O(V)
         *
         * @param g graph built
         * @param visited visited boolean arr
         * @param onPath onPath boolean arr
         * @param node traversal starting node
         */
        private void traverseGraph(List<Integer>[] g, boolean[] visited, boolean[] onPath, int node) {
            if (onPath[node]) hasCycle = true;
            if (visited[node] || onPath[node]) return;

            visited[node] = true;
            onPath[node] = true;
            for (int dependent : g[node]) {
                traverseGraph(g, visited, onPath, dependent);
            }
            onPath[node] = false;
        }
    }

    class Solution2_BFS {
        /**
         * BFS Traversal.
         * 1) Build a directed graph by implementing an adjacency list
         * 2) Build an in-degree array, which is to store the in-degree of each course
         * 3) Initialize a BFS queue & offer the starting courses into it, that is the courses who don't have any prerequisites/zero in-degree
         * 4) Pop out the starting courses, and decrease the in-degree of the dependent courses
         * 5) Push the dependent course into the queue if its in-degree is zero (doesn't have any pre-requisites)
         * 6) Continue util the queue is empty
         * 7) if all courses have been traversed, then there's no cycle
         *
         * Time: O(V + E) + O(E) + O(V) + O(V) where V is the numCourses, and E is the num of edges, AKA prerequisites.length
         * Space: O(V + E) + O(V) + O(V)
         *
         * @param numCourses total num of courses
         * @param prerequisites int [2]: dependent, dependent_course
         * @return true if the built graph is acyclic, false if not
         */
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            List<Integer>[] g = buildGraph(numCourses, prerequisites);

            int[] inDegree = new int[numCourses];
            for (int[] prerequisite : prerequisites) {
                inDegree[prerequisite[0]]++; // edge direction: prerequisite[1] --> prerequisite[0]
            }

            Queue<Integer> q = new LinkedList<>();
            for (int node = 0; node < numCourses; node++) {
                if (inDegree[node] == 0) q.offer(node);
            }

            int count = 0;
            while (!q.isEmpty()) {
                int node = q.poll();
                count++;
                for (int dependent : g[node]) {
                    if (--inDegree[dependent] == 0) q.offer(dependent);
                }
            }
            return count == numCourses;
        }

        private List<Integer>[] buildGraph(int n, int[][] edges) {
            List<Integer>[] g = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new LinkedList<>();
            }
            for (int[] edge : edges) {
                g[edge[1]].add(edge[0]); // direction: edge[1] -> edge[0] -> ...
            }
            return g;
        }
    }
}
