/**
 * 27. Remove Element
 */
public class _27 {
    class Solution1_Fast_Slow_Pointer {
        public int removeElement(int[] nums, int val) {
            int left = 0;
            // [0, left): nums != val
            for (int right = 0; right < nums.length; right++) {
                if (nums[right] != val) {
                    nums[left] = nums[right];
                    left++;
                }
            }
            return left;
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
