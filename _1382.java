import java.util.ArrayList;
import java.util.List;

import helper.TreeNode;

/**
 * 1382. Balance a Binary Search Tree
 * 
 * In-Order Traversal + LC 108
 */
public class _1382 {
    // Time: O(n)
    // Space: O(n)
    class Solution1 {
        public TreeNode balanceBST(TreeNode root) {
            List<Integer> sorted = new ArrayList<>();
            inorder(root, sorted);
            return buildBalanced(sorted, 0, sorted.size() - 1);
        }

        private void inorder(TreeNode node, List<Integer> list) {
            if (node == null) return;
            inorder(node.left, list);
            list.add(node.val);
            inorder(node.right, list);
        }

        private TreeNode buildBalanced(List<Integer> list, int left, int right) {
            if (left > right) return null;
            int mid = left + (right - left) / 2;
            TreeNode node = new TreeNode(list.get(mid));
            node.left = buildBalanced(list, left, mid - 1);
            node.right = buildBalanced(list, mid + 1, right);
            return node;
        }
    }
}
