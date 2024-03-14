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
    
    class Solution2_Two_Pointers {
        public int removeElement(int[] nums, int val) {
            int left = -1, right = 0;
            while (right < nums.length) {
                if (nums[right] != val) {
                    nums[++left] = nums[right];
                }
                right++;
            }
            return left + 1;
        }
    }
}
