import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 1143. Longest Common Subsequence
 * 
 * Clarification:
 * - Case sensitive? Yes A ≠ a
 * 
 * Followup:
 * - Longest Common Substring (not subsequence -- must be contiguous)? LC 718
 * dp[i][j] max substring len end at i-1 & j-1;
 * Recurrence: dp[i][j] = dp[i-1][j-1] + 1 if s1[i-1] == s2[j-1]; 0 s1[i-1] ≠ s2[j-1]
 * - Shortest Supersequence? LC 1092
 * - Longest palindromic subsequence? return lcs(s, reverse(s))
 */
public class _1143 {
    // Time: O(2^(m+n))
    // Space: O(m+n)
    class Solution0_Recursion_TLE {
        public int longestCommonSubsequence(String text1, String text2) {
            return dp(text1, text1.length() - 1, text2, text2.length() - 1);
        }

        private int dp(String s1, int i, String s2, int j) {
            if (i == -1 || j == -1) return 0;
            if (s1.charAt(i) == s2.charAt(j)) {
                return dp(s1, i - 1, s2, j - 1) + 1;
            }
            return Math.max(
                dp(s1, i - 1, s2, j), // ignore char i from s1
                Math.max(
                    dp(s1, i - 1, s2, j - 1), // ignore char i fromm s1 & char j from s2
                    dp(s1, i, s2, j - 1) // ignore j from s2
                )
            );
        }
    }

    // Time: O(mn)
    // Space: O(mn)
    class Solution1_Recursion_WithMemo {
        public int longestCommonSubsequence(String text1, String text2) {
            int[][] memo = new int[text1.length()][text2.length()];
            for (int i = 0; i < text1.length(); i++) {
                Arrays.fill(memo[i], -1);
            }
            return dp(text1, text1.length() - 1, text2, text2.length() - 1, memo);
        }

        private int dp(String s1, int i, String s2, int j, int[][] memo) {
            if (i == -1 || j == -1) return 0;
            if (memo[i][j] >= 0) return memo[i][j];

            if (s1.charAt(i) == s2.charAt(j)) {
                return dp(s1, i - 1, s2, j - 1, memo) + 1;
            }
            memo[i][j] = Math.max(
                dp(s1, i - 1, s2, j, memo), // ignore char i from s1
                Math.max(
                    dp(s1, i - 1, s2, j - 1, memo), // ignore char i fromm s1 & char j from s2
                    dp(s1, i, s2, j - 1, memo) // ignore j from s2
                )
            );
            return memo[i][j];
        }
    }

