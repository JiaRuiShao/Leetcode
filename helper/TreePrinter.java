package helper;

public class TreePrinter {

    public static void print(TreeNode root) {
        print(root, "", true);
    }

    private static void print(TreeNode node, String prefix, boolean isTail) {
        if (node == null) return;

        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.val);

        boolean hasLeft = node.left != null;
        boolean hasRight = node.right != null;

        if (hasLeft || hasRight) {
            if (node.left != null)
                print(node.left, prefix + (isTail ? "    " : "│   "), !hasRight);
            if (node.right != null)
                print(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}