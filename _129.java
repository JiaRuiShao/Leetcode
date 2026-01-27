import helper.TreeNode;

/**
 * 129. Sum Root to Leaf Numbers
 */
public class _129 {
    class Solution1 {
        int sum = 0;
        StringBuilder path = new StringBuilder();

        private void traverse(TreeNode root) {
            // base cases
            if (root == null) return;

            // select
            path.append(root.val);

            // if leaf nodes
            if (root.left == null && root.right == null) {
                sum += Integer.parseInt(path.toString());
                return;
            }

            // traverse to the left & right
            traverse(root.left);
            traverse(root.right);

            // undo the selection
            path.deleteCharAt(path.length() - 1);
        }

        public int sumNumbers(TreeNode root) {
            traverse(root);
            return sum;
        }
    }

    /**
     * Divide into sub-problems. (dp framework)
     */
    class Solution2 {

        private int dfs(TreeNode root, int sum) {
            // base cases
            if (root == null) return 0; // not a valid path b/c it's parent is not a leaf node

            int currSum = 10 * sum + root.val;
            if (root.left == null && root.right == null) return currSum;

            return dfs(root.left, currSum) + dfs(root.right, currSum);
        }

        public int sumNumbers(TreeNode root) {
            return dfs(root, 0);
        }
    }
}
