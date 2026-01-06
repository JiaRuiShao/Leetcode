/**
 * 67. Add Binary
 * 
 * Clarification:
 * - Range of input string?
 * 
 * Followup:
 * - Add two numbers in any base (2 - 36)?
 * - Subtract binary numbers?
 * - Multiply binary numbers?
 * - Pad results with leading zeros? No remove leading zeros
 */
public class _67 {
    // Time: O(max(m, n))
    // Space: O(max(m, n))
    class Solution1_Array {
        public String addBinary(String a, String b) {
            int m = a.length(), n = b.length();
            char[] sum = new char[Math.max(m, n) + 1];
            int carry = 0, i = m - 1, j = n - 1, pos = sum.length - 1;
            while (i >= 0 || j >= 0 || carry > 0) {
                int digit = carry + (i >= 0 ? a.charAt(i--) - '0' : 0) + (j >= 0 ? b.charAt(j--) - '0' : 0);
                sum[pos--] = (char) (digit % 2 + '0');
                carry = digit / 2;
            }
            return new String(sum, pos + 1, sum.length - pos - 1);
        }
    }

    class Solution2_StringBuilder {
        public String addBinary(String a, String b) {
            StringBuilder result = new StringBuilder();
            
            int i = a.length() - 1;
            int j = b.length() - 1;
            int carry = 0;
            
            while (i >= 0 || j >= 0 || carry > 0) {
                int digitA = i >= 0 ? a.charAt(i--) - '0' : 0;
                int digitB = j >= 0 ? b.charAt(j--) - '0' : 0;
                
                int sum = digitA + digitB + carry;
                
                result.append(sum % 2);
                carry = sum / 2;
            }
            
            return result.reverse().toString();
        }
    }

    class Followup_AnyBase {
        public String addBase(String a, String b, int base) {
            StringBuilder result = new StringBuilder();
            int i = a.length() - 1;
            int j = b.length() - 1;
            int carry = 0;
            
            while (i >= 0 || j >= 0 || carry > 0) {
                int digitA = i >= 0 ? digitValue(a.charAt(i--)) : 0;
                int digitB = j >= 0 ? digitValue(b.charAt(j--)) : 0;
                
                int sum = digitA + digitB + carry;
                result.append(digitChar(sum % base));
                carry = sum / base;
            }
            
            return result.reverse().toString();
        }

        private int digitValue(char c) {
            if (c >= '0' && c <= '9') return c - '0';
            return Character.toUpperCase(c) - 'A' + 10;
        }

        private char digitChar(int value) {
            if (value < 10) return (char)('0' + value);
            return (char)('A' + value - 10);
        }
    }

    class Followup_Subtract {
        public String subtractBinary(String a, String b) {
            int m = a.length(), n = b.length();
            if (m < n || a.compareTo(b) < 0) {
                return "-" + subtractBinary(b, a);
            }
            char[] subtract = new char[m]; // max(m, n) = m
            int pos = subtract.length - 1, borrow = 0, i = m - 1, j = n - 1;
            while (i >= 0) {
                int digitA = a.charAt(i--);
                int digitB = j >= 0 ? b.charAt(j--) - '0' : 0;
                int diff = digitA - digitB - borrow;
                if (diff < 0) {
                    diff += 2;
                    borrow = 1;
                } else {
                    borrow = 0;
                }
                subtract[pos--] = (char) (diff + '0');
            }
            pos = 0;
            while (pos < subtract.length - 1 && subtract[pos] == '0') pos++;
            return new String(subtract, pos, subtract.length - pos);
        }
    }

    /**
     *   1011  (11)
       ×  101  (5)
       ------
         1011  (11 × 1, shifted 0)
         0000   (11 × 0, shifted 1)
         1011    (11 × 1, shifted 2)
        ------
       110111  (55)
     */
    class Followup_Multiply {
        public String multiplyBinary(String a, String b) {
            if (a.equals("0") || b.equals("0")) return "0";
            int m = a.length(), n = b.length();
            int[] product = new int[m+n];
            for (int j = n - 1; j >= 0; j--) {
                for (int i = m - 1; i >= 0; i--) {
                    product[i+j+1] += (b.charAt(j) - '0') * (a.charAt(i) - '0');
                    if (product[i+j+1] >= 2) {
                        product[i+j] += product[i+j+1] / 2;
                        product[i+j+1] = product[i+j+1] % 2;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int digit : product) {
                if (digit == 0 && sb.isEmpty()) continue;
                sb.append(digit); 
            }
            return sb.isEmpty() ? "0" : sb.toString();
        }

        public String multiplyBinary2(String a, String b) {
            if (a.equals("0") || b.equals("0")) return "0";
            
            String result = "0";
            
            for (int i = b.length() - 1; i >= 0; i--) {
                if (b.charAt(i) == '1') {
                    // Multiply a by 1 and shift left
                    String shifted = a + "0".repeat(b.length() - 1 - i);
                    result = addBinary(result, shifted);
                }
            }
            
            return result;
        }
        
        private String addBinary(String a, String b) {
            // same logic as above
            return "";
        }
    }
}
