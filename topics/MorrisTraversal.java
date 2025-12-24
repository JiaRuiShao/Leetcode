package topics;

import helper.TreeNode;

/**
 * Morris Traversal is an advanced iterative binary tree traversal algo.
 * 
 * Use Cases:
 * - Parents are not needed
 * - Tree traversal problems with space optimization
 * - In-order or pre-order traversal (post-order would be too complicated)
 * - Tree is not immutable
 * 
 * Core Idea:
 * It uses threading to avoid recursion and stack; uses two traversal pass with constant space (once to create thread, once to remove it)
 * 
 * For each node:
 * 1. find in-order predecessor (rightmost in left subtree)
 * 2. create a thread: predecessor.right = curr
 * 3. return to curr node after processing left subtree
 * 4. remove thread when revisiting
 */
public class MorrisTraversal {
    class WalkThrough {
        /**
         * Tree:     4
                    / \
                  2    6
                / \
              1    3

            Execution trace:

            Step 1: current = 4
            - Has left child (2)
            - Find predecessor: go to left (2), then rightmost → 3
            - predecessor.right = null, so create thread: 3.right = 4
            - Go left: current = 2

            Step 2: current = 2  
            - Has left child (1)
            - Find predecessor: go to left (1), then rightmost → 1 (no right)
            - predecessor.right = null, so create thread: 1.right = 2
            - Go left: current = 1

            Step 3: current = 1
            - No left child
            - Visit 1, add to result → [1]
            - Go right: current = 1.right = 2 (via thread!)

            Step 4: current = 2
            - Has left child (1)
            - Find predecessor: 1
            - predecessor.right = 2 (thread exists!)
            - Remove thread: 1.right = null
            - Visit 2, add to result → [1, 2]
            - Go right: current = 3

            Step 5: current = 3
            - No left child  
            - Visit 3, add to result → [1, 2, 3]
            - Go right: current = 3.right = 4 (via thread!)

            Step 6: current = 4
            - Has left child (2)
            - Find predecessor: 3
            - predecessor.right = 4 (thread exists!)
            - Remove thread: 3.right = null
            - Visit 4, add to result → [1, 2, 3, 4]
            - Go right: current = 6

            Step 7: current = 6
            - No left child
            - Visit 6, add to result → [1, 2, 3, 4, 6]
            - Go right: current = null

            Done! Result: [1, 2, 3, 4, 6] ✓
         */
    }

    public void inorder(TreeNode root) {
        TreeNode curr = root, prev = null;
        while (curr != null) {
            if (curr.left == null) { // no left subtree, go right directly

                processNode(curr, prev);
                prev = curr;

                curr = curr.right;
            } else { // find predecessor
                TreeNode predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                // create a link if not exist; remove link if exist
                if (predecessor.right == null) {
                    // first time visit - link
                    predecessor.right = curr;
                    curr = curr.left; // go left
                } else { // predecessor.right == curr;
                    /** IN-ORDER POS **/
                    processNode(curr, prev);
                    prev = curr;
                    
                    // second time visit - unlink
                    predecessor.right = null;
                    curr = curr.right; // go right
                }
            }
        }
    }

    private void processNode(TreeNode current, TreeNode prev) {
        // Your custom logic here
        // Example: detect BST violations
        if (prev != null && prev.val > current.val) {
            // Found violation
        }
    }

    public void preorder(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                /** BOTH IN_ORDER & PRE-ORDER POS */
                processNode(curr);

                curr = curr.right;
            } else {
                TreeNode predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    /** PRE-ORDER POS */
                    processNode(curr);

                    predecessor.right = curr;
                    curr = curr.left;
                } else { // pred.right == null
                    predecessor.right = null;
                    curr = curr.right;
                }
            }
        }
    }

    private void processNode(TreeNode root) {
        // some logic here
    }
}
