import java.util.HashMap;
import java.util.Map;

import helper.TreeNode;

/**
 * 106. Construct Binary Tree from Inorder and Postorder Traversal
 */
public class _106 {
    class Solution {
        public TreeNode buildTree(int[] inorder, int[] postorder) {
            int n = inorder.length;
            Map<Integer, Integer> nodeToIndex = new HashMap<>();
            for (int i = 0; i < n; i++) {
                nodeToIndex.put(inorder[i], i);
            }
            return build(postorder, 0, n - 1, nodeToIndex, 0, n - 1);
        }

        private TreeNode build(int[] post, int ps, int pe, Map<Integer, Integer> in, int is, int ie) {
            if (ps > pe || is > ie) return null;
            int rootVal = post[pe];
            int inIdx = in.get(rootVal); // inorder: [is, inIdx - 1], [inIdx + 1, ie]
            int leftLen = inIdx - is;
            int postRight = ps + leftLen; // postorder: [ps, postRight - 1], [postRight, pe - 1]
            
            TreeNode root = new TreeNode(rootVal);
            root.left = build(post, ps, postRight - 1, in, is, inIdx - 1);
            root.right = build(post, postRight, pe - 1, in, inIdx + 1, ie);
            return root;
        }
    }
}
