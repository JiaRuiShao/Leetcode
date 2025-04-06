import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 1514. Path with Maximum Probability
 */
public class _1514 {
    // the reason we use a helper class here is because node is of int type and succProb is of double type
    class Solution1_Dijkstra_Without_AdjList {

        class Pair {
            public int node;
            public double succRate;
            public Pair(int node, double succRate) {
                this.node = node;
                this.succRate = succRate;
            }
        }
    
        public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
            double[] prob = new double[n];
            Arrays.fill(prob, 0);
    
            PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Double.compare(b.succRate, a.succRate));
            pq.offer(new Pair(start_node, 1));
            prob[start_node] = 1;
    
            while(!pq.isEmpty()) {
                Pair pair = pq.poll();
                int curr = pair.node;
                double succRate = pair.succRate;
                if (curr == end_node) {
                    return succRate;
                }
                if (succRate < prob[curr] || succRate == 0) continue;
                for (int i = 0; i < edges.length; i++) {
                    int next = -1;
                    if (edges[i][0] == curr) {
                        next = edges[i][1];
                    } else if (edges[i][1] == curr) {
                        next = edges[i][0];
                    }
                    if (next != -1) {
                        double nextSuccRate = succRate * succProb[i];
                        if (nextSuccRate > prob[next]) {
                            pq.offer(new Pair(next, nextSuccRate));
                            prob[next] = nextSuccRate;
                        }
                    }
                }
            }
            return 0;
        }
    }

    class Solution2_Dijkstra_With_AdjList {

        class Pair {
            public int node;
            public double succRate;
            public Pair(int node, double succRate) {
                this.node = node;
                this.succRate = succRate;
            }
        }
    
        public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
            List<Pair>[] graph = new ArrayList[n];
            for (int node = 0; node < n; node++) {
                graph[node] = new ArrayList<>();
            }
            for (int i = 0; i < edges.length; i++) {
                int[] edge = edges[i];
                double succRate = succProb[i];
                int from = edge[0], to = edge[1];
                graph[from].add(new Pair(to, succRate));
                graph[to].add(new Pair(from, succRate));
            }
            
            double[] prob = new double[n];
            Arrays.fill(prob, 0);
    
            PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Double.compare(b.succRate, a.succRate));
            pq.offer(new Pair(start_node, 1));
            prob[start_node] = 1;
    
            while(!pq.isEmpty()) {
                Pair pair = pq.poll();
                int curr = pair.node;
                double succRate = pair.succRate;
                if (curr == end_node) {
                    return succRate;
                }
                if (succRate < prob[curr] || succRate == 0) continue;
                for (Pair nextPair : graph[curr]) {
                    int next = nextPair.node;
                    double nextSuccRate = succRate * nextPair.succRate; 
                    if (nextSuccRate > prob[next]) {
                        pq.offer(new Pair(next, nextSuccRate));
                        prob[next] = nextSuccRate;
                    }
                }
            }
            return 0;
        }
    }
}
