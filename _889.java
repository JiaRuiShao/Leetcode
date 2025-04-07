import java.util.HashMap;
import java.util.Map;

import helper.TreeNode;

/**
 * 889. Construct Binary Tree from Preorder and Postorder Traversal
 */
public class _889 {
    class Solution {
        public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
            int n = preorder.length;
            Map<Integer, Integer> nodeToIdx = new HashMap<>();
            for (int i = 0; i < n; i++) {
                nodeToIdx.put(postorder[i], i);
            }
            return build(preorder, 0, n - 1, nodeToIdx, 0, n - 1);
        }

        private TreeNode build(int[] pre, int preS, int preE, Map<Integer, Integer> post, int postS, int postE) {
            if (preS > preE || postS > postE) return null;
            int rootVal = pre[preS];
            TreeNode root = new TreeNode(rootVal);
            int leftLen = 0;
            if (preS + 1 < preE) { // ✅ use preE instead of pre.length, we could not have any right subtree
                int leftRoot = pre[preS + 1];
                leftLen = post.get(leftRoot) - postS + 1;
            }
            // pre: [preS + 1, preS + leftLen], [preS + leftLen + 1, preE]
            // post: [postS, postS + leftLen - 1], [postS + leftLen, postE -1]
            root.left = build(pre, preS + 1, preS + leftLen, post, postS, postS + leftLen - 1);
            root.right = build(pre, preS + leftLen + 1, preE, post, postS + leftLen, postE - 1);
            return root;
        }
    }
}
