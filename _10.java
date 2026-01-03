/**
 * 10. Regular Expression Matching
 * 
 * Clarification:
 * - Regular expression is valid? Yes
 * 
 * Test Cases:
 * - "ab" "a*"     => false
 * - "" "a*b*c*"   => true
 * - "aab" "c*a*b" => true
 * 
 * Followup:
 * - What if the pattern also supports '+' (one or more)? 
 * We can either:
 *  1. convert a+ to aa*
 *  2. dp[i][j] = dp[i - 1][j - 2] || dp[i - 1][j] when p[j-1] is '+'
 *     dp[i - 1][j - 2] match s[i-1] with p[j-2] once, then move past the '+'
 *     dp[i - 1][j]     match s[i-1] with p[j-2], stay at j to match more
 * - How would you handle '?' (zero or one occurrence)?
 * dp[i][j] = dp[i][j-2] || dp[i-1][j-2] when p[j-2] == '.' or s[i-1]
 * - Wildcard matching? LC 44
 * No char needed before '*' for wildcard; '*' must have preceding char in regex
 * - Validate if the pattern is valid?
 */
public class _10 {
    // Time: O(mn)
    // Space: O(mn)
    class Solution1_DP_2D {
        // dp[i][j] = whether s[0...i-1] matches p[0...j-1]
        // dp[i][j] = dp[i-1][j-1] if s[i-1] == p[j-1] || p[j-1] == '.'
        //          = dp[i][j-2] if p[i-1] = '*'
        //          = dp[i-1][j] if p[i-1] = '*' && (s[i-1] == p[j-2] || p[j-2] == '.')
        public boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            boolean[][] dp = new boolean[m + 1][n + 1];
            dp[0][0] = true;
            for (int j = 2; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[0][j] = dp[0][j - 2];
                }
            }

            for (int i = 1; i <= m; i++) {
                char sc = s.charAt(i - 1);
                for (int j = 1; j <= n; j++) {
                    char pc = p.charAt(j - 1);
                    if (pc == '.' || sc == pc) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else if (j > 1 && pc == '*') {
                        // not use this match
                        dp[i][j] = dp[i][j - 2];
                        // use this match
                        pc = p.charAt(j - 2);
                        if (pc == '.' || pc == sc) {
                            dp[i][j] = dp[i][j] || dp[i - 1][j];
                        }
                    } else {
                        dp[i][j] = false;
                    }
                }
            }
            return dp[m][n];
        }
    }

    // Time: O(mn)
    // Space: O(n)
    class Solution2_DP_1D {
        public boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            // dp[i][j] = whether s[0...i-1] matches p[0...j-1]
            // dp[i][j] = dp[i-1][j-1] if s[i-1] == p[j-1] || p[j-1] == '.'
            //          = dp[i][j-2] if p[i-1] = '*'
            //          = dp[i-1][j] if p[i-1] = '*' && (s[i-1] == p[j-2] || p[j-2] == '.')
            boolean[] dp = new boolean[n + 1];
            dp[0] = true;
            for (int j = 2; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[j] = dp[j - 2];
                }
            }

            for (int i = 1; i <= m; i++) {
                char sc = s.charAt(i - 1);
                boolean prev = dp[0]; // dp[i-1][j-1]
                dp[0] = false; // empty pattern can't match any valid string
                for (int j = 1; j <= n; j++) {
                    char pc = p.charAt(j - 1);
                    boolean temp = dp[j]; // dp[i-1][j]
                    if (pc == '.' || sc == pc) {
                        dp[j] = prev;
                    } else if (j > 1 && pc == '*') {
                        // not use this match
                        dp[j] = dp[j - 2];
                        // use this match
                        pc = p.charAt(j - 2);
                        if (pc == '.' || pc == sc) {
                            dp[j] = dp[j] || temp;
                        }
                    } else {
                        dp[j] = false;
                    }
                    prev = temp;
                }
            }
            return dp[n];
        }
    }

    // Time: O(mn)
    // Space: O(mn)
    class Solution3_Recursion_WithMemo {
        public boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            Boolean[][] memo = new Boolean[m][n];
            return dp(s, m - 1, p, n - 1, memo);
        }

        private boolean dp(String s, int i, String p, int j, Boolean[][] memo) {
            if (j == -1) return i == -1;
            if (i == -1) {
                if (p.charAt(j) == '*') return dp(s, i, p, j - 2, memo);
                else return false;
            }
            if (memo[i][j] != null) return memo[i][j];

            char sc = s.charAt(i), pc = p.charAt(j);
            boolean match = false;
            if (sc == pc || pc == '.') {
                match = dp(s, i - 1, p, j - 1, memo);
            } else if (j > 0 && pc == '*') {
                // not use
                match = dp(s, i, p, j - 2, memo);
                // use
                if (!match) {
                    pc = p.charAt(j - 1);
                    if (sc == pc || pc == '.') {
                        match = dp(s, i - 1, p, j, memo);
                    }
                }
            }
            return memo[i][j] = match;
        }
    }

    class Followup_PatternValidation {
        // Rules:
        // 1. '*' cannot be first character
        // 2. '**' not allowed
        // 3. Only lowercase letters, '.', and '*'
        public boolean isValidPattern(String p) {
            if (p.isEmpty()) return true;
            if (p.charAt(0) == '*') return false;  // Can't start with '*'
            
            for (int i = 0; i < p.length(); i++) {
                char c = p.charAt(i);
                // Check valid characters
                if (c != '.' && c != '*' && (c < 'a' || c > 'z')) {
                    return false;
                }
                // Check for consecutive '*'
                if (c == '*' && i > 0 && p.charAt(i - 1) == '*') {
                    return false;
                }
            }
            
            return true;
        }
    }
}
