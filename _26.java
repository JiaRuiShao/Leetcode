/**
 * 26. Remove Duplicates from Sorted Array
 */
public class _26 {
    class Solution1_Fast_Slow_Pointers {
        public int removeDuplicates(int[] nums) {
            // [0, left) distinct num
            int len = nums.length;
            int left = 0, right = 0;
            while (right < len) {
                int distinct = nums[right];
                nums[left++] = distinct;
                while (right < len && nums[right] == distinct) {
                    right++;
                }
            }
            return left;
        }
    
        public int removeDuplicates2(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int slow = 0, fast = 0; // unique nums are records at the slow (& equal to) of the slow fast
            while (++fast < nums.length) {
                if (nums[fast] != nums[slow]) {
                    slow++;
                    if (slow != fast) nums[slow] = nums[fast];
                }
            }
            return slow + 1;
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
