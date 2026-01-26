/**
 * 44. Wildcard Matching
 */
public class _44 {
    // Time: O(mn)
    // Space: O(mn) / O(n) optimized
    class Solution1_DP {
        public boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            // match s[0..i-1] using p[0..j-1]
            boolean[][] dp = new boolean[m + 1][n + 1];
            dp[0][0] = true;
            // dp[0][1..n] = false
            // dp[1..m][0] = false
            // dp[0][j] = true if p[j-1] == *
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[0][j] = dp[0][j-1];
                }
            }
            // dp[i][j] = if s[i-1] == p[j-1]: dp[i-1][j-1]
            //            else if p[j-1] == '?': dp[i-1][j-1] (match ? to s[i-1])
            //            else if p[j-1] == '*': dp[i-1][j-1] (match once) || dp[i-1][j] (match more than once) || dp[i][j-1] (match empty char)
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    char cs = s.charAt(i - 1);
                    char cp = p.charAt(j - 1);
                    if (cp == '*') {
                        dp[i][j] = dp[i - 1][j - 1] || dp[i - 1][j] || dp[i][j - 1];
                    } else if (cp == '?' || cp == cs) {
                        dp[i][j] = dp[i - 1][j - 1];
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
    class Solution1_DP_SpaceOptimized {
        public boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            // match s[0..i-1] using p[0..j-1]
            boolean[] dp = new boolean[n + 1];
            dp[0] = true;
            // dp[0][1..n] = false
            // dp[1..m][0] = false
            // dp[0][j] = true if p[j-1] == *
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[j] = dp[j-1];
                }
            }
            // dp[i][j] = if s[i-1] == p[j-1]: dp[i-1][j-1]
            //            else if p[j-1] == '?': dp[i-1][j-1] (match ? to s[i-1])
            //            else if p[j-1] == '*': dp[i-1][j-1] (match once) || dp[i-1][j] (match more than once) || dp[i][j-1] (match empty char)
            for (int i = 1; i <= m; i++) {
                boolean prev = dp[0];
                dp[0] = false;
                for (int j = 1; j <= n; j++) {
                    boolean temp = dp[j];
                    char cs = s.charAt(i - 1);
                    char cp = p.charAt(j - 1);
                    if (cp == '*') {
                        dp[j] = prev || dp[j] || dp[j - 1];
                    } else if (cp == '?' || cp == cs) {
                        dp[j] = prev;
                    } else {
                        dp[j] = false;
                    }
                    prev = temp;
                }
            }
            return dp[n];
        }
    }

    // Time: O(m+n)
    // Space: O(1)
    class Solution2_Greedy {
        public boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            int ps = 0, pp = 0; // [0, ps): already matched char in s
            int star = -1, lastMatch = -1;
            while (ps < m) {
                char cs = s.charAt(ps), cp = pp < n ? p.charAt(pp) : '\0';
                if (cs == cp || cp == '?') {
                    ps++;
                    pp++;
                } else if (cp == '*') {
                    star = pp;
                    lastMatch = ps;
                    pp++;
                } else if (star != -1) {
                    pp = star + 1;
                    lastMatch++;
                    ps = lastMatch;
                } else {
                    return false;
                }
            }
            while (pp < n && p.charAt(pp) == '*') {
                pp++;
            }
            return pp == n;
        }
    }
}
