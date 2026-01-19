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
            return clone(node, nodeToClone);
        }

        private Node clone(Node root, Map<Node, Node> map) {
            if (root == null) {
                return null;
            }
            // pre-order to create node
            Node clone = new Node(root.val);
            map.put(root, clone);

            for (Node nbr : root.neighbors) {
                if (!map.containsKey(nbr)) {
                    clone(nbr, map);
                }
                clone.neighbors.add(map.get(nbr));
            }
            return clone;
        }
    }
}
