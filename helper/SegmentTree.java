package helper;

public class SegmentTree {
    enum Mode {
        SUM, MAX, MIN
    }

    static class SegmentNode {
        int start, end;
        int aggVal; // aggregated value (sum, max, or min)
        SegmentNode left, right;

        // Lazy propagation markers:
        int lazyAdd;     // pending addition (0 means none)
        int lazySet;     // pending set (assignment) value
        boolean pendingSet; // indicates a pending assignment

        public SegmentNode(int start, int end, int aggVal) {
            this.start = start;
            this.end = end;
            this.aggVal = aggVal;
            this.lazyAdd = 0;
            this.lazySet = 0;
            this.pendingSet = false;
        }
    }

    private final SegmentNode root;
    private final int defVal;
    private final Mode mode;

    public SegmentTree(int start, int end, int defVal, String type) {
        // Convert type string to enum
        switch (type) {
            case "sum":
                this.mode = Mode.SUM;
                break;
            case "max":
                this.mode = Mode.MAX;
                break;
            case "min":
                this.mode = Mode.MIN;
                break;
            default:
                throw new IllegalArgumentException("Invalid type, must be sum, max, or min");
        }
        this.defVal = defVal;
        int rootAggVal = calcRangeVal(start, end, defVal);
        this.root = new SegmentNode(start, end, rootAggVal);
    }

    // Calculate the aggregated value for the range [l, r] when every element is set to val.
    private int calcRangeVal(int l, int r, int val) {
        if (mode == Mode.SUM) {
            return (r - l + 1) * val;
        } else {
            return val;
        }
    }

    // Apply addition update to node's aggregated value.
    private int applyAddVal(SegmentNode node, int delta) {
        if (mode == Mode.SUM) {
            return node.aggVal + (node.end - node.start + 1) * delta;
        } else {
            return node.aggVal + delta;
        }
    }

    // Merge two children values based on the mode.
    private int mergeValues(int leftVal, int rightVal) {
        if (mode == Mode.SUM) {
            return leftVal + rightVal;
        } else if (mode == Mode.MAX) {
            return Math.max(leftVal, rightVal);
        } else { // Mode.MIN
            return Math.min(leftVal, rightVal);
        }
    }

    // Ensure the children of the current node are initialized.
    private void ensureChildren(SegmentNode node) {
        if (node.start == node.end) return;
        int mid = node.start + (node.end - node.start) / 2;
        if (node.left == null) {
            int leftAggVal = calcRangeVal(node.start, mid, defVal);
            node.left = new SegmentNode(node.start, mid, leftAggVal);
        }
        if (node.right == null) {
            int rightAggVal = calcRangeVal(mid + 1, node.end, defVal);
            node.right = new SegmentNode(mid + 1, node.end, rightAggVal);
        }
    }

    // Push lazy updates down to the children.
    private void pushDown(SegmentNode node) {
        ensureChildren(node);
        if (node.pendingSet) {
            applySet(node.left, node.lazySet);
            applySet(node.right, node.lazySet);
            node.pendingSet = false;
            node.lazySet = 0;
        }
        if (node.lazyAdd != 0) {
            applyAdd(node.left, node.lazyAdd);
            applyAdd(node.right, node.lazyAdd);
            node.lazyAdd = 0;
        }
    }

    // Apply a lazy set update to a child node.
    private void applySet(SegmentNode child, int val) {
        child.pendingSet = true;
        child.lazySet = val;
        child.lazyAdd = 0;
        child.aggVal = calcRangeVal(child.start, child.end, val);
    }

    // Apply a lazy add update to a child node.
    private void applyAdd(SegmentNode child, int delta) {
        if (child.pendingSet) {
            child.lazySet += delta;
            child.aggVal = calcRangeVal(child.start, child.end, child.lazySet);
        } else {
            child.lazyAdd += delta;
            child.aggVal = applyAddVal(child, delta);
        }
    }

    // Single point assignment: update index to val.
    public void update(int index, int val) {
        rangeUpdate(index, index, val);
    }

    // Range assignment: set all values in [qL, qR] to val.
    public void rangeUpdate(int qL, int qR, int val) {
        _rangeUpdate(root, qL, qR, val);
    }

    private void _rangeUpdate(SegmentNode node, int qL, int qR, int val) {
        if (node.end < qL || node.start > qR) {
            throw new IllegalArgumentException("Invalid update range");
        }
        if (qL <= node.start && node.end <= qR) {
            node.pendingSet = true;
            node.lazySet = val;
            node.lazyAdd = 0;
            node.aggVal = calcRangeVal(node.start, node.end, val);
            return;
        }
        pushDown(node);
        int mid = node.start + (node.end - node.start) / 2;
        if (qL <= mid) {
            _rangeUpdate(node.left, qL, Math.min(qR, mid), val);
        }
        if (qR > mid) {
            _rangeUpdate(node.right, Math.max(qL, mid + 1), qR, val);
        }
        node.aggVal = mergeValues(node.left.aggVal, node.right.aggVal);
    }

    // Single point addition: add delta to index.
    public void add(int index, int delta) {
        rangeAdd(index, index, delta);
    }

    // Range addition: add delta to every element in [qL, qR].
    public void rangeAdd(int qL, int qR, int delta) {
        _rangeAdd(root, qL, qR, delta);
    }

    private void _rangeAdd(SegmentNode node, int qL, int qR, int delta) {
        if (node.end < qL || node.start > qR) {
            throw new IllegalArgumentException("Invalid update range");
        }
        if (qL <= node.start && node.end <= qR) {
            if (node.pendingSet) {
                node.lazySet += delta;
                node.aggVal = calcRangeVal(node.start, node.end, node.lazySet);
            } else {
                node.lazyAdd += delta;
                node.aggVal = applyAddVal(node, delta);
            }
            return;
        }
        pushDown(node);
        int mid = node.start + (node.end - node.start) / 2;
        if (qL <= mid) {
            _rangeAdd(node.left, qL, qR, delta);
        }
        if (qR > mid) {
            _rangeAdd(node.right, qL, qR, delta);
        }
        node.aggVal = mergeValues(node.left.aggVal, node.right.aggVal);
    }

    // Query the aggregated value for the range [qL, qR].
    public int query(int qL, int qR) {
        return _query(root, qL, qR);
    }

    private int _query(SegmentNode node, int qL, int qR) {
        if (node.end < qL || node.start > qR) {
            throw new IllegalArgumentException("Invalid query range");
        }
        if (qL <= node.start && node.end <= qR) {
            return node.aggVal;
        }
        pushDown(node);
        int mid = node.start + (node.end - node.start) / 2;
        if (qR <= mid) {
            return _query(node.left, qL, qR);
        } else if (qL > mid) {
            return _query(node.right, qL, qR);
        } else {
            int leftRes = _query(node.left, qL, mid);
            int rightRes = _query(node.right, mid + 1, qR);
            return mergeValues(leftRes, rightRes);
        }
    }
}