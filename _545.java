import java.util.ArrayList;
import java.util.List;

import helper.TreeNode;

/**
 * 545. Boundary of Binary Tree
 */
public class _545 {
    class Solution1_DFS {
        public List<Integer> boundaryOfBinaryTree(TreeNode root) {
            List<Integer> boundary = new ArrayList<>();
            boundary.add(root.val);
            getLeftNodes(root.left, boundary);
            getLeafNodes(root.left, boundary);
            getLeafNodes(root.right, boundary);
            getRightNodes(root.right, boundary);
            return boundary;
        }

        private void getLeftNodes(TreeNode root, List<Integer> lefts) {
            if (root == null || root.left == null && root.right == null) {
                return;
            }
            lefts.add(root.val);
            if (root.left != null) {
                getLeftNodes(root.left, lefts);
            } else {
                getLeftNodes(root.right, lefts);
            }
        }

        private void getRightNodes(TreeNode root, List<Integer> rights) {
            if (root == null || root.left == null && root.right == null) {
                return;
            }
            if (root.right != null) {
                getRightNodes(root.right, rights);
            } else {
                getRightNodes(root.left, rights);
            }
            rights.add(root.val);
        }

        private void getLeafNodes(TreeNode root, List<Integer> leaves) {
            if (root == null) {
                return;
            }
            if (root.left == null && root.right == null) {
                leaves.add(root.val);
                return;
            }
            getLeafNodes(root.left, leaves);
            getLeafNodes(root.right, leaves);
        }
    }
}
