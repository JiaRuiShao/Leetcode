import helper.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 617. Merge Two Binary Trees
 */
public class _617 {
    class Solution1_DP_Subproblem {
        /**
         * Merge the left & right subtrees by merging the left and right of teh current node.
         * Time: O(n1 + n2)
         * Space: O(max(h1, h2))
         *
         * @param r1
         * @param r2
         * @return
         */
        public TreeNode mergeTrees(TreeNode r1, TreeNode r2) {
            if (r1 == null && r2 == null) return null;
            TreeNode root;
            if (r1 == null) {
                root = new TreeNode(r2.val);
                root.left = mergeTrees(null, r2.left);
                root.right = mergeTrees(null, r2.right);
            } else if (r2 == null) {
                root = new TreeNode(r1.val);
                root.left = mergeTrees(r1.left, null);
                root.right = mergeTrees(r1.right, null);
            } else {
                root = new TreeNode(r1.val + r2.val);
                root.left = mergeTrees(r1.left, r2.left);
                root.right = mergeTrees(r1.right, r2.right);
            }
            return root;
        }

        public TreeNode mergeTreesConcise1(TreeNode root1, TreeNode root2) {
            if (root1 == null) {
                return root2;
            }
            if (root2 == null) {
                return root1;
            }
            root1.val += root2.val;
            root1.left = mergeTrees(root1.left, root2.left);
            root1.right = mergeTrees(root1.right, root2.right);
            return root1;
        }

        public TreeNode mergeTreesConcise2(TreeNode t1, TreeNode t2) {
            if (t1 == null && t2 == null) return null;
            int t1Val = t1 == null ? 0 : t1.val;
            int t2Val = t2 == null ? 0 : t2.val;

            TreeNode merged = new TreeNode(t1Val + t2Val);
            merged.left = mergeTrees(t1 == null ? null : t1.left, t2 == null ? null : t2.left);
            merged.right = mergeTrees(t1 == null ? null : t1.right, t2 == null ? null : t2.right);

            return merged;
        }
    }

    class Solution2_Iterative_PostOrder {
        /**
         * Time: O(min(n1, n2))
         * Space: O(min(n1, n2))
         *
         * @param r1
         * @param r2
         * @return
         */
        public TreeNode mergeTrees(TreeNode r1, TreeNode r2) {
            if (r1 == null) return r2;
            Deque<TreeNode[]> stack = new ArrayDeque<>();
            stack.push(new TreeNode[]{r1, r2});
            TreeNode[] temp;
            while (!stack.isEmpty()) {
                temp = stack.pop();
                if (temp[0] == null || temp[1] == null) continue;
                temp[0].val += temp[1].val;
                if (temp[0].left == null) {
                    temp[0].left = temp[1].left;
                } else {
                    stack.push(new TreeNode[]{temp[0].left, temp[1].left});
                }
                if (temp[0].right == null) {
                    temp[0].right = temp[1].right;
                } else {
                    stack.push(new TreeNode[]{temp[0].right, temp[1].right});
                }
            }
            return r1;
        }
    }
}
