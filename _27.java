/**
 * 27. Remove Element.
 *
 * Given an integer array nums and an integer val, remove all occurrences of val
 * in nums in-place. The relative order of the elements may be changed.
 * More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result.
 * It does not matter what you leave beyond the first k elements.
 * Return k after placing the final result in the first k slots of nums.
 *
 * Do not allocate extra space for another array.
 * You must do this by modifying the input array in-place with O(1) extra memory.
 */
public class _27 {
    class Solution1_Fast_Slow_Pointer {
        public int removeElement(int[] nums, int val) {
            int slow = 0, fast = 0;
            while (fast < nums.length) {
                if (nums[fast] != val) {
                    nums[slow++] = nums[fast];
                }
                fast++;
            }
            return slow;
        }
    }
    
    class Solution2_Left_Right_Pointer {
        public int removeElement(int[] nums, int val) {
            if (nums == null || nums.length == 0) return 0;
            int i = 0, j = nums.length - 1;
            while (j >= i) {
                while (i < nums.length && nums[i] != val) i++;
                while (j >= 0 && nums[j] == val) j--;
                if (i < j) {
                    nums[i] = nums[j];
                    nums[j] = val;
                }
            }
            return j + 1;
        }
    }
}
