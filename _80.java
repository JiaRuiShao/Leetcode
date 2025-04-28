/**
 * 80. Remove Duplicates from Sorted Array II
 */
public class _80 {
    class Solution1_Two_Pointers {
        public int removeDuplicates(int[] nums) {
            int n = nums.length, left = 0, right = 0;
            while (right < n) {
                int distinct = nums[right++];
                int start = right;
                nums[left++] = distinct;
                while (right < n && nums[right] == distinct) {
                    right++;
                }
                if (right > start) {
                    nums[left++] = distinct;
                }
            }
            return left;
        }
    }
}