    // dp[i][j] = s1[i] == s2[j] ? dp[i-1][j-1]+1 : max(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
    // OR                                           max(dp[i-1][j], dp[i][j-1])
    class Solution1_DP_2D {
        public int longestCommonSubsequence(String text1, String text2) {
            int m = text1.length(), n = text2.length();
            // dp[i][j] = s1[i] == s2[j] ? dp[i-1][j-1]+1 : max(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
            int[][] dp = new int[m + 1][n + 1]; // max seq len using first i char in s1 & j char in s2
            // dp[0][...] = 0
            // dp[...][0] = 0
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(
                            dp[i - 1][j], // ith char in s1 not in LCS
                            Math.max(
                                dp[i][j - 1], // jth char in s2 not in LCS
                                dp[i - 1][j - 1] // both char not in LCS
                            )
                        );
                    }
                }
            }
            return dp[m][n];
        }
    }

    // Time: O(mn)
    // Space: O(min(n, m))
    class Solution2_DP_1D {
        public int longestCommonSubsequence(String text1, String text2) {
            int m = text1.length(), n = text2.length();
            if (n > m) return longestCommonSubsequence(text2, text1);
            int[] dp = new int[n + 1];
            for (int i = 1; i <= m; i++) {
                int prev = 0; // dp[i-1][j-1]
                for (int j = 1; j <= n; j++) {
                    int temp = dp[j]; // dp[i-1][j]
                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[j] = prev + 1;
                    } else {
                        dp[j] = Math.max(dp[j], dp[j - 1]);
                    }
                    prev = temp;
                }
            }
            return dp[n];
        }
    }

    class Followup_ReturnLCS {
        public String longestCommonSubsequence(String text1, String text2) {
            int m = text1.length();
            int n = text2.length();
            int[][] dp = new int[m + 1][n + 1];
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            
            // Backtrack to find the actual LCS
            int maxLen = dp[m][n];
            char[] lcs = new char[maxLen];
            int i = m, j = n, pos = maxLen - 1;
            
            while (i > 0 && j > 0) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    lcs[pos--] = text1.charAt(i - 1);
                    i--;
                    j--;
                } else if (dp[i - 1][j] > dp[i][j - 1]) {
                    // ignore s1[i-1]
                    i--;
                } else {
                    // ignore s2[j-1]
                    j--;
                }
            }
            
            return new String(lcs);
        }
    }

    class Followup_ReturnAllLCS {
        public List<String> allLCS(String text1, String text2) {
            int m = text1.length();
            int n = text2.length();
            int[][] dp = new int[m + 1][n + 1];
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            
            // Backtrack to find all LCS
            Set<String> lcss = new HashSet<>();
            backtrack(text1, text2, dp, m, n, new StringBuilder(), lcss);
            return new ArrayList<>(lcss);
        }

        private void backtrack(String text1, String text2, int[][] dp, 
                                int i, int j, StringBuilder path, Set<String> lcss) {
            if (i == 0 || j == 0) {
                lcss.add(new StringBuilder(path).reverse().toString());
                return;
            }
            
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // Character is part of LCS
                backtrack(text1, text2, dp, i - 1, j - 1, 
                            path.append(text1.charAt(i - 1)), lcss);
            } else {
                // Explore both paths if they lead to same length
                if (dp[i - 1][j] == dp[i][j]) {
                    backtrack(text1, text2, dp, i - 1, j, path, lcss);
                }
                if (dp[i][j - 1] == dp[i][j]) {
                    backtrack(text1, text2, dp, i, j - 1, path, lcss);
                }
            }
        }
    }

    class Followup_LongestCommonSubstring {
        // Recurrence: dp[i][j] = dp[i-1][j-1] + 1 if s1[i-1] == s2[j-1]; 0 s1[i-1] ≠ s2[j-1]
        // we can reduce the space to 1D if further optimized
        public int longestCommonSubstring(String text1, String text2) {
            int m = text1.length();
            int n = text2.length();
            
            // dp[i][j] = length of longest common substring ENDING at i-1 and j-1
            int[][] dp = new int[m + 1][n + 1];
            int maxLen = 0;
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        maxLen = Math.max(maxLen, dp[i][j]);
                    } else {
                        dp[i][j] = 0;  // Must be contiguous
                    }
                }
            }
            
            return maxLen;
        }

        // Below is a better approach with O(1) space using diagonal traversal
        public int longestCommonSubstring_BF(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            int maxLen = 0;

            // diagonals starting from s1
            for (int i = 0; i < m; i++) {
                int x = i, y = 0, len = 0;
                while (x < m && y < n) {
                    if (s1.charAt(x) == s2.charAt(y)) {
                        len++;
                        maxLen = Math.max(maxLen, len);
                    } else {
                        len = 0;
                    }
                    x++;
                    y++;
                }
            }

            // diagonals starting from s2 (skip 0,0 diagonal)
            for (int j = 1; j < n; j++) {
                int x = 0, y = j, len = 0;
                while (x < m && y < n) {
                    if (s1.charAt(x) == s2.charAt(y)) {
                        len++;
                        maxLen = Math.max(maxLen, len);
                    } else {
                        len = 0;
                    }
                    x++;
                    y++;
                }
            }

            return maxLen;
        }
    }
}
