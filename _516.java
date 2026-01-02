/**
 * 516. Longest Palindromic Subsequence
 * 
 * Followup:
 * - Return actual LPS
 * - Return Longest Palindromic Substring (LPSubstring)?
 * - Min deletions/insertions to make string palindrome? LC 1312 n - LPS(s)
 */
public class _516 {
    // dp[i][j] = len of the longest palindromic subsequence in s[i..j]
    // recurrence: dp[i][j] = dp[i+1][j-1] + 2            if s[i] == s[j]
    //                      = max(dp[i+1][j], dp[i][j-1]) if s[i] != s[j]
    // base case: dp[i][j] = 1 when i == j
    //            dp[i][j] = 0 when i > j
    class Solution1_LPS_DP_2D {
        // there're two ways to traverse
        // 1 - traverse bottom left to top right
        public int longestPalindromeSubseq1(String s) {
            int n = s.length();
            int[][] dp = new int[n][n];
            for (int i = 0; i < n; i++) dp[i][i] = 1;

            for (int i = n - 1; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) { // j > i
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    } else {
                        dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }
            return dp[0][n - 1];
        }

        // 2 - diagonal traversal from top-left to bottom-right
        // gap = j - 1, len(i..j) = gap + 1
        public int longestPalindromeSubseq2(String s) {
            int n = s.length();
            int[][] dp = new int[n][n];

            // gap = 0 (single characters)
            for (int i = 0; i < n; i++) dp[i][i] = 1;

            // gap from 1 to n - 1
            for (int gap = 0; gap < n; gap++) {
                for (int i = 0; i + gap < n; i++) {
                    int j = i + gap;
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    } else {
                        dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }

            return dp[0][n - 1];
        }
    }

    // Time: O(n^2)
    // Space: O(n)
    class Solution2_LPS_DP_1D {
        public int longestPalindromeSubseq(String s) {
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

    // Time: O(n^2)
    // Space: O(n^2)
    class Solution3_Recursion_WithMemo {
        public int longestPalindromeSubseq(String s) {
            Integer[][] memo = new Integer[s.length()][s.length()];
            return dp(s, 0, s.length() - 1, memo);
        }

        private int dp(String s, int i, int j, Integer[][] memo) {
            if (i > j) return 0;
            if (i == j) return 1;
            if (memo[i][j] != null) return memo[i][j];
            
            int result;
            if (s.charAt(i) == s.charAt(j)) {
                result = dp(s, i + 1, j - 1, memo) + 2;
            } else {
                int excludeLeft = dp(s, i + 1, j, memo);
                int excludeRight = dp(s, i, j - 1, memo);
                result = Math.max(excludeLeft, excludeRight);
            }
            memo[i][j] = result;
            return result;
        }
    }

    class Followup_ReturnLPS {
        // we use char[] here to build LPS instead of StringBuilder
        // If we use StringBuilder, left SB would append at end which takes O(1) time;
        // however for right SB, we need to insert at front for each char, which takes O(n) time
        public String longestPalindromeSubseqString(String s) {
            int n = s.length();
            int[][] dp = new int[n][n];
            // build DP table (logic ignored)

            int len = dp[0][n - 1];
            char[] result = new char[len];
            int left = 0, right = len - 1;
            int i = 0, j = n - 1;

            while (i <= j) {
                if (s.charAt(i) == s.charAt(j)) {
                    result[left++] = s.charAt(i);
                    result[right--] = s.charAt(j);
                    i++;
                    j--;
                } else if (dp[i + 1][j] > dp[i][j - 1]) {
                    i++;
                } else {
                    j--;
                }
            }

            return new String(result);
        }
    }

    class Followup_LPSubstring {
        // expected standard solution to expand outward from curr char to find longest odd/even palindrome
        // Time: O(n^2)
        // Space: O(1)
        public String longestPalindromeSubstring(String s) {
            if (s == null || s.length() < 2) return s;

            int start = 0, maxLen = 1;

            for (int i = 0; i < s.length(); i++) {
                // odd-length palindrome
                int len1 = expand(s, i, i);
                // even-length palindrome
                int len2 = expand(s, i, i + 1);

                int len = Math.max(len1, len2);

                if (len > maxLen) {
                    maxLen = len;
                    start = i - (len - 1) / 2;
                }
            }

            return s.substring(start, start + maxLen);
        }

        private int expand(String s, int left, int right) {
            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            return right - left - 1;
        }

        // below is a DP solution to this problem for educational purpose
        // Time: O(n^2)
        // Space: O(n^2)
        public int longestPalindromeSubstring_DP(String s) {
            int n = s.length();
            boolean[][] dp = new boolean[n][n]; // if s[i..j] is a palindrome
            int maxLen = 1;
            // base case: single character is a palindrome
            for (int i = 0; i < n; i++) {
                dp[i][i] = true;
            }
            
            // traverse diagonally from top left to bottom right
            for (int gap = 0; gap < n; gap++) {
                for (int i = 0; i + gap < n; i++) {
                    int j = i + gap;
                    
                    if (s.charAt(i) == s.charAt(j)) {
                        if (gap == 1) {
                            dp[i][j] = true;
                        } else {
                            dp[i][j] = dp[i + 1][j - 1];
                        }
                        
                        if (dp[i][j]) {
                            maxLen = gap + 1; // need to use max() to compare because gap can only grow larger
                        }
                    }
                    // substring needs to be contiguous, must reset if not match
                    // else dp[i][j] = false;
                }
            }
            
            return maxLen;
        }
    }
}
