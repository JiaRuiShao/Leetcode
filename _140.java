import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 140. Word Break II
 * 
 * - S1: Backtrack with no memo O(2^n) O(n)
 * - S2: Backtrack with memo: O(n^2+Rn) O(n^2+Rn)
 * - S3: Iterative DP: O(n^3+Rn) O(Rn)
 * 
 * Clarification:
 * - Can words in wordDict used multiple times? Yes
 * - What to return when s is empty? ""
 * - Case sensitive? Yes - no need to convert to lowercase
 */
public class _140 {
    // Time: O(2^n*m*l) = O(2^n) where m is wordDict size, n is s len, & l is avg wor length in wordDict
    // Space: O(n)
    class Solution1_Backtrack {
        public List<String> wordBreak(String s, List<String> wordDict) {
            List<String> sentences = new ArrayList<>();
            List<String> path = new ArrayList<>();
            backtrack(s, 0, wordDict, path, sentences);
            return sentences;
        }

        private void backtrack(String s, int start, List<String> dict, List<String> path, List<String> res) {
            if (start == s.length()) {
                res.add(String.join(" ", path));
                return;
            }

            for (String dictStr : dict) {
                int end = start + dictStr.length();
                if (end > s.length()) continue;
                String word = s.substring(start, end);
                if (!dictStr.equals(word)) continue;
                path.add(word);
                backtrack(s, end, dict, path, res);
                path.remove(path.size() - 1);
            }
        }
    }

    // Time: O(n^3 + Rn) = O(n^3)
    // Space: O(Rn+n)= O(Rn)
    class Solution2_Backtrack_WithMemo {
        public List<String> wordBreak(String s, List<String> wordDict) {
            return dfs(s, 0, new HashSet<>(wordDict), new HashMap<>());
        }

        private List<String> dfs(String s, int start, Set<String> dict, Map<Integer, List<String>> memo) {
            if (memo.containsKey(start)) {
                return memo.get(start);
            }

            List<String> res = new ArrayList<>();

            if (start == s.length()) {
                res.add(""); // base for concatenation
                return res;
            }

            for (int end = start + 1; end <= s.length(); end++) {
                String word = s.substring(start, end);
                if (!dict.contains(word)) continue;

                List<String> subSentences = dfs(s, end, dict, memo);
                for (String sub : subSentences) {
                    if (sub.isEmpty()) {
                        res.add(word);
                    } else {
                        res.add(word + " " + sub);
                    }
                }
            }

            memo.put(start, res);
            return res;
        }
    }

    // Time: O(n^3 + R * n) where n is s len and R is num of valid sentences in the output (can be exponential in n)
    // Space: O(R * n)
    class Solution3_DP_Iterative {
        public List<String> wordBreakIterative(String s, List<String> wordDict) {
            Set<String> wordSet = new HashSet<>(wordDict);
            int n = s.length();
            
            // dp[i] = list of all sentences for s[0...i-1]
            List<List<String>> dp = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                dp.add(new ArrayList<>());
            }
            dp.get(0).add("");  // Base case
            
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < i; j++) {
                    String word = s.substring(j, i);
                    
                    if (!dp.get(j).isEmpty() && wordSet.contains(word)) {
                        for (String prev : dp.get(j)) {
                            dp.get(i).add(prev.isEmpty() ? word : prev + " " + word);
                        }
                    }
                }
            }
            
            return dp.get(n);
        }
    }
}
