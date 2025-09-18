import java.util.ArrayDeque;
import java.util.Queue;

import helper.TreeNode;

/**
 * 919. Complete Binary Tree Inserter
 */
public class _919 {
    class CBTInserter {
        private Queue<TreeNode> nodesWithNullChild;
        private TreeNode root;

        public CBTInserter(TreeNode root) {
            this.root = root;
            nodesWithNullChild = new ArrayDeque<>();

            Queue<TreeNode> nodes = new ArrayDeque<>();
            nodes.offer(root);
            while (!nodes.isEmpty()) {
                int size = nodes.size();
                for (int i = 0; i < size; i++) {
                    TreeNode curr = nodes.poll();
                    if (curr.left != null) nodes.offer(curr.left);
                    if (curr.right != null) nodes.offer(curr.right);
                    if (curr.left == null || curr.right == null) nodesWithNullChild.offer(curr);
                }
            }
        }
        
        public int insert(int val) {
            TreeNode nextParent = nodesWithNullChild.peek();
            TreeNode curr = new TreeNode(val);
            if (nextParent.left == null) {
                nextParent.left = curr;
            } else {
                nextParent.right = curr;
                nodesWithNullChild.poll();
            }
            nodesWithNullChild.offer(curr);
            return nextParent.val;
        }
        
        public TreeNode get_root() {
            return root;
        }
    }
}
