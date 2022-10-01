import helper.TreeNode;

import java.util.*;

/**
 * 145. Binary Tree Postorder Traversal.
 */
public class _145 {
    class Solution1_Iterative_Stack {
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

        private void pushLeftBranch(TreeNode cur, Deque<TreeNode> stack) {
            // go left until no left child
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
        }
    }

    /**
     * Convert the post-order to reverse pre-order problem.
     */
    class Solution2_IterativeStackReverse {
        /**
         * Use a LinkedList and add values to the head, no reverse is needed.
         * the linked list contents get added like this:
         * root
         * right, root
         * left, right, root
         */
        public List<Integer> postorderTraversal1(TreeNode root) {
            List<Integer> list = new LinkedList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            if (root == null) stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode curr = stack.pop();
                list.add(0, curr.val);
                if (curr.left != null) {
                    stack.push(curr.left);
                }
                if (curr.right != null) {
                    stack.push(curr.right);
                }
            }
            return list;
        }

        /**
         * root
         * right root
         * left right root
         *
         * @param root
         * @return
         */
        public List<Integer> postorderTraversal2(TreeNode root) {
            List<Integer> res = new LinkedList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;

            while (!stack.isEmpty() || cur != null) {
                // go right until no right child
                while (cur != null) {
                    res.add(0, cur.val);
                    stack.push(cur); // 1st time traverse this node
                    cur = cur.right;
                }

                // go left
                cur = stack.pop().left;
            }

            return res;
        }

        public List<Integer> postorderTraversal3(TreeNode root) { // node, right, left
            List<Integer> res = new LinkedList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = null, visited = null;
            pushRightBranch(root, stack, res);
            while (!stack.isEmpty()) {
                cur = stack.peek();
                if ((cur.right == null || cur.right == visited) && cur.left != visited) pushRightBranch(root.left, stack, res);
                if (cur.left == null || cur.left == visited) {
                    visited = stack.pop(); // update the visited node
                }
            }
            return res;
        }

        void pushRightBranch(TreeNode cur, Deque<TreeNode> stk, List<Integer> res) {
            while (cur != null) {
                res.add(0, cur.val);
                stk.push(cur);
                cur = cur.right;
            }
        }
    }
}
