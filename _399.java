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
 * 
 * - S1 DFS O(E+Q(V+E)), O(V+E)
 * - S2 BFS O(E+Q(V+E)), O(V+E)
 * - S3 UF O(E+Q), O(V) [PREFERRED]
 * 
 * Clarification:
 * - Are variables in queries also in equations? Not necessary
 * - Are variables all lowercase English letters? NO they could also be digits -- if only lowercase English letters, we don't need HashMap and helper class in UF solution, int[] parent and double[] weight should be enough
 */
public class _399 {
    // think each variable as node and their relationship as the edge weight, 
    // i.e. for equation a / b = k, we can build edges:
    // a -> b with weight k 
    // b -> a with weight 1/k
    // Time: O(E+Q(V+E))
    // Space: O(V+E+V) = O(V+E) where O(V) for each recursion stack
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
                if (!graph.containsKey(a) || !graph.containsKey(b)) {
                    calc[i] = -1.0;
                    continue;
                }
                if (a.equals(b)) {
                    calc[i] = 1.0;
                    continue;
                }
                Set<String> visited = new HashSet<>();
                calc[i] = dfs(graph, a, b, visited);
            }
            return calc;
        }

        private double dfs(Map<String, List<Edge>> graph, String start, String end, Set<String> visited) {
            visited.add(start);
            for (Edge edge : graph.get(start)) {
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
    class Solution2_UF_HashMap {
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

    // Time: O(E × α(V) + Q × α(V)) = O(E+Q) where Q is num of queries
    // Space: O(V)
    class Solution2_UF_HelperClass {
        static class EquationNode {
            String var;
            EquationNode parent;
            double ratio; // ratio of this / parent
            EquationNode(String var) {
                this.var = var;
                this.parent = this;
                this.ratio = 1.0;
            }
        }

        private void union(EquationNode a, EquationNode b, double ratio) {
            EquationNode p1 = find(a);
            EquationNode p2 = find(b);
            if (p1 != p2) {
                p1.parent = p2;
                // a/b = ratio
                // a/p1 = a.ratio
                // b/p2 = b.ratio
                // p1/p2 = (a/a.ratio) * (b.ratio/b) = b.ratio/a.ratio * a/b = ratio * b.ratio/a.ratio
                p1.ratio = ratio / a.ratio * b.ratio;
            }
        }

        private EquationNode find(EquationNode node) {
            if (node.parent != node) {
                EquationNode root = find(node.parent);
                // node.ratio = node/parent
                // parent.ratio = parent/root
                // node/root = node.ratio * parent * parent.ratio/parent = node.ratio * parent.ratio
                node.ratio = node.ratio * node.parent.ratio;
                node.parent = root;
            }
            return node.parent;
        }

        public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            Map<String, EquationNode> nodes = new HashMap<>();
            for (int i = 0; i < equations.size(); i++) {
                String a = equations.get(i).get(0);
                String b = equations.get(i).get(1);
                double ratio = values[i];
                // create equation variable & connect them
                nodes.putIfAbsent(a, new EquationNode(a));
                nodes.putIfAbsent(b, new EquationNode(b));
                union(nodes.get(a), nodes.get(b), ratio);
            }

            double[] calculated = new double[queries.size()];
            for (int i = 0; i < queries.size(); i++) {
                String a = queries.get(i).get(0);
                String b = queries.get(i).get(1);
                // check if a & b are valid variable
                EquationNode na = nodes.get(a);
                EquationNode nb = nodes.get(b);
                if (na == null || nb == null) {
                    calculated[i] = -1.0;
                    continue;
                }
                // check if a & b are connected
                EquationNode pa = find(na);
                EquationNode pb = find(nb);
                if (pa != pb) {
                    calculated[i] = -1.0;
                } else {
                    calculated[i] = na.ratio / nb.ratio;
                }
            }
            return calculated;
        }
    }
}
