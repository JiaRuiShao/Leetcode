import java.util.ArrayList;
import java.util.List;

/**
 * 91. Decode Ways
 * 
 * Clarification:
 * - Can I assume the string is not empty and only contains digits?
 * - If leading zeros are allowed? No
 * 
 * Followup:
 * - Return all possible decoding, not just count? Backtrack with StringBuilder
 * - What if '*' can represent any digit 1-9? LC 639
 * - Leading zeros are allowed (01 = A but 00 is still invalid)
 * - Each valid decode has 2 interpretations? dp[i] = 2*dp[i-1]+2*dp[i-2]
 */
public class _91 {
    class Solution_DP {
        public int numDecodings(String s) {
            int n = s.length();
            int[] dp = new int[n + 1];
            dp[0] = 1;
            // dp[i] = if s[i-1] valid: dp[i-1]
            //       = else: 0
            //       + if s[i-2 .. i-1] valid: dp[i-2]
            for (int i = 1; i <= n; i++) {
                char c = s.charAt(i - 1);
                dp[i] = isValidDecode(c) ? dp[i - 1] : 0;
                if (i >= 2 && isValidDecode(c, s.charAt(i - 2))) dp[i] += dp[i - 2];
            }
            return dp[n];
        }

        private boolean isValidDecode(char c) {
            return c >= '1' && c <= '9';
        }

        private boolean isValidDecode(char curr, char prev) {
            return (prev == '1' && curr >= '0' && curr <= '9') || (prev == '2' && curr >= '0' && curr <= '6');
        }
    }

    class Solution1_DP_SpaceOptimized {
        public int numDecodings(String s) {
            int n = s.length();
            int prev2 = 0, prev1 = 1;
            for (int i = 1; i <= n; i++) {
                char c = s.charAt(i - 1);
                int curr = isValidDecode(c) ? prev1 : 0;
                if (i >= 2 && isValidDecode(c, s.charAt(i - 2))) curr += prev2;
                prev2 = prev1;
                prev1 = curr;
            }
            return prev1;
        }

        private boolean isValidDecode(char c) {
            return c >= '1' && c <= '9';
        }

        private boolean isValidDecode(char curr, char prev) {
            return (prev == '1' && curr >= '0' && curr <= '9') || (prev == '2' && curr >= '0' && curr <= '6');
        }

        public int numDecodings2(String s) {
            int n = s.length();
            int prev2 = 1; // dp[i-2]
            int prev1 = s.charAt(0) == '0' ? 0 : 1; // dp[i-1]
            
            for (int i = 2; i <= n; i++) {
                int current = 0;
                // single digit decode
                if (s.charAt(i - 1) != '0') {
                    current = prev1;
                }
                
                // two digits decode
                int twoDigit = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
                if (twoDigit >= 10 && twoDigit <= 26) {
                    current += prev2;
                }

                prev2 = prev1;
                prev1 = current;
            }
            
            return prev1;
        }
    }

    class Solution2_Recursion_WithMemo {
        public int numDecodings(String s) {
            Integer[] memo = new Integer[s.length()];
            return decode(s, 0, memo);
        }

        private int decode(String s, int index, Integer[] memo) {
            if (index == s.length()) {
                return 1;
            }
            
            if (s.charAt(index) == '0') {
                return 0;
            }
            
            if (memo[index] != null) {
                return memo[index];
            }
            
            // Single digit
            int ways = decode(s, index + 1, memo);
            
            // Two digits
            if (index + 1 < s.length()) {
                int twoDigit = Integer.parseInt(s.substring(index, index + 2));
                if (twoDigit >= 10 && twoDigit <= 26) {
                    ways += decode(s, index + 2, memo);
                }
            }
            
            memo[index] = ways;
            return ways;
        }
    }

    // Time: O(2^n)
    // Space: O(n)
    class Followup_ReturnDecodings {
        public List<String> allDecodings(String s) {
            List<String> result = new ArrayList<>();
            if (s == null || s.length() == 0 || s.charAt(0) == '0') {
                return result;
            }
            
            backtrack(s, 0, new StringBuilder(), result);
            return result;
        }

        private void backtrack(String s, int index, StringBuilder current, 
                            List<String> result) {
            if (index == s.length()) {
                result.add(current.toString());
                return;
            }
            
            if (s.charAt(index) == '0') {
                return; // Invalid
            }
            
            // Try single digit
            int oneDigit = s.charAt(index) - '0';
            char letter = (char)('A' + oneDigit - 1);
            current.append(letter);
            backtrack(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1);
            
            // Try two digits
            if (index + 1 < s.length()) {
                int twoDigit = (s.charAt(index) - '0') * 10 + (s.charAt(index + 1) - '0');
                if (twoDigit >= 10 && twoDigit <= 26) {
                    char twoLetter = (char)('A' + twoDigit - 1);
                    current.append(twoLetter);
                    backtrack(s, index + 2, current, result);
                    current.deleteCharAt(current.length() - 1);
                }
            }
        }
    }
}
