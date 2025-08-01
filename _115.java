import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * 115. Distinct Subsequences
 */
public class _115 {
    class Solution0_Backtrack_By_T_TLE {
        private int distinct;
        public int numDistinct(String s, String t) {
            distinct = 0;
            backtrack(s, 0, t, 0);
            return distinct;
        }

        private void backtrack(String s, int sp, String t, int tp) {
            if (tp == t.length()) {
                distinct++;
                return;
            }

            for (int i = sp; i < s.length(); i++) {
                if (s.charAt(i) != t.charAt(tp)) continue;
                backtrack(s, i + 1, t, tp + 1);
            }
        }
    }

    // Time: O(mn*m) = O(m^2n)
    // still got TLE
    class Solution0_Backtrack_By_T_Improved_Memo_TLE {
        private int[][] memo;
        public int numDistinct(String s, String t) {
            memo = new int[s.length()][t.length()];
            for (int i = 0; i < s.length(); i++) {
                Arrays.fill(memo[i], -1);
            }
            return backtrack(s, 0, t, 0, memo);
        }

        private int backtrack(String s, int sp, String t, int tp, int[][] memo) {
            if (tp == t.length()) {
                return 1;
            }
            if (sp == s.length()) {
                return 0;
            }

            if (memo[sp][tp] != -1) return memo[sp][tp];
            memo[sp][tp] = 0;
            for (int i = sp; i < s.length(); i++) {
                if (s.charAt(i) != t.charAt(tp)) continue;
                memo[sp][tp] += backtrack(s, i + 1, t, tp + 1, memo);
            }
            return memo[sp][tp];
        }
    }

    // Iterative DP from bottom up
    // dp(s, i, t, j) = SUM{dp(s, k + 1, t, j + 1)} where k >= i and s[k] == t[j]
    class Solution0_By_T_Bottom_Up_DP_TLE {
        // s: babgbag
        // t: bag
        public int numDistinct(String s, String t) {
            int m = s.length(), n = t.length();
            int[][] memo = new int[m + 1][n + 1];
            for (int i = 0; i <= m; i++) {
                memo[i][n] = 1;
            }
            for (int j = n - 1; j >= 0; j--) { // tp
                char ct = t.charAt(j);
                for (int i = m - 1; i >= 0; i--) { // sp
                    for (int k = i; k < m; k++) {
                        if (s.charAt(k) == ct) {
                             memo[i][j] += memo[k + 1][j + 1];
                        }
                    }
                }
            }
            return memo[0][0];
        }
    }

    class Solution0_Backtrack_By_S {
        private int distinct;
        public int numDistinct(String s, String t) {
            distinct = 0;
            backtrack(s, 0, t, 0);
            return distinct;
        }

        private void backtrack(String s, int sp, String t, int tp) {
            if (tp == t.length()) {
                distinct++;
                return;
            }
            if (sp == s.length()) {
                return;
            }

            if (s.charAt(sp) == t.charAt(tp)) {
                backtrack(s, sp + 1, t, tp + 1); // choose to use current char
            }
            backtrack(s, sp + 1, t, tp); // choose not to use current char (current char in s could be == or != target char in t)
        }
    }
    
    // Time: O(mn)
    class Solution1_Backtrack_By_S_Improved_Memo {
        public int numDistinct(String s, String t) {
            int[][] memo = new int[s.length()][t.length()];
            for (int i = 0; i < s.length(); i++) {
                Arrays.fill(memo[i], -1);
            }
            return backtrack(s, 0, t, 0, memo);
        }

        private int backtrack(String s, int sp, String t, int tp, int[][] memo) {
            if (tp == t.length()) {
                return 1;
            }
            if (sp == s.length()) {
                return 0;
            }
            if (memo[sp][tp] != -1) return memo[sp][tp];
            memo[sp][tp] = 0;
            if (s.charAt(sp) == t.charAt(tp)) {
                memo[sp][tp] += backtrack(s, sp + 1, t, tp + 1, memo);
            }
            memo[sp][tp] += backtrack(s, sp + 1, t, tp, memo);
            return memo[sp][tp];
        }
    }
    
    // s: babgbag
    // t: bag
    // dp(s, i, t, j) = dp(s, i + 1, t, j)                          if s[i] != t[j]
    // dp(s, i, t, j) = dp(s, i + 1, t, j) + dp(s, i + 1, t, j + 1) if s[i] == t[j]
    class Solution2_By_S_Bottom_Up_DP {
        public int numDistinct(String s, String t) {
            int m = s.length(), n = t.length();
            int[][] memo = new int[m + 1][n + 1];
            for (int i = 0; i <= m; i++) {
                memo[i][n] = 1;
            }
            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    memo[i][j] = memo[i + 1][j];
                    if (s.charAt(i) == t.charAt(j)) {
                        memo[i][j] += memo[i + 1][j + 1];
                    }
                }
            }
            return memo[0][0];
        }
    }

    // dp(s, i, t, j) = dp(s, i + 1, t, j) + (s[i] == t[j] ? dp(s, i + 1, t, j + 1) : 0)
    // from this equation we can see we only need next row (i + 1)
    class Solution2_By_S_Bottom_Up_DP_Improved {
        public int numDistinct(String s, String t) {
            int m = s.length(), n = t.length();
            int[] memo = new int[n];
            for (int i = m - 1; i >= 0; i--) {
                int prevJ = 1; // initialize memo[i][n] = 1
                for (int j = n - 1; j >= 0; j--) {
                    int currJ = memo[j]; // memo[i+1][j]
                    if (s.charAt(i) == t.charAt(j)) {
                        memo[j] += prevJ;
                    }
                    prevJ = currJ; // memo[i+1][j+1]
                }
            }
            return memo[0];
        }
    }
        
    @Test
    public void example1_babgbag_to_bag() {
        String s = "babgbag";
        String t = "bag";
        // From LeetCode example: there are 5 distinct subsequences of "babgbag" that spell "bag"
        assertEquals(5, new _115().new Solution1_Backtrack_By_S_Improved_Memo().numDistinct(s, t));
    }

    @Test
    public void example2_allAs_longStrings() {
        // simulate the “very long” all-'a' strings from your second example:
        // here we pick some large N; in your real test you can match the exact lengths
        int N = 500;      
        String s = "a".repeat(N);
        String t = "a".repeat(N);
        // when s and t are identical, there's exactly one way to match them as a subsequence
        assertEquals(1, new _115().new Solution1_Backtrack_By_S_Improved_Memo().numDistinct(s, t));
    }
}
