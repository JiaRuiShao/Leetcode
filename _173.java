import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

import helper.TreeNode;

/**
 * 173. Binary Search Tree Iterator
 * 
 * - S1: in-order traversal and save elements in list, O(n), O(n)
 * - S2: use stack to traverse, push all the way down to the left; then process node; go to the right, O(n), O(h)
 * - S3: Morris Traversal, optimized space complexity
 * 
 * Followup:
 * - How would you implement a two-way iterator that has both next() and prev()? Use S1 to allocate an array
 * - Support both pre-order and in-order
 */
public class _173 {
    class Solution1_ConvertToArray {
        class BSTIterator {
            private List<Integer> nodes;
            private int index;
            
            public BSTIterator(TreeNode root) {
                nodes = new ArrayList<>();
                index = 0;
                inorder(root);
            }
            
            private void inorder(TreeNode node) {
                if (node == null) return;
                inorder(node.left);
                nodes.add(node.val);
                inorder(node.right);
            }
            
            public int next() {
                return nodes.get(index++);
            }
            
            public boolean hasNext() {
                return index < nodes.size();
            }
        }
    }

    class Solution2_Stack {
        class BSTIterator {
            Deque<TreeNode> stk;
    
            public BSTIterator(TreeNode root) {
                stk = new ArrayDeque<>();
                push(root);
            }
    
            private void push(TreeNode root) {
                while (root != null) {
                    stk.push(root);
                    root = root.left;
                }
            }
            
            public int next() {
                TreeNode curr = stk.pop();
                push(curr.right);
                return curr.val;
            }
            
            public boolean hasNext() {
                return !stk.isEmpty();
            }
        }
    }

    class Solution3_Morris {
        class BSTIterator {
            TreeNode curr;

            public BSTIterator(TreeNode root) {
                curr = root;
            }
            
            public int next() {
                while (curr != null) {
                    if (curr.left == null) {
                        int val = curr.val;
                        curr = curr.right;
                        return val;
                    }
                    TreeNode predecessor = curr.left;
                    while (predecessor.right != null && predecessor.right != curr) {
                        predecessor = predecessor.right;
                    }
                    if (predecessor.right == null) {
                        predecessor.right = curr;
                        curr = curr.left;
                    } else {
                        int val = curr.val;
                        predecessor.right = null;
                        curr = curr.right;
                        return val;
                    }
                }
                return -1; // never reach if curr != null
            }
            
            public boolean hasNext() {
                return curr != null;
            }
        }
    }

    class Followup_PreOrderSupport {
        class BSTIterator {
            private Stack<TreeNode> stack;
            private boolean inorder;
            
            public BSTIterator(TreeNode root, boolean inorder) {
                this.inorder = inorder;
                stack = new Stack<>();
                
                if (inorder) {
                    pushLeft(root);
                } else {
                    if (root != null) stack.push(root);  // Preorder: just push root
                }
            }
            
            public int next() {
                if (inorder) {
                    return inorderNext();
                } else {
                    return preorderNext();
                }
            }
            
            private int inorderNext() {
                TreeNode node = stack.pop();
                if (node.right != null) {
                    pushLeft(node.right);
                }
                return node.val;
            }
            
            private int preorderNext() {
                TreeNode node = stack.pop();
                
                // Preorder: push right first (LIFO), then left
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                
                return node.val;
            }

            private void pushLeft(TreeNode root) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
            }
        }
    }
}
