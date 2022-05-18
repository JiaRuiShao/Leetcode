import java.util.LinkedList;
import java.util.Queue;

public class _116 {

    private static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    static class SolutionBFS {

        /**
         * BFS Level-Order Traversal.
         * Time: O(n)
         * Space: O(n)
         *
         * @param root tree root node
         * @return the root node
         */
        public Node connect(Node root) {
            if (root == null) return root;
            Node curr, prev;
            Queue<Node> q = new LinkedList<>();
            q.offer(root);

            while (!q.isEmpty()) {
                int sz = q.size();
                prev = null; //**IMPORTANT**

                for (int i = 0; i < sz; i++) {
                    curr = q.poll();

                    // connect to left adjacent nodes if there's any
                    if (prev != null) {
                        prev.next = curr;
                    }

                    // add the left & right child if there's any
                    if (curr.left != null) {
                        q.offer(curr.left);
                    }
                    if (curr.right != null) {
                        q.offer(curr.right);
                    }

                    // set this curr node as prev
                    prev = curr;
                }
            }

            return root;
        }
    }

    static class SolutionRecursion {
        Node connect(Node root) {
            if (root == null) return null;
            connectTwoNode(root.left, root.right);
            return root;
        }

        void connectTwoNode(Node node1, Node node2) {
            if (node1 == null || node2 == null) {
                return;
            }
            /**** pre-order ****/
            // connect the left & right child
            node1.next = node2;

            // recursively go to the left & right substree
            connectTwoNode(node1.left, node1.right);
            connectTwoNode(node2.left, node2.right);

            /**** post-order ****/
            // connect the nodes on the same level with different parent
            connectTwoNode(node1.right, node2.left);
        }
    }
}
