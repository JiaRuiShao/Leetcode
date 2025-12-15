import java.util.TreeMap;

/**
 * 732. My Calendar III
 */
public class _732 {
    // Time: O(nlogn) where n is total number of bookings
    class Solution1_DifferenceMap {
        class MyCalendarThree {
            private TreeMap<Integer, Integer> diffTreeMap;
            private int maxBooking;

            public MyCalendarThree() {
                diffTreeMap = new TreeMap<>();
                maxBooking = 0;
            }
            
            public int book(int startTime, int endTime) {
                diffTreeMap.put(startTime, diffTreeMap.getOrDefault(startTime, 0) + 1);
                diffTreeMap.put(endTime, diffTreeMap.getOrDefault(endTime, 0) - 1);
                int currBooking = 0;
                for (int booking : diffTreeMap.values()) {
                    currBooking += booking;
                    if (currBooking > maxBooking) {
                        maxBooking = currBooking;
                    }
                }
                return maxBooking;
            }
        }
    }

    // Time: O(logn) where n is indexed domain size, n = 10^9 here
    class Solution2_SegmentTree {
        class MyCalendarThree {
            private static class Node {
                int max, lazy;
                Node left, right;
            }

            private final Node root = new Node();
            private static final int MIN = 0, MAX = 1_000_000_000;

            public int book(int start, int end) {
                update(root, MIN, MAX, start, end - 1, 1);
                return root.max;
            }

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
