/**
 * 152. Maximum Product Subarray
 * 
 * - S1: BF O(n^2), O(1)
 * - S2: DP Kadane's Algo O(n), O(1)
 */
public class _152 {
    class Solution1_DP {
        // dp[i] = max(dp[i-1] * nums[i], nums[i])
        public int maxProduct(int[] nums) {
            int maxSoFar = nums[0];
            int maxEndingHere = nums[0];
            int minEndingHere = nums[0];  // Track min for negative numbers
            
            for (int i = 1; i < nums.length; i++) {
                int temp = maxEndingHere;
                
                // Max product ending here
                maxEndingHere = Math.max(nums[i], 
                                Math.max(maxEndingHere * nums[i], 
                                        minEndingHere * nums[i]));
                
                // Min product ending here
                minEndingHere = Math.min(nums[i], 
                                Math.min(temp * nums[i], 
                                        minEndingHere * nums[i]));
                
                maxSoFar = Math.max(maxSoFar, maxEndingHere);
            }
            
            return maxSoFar;
        }
    }
}
