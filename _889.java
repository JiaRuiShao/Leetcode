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
            Map<Integer, Integer> postMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                postMap.put(postorder[i], i);
            }
            return dfsBuild(preorder, 0, n - 1, postMap, 0, n - 1);
        }
    
        private TreeNode dfsBuild(int[] pre, int preL, int preR, Map<Integer, Integer> post, int postL, int postR) {
            if (preL > preR || postL > postR) {
                return null;
            }
            int rootVal = pre[preL];
            TreeNode root = new TreeNode(rootVal);
            int leftLen = 0;
            if (preL + 1 <= preR) { // suppose we have a left subtree
                int leftRootVal = pre[preL + 1];
                leftLen = post.get(leftRootVal) - postL + 1;
            }
            root.left = dfsBuild(pre, preL + 1, preL + 1 + leftLen - 1, post, postL, postL + leftLen - 1);
            root.right = dfsBuild(pre, preL + 1 + leftLen - 1 + 1, preR, post, postL + leftLen - 1 + 1, postR - 1);
            return root;
        }
    }
}
