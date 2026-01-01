import java.util.Arrays;

/**
 * 72. Edit Distance
 * 
 * - S1: Recursion without memo O(3^(m+n)), O(m+n)
 * - S2: Recursion with memo O(mn), O(mn)
 * - S3: Iterative DP 2D O(mn), O(mn)
 * - S4: Iterative DP 1D O(mn), O(n)
 * 
 * Clarification:
 * - Is case sensitive? Yes
 * - What's the range? 0 ≤ word1.length, word2.length ≤ 500
 * 
 * Followup:
 * - Explain three operations in the recurrence? 
 * At position (i, j), comparing word1[i-1] and word2[j-1]:
 * 1. REPLACE: dp[i-1][j-1] + 1
 *    - Replace word1[i-1] with word2[j-1]
 *    - Then convert word1[0...i-2] to word2[0...j-2]
 *    - Example: "horse" → "ros"
 *      At (1,1): replace 'h' with 'r' → "rorse"
 * 2. DELETE: dp[i-1][j] + 1
 *    - Delete word1[i-1]
 *    - Then convert word1[0...i-2] to word2[0...j-1]
 *    - Example: "horse" → "ros"
 *      At (2,1): delete 'o' → "hrse"
 * 3. INSERT: dp[i][j-1] + 1
 *    - Insert word2[j-1] into word1
 *    - Then convert word1[0...i-1] to word2[0...j-2]
 *    - Equivalent to: delete from word2
 *    - Example: "ros" → "horse"
 *      At (1,2): insert 'o' after 'r' → "roos"
 * 
 * - Return actual sequence of operations? Use char[] opr to track and then backtrack to find operations
 * - What if operations have different costs?
 * - What if we can swap adjacent characters?
 */
public class _72 {
    // Time: O(3^(m+n))
    // Space: O(m+n)
    class Solution0_Recursion {
        public int minDistance(String word1, String word2) {
            return backtrack(word1.toCharArray(), word1.length() - 1, word2.toCharArray(), word2.length() - 1);
        }

        private int backtrack(char[] arr1, int i, char[] arr2, int j) {
            if (i == -1) return j + 1;
            if (j == -1) return i + 1;
            if (arr1[i] == arr2[j]) return backtrack(arr1, i - 1, arr2, j - 1);
            int minOps = backtrack(arr1, i, arr2, j - 1) + 1; // insert at i with arr2[j]
            minOps = Math.min(minOps, backtrack(arr1, i - 1, arr2, j) + 1); // delete arr[i]
            minOps = Math.min(minOps, backtrack(arr1, i - 1, arr2, j - 1) + 1); // replace at i with arr2[j]
            return minOps;
        }

        // another way to implement, iterate from left to right
        private int backtrack2(char[] arr1, int i, char[] arr2, int j) {
            if (i == arr1.length) return arr2.length - j;
            if (j == arr2.length) return arr1.length - i;
            if (arr1[i] == arr2[j]) return backtrack(arr1, i + 1, arr2, j + 1);
            int minOps = backtrack(arr1, i, arr2, j + 1) + 1; // insert at i with arr2[j]
            minOps = Math.min(minOps, backtrack(arr1, i + 1, arr2, j) + 1); // delete arr[i]
            minOps = Math.min(minOps, backtrack(arr1, i + 1, arr2, j + 1) + 1); // replace at i with arr2[j]
            return minOps;
        }
    }

    // Time: O(mn)
    // Space: O(mn)
    class Solution1_Recursion_WithMemo {
        public int minDistance(String word1, String word2) {
            int[][] memo = new int[word1.length()][word2.length()];
            for (int i = 0; i < word1.length(); i++) {
                Arrays.fill(memo[i], -1);
            }
            return backtrack(word1, 0, word2, 0, memo);
        }

        private int backtrack(String s1, int i, String s2, int j, int[][] memo) {
            if (i == s1.length()) return s2.length() - j;
            if (j == s2.length()) return s1.length() - i;
            if (memo[i][j] != -1) return memo[i][j];
            if (s1.charAt(i) == s2.charAt(j)) return backtrack(s1, i + 1, s2, j + 1, memo);
            int minOps = backtrack(s1, i, s2, j + 1, memo) + 1; // insert at i with arr2[j]
            minOps = Math.min(minOps, backtrack(s1, i + 1, s2, j, memo) + 1); // delete arr[i]
            minOps = Math.min(minOps, backtrack(s1, i + 1, s2, j + 1, memo) + 1); // replace at i with arr2[j]
            return memo[i][j] = minOps;
        }
    }

