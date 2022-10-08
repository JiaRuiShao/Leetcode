/**
 * 283. Move Zeroes.
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 * Note that you must do this in-place without making a copy of the array.
 */
public class _283 {
    class Solution1_Fast_Slow_Pointers {
        public void moveZeroes(int[] nums) {
            int slow = 0, fast = 0;
            while (fast < nums.length) {
                if (nums[fast] != 0) {
                    if (fast != slow) {
                        nums[slow] = nums[fast];
                        nums[fast] = 0;
                    }
                    slow++;
                }
                fast++;
            }
        }
    }
}
