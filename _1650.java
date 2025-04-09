import java.util.HashSet;
import java.util.Set;

/**
 * 1650. Lowest Common Ancestor of a Binary Tree III
 */
public class _1650 {
    public class Solution1_HashSet {
        public Node lowestCommonAncestor(Node p, Node q) {
            Set<Node> set = new HashSet<>();
            while (p != null) {
                set.add(p);
                p = p.parent;
            }
            while (q != null) {
                if (set.contains(q)) {
                    return q;
                }
                q = q.parent;
            }
            return null;
        }
    }

    public class Solution2_Two_Pointers {
        public Node lowestCommonAncestor(Node p, Node q) {
            Node a = p, b = q;
            while (a != b) {
                a = a == null ? q : a.parent;
                b = b == null ? p : b.parent;
            }
            return a;
        }
    }

    class Node {
        public int val;
        public Node parent;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }
    }
}
