import helper.TreeNode;

/**
 * 449. Serialize and Deserialize BST
 * 
 * This is BST version of LC 297 -- we must encode nulls for regular binary trees
 * but for this problem we can leverage BST property to avoid encoding nulls
 * 
 * - S1: Normal tree serialization & deserialization
 * - S2: BST preorder/postorder without nulls recursively
 * - S3: level-order traversal iteratively
 * 
 * Clarification:
 * - Range of node values?
 * 
 * Followup:
 * - Why don't you need null markers for BST but need them for regular binary tree?
 * Only one valid BST for given preorder or postorder vals
 */
public class _449 {
    public class Solution2_PreOrder {
        private String comma = ",";

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            preorder(root, sb);
            // if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        private void preorder(TreeNode root, StringBuilder sb) {
            if (root == null) {
                // sb.append("#") // no need to append null pointers for BST
                return;
            }
            sb.append(root.val).append(comma);
            preorder(root.left, sb);
            preorder(root.right, sb);
        }

        private int pos;

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data.isEmpty()) return null;
            pos = 0;
            String[] preorder = data.split(comma);
            return buildBST(preorder, 0, 10_000); // [0, 10000]
        }

        private TreeNode buildBST(String[] preorder, int lo, int hi) {
            if (pos >= preorder.length) return null;
            int val = Integer.valueOf(preorder[pos]);
            if (val < lo || val > hi) return null;
            pos++;
            TreeNode root = new TreeNode(val);
            root.left = buildBST(preorder, lo, val - 1);
            root.right = buildBST(preorder, val + 1, hi);
            return root;
        }
    }
}
