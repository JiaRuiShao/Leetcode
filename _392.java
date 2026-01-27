import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 392. Is Subsequence
 * 
 * Clarification:
 * - What to return if s is empty?
 * 
 * Followup:
 * - Large t and need to check if many strings are subseq of t? Pre-build char freq map for t; then use BS to find corresponding index
 * - Count num of distinct subseq of t equals s? LC 115, DP
 * - Given two strings `s1` and `s2`, find the shortest string that has both `s1` and `s2` as subsequences? LC 1092 LCS DP + reconstruct
 * - Find longest common subsequence between s and t? LC 128
 */
public class _392 {
    // Time: O(n)
    // Space: O(1)
    class Solution1_TwoPointers {
        public boolean isSubsequence(String s, String t) {
            if (t.length() < s.length()) {
                return false;
            }
    
            int ps = 0, pt = 0;
            while (ps < s.length() && pt < t.length()) {
                if (s.charAt(ps) == t.charAt(pt)) ps++;
                pt++;
            }
            return ps == s.length();
        }
    }

    // Time: O(n)
    // Space: O(n)
    class Solution2_Recursion {
        public boolean isSubsequence(String s, String t) {
            return helper(s, t, 0, 0);
        }
        
        private boolean helper(String s, String t, int i, int j) {
            // Base cases
            if (i == s.length()) {
                return true;  // Matched all of s
            }
            if (j == t.length()) {
                return false;  // Ran out of t without matching all of s
            }
            
            // If characters match, advance both pointers
            if (s.charAt(i) == t.charAt(j)) {
                return helper(s, t, i + 1, j + 1);
            }
            
            // Otherwise, only advance t pointer
            return helper(s, t, i, j + 1);
        }
    }

    // Time: O(n+mlogn)
    // Space: O(n)
    class Followup_MultipleS {
        public boolean isSubsequence(String s, String t) {
            Map<Character, List<Integer>> indices = new HashMap<>();
            for (int i = 0; i < t.length(); i++) {
                indices.computeIfAbsent(t.charAt(i), k -> new ArrayList<>()).add(i);
            }

            int prevIdx = -1; // leftmost index in t for last char in s
            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                if (!indices.containsKey(c)) {
                    return false;
                }
                // find next occurrence after prevIdx
                int currIdx = findSmallestIndexGTE(indices.get(c), prevIdx + 1);
                if (currIdx == -1) {
                    return false;
                }
                prevIdx = currIdx;
            }
            return true;
        }

        private int findSmallestIndexGTE(List<Integer> indices, int k) {
            int lo = 0, hi = indices.size() - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int index = indices.get(mid);
                if (index == k) {
                    return index;
                } else if (index < k) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            return lo == indices.size() ? -1 : indices.get(lo);
        }
    }

    class Followup_CountSebseq {
        // Recurrence:
        // dp[i][j] += dp[i-1][j-1] if s[i-1] matches t[j-1]
        //           = dp[i][j-1] not use this char
        public int countSubseq(String s, String t) {
            int m = s.length(), n = t.length();
            // dp[i][j]: ways to form s[0..i-1] using t[0..j-1]
            int[][] dp = new int[m + 1][n + 1];
            for (int j = 0; j < n; j++) {
                dp[0][j] = 1;
            }

            // NOT FINISHED
            return m;
        }
    }
}
