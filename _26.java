/**
 * 26. Remove Duplicates from Sorted Array
 */
public class _26 {
    class Solution1_Two_Pointers {
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
            int idx = 0;
            // [0, idx]: unique nums
            for (int i = 1; i < nums.length; i++) {
                if (nums[idx] != nums[i]) {
                    nums[++idx] = nums[i];
                }
            }
            return idx + 1;
        }
    }
}
