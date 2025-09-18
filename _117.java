import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 117. Populating Next Right Pointers in Each Node II
 */
public class _117 {
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

    class Solution1_BFS {
        public Node connect(Node root) {
            if (root == null) return null;
            Queue<Node> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                int size = q.size();
                Node prev = null;
                for (int i = 0; i < size; i++) {
                    Node curr = q.poll();
                    if (prev != null) prev.next = curr;
                    if (curr.left != null) q.offer(curr.left);
                    if (curr.right != null) q.offer(curr.right);
                    prev = curr;
                }
            }
            return root;
        }
    }

    // Construct next level's next pointers as a LinkedList while traverse current level
    // Space: O(1) 
    class Solution2_BFS_LinkedList {
        public Node connect(Node root) {
            Node currLevelHead = root;
            Node dummyHead = new Node(-1), nextLevelHead = dummyHead;
            while (currLevelHead != null) {
                if (currLevelHead.left != null) {
                    nextLevelHead.next = currLevelHead.left;
                    nextLevelHead = nextLevelHead.next;
                }
                if (currLevelHead.right != null) {
                    nextLevelHead.next = currLevelHead.right;
                    nextLevelHead = nextLevelHead.next;
                }
                currLevelHead = currLevelHead.next;
                if (currLevelHead == null) {
                    currLevelHead = dummyHead.next;
                    dummyHead = new Node(-1);
                    nextLevelHead = dummyHead;
                }
            }
            return root;
        }
    }
}
