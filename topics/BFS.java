package topics;

import helper.Edge;
import helper.Graph;
import helper.Node;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    void bfsForTree(Node root) {
        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.println(curr.val);
            for (Node children : curr.children) {
                queue.offer(children);
            }
        }
    }

    void bfsForTreeWithLevelCount(Node root) {
        int level = 0;
        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node curr = queue.poll();
                System.out.println("level = " + level + ", val = " + curr.val);
                for (Node children : curr.children) {
                    queue.offer(children);
                }
            }
        }
    }

    void bfsForTreeWithNodeState(Node root) {
        Queue<helper.State> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(new helper.State(root, 1));
        }

        while (!queue.isEmpty()) {
            helper.State curr = queue.poll();
            System.out.println("level = " + curr.level + ", val = " + curr.node.val);
            for (Node children : curr.node.children) {
                queue.offer(new helper.State(children, curr.level + 1));
            }
        }
    }

    void bfsForGraph(Graph graph, int s) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        visited[s] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();
            System.out.println("visit " + cur);
            for (Edge e : graph.neighbors(cur)) {
                if (!visited[e.to]) {
                    q.offer(e.to);
                    visited[e.to] = true;
                }
            }
        }
    }

    void bfsForGraphWithStep(Graph graph, int s) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        visited[s] = true;
        int step = 0; // steps from vertex s

        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                int cur = q.poll();
                System.out.println("visit " + cur + " at step " + step);
                for (Edge e : graph.neighbors(cur)) {
                    if (!visited[e.to]) {
                        q.offer(e.to);
                        visited[e.to] = true;
                    }
                }
            }
            step++;
        }
    }

    private static class State {
        // curr node index
        int node;
        // weight sum from vertex s to curr vertex
        int weight;

        public State(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }


    void bfsForGraphWithWeight(Graph graph, int s) {
        boolean[] visited = new boolean[graph.size()];
        Queue<State> q = new LinkedList<>();
        q.offer(new State(s, 0));
        visited[s] = true;

        while (!q.isEmpty()) {
            State state = q.poll();
            int cur = state.node;
            int weight = state.weight;
            System.out.println("visit " + cur + " with path weight " + weight);
            for (Edge e : graph.neighbors(cur)) {
                if (!visited[e.to]) {
                    q.offer(new State(e.to, weight + e.weight));
                    visited[e.to] = true;
                }
            }
        }
    }
    
}
