/**
 * 415. Add Strings
 * 
 * Followup:
 * - Why process right to left? carry propagate from right to left; 
 * if we start from left, we wouldn't know the carry from right
 * - Why use StringBuilder? String is immutable in Java, we don't want to create a new String for every concatenation
 * - Implement subtraction (num1 - num2)
 * - Solve without using reverse()? We can perform a manual length reverse of the StringBuilder OR use char[]
 * - What is inputs can be negative? Check sign before number to decide to subtract or to add
 */
public class _415 {
    // Time: O(max(m, n))
    // Space: O(max(m, n))
    class Solution1_StringBuilder {
        public String addStrings(String num1, String num2) {
            int m = num1.length(), n = num2.length();
            StringBuilder sb = new StringBuilder();

            int i = m - 1, j = n - 1, carry = 0;
            while (i >= 0 || j >= 0 || carry > 0) {
                int sum = carry;
                if (i >= 0) sum += (num1.charAt(i) - '0');
                if (j >= 0) sum += (num2.charAt(j) - '0');
                sb.append(sum % 10);
                carry = sum / 10;
                i--;
                j--;
            }
            return sb.reverse().toString();
        }
    }

    class Solution2_Array {
        // Use char[]
        public String addStrings(String num1, String num2) {
            int m = num1.length(), n = num2.length(), maxLen = Math.max(m, n) + 1;
            char[] res = new char[maxLen];

            int i = m - 1, j = n - 1, pos = maxLen - 1, carry = 0;
            while (i >= 0 || j >= 0 || carry > 0) {
                int sum = carry;
                if (i >= 0) sum += (num1.charAt(i--) - '0');
                if (j >= 0) sum += (num2.charAt(j--) - '0');
                res[pos--] = (char) ('0' + sum % 10);
                carry = sum / 10;
            }
            int start = pos + 1;
            while (start < maxLen && res[start] == '0') start++; 
            return new String(res, start, maxLen - start);
        }

        // use int[]
        public String addStrings2(String num1, String num2) {
            int m = num1.length(), n = num2.length(), maxLen = Math.max(m, n) + 1;
            int[] result = new int[maxLen];
            int i = m - 1, j = n - 1, pos = maxLen - 1, carry = 0;
            
            while (i >= 0 || j >= 0 || carry > 0) {
                int digit1 = i >= 0 ? num1.charAt(i--) - '0' : 0;
                int digit2 = j >= 0 ? num2.charAt(j--) - '0' : 0;
                
                int sum = digit1 + digit2 + carry;
                result[pos--] = sum % 10;
                carry = sum / 10;
            }
            
            // Convert to string, skip leading zero
            StringBuilder sb = new StringBuilder();
            int start = pos + 1;
            if (result[start] == 0 && start < maxLen - 1) start++;
            
            for (int k = start; k < maxLen; k++) {
                sb.append(result[k]);
            }
            
            return sb.toString();
        }
    }

    class Followup_WithoutReverse {
        // Method 1: Build from left to right (insert at beginning)
        // This is O(n²) - not recommended

        // Method 2: Manual reverse
        public String addStrings(String num1, String num2) {
            int i = num1.length() - 1;
            int j = num2.length() - 1;
            int carry = 0;
            
            // First, build result in reverse
            StringBuilder sb = new StringBuilder();
            
            while (i >= 0 || j >= 0 || carry > 0) {
                int digit1 = i >= 0 ? num1.charAt(i--) - '0' : 0;
                int digit2 = j >= 0 ? num2.charAt(j--) - '0' : 0;
                
                int sum = digit1 + digit2 + carry;
                sb.append(sum % 10);
                carry = sum / 10;
            }
            
            // Method 1: Manual reverse
            int left = 0, right = sb.length() - 1;
            while (left < right) {
                char temp = sb.charAt(left);
                sb.setCharAt(left, sb.charAt(right));
                sb.setCharAt(right, temp);
                left++;
                right--;
            }
            return sb.toString();
        }

        // Method 3: Use char array
        public String addStrings2(String num1, String num2) {
            int maxLen = Math.max(num1.length(), num2.length()) + 1;
            char[] result = new char[maxLen];
            int pos = maxLen - 1;
            
            int i = num1.length() - 1;
            int j = num2.length() - 1;
            int carry = 0;
            
            while (i >= 0 || j >= 0 || carry > 0) {
                int digit1 = i >= 0 ? num1.charAt(i--) - '0' : 0;
                int digit2 = j >= 0 ? num2.charAt(j--) - '0' : 0;
                
                int sum = digit1 + digit2 + carry;
                result[pos--] = (char)('0' + sum % 10);
                carry = sum / 10;
            }
            
            // Skip leading zeros (only the first position could be 0)
            int start = pos + 1;
            if (start < maxLen && result[start] == '0') {
                start++;
            }
            
            return new String(result, start, maxLen - start);
        }

        // Time: O(n) without reverse
        // Space: O(n)
    }

    class Followup_WithoutCarry {
        public String addStrings(String num1, String num2) {
            int m = num1.length(), n = num2.length(), maxLen = Math.max(m, n) + 1;
            int[] result = new int[maxLen];
            int i = m - 1, j = n - 1, pos = maxLen - 1;
            
            while (i >= 0 || j >= 0) {
                int digit1 = i >= 0 ? num1.charAt(i--) - '0' : 0;
                int digit2 = j >= 0 ? num2.charAt(j--) - '0' : 0;
                
                result[pos] += digit1 + digit2;  // Add to existing value (might be carry)
                
                if (result[pos] >= 10) {
                    result[pos - 1] += result[pos] / 10;  // Propagate carry to next position
                    result[pos] %= 10;                     // Keep only ones digit
                }
                
                pos--;
            }
            
            StringBuilder sb = new StringBuilder();
            int start = pos;
            while (start < maxLen && result[start] == 0) start++;
            
            for (int k = start; k < maxLen; k++) {
                sb.append(result[k]);
            }
            
            return sb.length() == 0 ? "0" : sb.toString();
        }
    }

    class Followup_Subtract {
        public String subtractStrings(String num1, String num2) {
            // Assume num1 >= num2 (non-negative result)
            
            // First check if num1 < num2
            if (isSmaller(num1, num2)) {
                String result = subtractStrings(num2, num1);
                return "-" + result;  // Negative result
            }
            
            StringBuilder sb = new StringBuilder();
            int borrow = 0;
            int i = num1.length() - 1;
            int j = num2.length() - 1;
            
            while (i >= 0) {
                int digit1 = num1.charAt(i) - '0';
                int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;
                
                int diff = digit1 - digit2 - borrow;
                
                if (diff < 0) {
                    diff += 10;
                    borrow = 1;
                } else {
                    borrow = 0;
                }
                
                sb.append(diff);
                i--;
                j--;
            }
            
            // Remove leading zeros
            while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
                sb.deleteCharAt(sb.length() - 1);
            }
            
            return sb.reverse().toString();
        }

        private boolean isSmaller(String num1, String num2) {
            if (num1.length() != num2.length()) {
                return num1.length() < num2.length();
            }
            return num1.compareTo(num2) < 0;
        }

        // Example:
        // "543" - "321" = "222"
        // "100" - "99" = "1"
        // "321" - "543" = "-222"
    }
}
