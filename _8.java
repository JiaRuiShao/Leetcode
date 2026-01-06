/**
 * 8. String to Integer (atoi)
 * 
 * Clarification:
 * - What about space in the middle? Stop reading
 * - Is sign in the middle valid? No stop reading
 * - Sign only valid when it's after whitespace but before digit? Yes
 * - Do you want me to use int or long to save the number?
 * - ASCII encode?
 */
public class _8 {
    public int myAtoi(String s) {
        int sign = 1, pos = 0, n = s.length();
        int num = 0, min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
        while (pos < n && s.charAt(pos) == ' ') {
            pos++;
        }
    
        if (pos < n && (s.charAt(pos) == '-' || s.charAt(pos) == '+')) {
            sign = (s.charAt(pos) == '-') ? -1 : 1;
            pos++;
        }

        while (pos < n) {
            char c = s.charAt(pos++);
            if (!Character.isDigit(c)) break;
            if (c == '0' && num == 0) continue;
            if (num > (max - (c - '0')) / 10) return sign == 1 ? max : min;
            num = num * 10 + (c - '0');
        }

        num *= sign;
        return num;
    }

    class Followup_SupportDecimalAndScientificNotation {
        public double myAtof(String s) {
            if (s == null || s.length() == 0) return 0.0;
            
            int i = 0;
            int n = s.length();
            
            while (i < n && s.charAt(i) == ' ') i++;
            if (i == n) return 0.0;
            
            int sign = 1;
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                sign = s.charAt(i) == '-' ? -1 : 1;
                i++;
            }
            
            double result = 0.0;
            
            // Integer part
            while (i < n && Character.isDigit(s.charAt(i))) {
                result = result * 10 + (s.charAt(i) - '0');
                i++;
            }
            
            // Decimal part
            if (i < n && s.charAt(i) == '.') {
                i++;
                double fraction = 1.0;
                
                while (i < n && Character.isDigit(s.charAt(i))) {
                    fraction /= 10;
                    result += (s.charAt(i) - '0') * fraction;
                    i++;
                }
            }
            
            // Scientific notation (optional)
            if (i < n && (s.charAt(i) == 'e' || s.charAt(i) == 'E')) {
                i++;
                int expSign = 1;
                if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
                    expSign = s.charAt(i) == '-' ? -1 : 1;
                    i++;
                }
                
                int exponent = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    exponent = exponent * 10 + (s.charAt(i) - '0');
                    i++;
                }
                
                result *= Math.pow(10, expSign * exponent);
            }
            
            return sign * result;
        }
    }

    class Followup_Unicode {
        public int myAtoiUnicode(String s) {            
            int i = 0;
            int n = s.length();
            
            while (i < n && Character.isWhitespace(s.charAt(i))) i++;
            if (i == n) return 0;
            
            int sign = 1;
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                sign = s.charAt(i) == '-' ? -1 : 1;
                i++;
            }
            
            int result = 0;
            
            while (i < n) {
                char c = s.charAt(i);
                
                // Character.isDigit works with Unicode digits
                if (!Character.isDigit(c)) break;
                
                int digit = Character.getNumericValue(c);
                
                if (result > (Integer.MAX_VALUE - digit) / 10) {
                    return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
                
                result = result * 10 + digit;
                i++;
            }
            
            return sign * result;
        }
    }
}
