/**
 * 43. Multiply Strings
 * 
 * Clarification:
 * - Would there be leading zeros?
 * - Only digits in input?
 * - Max len of input String?
 * - Non-empty String?
 * 
 * Followup:
 * - Why the result size m + n? Maximum product of m-digit and n-digit numbers: (10^m - 1) × (10^n - 1) < 10^m × 10^n = 10^(m+n); since 10^(m+n) is the smallest num with m+n+1 digits, we can conclude that our project max digit is m+n
 * - Why the pos1 and pos2 calculated as i + j and i + j + 1? 
 * 1. Math Interpretation:
 * In num1, digit at index i has positional value: digit × 10^(m-1-i)
 * In num2, digit at index j has positional value: digit × 10^(n-1-j)
 * When product multiplied:
 * product = digit1 × 10^(m-1-i) × digit2 × 10^(n-1-j)
 *      = (digit1 × digit2) × 10^(m-1-i+n-1-j)
 *      = (digit1 × digit2) × 10^(m+n-2-i-j)
 * 
 * In the result array of size (m+n): index 0 represents 10^(m+n-1); index k represents 10^(m+n-1-k)
 * Unknown idx of this ONES pos in the new allocated array represents 10^(m+n-1-idx)
 * We want to know the idx with the same positional value as the product, so m+n-1-idx=m+n-2-i-j thus
 * idx = i+j+1, carry index is this number pos - 1
 * 
 * 2. Simpler Intuitive Interpretation
 * When we calculate the rightmost index product, we always want to make sure the results are saved as right as possible
 * The max index for result is m+n-1, max index for num1 is m-1; max index for num2 is n-1
 * We can see to get to the last index in result, we need i (m-1) + j (n-1) + 1
 * 
 * - How would you optimize for num1 or num2 being "1", "10", "100", etc? Append zeros
 */
public class _43 {
    // Time: O(mn)
    // Space: O(m+n)
    class Solution1 {
        public String multiply(String num1, String num2) {
            // Edge case: multiplication by zero
            if (num1.equals("0") || num2.equals("0")) {
                return "0";
            }
            
            int m = num1.length();
            int n = num2.length();
            int[] result = new int[m + n];
            
            // Multiply each digit from right to left
            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    int digit1 = num1.charAt(i) - '0';
                    int digit2 = num2.charAt(j) - '0';
                    int product = digit1 * digit2;
                    
                    int pos1 = i + j; // carry pos
                    int pos2 = i + j + 1; // ones pos
                    
                    int sum = product + result[pos2];
                    result[pos2] = sum % 10;
                    result[pos1] += sum / 10;
                }
            }
            
            // Convert to string, skipping leading zeros
            StringBuilder sb = new StringBuilder();
            for (int num : result) {
                if (sb.length() > 0 || num != 0) {
                    sb.append(num);
                }
            }
            
            return sb.length() == 0 ? "0" : sb.toString();
        }
    }
}
