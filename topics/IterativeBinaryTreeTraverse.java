package topics;

import helper.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Binary Tree Iterative Traverse.
 */
public class IterativeBinaryTreeTraverse {

    class Solution1_Iterative_Stack {

        public List<Integer> preorderTraversal(TreeNode root) { // root, left, right
            List<Integer> res = new ArrayList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;

            while (!stack.isEmpty() || cur != null) {
                // go left until no left child
                while (cur != null) {
                    res.add(cur.val); // add the node to the result list the 1st time been seen
                    stack.push(cur);
                    cur = cur.left;
                }

                cur = stack.pop().right;
            }

            return res;
        }

        public List<Integer> inorderTraversal(TreeNode root) { // left, root, right
            List<Integer> res = new ArrayList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;

            while (!stack.isEmpty() || cur != null) {
                // go left until no left child
                while (cur != null) {
                    stack.push(cur); // 1st time traverse this node
                    cur = cur.left;
                }

                res.add(stack.peek().val); // 2nd time, add it
                cur = stack.pop().right;
            }

            return res;
        }

        public List<Integer> postorderTraversal(TreeNode root) { // left, root, right
            List<Integer> res = new ArrayList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = null, visited = null; // visited is to record the last traversed node

            pushLeftBranch(root, stack);
            while (!stack.isEmpty()) {
                cur = stack.peek();
                // traversed the left branch, go right
                if ((cur.left == null || cur.left == visited) && cur.right != visited) pushLeftBranch(cur.right, stack);
                // traversed the left & right branch
                if (cur.right == null || cur.right == visited) {
                    res.add(cur.val);
                    visited = stack.pop();
                }
            }

            return res;
        }

        /**
         * Add all the left child into the stack.
         * @param cur
         * @param stack
         */
        private void pushLeftBranch(TreeNode cur, Deque<TreeNode> stack) {
            // go left until no left child
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
        }
    }

}
