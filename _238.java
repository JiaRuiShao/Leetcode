/**
 * 238. Product of Array Except Self
 */
public class _238 {
    class Solution1_Prefix_Suffix {
        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            int[] prefix = new int[n];
            int[] suffix = new int[n];
            int[] productExceptSelf = new int[n];
    
            for (int i = 0; i < n; i++) {
                if (i == 0) prefix[0] = nums[0];
                else {
                    prefix[i] = prefix[i - 1] * nums[i];
                }
            }
    
            for (int i = n - 1; i >= 0; i--) {
                if (i == n - 1) suffix[i] = nums[i];
                else {
                    suffix[i] = suffix[i + 1] * nums[i];
                }
            }
    
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    productExceptSelf[i] = suffix[i + 1];
                } else if (i == n - 1) {
                    productExceptSelf[i] = prefix[i - 1];
                } else {
                    productExceptSelf[i] = prefix[i - 1] * suffix[i + 1];
                }
            }
            return productExceptSelf;
        }
    }

    class Solution2_Prefix_Suffix_Improved {
        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            int[] productExceptSelf = new int[n];
            // build prefix
            productExceptSelf[0] = nums[0];
            for (int i = 1; i < n; i++) {
                productExceptSelf[i] = productExceptSelf[i - 1] * nums[i];
            }
    
            // build suffix & get answer
            int suffix = 1;
            for (int i = n - 1; i > 0; i--) {
                productExceptSelf[i] = suffix * productExceptSelf[i - 1];
                suffix *= nums[i];
            }
            productExceptSelf[0] = suffix;
            return productExceptSelf;
        }
    }
}
