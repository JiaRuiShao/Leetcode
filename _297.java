import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class _297 {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * Solution 1: Pre-order Traversal.
     * When we deserialize, first find left subtree, and then find right one.
     */
    public class Solution1 {

        private void preorderSerialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("#").append(",");
                return;
            }
            sb.append(root.val).append(",");
            preorderSerialize(root.left, sb);
            preorderSerialize(root.right, sb);
        }

        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            preorderSerialize(root, sb);
            return sb.toString();
        }

        private TreeNode preorderDeserialize(LinkedList<String> data) {
            // base cases
            if (data.isEmpty()) return null;
            String nodeVal = data.removeFirst();
            if (nodeVal.equals("#")) return null;
            TreeNode root = new TreeNode(Integer.parseInt(nodeVal));
            root.left = preorderDeserialize(data);
            root.right = preorderDeserialize(data);

            return root;
        }

        public TreeNode deserialize(String data) {
            return preorderDeserialize(new LinkedList<String>(Arrays.asList(data.split(","))));
        }
    }

    /**
     * Solution 2: Pre-order Traversal.
     * When we deserialize, first find left subtree, and then find right one.
     */
    public class Solution2 {

        private String preorderSerialize(TreeNode root) {
            if (root == null) return "#";
            String left = preorderSerialize(root.left);
            String right = preorderSerialize(root.right);
            return root.val + "," + left + "," + right;
        }

        public String serialize(TreeNode root) {
            return preorderSerialize(root);
        }

        private TreeNode preorderDeserialize(Queue<String> data) {
            // base cases
            if (data.isEmpty()) return null;
            String nodeVal = data.poll();
            if (nodeVal.equals("#")) return null;
            TreeNode root = new TreeNode(Integer.parseInt(nodeVal));
            root.left = preorderDeserialize(data);
            root.right = preorderDeserialize(data);
            return root;
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            Queue<String> input = new LinkedList<>(Arrays.asList(data.split(",")));
            return preorderDeserialize(input);
        }
    }

    /**
     * Solution 2: Post-order Traversal.
     * When we deserialize, first find right subtree, and then find left one.
     */
    public class Solution3 {
        private void postorderSerialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("#").append(",");
                return;
            }
            postorderSerialize(root.left, sb);
            postorderSerialize(root.right, sb);
            sb.append(root.val).append(",");
        }

        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            postorderSerialize(root, sb);
            return sb.toString();
        }

        private TreeNode postorderDeserialize(LinkedList<String> data) {
            // base cases
            if (data.isEmpty()) return null;
            String nodeVal = data.removeLast();
            if (nodeVal.equals("#")) return null;
            TreeNode root = new TreeNode(Integer.parseInt(nodeVal));
            root.right = postorderDeserialize(data);
            root.left = postorderDeserialize(data);
            return root;
        }

        public TreeNode deserialize(String data) {
            return postorderDeserialize(new LinkedList<String>(Arrays.asList(data.split(","))));
        }
    }

    /**
     * Level-Order Traversal / BFS.
     */
    public class Solution4 {
        public String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder sb = new StringBuilder();
            Queue<TreeNode> q = new LinkedList<>();
            TreeNode curr;
            q.offer(root);

            while (!q.isEmpty()) {
                curr = q.poll();
                if (curr == null) {
                    sb.append("#").append(",");
                    continue;
                }
                sb.append(curr.val).append(",");
                q.offer(curr.left);
                q.offer(curr.right);
            }

            return sb.toString();
        }

        public TreeNode deserialize(String data) {
            if (data.equals("")) return null;
            String[] nodes = data.split(",");
            int idx = 0;
            TreeNode curr;
            TreeNode root = new TreeNode(Integer.parseInt(nodes[idx++]));
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            String left, right;
            while (idx < nodes.length - 1) {
                curr = q.poll();
                left = nodes[idx++];
                right = nodes[idx++];

                if (left.equals("#")) {
                    curr.left = null;
                } else {
                    curr.left = new TreeNode(Integer.parseInt(left));
                    q.offer(curr.left); // add the node to the q if not null
                }

                if (right.equals("#")) {
                    curr.right = null;
                } else {
                    curr.right = new TreeNode(Integer.parseInt(right));
                    q.offer(curr.right);
                }
            }

            return root;
        }
    }
}
