/**
 * 712. Minimum ASCII Delete Sum for Two Strings
 * 
 * Followup:
 * - Return actual characters deleted?
 */
public class _712 {
    /**
     * State: dp[i][j] = minimum ASCII delete sum to make s1[0...i-1] 
                  and s2[0...j-1] equal
     * 
     * Recurrence:
     *  If s1[i-1] == s2[j-1]:
     *  → Characters match, no deletion needed
     *  → dp[i][j] = dp[i-1][j-1]
     * 
     *  If s1[i-1] != s2[j-1]:
     *  → Two choices:
     *      a) Delete s1[i-1]: dp[i-1][j] + ascii(s1[i-1])
     *      b) Delete s2[j-1]: dp[i][j-1] + ascii(s2[j-1])
     *  → dp[i][j] = min(delete from s1, delete from s2)
     * 
     *  Base cases: [IMPORTANT]
     *  dp[0][j] = sum of ASCII values of s2[0...j-1]
     *  dp[i][0] = sum of ASCII values of s1[0...i-1]
     * 
     *  Time: O(m × n)
     *  Space: O(m × n) or O(n) optimized
     */
    class Solution1_DP_2D {
        public static int minimumDeleteSum(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            int[][] dp = new int[m + 1][n + 1]; // min delete to make s1[0..i-1] == s2[0..j-1]
            for (int i = 1; i <= m; i++) {
                dp[i][0] = dp[i - 1][0] + (int) s1.charAt(i - 1);
            }

            for (int j = 1; j <= n; j++) {
                dp[0][j] = dp[0][j - 1] + (int) s2.charAt(j - 1);
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        int deleteS1 = dp[i - 1][j] + (int) s1.charAt(i - 1);
                        int deleteS2 = dp[i][j - 1] + (int) s2.charAt(j - 1);
                        dp[i][j] = Math.min(deleteS1, deleteS2);
                    }
                }
            }
            return dp[m][n];
        }
    }

    class Solution2_DP_1D {
        public int minimumDeleteSum(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            if (n > m) return minimumDeleteSum(s2, s1);
            int[] dp = new int[n + 1]; // min delete to make s1[0..i-1] == s2[0..j-1]
            // for (int i = 1; i <= m; i++) {
            //     dp[i][0] = dp[i - 1][0] + (int) s1.charAt(i - 1);
            // }
            for (int j = 1; j <= n; j++) {
                dp[j] = dp[j - 1] + (int) s2.charAt(j - 1);
            }

            for (int i = 1; i <= m; i++) {
                int prev = dp[0]; // dp[i-1][j-1]
                dp[0] += (int) s1.charAt(i - 1); // dp[i][j-1]
                for (int j = 1; j <= n; j++) {
                    int temp = dp[j]; // dp[i-1][j]
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[j] = prev;
                    } else {
                        int deleteS1 = dp[j] + (int) s1.charAt(i - 1);
                        int deleteS2 = dp[j - 1] + (int) s2.charAt(j - 1);
                        dp[j] = Math.min(deleteS1, deleteS2);
                    }
                    prev = temp;
                }
            }
            return dp[n];
        }
    }

    class Followup_Recursion_WithMemo {
        public int minimumDeleteSum(String s1, String s2) {
            Integer[][] memo = new Integer[s1.length()][s2.length()];
            return helper(s1, s2, 0, 0, memo);
        }

        private int helper(String s1, String s2, int i, int j, Integer[][] memo) {
            // Base cases
            if (i == s1.length()) {
                // Delete all remaining in s2
                int sum = 0;
                for (int k = j; k < s2.length(); k++) {
                    sum += s2.charAt(k);
                }
                return sum;
            }
            
            if (j == s2.length()) {
                // Delete all remaining in s1
                int sum = 0;
                for (int k = i; k < s1.length(); k++) {
                    sum += s1.charAt(k);
                }
                return sum;
            }
            
            // Check memo
            if (memo[i][j] != null) {
                return memo[i][j];
            }
            
            int result;
            if (s1.charAt(i) == s2.charAt(j)) {
                // Characters match, no deletion
                result = helper(s1, s2, i + 1, j + 1, memo);
            } else {
                // Try deleting from either string
                int deleteS1 = s1.charAt(i) + helper(s1, s2, i + 1, j, memo);
                int deleteS2 = s2.charAt(j) + helper(s1, s2, i, j + 1, memo);
                result = Math.min(deleteS1, deleteS2);
            }
            
            memo[i][j] = result;
            return result;
        }
    }

    class Followup_ReturnDeletedChar {
        public String[] minimumDeleteSequence(String s1, String s2) {
            int m = s1.length();
            int n = s2.length();
            
            int[][] dp = new int[m + 1][n + 1];
            
            // Build DP table (same as before)
            for (int j = 1; j <= n; j++) {
                dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);
            }
            
            for (int i = 1; i <= m; i++) {
                dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
            }
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        int deleteS1 = dp[i - 1][j] + s1.charAt(i - 1);
                        int deleteS2 = dp[i][j - 1] + s2.charAt(j - 1);
                        dp[i][j] = Math.min(deleteS1, deleteS2);
                    }
                }
            }
            
            // Backtrack to find deleted characters
            StringBuilder deletedS1 = new StringBuilder();
            StringBuilder deletedS2 = new StringBuilder();
            
            int i = m, j = n;
            
            while (i > 0 || j > 0) {
                if (i == 0) {
                    // Delete from s2
                    deletedS2.append(s2.charAt(j - 1));
                    j--;
                } else if (j == 0) {
                    // Delete from s1
                    deletedS1.append(s1.charAt(i - 1));
                    i--;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Match, no deletion
                    i--;
                    j--;
                } else {
                    int deleteS1Cost = dp[i - 1][j] + s1.charAt(i - 1);
                    // int deleteS2Cost = dp[i][j - 1] + s2.charAt(j - 1);
                    
                    if (dp[i][j] == deleteS1Cost) {
                        // Deleted from s1
                        deletedS1.append(s1.charAt(i - 1));
                        i--;
                    } else {
                        // Deleted from s2
                        deletedS2.append(s2.charAt(j - 1));
                        j--;
                    }
                }
            }
            
            return new String[] {
                deletedS1.reverse().toString(),
                deletedS2.reverse().toString()
            };
        }
    }

    class Followup_MaxKeptCharASCIISum {
        public int maximumKeptSum(String s1, String s2) {
            int totalSum = 0;
            for (char c : s1.toCharArray()) totalSum += c;
            for (char c : s2.toCharArray()) totalSum += c;
            
            int minDeleteSum = Solution1_DP_2D.minimumDeleteSum(s1, s2);
            
            return totalSum - minDeleteSum;
        }

        // Or solve directly with modified DP:
        public int maximumKeptSum2(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            
            // dp[i][j] = max ASCII sum of common subsequence
            int[][] dp = new int[m + 1][n + 1];
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        // Keep this character (counts twice: once from each string)
                        dp[i][j] = dp[i - 1][j - 1] + 2 * s1.charAt(i - 1);
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            
            return dp[m][n];
        }
    }
}
