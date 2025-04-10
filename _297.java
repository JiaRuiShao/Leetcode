import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import helper.TreeNode;
import helper.TreePrinter;

/**
 * 297. Serialize and Deserialize Binary Tree
 */
public class _297 {
    /**
     * Solution 1: DFS Pre-order Traversal.
     * When we deserialize, first find left subtree, and then find right one.
     */
    public class Solution1 {

        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            dfsSerialize(root, sb);
            return sb.toString();
        }

        private void dfsSerialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("#").append(",");
                return;
            }
            sb.append(root.val).append(",");
            dfsSerialize(root.left, sb);
            dfsSerialize(root.right, sb);
        }

        // For example 1 where root = [1,2,3,null,null,4,5], our serialized str is: [1,2,#,#,3,4,#,#,5,#,#]
        public TreeNode deserialize(String data) {
            List<String> nodes = new LinkedList<>();
            for (String node : data.split(",")) {
                nodes.add(node); // addLast
            }
            return dfsDeserialize(nodes);
        }

        private TreeNode dfsDeserialize(List<String> nodes) {
            if (nodes.isEmpty()) return null;
            String currVal = nodes.remove(0); // removeFirst
            if (currVal.equals("#")) return null;
            TreeNode root = new TreeNode(Integer.valueOf(currVal));
            root.left = dfsDeserialize(nodes);
            root.right = dfsDeserialize(nodes);
            return root;
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
        // for input [1,2,3,null,null,4,5], the String version would be [1,2,3,#,#,4,5,#,#,#,#]
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            String delimiter = ",", nullNode = "#";

            if (root == null) return sb.toString();

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int sz = queue.size();
                for (int i = 0; i < sz; i++) {
                    TreeNode curr = queue.poll();
                    if (curr == null) {
                        sb.append(nullNode).append(delimiter);
                        continue;
                    }
                    // qq: do we need to add null childs for null node? Here I didn't
                    sb.append(curr.val).append(delimiter);
                    queue.offer(curr.left);
                    queue.offer(curr.right);
                }
            }
            return sb.toString();
        }

        // each not null node have left & right childs saved in data
        public TreeNode deserialize(String data) {
            if (data.isEmpty()) return null;
            String delimiter = ",", nullNode = "#";
            String[] nodes = data.split(delimiter);
            int idx = 0;
            TreeNode curr;
            TreeNode root = new TreeNode(Integer.parseInt(nodes[idx++]));
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            String left, right;
            while (!q.isEmpty() && idx < nodes.length) {
                curr = q.poll();
                left = nodes[idx++];
                right = nodes[idx++];

                if (left.equals(nullNode)) {
                    curr.left = null;
                } else {
                    curr.left = new TreeNode(Integer.parseInt(left));
                    q.offer(curr.left);
                }

                if (right.equals(nullNode)) {
                    curr.right = null;
                } else {
                    curr.right = new TreeNode(Integer.parseInt(right));
                    q.offer(curr.right);
                }
            }

            return root;
        }
    }

    public static void main(String[] args) {
        // root = [1,2,3,null,null,4,5]
        //      1
        //     / \
        //    2   3
        //       / \
        //      4   5
        // Build root = [1,2,3,null,null,4,5]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        // Call the function you want to test
        // For example:
        _297 solution = new _297();
        TreePrinter.print(root);
        String serializedTree = solution.new Solution4().serialize(root);
        System.out.println(serializedTree);
        TreeNode deNode = solution.new Solution4().deserialize(serializedTree);
        TreePrinter.print(deNode);
    }
}
