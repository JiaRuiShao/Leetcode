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
}
