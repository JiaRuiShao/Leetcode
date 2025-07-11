/**
 * 268. Missing Number
 */
public class _268 {
    class Solution1_Bitwise_XOR {
        public int missingNumber(int[] nums) {
            int n = nums.length;        // the array has n elements drawn from 0..n
            int xor = 0;

            // 1) XOR all valid numbers -- indices 0..n
            for (int i = 0; i <= n; i++) {
                xor ^= i;
            }

            // 2) XOR all array values
            for (int num : nums) {
                xor ^= num;
            }

            // what remains is the missing number
            return xor;
        }
    }

    class Solution2_APSum {
        int missingNumber(int[] nums) {
            int n = nums.length;
            // Sum of Arithmetic Series -- AP Sum: (first term + last term) * number of terms / 2
            long expect = (0 + n) * (n + 1) / 2;
            long sum = 0;
            for (int x : nums) {
                sum += x;
            }
            return (int)(expect - sum);
        }
    }
}
