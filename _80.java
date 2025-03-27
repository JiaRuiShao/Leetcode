/**
 * 80. Remove Duplicates from Sorted Array II
 */
public class _80 {
    class Solution1_Two_Pointers {
        public int removeDuplicates(int[] nums) {
            // range [0, left)
            int left = 0, right = 0;
            while (right < nums.length) {
                int distinct = nums[right];
                int start = right;

                nums[left++] = distinct;
                while (right < nums.length && nums[right] == distinct) {
                    right++;
                }
                // extra step: check freq of this distinct num > 1
                if (right > start + 1) {
                    nums[left++] = distinct;
                }
            }
            return left;
        }
    }
}
