import java.util.Arrays;

/**
 * 268. Missing Number
 */
public class _268 {
    class Solution1_Bitwise_XOR {
        public int missingNumber(int[] nums) {
            int n = nums.length, missing = 0;
            for (int num = 0; num <= n; num++) {
                missing ^= num;
            }
            for (int num : nums) {
                missing ^= num;
            }
            return missing;
        }
    }

    class Solution2_APSum {
        int missingNumber(int[] nums) {
            int n = nums.length;
            // Sum of Arithmetic Series -- AP Sum: (first term + last term) * number of terms / 2
            int expect = (0 + n) * (n + 1) / 2;
            for (int x : nums) {
                expect -= x;
            }
            return expect;
        }
    }

    // Time: O(nlogn)
    // Space: O(logn)
    class Solution3_Sorting {
        public int missingNumber(int[] nums) {
            Arrays.sort(nums);
            
            // Check if 0 is missing
            if (nums[0] != 0) {
                return 0;
            }
            
            // Check if n is missing
            if (nums[nums.length - 1] != nums.length) {
                return nums.length;
            }
            
            // Check for missing number in the middle
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] != nums[i - 1] + 1) {
                    return nums[i - 1] + 1;
                }
            }
            
            return -1;  // Should never reach
        }
    }
}
