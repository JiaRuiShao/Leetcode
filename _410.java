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
            for (int i = 0; i < nums.length; ) {
                int num = nums[i];
                if (currSum + num <= maxSum) {
                    currSum += num;
                    i++;
                } else {
                    currSum = 0;
                    partition++;
                }
            }
            return partition;
        }
    }
}
