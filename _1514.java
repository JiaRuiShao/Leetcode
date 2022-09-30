import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 1514. Path with Maximum Probability。
 *
 * You are given an undirected weighted graph of n nodes (0-indexed), represented by an edge list where
 * edges[i] = [a, b] is an undirected edge connecting the nodes a and b with a probability of success of traversing that edge succProb[i].
 *
 * Given two nodes start and end, find the path with the maximum probability of success to go from start to end
 * and return its success probability. If there is no path from start to end, return 0.
 */
public class _1514 {
    class Solution1_Dijkstra {

        /**
         * A helper class to record current node id and its probability.
         */
        private class State {
            int id; // node id
            double prob; // prob from starting node

            public State(int id, double prob) {
                this.id = id;
                this.prob = prob;
            }
        }

        /**
         * Time: O(n+e+eloge+n) = O(n+eloge)
         * Space: O(n+e+e+n) = O(n+e)
         * *
         * @param n num of nodes
         * @param edges edges of the graph
         * @param succProb prob array for each edge
         * @param start start node
         * @param end end node
         * @return max probability from start node to end node; if no path from start to end, return 0
         */
        public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
            // 1 - build the graph using the given edges
            List<double[]>[] g = new ArrayList[n]; // double[]{end, prob}
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
            }
            for (int i = 0; i < edges.length; i++) {
                int[] edge = edges[i];
                g[edge[0]].add(new double[]{edge[1], succProb[i]});
                g[edge[1]].add(new double[]{edge[0], succProb[i]});
            }

            // 2 - create the max-heap of the nodes, create a prob array
            PriorityQueue<State> maxHeap = new PriorityQueue<>(edges.length, (a, b) -> Double.compare(b.prob, a.prob));
            // 3 - create the prob table to store the best probability for each node during the traverse
            double[] prob = new double[n];
            Arrays.fill(prob, -1);

            // 4 - start the traverse from the start node, notice the initialized probability for start node is 1 instead of 0
            prob[start] = 1;
            maxHeap.offer(new State(start, 1));

            // 5 - BFS traverse
            while (!maxHeap.isEmpty()) {
                State state = maxHeap.poll(); // poll the starting node with the largest edge
                int curNode = state.id;
                double curProb = state.prob;
                if (curNode == end) return prob[end];
                if (curProb < prob[curNode])
                    continue;  // ignore this edge if the starting node already has a larger probability

                for (double[] edge : g[curNode]) {
                    double nextProb = curProb * edge[1];
                    int nextNode = (int) edge[0];
                    if (nextProb > prob[nextNode]) {
                        prob[nextNode] = nextProb;
                        maxHeap.offer(new State(nextNode, nextProb));
                    }
                }
            }
            return 0;
        }

    }
}
