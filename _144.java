import java.util.ArrayList;
import java.util.List;

import helper.TreeNode;

/**
 * 144. Binary Tree Preorder Traversal
 */
public class _144 {
    class Solution1_DFS {
        List<Integer> res;
        public List<Integer> preorderTraversal(TreeNode root) {
            res = new ArrayList<>();
            traverse(root);
            return res;
        }

        private void traverse(TreeNode node) {
            if (node == null) return;
            res.add(node.val);
            traverse(node.left);
            traverse(node.right);
        }
    }

    class Solution2_Subproblem {
        public List<Integer> preorderTraversal(TreeNode root) {
            if (root == null) return new ArrayList<>();
            List<Integer> res = new ArrayList<>();
            res.add(root.val);
            res.addAll(preorderTraversal(root.left));
            res.addAll(preorderTraversal(root.right));
            return res;
        }
    }
}