    // Time: O(mn)
    // Space: O(mn)
    class Solution2_DP_2D {
        public int minDistance(String word1, String word2) {
            int m = word1.length(), n = word2.length();
            int[][] dp = new int[m + 1][n + 1]; // min ops to convert word1[0...i-1] to word2[0...j-1]
            // dp[0][0..n] = j -- word1 empty
            // dp[m][0] = i -- word2 empty
            for (int j = 0; j <= n; j++) {
                dp[0][j] = j;
            }
            for (int i = 0; i <= m; i++) {
                dp[i][0] = i;
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = 1 + min(
                            dp[i][j - 1], // insert
                            dp[i - 1][j], // delete
                            dp[i - 1][j - 1] // replace
                            );
                    }
                }
            }
            return dp[m][n];
        }

        private int min(int a, int b, int c) {
            return Math.min(a, Math.min(b, c));
        }
    }

    // Time: O(mn)
    // Space: O(n)
    class Solution3_DP_1D {
        // dp[i][j] depends on dp[i-1][j-1], dp[i-1][j], & dp[i][j-1]
        // we can either keep two rows or keep one row with dp[i-1][j-1] value saved
        public int minDistance(String word1, String word2) {
            int m = word1.length(), n = word2.length();
            int[] prev = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                prev[j] = j;
            }

            for (int i = 1; i <= m; i++) {
                int[] curr = new int[n + 1];
                curr[0] = i;
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        curr[j] = prev[j - 1];
                    } else {
                        curr[j] = 1 + min(
                            curr[j - 1], // insert
                            prev[j], // delete
                            prev[j - 1] // replace
                            );
                    }
                }
                prev = curr;
            }
            return prev[n];
        }

        // below solution uses one row only, which makes the logic harder to track
        // draw the dp matrix to help decide if and what default value we should have
        public int minDistance2(String word1, String word2) {
            int m = word1.length(), n = word2.length();
            int[] dp = new int[n + 1]; // min ops to convert word1[0...i-1] to word2[0...i-1]
            for (int j = 0; j <= n; j++) {
                dp[j] = j;
            }

            for (int i = 1; i <= m; i++) {
                int prev = i - 1;
                for (int j = 0; j <= n; j++) {
                    int temp = dp[j]; // dp[i-1][j-1]
                    if (j > 0 && word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[j] = prev;
                    } else {
                        dp[j] = 1 + min(
                            j == 0 ? i : dp[j - 1], // insert
                            dp[j], // delete
                            prev // replace
                            );
                    }
                    prev = temp;
                }
            }
            return dp[n];
        }

        private int min(int a, int b, int c) {
            return Math.min(a, Math.min(b, c));
        }
    }

    class Followup_OperationsWithCosts {
        public int minDistanceWithCosts(String word1, String word2, 
                                        int replaceCost, int deleteCost, int insertCost) {
            int m = word1.length();
            int n = word2.length();
            
            int[][] dp = new int[m + 1][n + 1];
            
            // Base cases
            for (int j = 0; j <= n; j++) {
                dp[0][j] = j * insertCost;
            }
            
            for (int i = 0; i <= m; i++) {
                dp[i][0] = i * deleteCost;
            }
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        int replace = dp[i - 1][j - 1] + replaceCost;
                        int delete = dp[i - 1][j] + deleteCost;
                        int insert = dp[i][j - 1] + insertCost;
                        
                        dp[i][j] = Math.min(replace, Math.min(delete, insert));
                    }
                }
            }
            
            return dp[m][n];
        }
    }

    class Followup_WithSwap {
        public int minDistanceWithSwap(String word1, String word2) {
            int m = word1.length();
            int n = word2.length();
            
            int[][] dp = new int[m + 1][n + 1];
            
            for (int i = 0; i <= m; i++) dp[i][0] = i;
            for (int j = 0; j <= n; j++) dp[0][j] = j;
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        int replace = dp[i - 1][j - 1] + 1;
                        int delete = dp[i - 1][j] + 1;
                        int insert = dp[i][j - 1] + 1;
                        dp[i][j] = Math.min(replace, Math.min(delete, insert));
                    }
                    
                    // Check for swap
                    if (i > 1 && j > 1 &&
                        word1.charAt(i - 1) == word2.charAt(j - 2) &&
                        word1.charAt(i - 2) == word2.charAt(j - 1)) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + 1);
                    }
                }
            }
            
            return dp[m][n];
        }
    }
}
