import java.util.Stack;

import helper.TreeNode;

/**
 * 1008. Construct Binary Search Tree from Preorder Traversal
 */
public class _1008 {
    // Time: O(n^2)
    // Space: O(h) = O(n)
    class Solution0_BF_Recursion {
        public TreeNode bstFromPreorder(int[] preorder) {
            return buildBST(preorder, 0, preorder.length - 1);
        }

        private TreeNode buildBST(int[] pre, int lo, int hi) {
            if (lo > hi || lo >= pre.length) return null;
            int rootVal = pre[lo];
            TreeNode root = new TreeNode(rootVal);
            int idx = lo + 1;
            while (idx <= hi && pre[idx] < rootVal) idx++; // this line could take O(n) time for each call stack
            root.left = buildBST(pre, lo + 1, idx - 1);
            root.right = buildBST(pre, idx, hi);
            return root;
        }
    }

    // Time: O(nlogn)
    // Space: O(h) = O(n)
    class Solution1_Recursion_Map {
        public TreeNode bstFromPreorder(int[] preorder) {
            return buildBST(preorder, 0, preorder.length - 1);
        }

        private TreeNode buildBST(int[] pre, int lo, int hi) {
            if (lo > hi || lo >= pre.length) return null;
            int rootVal = pre[lo++];
            TreeNode root = new TreeNode(rootVal);
            int idx = binarySearch(pre, lo, hi, rootVal);
            root.left = buildBST(pre, lo, idx - 1);
            root.right = buildBST(pre, idx, hi);
            return root;
        }

        private int binarySearch(int[] arr, int lo, int hi, int k) {
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (arr[mid] <= k) lo = mid + 1;
                else hi = mid - 1;
            }
            return lo;
        }
    }

    // Use range val as the parameter bound instead of preorder index
    // in-order traversal to build BST, take advantage of the BST property: left < root < right
    // Time: O(n)
    // Space: O(h) = O(n)
    class Solution2_Recursion_InOrder {
        private int pos;
        public TreeNode bstFromPreorder(int[] preorder) {
            pos = 0;
            return buildBST(preorder, 0, 1001);
        }

        private TreeNode buildBST(int[] pre, int min, int max) {
            if (pos >= pre.length || pre[pos] < min || pre[pos] > max) return null;
            int val = pre[pos++];
            TreeNode root = new TreeNode(val);
            root.left = buildBST(pre, min, val);
            root.right = buildBST(pre, val, max);
            return root;
        }
    }

    // Time: O(n) - each node pushed and popped once
    // Space: O(n) - stack

    // Idea: Maintain stack of potential parents
    // - If current > stack top: current is right child
    // - If current < stack top: current is left child
    class Solution2_Iterative {
        public TreeNode bstFromPreorder(int[] preorder) {
            if (preorder == null || preorder.length == 0) return null;
            
            TreeNode root = new TreeNode(preorder[0]);
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            
            for (int i = 1; i < preorder.length; i++) {
                TreeNode node = new TreeNode(preorder[i]);
                TreeNode parent = null;
                
                // Find parent: pop all nodes smaller than current
                while (!stack.isEmpty() && stack.peek().val < node.val) {
                    parent = stack.pop();
                }
                
                if (parent != null) {
                    // Current is right child of parent
                    parent.right = node;
                } else {
                    // Current is left child of stack top
                    stack.peek().left = node;
                }
                
                stack.push(node);
            }
            
            return root;
        }
    }
}
