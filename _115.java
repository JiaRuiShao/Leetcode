import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * 115. Distinct Subsequences
 */
public class _115 {
    // Time: O(m^n) where m is sp len and n is tp len
    // Space: O(n)
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
    class Solution0_Backtrack_By_T_Improved_Memo_TLE { // By Box
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

    class Solution0_Backtrack_By_T_Memo_TLE {
        public int numDistinct(String s, String t) {
            Map<String, Integer> memo = new HashMap<>();
            return backtrack(s, 0, t, 0, memo);
        }

        // backtrack by t
        private int backtrack(String s, int sp, String t, int st, Map<String, Integer> memo) {
            if (st == t.length()) return 1;
            if (sp == s.length()) return 0;
            String state = sp + "#" + st;
            if (memo.containsKey(state)) return memo.get(state);
            int subseq = 0;
            for (int i = sp; i < s.length(); i++) {
                if (t.charAt(st) != s.charAt(i)) continue;
                subseq += backtrack(s, i + 1, t, st + 1, memo);
            }
            memo.put(state, subseq);
            return subseq;
        }
    }

    // Iterative DP from bottom up
    // dp(s, i, t, j) = SUM{dp(s, k + 1, t, j + 1)} where k >= i and s[k] == t[j]
    class Solution0_By_T_Bottom_Up_DP_TLE { // By Box
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

    class Solution0_Backtrack_By_S { // By Ball
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
    class Solution1_Backtrack_By_S_Improved_Memo { // By Ball
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

    class Solution1_Backtrack_By_S_With_Memo { // By Ball {
        public int numDistinct(String s, String t) {
            Map<String, Integer> memo = new HashMap<>();
            return backtrack(s, 0, t, 0, memo);
        }

        private int backtrack(String s, int sp, String t, int tp, Map<String, Integer> memo) {
            if (tp == t.length()) return 1;
            if (sp == s.length()) return 0;
            String state = sp + "#" + tp;
            if (memo.containsKey(state)) return memo.get(state);
            int subseq = 0;
            // ignore curr char in s
            subseq += backtrack(s, sp + 1, t, tp, memo);
            // select curr char in s
            if (s.charAt(sp) == t.charAt(tp)) {
                subseq += backtrack(s, sp + 1, t, tp + 1, memo);
            }
            memo.put(state, subseq);
            return subseq;
        }
    }
    
    // s: babgbag
    // t: bag
    class Solution2_DP {
        // dp[i][j] = number of ways to form t[0..i-1] using s[0..j-1]
        // dp[0][0..m] = 1
        // dp[1..n][0] = 0
        // dp[i][j] = dp[i][j-1] if s[j-1] != t[i-1]
        //          = dp[i][j-1] + dp[i-1][j-1] else
        public int numDistinct(String s, String t) {
            int m = s.length(), n = t.length();
            int[][] dp = new int[n + 1][m + 1]; // ways to form t[0..i-1] using first j chars
            for (int j = 0; j <= m; j++) {
                dp[0][j] = 1;
            }
            for (int i = 1; i <= n; i++) {
                char tc = t.charAt(i - 1);
                for (int j = 1; j <= m; j++) {
                    char sc = s.charAt(j - 1);
                    dp[i][j] = dp[i][j-1];
                    if (sc == tc) {
                        dp[i][j] += dp[i-1][j-1];
                    }
                }
            }
            return dp[n][m];
        }
    }

    class Solution2_DP_SpaceOptimized {
        public int numDistinct(String s, String t) {
            int m = s.length(), n = t.length();
            int[] dp = new int[m + 1];
            for (int j = 0; j <= m; j++) {
                dp[j] = 1;
            }
            for (int i = 1; i <= n; i++) {
                int prev = dp[0];
                dp[0] = 0;
                for (int j = 1; j <= m; j++) {
                    int temp = dp[j];
                    dp[j] = dp[j-1];
                    if (t.charAt(i - 1) == s.charAt(j - 1)) {
                        dp[j] += prev;
                    }
                    prev = temp;
                }
            }
            return dp[m];
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
