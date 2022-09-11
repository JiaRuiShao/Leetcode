import java.util.*;

public class Test {

    private static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        /*int[] a = { 1, 2, 3, 4, 5 };
        int[] b = { 1, 2, 3, 4, 5 };

        if (a == b) {
            System.out.println("a == b is true");
        } else {
            System.out.println("a == b is false");
        }
        
        if (a.equals(b)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }*/

        /*System.out.println((int)'Z');
        System.out.println((int)'z');
        System.out.println((int)'a');
        System.out.println((int)'A');*/

        List<String> a = List.of("a", "b", "c");
        a.forEach(System.out::print);
        System.out.println(a);

        int[] arr = new int[2];
        arr[0] = 1;
        arr[1] = 2;
        System.out.println(Arrays.toString(arr));
    }

    void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        // pre-order position
        traverse(root.left);
        // in-order position
        traverse(root.right);
        // post-order position
    }

    private Stack<TreeNode> stk = new Stack<>();

    // add all left childs in the stack
    private void pushLeftBranch(TreeNode p) {
        while (p != null) {
            /** pre-order traversal position**/
            stk.push(p);
            p = p.left;
        }
    }

    public void traverseItr(TreeNode root) {
        // records the root node that has been traversed the last time
        TreeNode visited = new TreeNode(-1);
        // start traversal
        pushLeftBranch(root);

        while (!stk.isEmpty()) {
            TreeNode p = stk.peek();
            // if left subtree of p has been traversed & right subtree hasn't been traversed
            if ((p.left == null || p.left == visited) && p.right != visited) {
                /** in-order traversal position**/
                // traverse the right subtree of node p
                pushLeftBranch(p.right);
            }
            // if right subtree of p has been traversed
            if (p.right == null || p.right == visited) {
                /** post-order traversal position**/
                // update visited pointer
                visited = stk.pop();
            }
        }
    }
}
