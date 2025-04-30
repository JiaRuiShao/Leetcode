/**
 * 13. Roman to Integer
 */
public class _13 {
    // MCMXCIV
    // 1994
    class Solution1 {
        public int romanToInt(String s) {
            int curr = 0, prev = 0, total = 0;
            for (int i = 0; i < s.length(); i++) {
                curr = convertRomanToInt(s.charAt(i));
                total += curr;
                if (i > 0 && curr > prev) {
                    total -= 2 * convertRomanToInt(s.charAt(i - 1));
                }
                prev = curr;
            }
            return total;
        }
    
        private int convertRomanToInt(char c) {
            int num = 0;
            switch (c) {
                case 'I': 
                    num = 1; 
                    break;
                case 'V':
                    num = 5;
                    break;
                case 'X':
                    num = 10;
                    break;
                case 'L':
                    num = 50;
                    break;
                case 'C':
                    num = 100;
                    break;
                case 'D':
                    num = 500;
                    break;
                case 'M':
                    num = 1000;
                    break;
            }
            return num;
        }
    }

    class Solution2 { // from right to left
        public int romanToInt(String s) {
            int curr = 0, prev = 0, total = 0;
            for (int i = s.length() - 1; i >= 0; i--) {
                curr = convertRomanToInt(s.charAt(i));
                if (curr < prev) {
                    total -= curr;
                } else {
                    total += curr;
                }
                prev = curr;
            }
            return total;
        }
    
        private int convertRomanToInt(char c) {
            int num = 0;
            switch (c) {
                case 'I': 
                    num = 1; 
                    break;
                case 'V':
                    num = 5;
                    break;
                case 'X':
                    num = 10;
                    break;
                case 'L':
                    num = 50;
                    break;
                case 'C':
                    num = 100;
                    break;
                case 'D':
                    num = 500;
                    break;
                case 'M':
                    num = 1000;
                    break;
            }
            return num;
        }
    }
}
