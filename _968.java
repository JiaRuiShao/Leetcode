import helper.TreeNode;

public class _968 {
    static class Solution {
        static final int UNCOVERED = 0; // uncovered node
        static final int COVERED_W_CAMERA = 1; // a parent of a leaf node, with a camera
        static final int COVERED_WO_CAMERA = 2; // a parent of a leaf node covered with a camera, without a camera on the node
        int camera = 0;

        public int minCameraCover(TreeNode root) {
            // if root node is uncovered, add 1 to the camera num
            return (dfs(root) == UNCOVERED ? 1 : 0) + camera;
        }

        public int dfs(TreeNode root) {
            if (root == null) return COVERED_WO_CAMERA; // covered
            int left = dfs(root.left), right = dfs(root.right); // traverse to left & right subtree
            if (left == UNCOVERED || right == UNCOVERED) { // the node is the parent of >=1 uncovered node(s)
                camera++; // update the camera num
                return COVERED_W_CAMERA; // mark this node as camera node
            }
            // mark curr node as a covered or an uncovered node
            return left == COVERED_W_CAMERA || right == COVERED_W_CAMERA ? COVERED_WO_CAMERA : UNCOVERED;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(0);
        root.left.left = new TreeNode(0);
        root.left.left.left = new TreeNode(0);
        root.left.left.left.right = new TreeNode(0);
        System.out.println(new Solution().minCameraCover(root));
    }
}
