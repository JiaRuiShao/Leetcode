/**
 * 303. Range Sum Query - Immutable.
 * Implement the NumArray class:
 * - NumArray(int[] nums) Initializes the object with the integer array nums
 * - int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive
 * (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 */
public class _303 {
    static class Solution1_PrefixSum {
        class NumArray {
            private int[] preSum;

            public NumArray(int[] nums) {
                // preSum[0] = 0, used to calculate the sum
                preSum = new int[nums.length + 1];
                for (int i = 0; i < nums.length; i++) {
                    preSum[i + 1] = preSum[i] + nums[i];
                }
            }

            public int sumRange(int left, int right) {
                return preSum[right + 1] - preSum[left];
            }
        }
    }

    static class Solution2_PrefixSum_Modified {
        public static class NumArray {
            int[] sums;

            public NumArray(int[] nums) {
                sums = new int[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    if (i == 0) {
                        sums[i] = nums[i];
                    } else {
                        sums[i] = sums[i - 1] + nums[i];
                    }
                }
            }

            public int sumRange(int i, int j) {
                if (i == 0) {
                    return sums[j];
                }
                return sums[j] - sums[i - 1];
            }
        }
    }
}
