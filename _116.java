import java.util.LinkedList;
import java.util.Queue;

/**
 * 116. Populating Next Right Pointers in Each Node
 * Solution 1: dfs traversal (constant space except for callstack)
 * Solution 2: bfs (linear extra space)
 */
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
    }

    class Solution1_DFS {
        public Node connect(Node root) {
            if (root == null) return root;
            dfs(root.left, root.right);
            return root;
        }
    
        private void dfs(Node n1, Node n2) {
            if (n1 == null || n2  == null) return;
            n1.next = n2;
            dfs(n1.left, n1.right);
            dfs(n1.right, n2.left);
            dfs(n2.left, n2.right);
        }
    }

    class Solution2_BFS {
        public Node connect(Node root) {
            if (root == null) return root;
    
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                int sz = queue.size();
                Node prev = null;
                for (int i = 0; i < sz; i++) {
                    Node curr = queue.poll();
                    if (prev != null) {
                        prev.next = curr;
                    }
                    if (curr.left != null) {
                        queue.offer(curr.left);
                    }
                    if (curr.right != null) {
                        queue.offer(curr.right);
                    }
                    prev = curr;
                }
            }
            return root;
        }
    }
}
