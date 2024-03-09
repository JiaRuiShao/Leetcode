/**
 * 238. Product of Array Except Self.
 */
public class _238 {
    class Solution1_Prefix_Suffix {

        int[] prefix;
        int[] suffix;
    
        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            buildPrefix(nums, n);
            buildSuffix(nums, n);
            return buildAnswer(n);
        }
    
        private void buildPrefix(int[] nums, int n) {
            prefix = new int[n + 1];
            prefix[0] = 1;
            for (int i = 0; i < n; i++) {
                prefix[i + 1] = prefix[i] * nums[i];
            }
        }
    
        private void buildSuffix(int[] nums, int n) {
            suffix = new int[n + 1];
            suffix[n] = 1;
            for (int i = n - 1; i >= 0; i--) {
                suffix[i] = suffix[i + 1] * nums[i];
            }
        }
    
        private int findPrefix(int idx) { // product [0, 1, 2, ..., idx], both ends exclusive
            return prefix[idx + 1];
        }
    
        private int findSuffix(int idx) { // product [idx, idx + 1, ... n - 1], both ends exclusive
            return suffix[idx];
        }
    
        private int[] buildAnswer(int n) {
            int[] ans = new int[n];
            for (int i = 0; i < n; i++) {
                ans[i] = findPrefix(i - 1) * findSuffix(i + 1);
            }
            return ans;
        }
    }

    class Solution2_Prefix_Suffix {
        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            int[] pre = new int[n + 1]; // pre[i] stores the product of elements [0, i-1]
            int[] suf = new int[n + 1]; // suf[i] stores the product of elements [i+1, n-1]
            pre[0] = 1;
            suf[n] = 1;
            for (int i = 0; i < n; i++) {
                pre[i + 1] = pre[i] * nums[i];
                int j = n - 1 - i;
                suf[j] = suf[j + 1] * nums[j];
            }
            
            int[] prod = new int[n];
            for (int i = 0; i < n; i++) {
                // left: [0...i-1]
                // right: [i+1, n-1]
                prod[i] = pre[i] * suf[i + 1];
            }
            return prod;
        }
    }

    class Solution3_Prefix_Suffix_Improved {
        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            int[] result = new int[n];
            result[0] = 1;
            for (int i = 1; i < n; i++) {
                result[i] = result[i - 1] * nums[i - 1];
            }
            int right = 1;
            for (int i = n - 1; i >= 0; i--) {
                result[i] *= right;
                right *= nums[i];
            }
            return result;
        }
    }
}
