package helper;

public class SegmentTree {
    class SegmentNode {
        int start, end; // range this node covers: [start, end]
        int max;        // maximum value in this range
        int lazy;       // pending update to push down
        SegmentNode left, right; // child nodes

        public SegmentNode(int start, int end) {
            this.start = start;
            this.end = end;
            this.max = 0;
            this.lazy = 0;
            this.left = null;
            this.right = null;
        }
    }

    //  * When update a range [l, r]:
    //  *      if current node is fully covered in range [l, r], update max and lazy and directly return
    //  *      if partial overlap, push lazy down to childs and recompute max from children recursively
    private void update(SegmentNode node, int l, int r, int val) {
        if (node == null || r < node.start || node.end < l) return; // no overlap
        if (l <= node.start && node.end <= r) {                     // total overlap
            node.max += val;
            node.lazy += val;
            return;
        }

        pushDown(node); // ensure children see pending updates

        int mid = node.start + (node.end - node.start) / 2;
        if (node.left == null) node.left = new SegmentNode(node.start, mid);
        if (node.right == null) node.right = new SegmentNode(mid + 1, node.end);

        update(node.left, l, r, val);
        update(node.right, l, r, val);

        node.max = Math.max(node.left.max, node.right.max);
    }

    private void pushDown(SegmentNode node) {
        if (node.lazy == 0) return;

        int mid = node.start + (node.end - node.start) / 2;
        if (node.left == null) node.left = new SegmentNode(node.start, mid);
        if (node.right == null) node.right = new SegmentNode(mid + 1, node.end);

        node.left.max += node.lazy;
        node.left.lazy += node.lazy;

        node.right.max += node.lazy;
        node.right.lazy += node.lazy;

        node.lazy = 0; // clear after pushing
    }

}