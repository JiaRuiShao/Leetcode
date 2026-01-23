/**
 * 31. Next Permutation
 */
public class _31 {
    class Solution1_Math {
        // [1,3,2] -> [2,1,3]
        // [2,3,1] -> [3,1,2]
        // [1,2,5,4,3] -> [1,3,2,4,5]
        public void nextPermutation(int[] nums) {
            int n = nums.length;
            // traverse from right to left, find first elem break mono decreasing order
            int pos = n - 2;
            for (; pos >= 0; pos--) {
                if (nums[pos] < nums[pos + 1]) {
                    break;
                }
            }
            // [.. pos .. n-1]
            //         ------
            //        mono decreasing
            // find a larger elem from right to swap
            if (pos >= 0) {
                int greaterIdx = n - 1;
                while (greaterIdx > pos && nums[greaterIdx] <= nums[pos]) {
                    greaterIdx--;
                }
                swap(nums, pos, greaterIdx);
            }

            // reverse mono decreasing subarray
            int left = pos + 1, right = n - 1;
            while (left < right) {
                swap(nums, left++, right--);
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
