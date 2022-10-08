/**
 * 26. Remove Duplicates from Sorted Array.
 */
public class _26 {
    class Solution_Fast_Slow_Pointers {
        public int removeDuplicates(int[] nums) {
            // slow points to the end of the non-dup numbers, fast points to the next diff number
            int slow = 0, fast = 0, n = nums.length;
            while (fast < n) {
                if (nums[fast] != nums[slow]) {
                    nums[++slow] = nums[fast];
                }
                fast++;
            }
            return slow + 1;
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
}
