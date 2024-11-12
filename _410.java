import java.util.Arrays;

/**
 * 410. Split Array Largest Sum.
 * Redefine this problem to a similar problem like 1011. Capacity To Ship Packages Within D Days
 * Find minimized largest sum of the split given by k split --> find lower boundary of sub-array sum that associated with target k splits
 * The biggest challenge here is to think the largest sum as the capacity
 */
public class _410 {
    static class Solution {
        public int splitArray(int[] nums, int k) {
            return findMLS(nums, k);
        }

        /**
         * f(x) = subarrays # given largest sum x, use binary search to find min x so that f(x) = k.
         * @param nums nums
         * @param k k partition
         * @return MLS of nums
         */
        private int findMLS(int[] nums, int k) {
            int lo = Arrays.stream(nums).max().getAsInt(), hi = Arrays.stream(nums).sum();
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int partitions = findPartition(nums, mid);
                if (partitions <= k) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            return lo;
        }

        private int findPartition(int[] nums, int maxSum) {
            int currSum = 0, partition = 1;
            for (int num : nums) {
                if (currSum > maxSum - num) {
                    currSum = 0;
                    partition++;
                }
                currSum += num;
            }
            return partition;
        }
    }
}
