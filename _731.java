import java.util.TreeMap;

/**
 * 731. My Calendar II
 */
public class _731 {
    // Time: O(nlogn) where n is total number of valid bookings
    class Solution1_DifferenceMap {
        class MyCalendarTwo {
            TreeMap<Integer, Integer> diffTreeMap;

            public MyCalendarTwo() {
                diffTreeMap = new TreeMap<>();
            }
            
            public boolean book(int startTime, int endTime) {
                diffTreeMap.put(startTime, diffTreeMap.getOrDefault(startTime, 0) + 1);
                diffTreeMap.put(endTime, diffTreeMap.getOrDefault(endTime, 0) - 1);

                int overlapped = 0, maxOverlapped = 2;
                for (int booking : diffTreeMap.values()) {
                    overlapped += booking;
                    if (overlapped > maxOverlapped) { // rollback
                        diffTreeMap.put(startTime, diffTreeMap.getOrDefault(startTime, 0) - 1);
                        diffTreeMap.put(endTime, diffTreeMap.getOrDefault(endTime, 0) + 1);
                        if (diffTreeMap.get(startTime) == 0) diffTreeMap.remove(startTime);
                        if (diffTreeMap.get(endTime) == 0) diffTreeMap.remove(endTime);
                        return false;
                    }
                }
                return true;
            }
        }
    }

    // Time: O(logn) where n is indexed domain size, n = 10^9 here
    class Solution2_SegmentTree {
        class MyCalendarTwo {
            private static class Node {
                int max, lazy;
                Node left, right;
            }

            private final Node root = new Node();
            private static final int MIN = 0, MAX = 1_000_000_000; // or (int) 1e9

            public boolean book(int start, int end) {
                update(root, MIN, MAX, start, end - 1, 1);
                if (root.max > 2) {
                    update(root, MIN, MAX, start, end - 1, -1); // rollback
                    return false;
                }
                return true;
            }

            // range l & r can be passed as parameters here or could be define as fields in Node
            private void update(Node node, int l, int r, int ql, int qr, int val) {
                if (qr < l || r < ql) return;
                if (ql <= l && r <= qr) {
                    node.max += val;
                    node.lazy += val;
                    return;
                }

                int mid = l + (r - l) / 2;
                if (node.left == null) node.left = new Node();
                if (node.right == null) node.right = new Node();

                update(node.left, l, mid, ql, qr, val);
                update(node.right, mid + 1, r, ql, qr, val);

                node.max = node.lazy + Math.max(node.left.max, node.right.max);
            }
        }
    }
}
