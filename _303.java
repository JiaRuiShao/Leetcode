public class _303 {
    static class Solution1 {
        class NumArray {
            private int[] preSum;

            public NumArray(int[] nums) {
                // preSum[0] = 0, used to calculate the sum
                preSum = new int[nums.length + 1];
                for (int i = 1; i < preSum.length; i++) {
                    preSum[i] = preSum[i - 1] + nums[i - 1];
                }
            }

            public int sumRange(int left, int right) {
                return preSum[right + 1] - preSum[left];
            }
        }
    }

    static class Solution2 {
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
