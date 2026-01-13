import java.util.ArrayList;
import java.util.List;

/**
 * 131. Palindrome Partitioning
 */
public class _131 {
    // Time: O(n*2^n)
    // Space: O(n)
    class Solution1_Backtrack_Combination_ElemUsedOnce_NoDedup {
        // we need to use all chars in the given String
        // combination -- no dedup -- elem used once
        // example:
        // s = "aaab"
        // output = [["a","a","a","b"],["a","aa","b"],["aa","a","b"],["aaa","b"]]
        public List<List<String>> partition(String s) {
            List<List<String>> partitions = new ArrayList<>();
            backtrack(s, 0, new ArrayList<>(), partitions);
            return partitions;
        }

        private void backtrack(String s, int start, List<String> path, List<List<String>> res) {
            if (start == s.length()) {
                res.add(new ArrayList<>(path));
                return;
            }
            for (int end = start + 1; end <= s.length(); end++) {
                String partition = s.substring(start, end);
                if (!isPalindrome(partition)) {
                    continue;
                }
                path.add(partition);
                backtrack(s, end, path, res);
                path.remove(path.size() - 1);
            }
        }

        private boolean isPalindrome(String s) {
            int lo = 0, hi = s.length() - 1;
            while (lo < hi) {
                if (s.charAt(lo) != s.charAt(hi)) {
                    return false;
                }
                lo++;
                hi--;
            }
            return true;
        }
    }

    class Solution1_Backtrack_WithMemo {
        public List<List<String>> partition(String s) {
            boolean[][] dp = buildDP(s);
            List<List<String>> partitions = new ArrayList<>();
            backtrack(s, 0, new ArrayList<>(), partitions, dp);
            return partitions;
        }

        private boolean[][] buildDP(String s) {
            int n = s.length();
            boolean[][] dp = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                dp[i][i] = true;
            }
            // j >= i
            // dp[i][j] = dp[i+1][j-1] if s[i] == s[j]
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = (j == i + 1) || dp[i+1][j-1];
                    }
                }
            }
            return dp;
        }

        private void backtrack(String s, int start, List<String> path, List<List<String>> res, boolean[][] dp) {
            if (start == s.length()) {
                res.add(new ArrayList<>(path));
                return;
            }
            for (int end = start + 1; end <= s.length(); end++) {
                String partition = s.substring(start, end);
                if (!dp[start][end - 1]) {
                    continue;
                }
                path.add(partition);
                backtrack(s, end, path, res, dp);
                path.remove(path.size() - 1);
            }
        }
    }
}
