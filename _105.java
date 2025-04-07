import java.util.HashMap;
import java.util.Map;

import helper.TreeNode;

/**
 * 105. Construct Binary Tree from Preorder and Inorder Traversal
 */
public class _105 {
    class Solution {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            int n = inorder.length;
            Map<Integer, Integer> nodeToIdx = new HashMap<>();
            for (int i = 0; i < n; i++) {
                nodeToIdx.put(inorder[i], i);
            }
            return build(preorder, 0, n - 1, nodeToIdx, 0, n - 1);
        }

        private TreeNode build(int[] pre, int preLo, int preHi, Map<Integer, Integer> in, int inLo, int inHi) {
            if (preLo > preHi || inLo > inHi) return null;
            int val = pre[preLo];
            TreeNode root = new TreeNode(val);

            int inorderIdx = in.get(val);
            int leftLength = inorderIdx - inLo;
            int preLeft = preLo + 1;
            int preRight = preLeft + leftLength - 1;

            root.left = build(pre, preLeft, preRight, in, inLo, inorderIdx - 1);
            root.right = build(pre, preRight + 1, preHi, in, inorderIdx + 1, inHi);
            return root;
        }
    }
}
