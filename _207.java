import java.util.ArrayList;
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
        boolean[] visited;
        boolean[] onPath;
        boolean hasCycle;

        /**
         * DFS Traversal.
         * 1) Build the directed graph by implementing adjacency list using input course pre-requisite arr
         * 2) Traverse the tree to detect if the graph is acyclic
         * Time: O(V + E) (+ O(V^2) if we don't have visited) where O(V + E) is the time complexity for building the directed graph,
         * * and the O(V^2) is for traversing the graph; (if we have visited) = O(V + E) + O(V) cuz we're traversing each node once
         * Space: O(V + E) + O(V)
         *
         * @param numCourses total num of courses
         * @param prerequisites int [2]: dependent, dependent_course
         * @return true if the built graph is acyclic, false if not
         */
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = buildGraph(numCourses, prerequisites);

            visited = new boolean[numCourses];
            onPath = new boolean[numCourses];
            hasCycle = false;

            for (int course = 0; course < numCourses; course++) {
                if (!visited[course]) {
                    traverse(course, graph);
                }
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
                int nextCourse = edge[0];  // course to take
                int prereq = edge[1];      // prerequisite course
                g[prereq].add(nextCourse);  // edge from prereq -> nextCourse
            }

            return g;
        }

        /**
         * We have to check onPath first before check visited and return.
         * Time: O(V)
         * Space: O(V)
         *
         * @param graph graph built
         * @param course traversal starting node
         */
        private void traverse(int course, List<Integer>[] graph) {
            if (onPath[course]) {
                hasCycle = true;
            }
            if (hasCycle || visited[course]) {
                return;
            }

            visited[course] = true;
            onPath[course] = true;

            for (int nextCourse : graph[course]) {
                traverse(nextCourse, graph);
            }

            onPath[course] = false;
        }
    }

    class Solution2_DFS_Without_Global_Variables {
        class Solution {
            public boolean canFinish(int numCourses, int[][] prerequisites) {
                List<Integer>[] graph = buildGraph(numCourses, prerequisites);
                boolean[] visited = new boolean[numCourses];
                boolean[] onPath = new boolean[numCourses];

                for (int course = 0; course < numCourses; course++) {
                    if (!visited[course]) {
                        if (hasCycle(course, graph, visited, onPath)) {
                            return false;
                        }
                    }
                }
                return true;
            }

            private List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
                List<Integer>[] graph = new LinkedList[numCourses];
                for (int i = 0; i < numCourses; i++) {
                    graph[i] = new LinkedList<>();
                }
                for (int[] edge : prerequisites) {
                    int next = edge[0];
                    int prereq = edge[1];
                    graph[prereq].add(next);
                }
                return graph;
            }

            private boolean hasCycle(int curr, List<Integer>[] graph, boolean[] visited, boolean[] onPath) {
                if (onPath[curr]) return true;
                if (visited[curr]) return false;

                visited[curr] = true;
                onPath[curr] = true;

                for (int nbr : graph[curr]) {
                    if (hasCycle(nbr, graph, visited, onPath)) {
                        return true;
                    }
                }

                onPath[curr] = false;
                return false;
            }
        }
    }

    class Solution3_BFS {
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
            int studied = 0;
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                studied++;
                for (int neighbor : graph[curr]) {
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }

            return studied == numCourses;
        }
    }

    class Solution4_Without_Build_Graph {
        /**
         * Time: O(V + E + VE) = O(VE)
         * Space: O(V)
         * @param numCourses V
         * @param prerequisites E = prerequisites.length
         * @return if there's any cycle in this graph
         */
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            // Step 1: Build in-degree array
            int[] inDegree = new int[numCourses];
            for (int[] prereq : prerequisites) {
                int next = prereq[0], pre = prereq[1];
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
            int studied = 0;
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                studied++;
                for (int[] prereq : prerequisites) {
                    if (prereq[1] == curr) {
                        int next = prereq[0];
                        inDegree[next]--;
                        if (inDegree[next] == 0) {
                            queue.offer(next);
                        }
                    }
                }
            }

            return studied == numCourses;
        }
    }
}
