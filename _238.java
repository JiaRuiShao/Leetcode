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
        // product[i] = pre[i-1] * suff[i+1]
        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            int[] product = new int[n];
            int prefix = 1;
            // calc prefix product
            for (int i = 0; i < n; i++) {
                product[i] = prefix;
                prefix *= nums[i];
            }
            int suffix = 1;
            for (int j = n - 1; j >= 0; j--) {
                product[j] *= suffix;
                suffix *= nums[j];
            }
            return product;
        }
    }
}
