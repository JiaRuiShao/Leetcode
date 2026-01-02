/**
 * 1312. Minimum Insertion Steps to Make a String Palindrome
 */
public class _1312 {
    // dp[i][j] =  min insertions make s[i..j] palindrome
    // recurrence: dp[i][j] = dp[i+1][j-1] when s[i] == s[j]
    //                      = 1 + min(dp[i][j-1], dp[i+1][j])
    // base cases: dp[i][j] = 0   when i == j
    //             dp[i][j] = INF when i > j here we could initialize as 0
    class Solution1_DP_2D {
        public int minInsertions(String s) {
            int n = s.length();
            int[][] dp = new int[n][n];
            for (int gap = 1; gap < n; gap++) {
                for (int i = 0; i + gap < n; i++) {
                    int j = i + gap;
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1];
                    } else {
                        // insert s[i] after pos j OR insert s[j] before pos i
                        dp[i][j] = 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }
            return dp[0][n - 1];
        }
    }

    class Solution2_DP_1D {
        public int minInsertions(String s) {
            int n = s.length();
            int[] dp = new int[n];
            for (int i = n - 1; i >= 0; i--) {
                int prev = 0; // dp[i+1][j-1]
                for (int j = i + 1; j < n; j++) {
                    int temp = dp[j]; // dp[i+1][j]
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[j] = prev;
                    } else {
                        dp[j] = 1 + Math.min(dp[j - 1], dp[j]);
                    }
                    prev = temp;
                }
            }
            return dp[n - 1];
        }
    }

    // LC 516
    class Solution2_LPS {
        public int minInsertions(String s) {
            return s.length() - longestPalindromeSubseq(s);
        }

        private int longestPalindromeSubseq(String s) {
            int n = s.length();
            int[] dp = new int[n];

            for (int i = n - 1; i >= 0; i--) {
                int prev = 0; // dp[i+1][j-1] = 0 when j=i+1 dp[i+1][i] = 0 since i > j
                dp[i] = 1; // dp[i][j-1]
                for (int j = i + 1; j < n; j++) {
                    int temp = dp[j]; // dp[i+1][j]
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[j] = prev + 2;
                    } else {
                        dp[j] = Math.max(dp[j], dp[j - 1]);
                    }
                    prev = temp;
                }
            }
            return dp[n - 1];
        }
    }
}
