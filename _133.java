import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import helper.Node;

/**
 * 133. Clone Graph
 */
public class _133 {
    class Solution1_BFS {
        public Node cloneGraph(Node node) {
            if (node == null) return node;
            Map<Node, Node> nodeToClone = new HashMap<>();
            Node root = new Node(node.val);
            nodeToClone.put(node, root);
            Queue<Node> q = new ArrayDeque<>();
            q.offer(node);
            while (!q.isEmpty()) {
                Node curr = q.poll();
                Node clone = nodeToClone.get(curr);
                List<Node> neighbors = new ArrayList<>();
                for (Node nbr : curr.neighbors) {
                    if (!nodeToClone.containsKey(nbr)) {
                        nodeToClone.put(nbr, new Node(nbr.val));
                        q.offer(nbr);
                    }
                    neighbors.add(nodeToClone.get(nbr));
                }
                clone.neighbors = neighbors;
            }
            return root;
        }
    }

    class Solution2_DFS {
        public Node cloneGraph(Node node) {
            Map<Node, Node> nodeToClone = new HashMap<>();
            traverse(node, nodeToClone);
            return nodeToClone.getOrDefault(node, null);
        }

        private void traverse(Node root, Map<Node, Node> nodeToClone) {
            if (root == null || nodeToClone.containsKey(root)) return;
            Node clone = new Node(root.val);
            nodeToClone.put(root, clone);
            List<Node> neighbors = new ArrayList<>();
            for (Node nbr : root.neighbors) {
                traverse(nbr, nodeToClone);
                neighbors.add(nodeToClone.get(nbr));
            }
            clone.neighbors = neighbors;
        }
    }
}
