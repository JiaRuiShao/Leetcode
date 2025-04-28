/**
 * 26. Remove Duplicates from Sorted Array
 */
public class _26 {
    class Solution1_Fast_Slow_Pointers {
        public int removeDuplicates(int[] nums) {
            int left = 0, right = 0;
            while (right < nums.length) {
                while (right < nums.length - 1 && nums[right] == nums[right + 1]) {
                    right++;
                }
                nums[left++] = nums[right++];
            }
            return left;
        }
    }

    class Solution2_Two_Pointers {
        public int removeDuplicates(int[] nums) {
            int left = 0, right = 0;
            while (right < nums.length) {
                if (nums[right] != nums[left]) {
                    nums[++left] = nums[right];
                }
                right++;
            }
            return left + 1;
        }
    }
}
