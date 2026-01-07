import java.util.Arrays;

/**
 * 66. Plus One
 * 
 * Followup:
 * - Minus One
 */
public class _66 {
    class Solution1 {
        public int[] plusOne(int[] digits) {
            int n = digits.length, carry = 1, pos = n - 1;
            while (carry > 0 && pos >= 0) {
                digits[pos] += carry;
                if (digits[pos] > 9) {
                    carry = 1;
                    digits[pos] -= 10;
                } else {
                    carry = 0;
                }
                pos--;
            }

            if (carry == 0) {
                return digits;
            }
            // need to allocate new array with n+1 size
            int[] plusOne = new int[n+1];
            System.arraycopy(digits, 0, plusOne, 1, n);
            plusOne[0] = carry;
            return plusOne;
        }
    }

    class Solution2_Recursion {
        public int[] plusOne(int[] digits) {
            return plusOneHelper(digits, digits.length - 1);
        }

        private int[] plusOneHelper(int[] digits, int index) {
            if (index < 0) {
                // All digits were 9, need new array
                int[] result = new int[digits.length + 1];
                result[0] = 1;
                return result;
            }
            
            if (digits[index] < 9) {
                // Can increment without carry
                digits[index]++;
                return digits;
            }
            
            // digit is 9, set to 0 and recurse
            digits[index] = 0;
            return plusOneHelper(digits, index - 1);
        }
    }

    class Followup_MinusOne {
        public int[] minusOne(int[] digits) {
            int n = digits.length;
            
            // Edge case: [0] can't subtract
            if (n == 1 && digits[0] == 0) {
                return new int[]{0};  // Or throw exception
            }
            
            for (int i = n - 1; i >= 0; i--) {
                if (digits[i] > 0) {
                    // No borrow needed
                    digits[i]--;
                    return digits;
                }
                
                // digit is 0, borrow from previous
                digits[i] = 9;
            }
            
            // If we're here, number was like [1,0,0,0]
            // Result is [9,9,9] - need to remove leading zero
            if (digits[0] == 0) {
                return Arrays.copyOfRange(digits, 1, n);
            }
            
            return digits;
        }
    }
}
