import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import helper.TreeNode;

/**
 * 99. Recover Binary Search Tree
 * 
 * - S1: In-Order Traversal Recursive O(n), O(n)
 * - S2: In-Order Traversal Iterative with Stack O(n), O(n)
 * - S3: Morris Traversal (In-Order Traversal Iterative Advanced) O(n), O(1)
 * 
 * Follow-up:
 * - How you detect two nodes are swapped? In-order traversal is expected to get asc elem; there could be one violation OR two violations; if two violation, swap the 1st elem of first violation & 2nd elem of second violation
 * - Nodes are immutable, swap pointers instead? This would become much harder since we need to maintain parent nodes as well
 */
public class _99 {

    // We can extract all values, sort them and put them back
    // however this takes O(nlogn) time and O(n) space
    // This solution is a overkill for two swaps; used when swap num is unknown
    class Solution0_BF_SortManually {
    }

    // Traverse in order and get a list of values
    class Solution1_RecursionInOrderTraversal {
        public void recoverTree(TreeNode root) {
            List<TreeNode> nodes = new ArrayList<>();
            inorder(root, nodes);
            
            // Find two swapped nodes
            TreeNode first = null, second = null;
            
            // If two violations found, we swap the larger elem from the first violation
            //  & the smaller elem from the second violation
            for (int i = 0; i < nodes.size() - 1; i++) {
                if (nodes.get(i).val > nodes.get(i + 1).val) {
                    if (first == null) {
                        first = nodes.get(i);
                        second = nodes.get(i + 1);
                    } else {
                        second = nodes.get(i + 1);
                    }
                }
            }
            
            // Swap values
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }

        private void inorder(TreeNode root, List<TreeNode> nodes) {
            if (root == null) return;
            inorder(root.left, nodes);
            nodes.add(root);
            inorder(root.right, nodes);
        }
    }

    // Traverse in order and only get two violations of BST property: left < root < right
    // Time: O(n)
    // Space: O(h) = O(n)
    class Solution2_RecursionInOrderTraversal {
        TreeNode n1 = null, n2 = null, prev = null;

        private void traverse(TreeNode root) {
            if (root == null) return;
            traverse(root.left);
            // in-order position, check whether current node val > the prev val
            if (prev != null && root.val <= prev.val) {
                if (n1 == null) n1 = prev; /** IMPORTANT **/
                n2 = root;
            }
            prev = root;
            traverse(root.right);
        }

        private void swap() {
            int tmp = n1.val;
            n1.val = n2.val;
            n2.val = tmp;
        }

        public void recoverTree(TreeNode root) {
            traverse(root); // in-order traversal to find the two nodes that need to be swapped
            swap();
        }
    }

    class Solution3_IterativeInOrderTraversal {
        public void recoverTree(TreeNode root) {
            Deque<TreeNode> stk = new ArrayDeque<>();
            TreeNode prev = null, curr = root;
            TreeNode first = null, second = null;
            
            while (!stk.isEmpty() || curr != null) {
                while (curr != null) {
                    stk.push(curr);
                    curr = curr.left;
                }
                curr = stk.pop();
                // in-order
                if (prev != null && prev.val > curr.val) {
                    if (first == null) first = prev;
                    second = curr;
                }
                prev = curr;

                curr = curr.right;
            }

            // swap
            int tmp = first.val;
            first.val = second.val;
            second.val = tmp;
        }
    }

    // Time: O(n)
    // Space: O(1) - only a few pointers
    class Followup_Morris {
        public void recoverTree(TreeNode root) {
            TreeNode first = null, second = null;
            TreeNode prev = null, current = root;
            
            while (current != null) {
                if (current.left == null) {
                    // Visit current node
                    if (prev != null && prev.val > current.val) {
                        if (first == null) {
                            first = prev;
                            second = current;
                        } else {
                            second = current;
                        }
                    }
                    prev = current;
                    current = current.right;
                } else {
                    // Find predecessor
                    TreeNode predecessor = current.left;
                    while (predecessor.right != null && predecessor.right != current) {
                        predecessor = predecessor.right;
                    }
                    
                    if (predecessor.right == null) {
                        // Create thread
                        predecessor.right = current;
                        current = current.left;
                    } else {
                        // Visit current node
                        if (prev != null && prev.val > current.val) {
                            if (first == null) {
                                first = prev;
                                second = current;
                            } else {
                                second = current;
                            }
                        }
                        
                        // Remove thread
                        predecessor.right = null;
                        prev = current;
                        current = current.right;
                    }
                }
            }
            
            // Swap values
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }
    }
}
