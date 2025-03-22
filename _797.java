import java.util.ArrayList;
import java.util.List;

/**
 * 797. All Paths From Source to Target.
 * Given a directed acyclic graph (DAG) of n nodes labeled from 0 to n - 1,
 * find all possible paths from node 0 to node n - 1 and return them in any order.
 *
 * The graph is given as follows: graph[i] is a list of all nodes you can visit from node i
 * * (i.e., there is a directed edge from node i to node graph[i][j]).
 */
public class _797 {
    static class Solution {

        /**
         * Time: O(2^n*n) where 2^(n-2) is the possible path# from node 0 to n - 1, it takes O(n) time to build a path.
         * Space: O(2^n*n)
         * *
         * @param graph adjacent list of an acyclic graph
         * @return all possible path from node 0 to node n - 1
         */
        public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
            List<List<Integer>> paths = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            traverse(graph, paths, path, 0); // start from node 0
            return paths;
        }

        private void traverse(int[][] graph, List<List<Integer>> paths, List<Integer> path, int curr) {
            // select
            path.add(curr);

            // node n - 1
            if (curr == graph.length - 1) {
                paths.add(new ArrayList<>(path));
            }

            for (int node : graph[curr]) {
                traverse(graph, paths, path, node);
            }

            // undo selection
            path.remove(path.size() - 1);
        }

        /**
         * Similar Question: find all possible paths given a acyclic graph adjacent list.
         * Time: O(2^n*n) where O(2^n) is the possible path# from node 0 to n - 1, it takes O(n) time to build a path.
         * Space: O(2^n*n)
         *
         * @param graph adjacent list of an acyclic graph
         * @return all posible paths
         */
        public List<List<Integer>> allPaths(int[][] graph) {
            List<List<Integer>> paths = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            for (int node = 0; node < graph.length; node++) {
                backtrack(graph, paths, path, node);
            }
            return paths;
        }

        private void backtrack(int[][] graph, List<List<Integer>> paths, List<Integer> path, int curr) {
            // select
            path.add(curr);

            if (graph[curr].length == 0) {
                paths.add(new ArrayList<>(path));
            }

            for (int node : graph[curr]) {
                backtrack(graph, paths, path, node);
            }

            // undo selection
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {{4, 3, 1}, {3, 2, 4}, {}, {4}, {}};
        new Solution().allPathsSourceTarget(graph).stream().forEach(System.out::println);
    }
}
