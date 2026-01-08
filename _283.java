/**
 * 283. Move Zeroes
 */
public class _283 {
    class Solution1_TwoPointers {
        public void moveZeroes(int[] nums) {
            // [0, i) non-zero
            // [i, j) zero
            for (int i = 0, j = 0; j < nums.length; j++) {
                if (nums[j] != 0) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                    i++;
                }
            }
        }
    }
}
