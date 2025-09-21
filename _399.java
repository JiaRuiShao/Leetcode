import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 399. Evaluate Division
 */
public class _399 {
    // think each variable as node and their relationship as the edge weight, 
    // i.e. for equation a / b = k, we can build edges:
    // a -> b with weight k 
    // b -> a with weight 1/k
    class Solution1_Graph_DFS {
        class Edge {
            String var;
            double weight;
            public Edge(String var, double weight) {
                this.var = var;
                this.weight = weight;
            }
        }

        public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            // build adjacency list for this directed weighted graph
            int e = values.length;
            Map<String, List<Edge>> graph = new HashMap<>();
            for (int i = 0; i < e; i++) {
                String a = equations.get(i).get(0), b = equations.get(i).get(1);
                graph.computeIfAbsent(a, k -> new ArrayList<>()).add(new Edge(b, values[i]));
                graph.computeIfAbsent(b, k -> new ArrayList<>()).add(new Edge(a, 1 / values[i]));
            }

            // for each query, find the path using dfs/bfs & accumulated weight
            double[] calc = new double[queries.size()];
            for (int i = 0; i < queries.size(); i++) {
                List<String> query = queries.get(i);
                String a = query.get(0), b = query.get(1);
                Set<String> visited = new HashSet<>();
                calc[i] = dfs(graph, a, b, visited);
            }
            return calc;
        }

        private double dfs(Map<String, List<Edge>> graph, String node, String end, Set<String> visited) {
            if (!graph.containsKey(node) || !graph.containsKey(end)) return -1.0;
            if (node.equals(end)) return 1.0;
            visited.add(node);
            for (Edge edge : graph.get(node)) {
                String next = edge.var;
                if (!visited.contains(next)) {
                    double weight = dfs(graph, next, end, visited);
                    if (weight == -1) continue;
                    return edge.weight * weight;
                }
            }
            return -1.0;
        }
    }

    class Solution1_Graph_BFS {
        class Edge {
            String var;
            double weight;
            public Edge(String var, double weight) {
                this.var = var;
                this.weight = weight;
            }
        }

        public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            // build adjacency list for this directed weighted graph
            int e = values.length;
            Map<String, List<Edge>> graph = new HashMap<>();
            for (int i = 0; i < e; i++) {
                String a = equations.get(i).get(0), b = equations.get(i).get(1);
                graph.computeIfAbsent(a, k -> new ArrayList<>()).add(new Edge(b, values[i]));
                graph.computeIfAbsent(b, k -> new ArrayList<>()).add(new Edge(a, 1 / values[i]));
            }

            // for each query, find the path using dfs/bfs & accumulated weight
            double[] calc = new double[queries.size()];
            for (int i = 0; i < queries.size(); i++) {
                List<String> query = queries.get(i);
                String a = query.get(0), b = query.get(1);
                calc[i] = bfs(graph, a, b);
            }
            return calc;
        }

        private double bfs(Map<String, List<Edge>> graph, String start, String end) {
            if (!graph.containsKey(start) || !graph.containsKey(end)) return -1.0;
            Queue<Edge> q = new ArrayDeque<>();
            Set<String> visited = new HashSet<>();
            q.offer(new Edge(start, 1));
            visited.add(start);
            while(!q.isEmpty()) {
                Edge curr = q.poll();
                String node = curr.var;
                double weight = curr.weight;
                if (node.equals(end)) return weight;
                for (Edge edge : graph.get(node)) {
                    String next = edge.var;
                    if (!visited.contains(next)) {
                        q.offer(new Edge(next, weight * edge.weight));
                        visited.add(next);
                    }
                }
            }
            return -1.0;
        }
    }

    // we use == instead of equals to compare here because the heap address stays the same
    class Solution2_UF {
        Map<String, String> parent;
        Map<String, Double> weight; // var / parent
        public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            parent = new HashMap<>();
            weight = new HashMap<>();
            for (int i = 0; i < values.length; i++) {
                List<String> equation = equations.get(i);
                String var1 = equation.get(0), var2 = equation.get(1);
                union(var1, var2, values[i]);
            }
            double[] calc = new double[queries.size()];
            for (int i = 0; i < calc.length; i++) {
                List<String> query = queries.get(i);
                String var1 = query.get(0), var2 = query.get(1);
                if (parent.containsKey(var1) && parent.containsKey(var2) && find(var1) == find(var2)) {
                    calc[i] = weight.get(var1) / weight.get(var2);
                } else {
                    calc[i] = -1.0;
                }
            }
            return calc;
        }

        private void initialize(String var) {
            parent.putIfAbsent(var, var);
            weight.putIfAbsent(var, 1.0);
        }

        // a / b = k, if we set pb as parent of pa, what is the weight of pa? pa / pb
        // we also know wa = a / pa, wb = b / pb; then pa = a / wa and pb = b / wb;
        // so weight(pa) = pa / pb = a / b * (wb / wa) = k * wb / wa
        private void union(String a, String b, double k) {
            initialize(a);
            initialize(b);
            String pa = find(a), pb = find(b);
            if (!pa.equals(pb)) {
                parent.put(pa, pb);
                weight.put(pa, k * weight.get(b) / weight.get(a));
            }
        }

        // b is parent of a & c is parent of b before this method, we know a / b = wa, b / c = wb;
        // now we set parent of a as parent of b, which is c, we need to know newWa = a / c = wa * wb
        private String find(String var) {
            String p = parent.get(var);
            if (p != var) {
                String pp = find(p);
                parent.put(var, pp);
                weight.put(var, weight.get(var) * weight.get(p));
            }
            return parent.get(var);
        }
    }
}
