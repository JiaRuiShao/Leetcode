import java.util.TreeSet;

/**
 * 480. Sliding Window Median
 */
public class _480 {
    // Why use TreeSet and not PriorityQueue here?
    // PQ remove takes O(n) time, TreeSet only takes O(logn) time
    // Time: O(nlogk)
    // Space: O(k)
    class Solution1_TreeSet {
        public double[] medianSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            double[] medians = new double[n - k + 1];
            TreeSet<Integer> lower = new TreeSet<>((a, b) -> nums[a] != nums[b] ? Integer.compare(nums[b], nums[a]) : Integer.compare(a, b));
            TreeSet<Integer> upper = new TreeSet<>((a, b) -> nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : Integer.compare(a, b));
            // lower.size == upper.size (+ 1)
            for (int i = 0; i < n; i++) {
                // add i
                upper.add(i);
                lower.add(upper.pollFirst());
                rebalance(lower, upper);

                // remove i - k
                if (i >= k) {
                    lower.remove(i - k);
                    upper.remove(i - k);
                    rebalance(lower, upper);
                }

                // get median
                if (i - k + 1 >= 0) {
                    double median = nums[lower.first()] * 1.0;
                    if (k % 2 == 0) {
                        median = (median + nums[upper.first()]) / 2.0;
                    }
                    medians[i - k + 1] = median;
                }
            }
            return medians;
        }

        private void rebalance(TreeSet<Integer> lower, TreeSet<Integer> upper) {
            if (lower.size() > upper.size() + 1) {
                upper.add(lower.pollFirst());
            } else if (lower.size() < upper.size()) {
                lower.add(upper.pollFirst());
            }
        }
    }
}
