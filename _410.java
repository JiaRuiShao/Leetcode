import java.util.Arrays;

/**
 * 410. Split Array Largest Sum
 * Redefine this problem to a similar problem like 1011. Capacity To Ship Packages Within D Days
 * Find minimized largest sum of the split given by k split --> find lower boundary of sub-array sum that associated with target k splits
 * The biggest challenge here is to think the largest sum as the capacity
 */
public class _410 {
    static class Solution1_Binary_search {
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

    class Solution2_Binary_Search_Prefix_Sum {
        public int splitArray(int[] nums, int k) {
            // f(x) = # subarrays given max sum x
            // lower bound binary search to find smallest x so that f(x) = k
            int maxNum = 0, n = nums.length;
            int[] preSum = new int[n + 1];
            preSum[0] = 0;
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                preSum[i + 1] = preSum[i] + num;
                maxNum = Math.max(maxNum, num);
            }
            
            int lo = maxNum, hi = preSum[n], mid = 0;
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                if (canSplit(preSum, k, mid)) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            return lo;
        }
        
        // Helper function: can we split into <= k parts where each part sum <= maxSum?
        private boolean canSplit(int[] preSum, int k, int maxSum) {
            int lastIdx = 0, parts = 1;
            for (int i = 0; i < preSum.length; i++) {
                if (preSum[i] - preSum[lastIdx] > maxSum) {
                    parts++;
                    lastIdx = i - 1;
                }
            }
            return parts <= k;
        }
    }
}
