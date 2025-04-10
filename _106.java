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
            Map<Integer, Integer> inorderMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                inorderMap.put(inorder[i], i);
            }
            return dfsBuild(inorderMap, 0, n - 1, postorder, 0, n - 1);
        }
    
        private TreeNode dfsBuild(Map<Integer, Integer> inorder, int inL, int inR, int[] post, int postL, int postR) {
            if (inL > inR || postL > postR) {
                return null;
            }
            int rootVal = post[postR];
            TreeNode root = new TreeNode(rootVal);
            int leftLen = inorder.get(rootVal) - inL;
            root.left = dfsBuild(inorder, inL, inL + leftLen - 1, post, postL, postL + leftLen - 1);
            root.right = dfsBuild(inorder, inL + leftLen + 1, inR, post, postL + leftLen, postR - 1);
            return root;
        }
    }
}
