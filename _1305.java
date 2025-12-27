import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import helper.TreeNode;

/**
 * 1305. All Elements in Two Binary Search Trees
 * 
 * This problem is a merge two list + iterator problem (LC 173)
 * We could use Morris Traversal to fetch next element with optimized constant space
 */
public class _1305 {
    class Solution1_Stack {
        public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
            List<Integer> asc = new ArrayList<>();
            BSTIterator iterator1 = new BSTIterator(root1);
            BSTIterator iterator2 = new BSTIterator(root2);

            Integer next1 = iterator1.next(), next2 = iterator2.next();
            while (next1 != null || next2 != null) {
                if (next2 == null || (next1 != null && next1 < next2)) {
                    asc.add(next1);
                    next1 = iterator1.next();
                } else {
                    asc.add(next2);
                    next2 = iterator2.next();
                }
            }
            return asc;
        }

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

            public Integer next() {
                if (stk.isEmpty()) return null;
                TreeNode curr = stk.pop();
                push(curr.right);
                return curr.val;
            }
        }
    }

    class Solution2_MorrisTraversal {
        public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
            List<Integer> asc = new ArrayList<>();
            BSTIterator iterator1 = new BSTIterator(root1);
            BSTIterator iterator2 = new BSTIterator(root2);

            Integer next1 = iterator1.next(), next2 = iterator2.next();
            while (next1 != null || next2 != null) {
                if (next2 == null || (next1 != null && next1 < next2)) {
                    asc.add(next1);
                    next1 = iterator1.next();
                } else {
                    asc.add(next2);
                    next2 = iterator2.next();
                }
            }
            return asc;
        }

        class BSTIterator {
            TreeNode curr;
            public BSTIterator(TreeNode root) {
                curr = root;
            }

            public Integer next() {
                if (curr == null) return null;
                int val = 0;
                while (curr != null) {
                    if (curr.left == null) {
                        val = curr.val;
                        curr = curr.right;
                        break;
                    } else {
                        TreeNode predecessor = curr.left;
                        while (predecessor.right != null && predecessor.right != curr) {
                            predecessor = predecessor.right;
                        }
                        if (predecessor.right == null) {
                            predecessor.right = curr;
                            curr = curr.left;
                        } else {
                            val = curr.val;
                            predecessor.right = null;
                            curr = curr.right;
                            break;
                        }
                    }
                }
                return val;
            }
        }
    }
}
