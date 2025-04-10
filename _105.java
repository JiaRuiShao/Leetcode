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
            Map<Integer, Integer> inorderMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                inorderMap.put(inorder[i], i);
            }
            return dfsBuild(preorder, 0, n - 1, inorderMap, 0, n - 1);
        }
    
        private TreeNode dfsBuild(int[] preorder, int preL, int preR, Map<Integer, Integer> inorder, int inL, int inR) {
            if (preL > preR || inL > inR) {
                return null;
            }
            int rootVal = preorder[preL];
            TreeNode root = new TreeNode(rootVal);
            int leftLen = inorder.get(rootVal) - inL;
            root.left = dfsBuild(preorder, preL + 1, preL + 1 + leftLen - 1, inorder, inL, inL + leftLen - 1);
            root.right = dfsBuild(preorder, preL + 1 + leftLen - 1 + 1, preR, inorder, inL + leftLen + 1, inR);
            return root;
        }
    }
}
