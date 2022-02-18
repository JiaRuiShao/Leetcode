public class _283 {
    class Solution {
        public void moveZeroes(int[] nums) {
            int i = 0, j = 0; // the num left than i are non-zero
            while (j < nums.length) {
                if (nums[j] != 0) {
                    if (i != j) {
                        nums[i] = nums[j];
                        nums[j] = 0;
                    }
                    i++;
                }
                j++;
            }
        }
    }
}
