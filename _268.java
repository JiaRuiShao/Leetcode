/**
 * 268. Missing Number
 */
public class _268 {
    class Solution1_Bitwise_XOR {
        public int missingNumber(int[] nums) {
            int n = nums.length; // n is also is valid index since we're missing one number
            int res = 0;
            res ^= n;
            // XOR with other elements and indices
            for (int i = 0; i < n; i++) {
                res = res ^ i ^ nums[i];
            }
            return res;
        }
    }

    class Solution2_Arithmetic_Sequence {
        int missingNumber(int[] nums) {
            int n = nums.length;
            // Sum of Arithmetic Series: (first term + last term) * number of terms / 2
            long expect = (0 + n) * (n + 1) / 2;
            long sum = 0;
            for (int x : nums) {
                sum += x;
            }
            return (int)(expect - sum);
        }
    }
}
