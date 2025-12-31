import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 139. Word Break
 * 
 * - S1: Recursion (Top-Down DP) O(n*2^n) without memo, O(n^3) with memo
 * - S2: Bottom-Up Iterative DP O(n^3)
 * - S2: Trie O(n^3) could be optimized to O(n^2)
 * 
 * Clarification:
 * - Can words in wordDict be reused? Yes
 * - Case sensitive? Yes
 * - What's the range? 1 ≤ s.length ≤ 300, 1 ≤ wordDict.length ≤ 1000, 1 ≤ wordDict[i].length ≤ 20
 * 
 * Followup:
 * - Return all possible segmentations, not just true/false? LC 140
 * - Return the min number of words in segmentation? Use dp
 * - What if we can only use each word in dictionary once? Use recursion
 * - What if input could be upper case and we want case-insensitive matching? Convert wordDict & s to lowercase set
 * - Track which word was used? Use String[] parent pointer
 * - What if dictionary is extremely large? Use Trie instead of HashSet (with heavy collision time is O(logn))
 */
public class _139 {
    // Time: O(n*2^n)
    // Space: O(n)
    class Solution1_Backtrack {
        public boolean wordBreak(String s, List<String> wordDict) {
            return backtrack(s.toCharArray(), 0, 0, new HashSet<String>(wordDict));
        }

        private boolean backtrack(char[] words, int start, int end, Set<String> dict) {
            if (start == words.length) return true;
            if (end == words.length) return false;
            // do not end at this char
            boolean exist = backtrack(words, start, end + 1, dict);
            // end at this char
            String s = new String(words, start, end - start + 1);
            if (dict.contains(s)) {
                exist = exist || backtrack(words, end + 1, end + 1, dict);
            }
            return exist;
        }
    }

    // Time: O(n^3)
    // Space: O(n^2)
    class Solution1_Backtrack_WithMemo {
        public boolean wordBreak(String s, List<String> wordDict) {
            return backtrack(s.toCharArray(), 0, 0, new HashSet<String>(wordDict), new HashMap<String, Boolean>());
        }

        private boolean backtrack(char[] words, int start, int end, Set<String> dict, Map<String, Boolean> memo) {
            if (start == words.length) return true;
            if (end == words.length) return false;
            String key = start + "-" + end;
            if (memo.containsKey(key)) return memo.get(key);
            // do not end at this char
            boolean exist = backtrack(words, start, end + 1, dict, memo);
            // end at this char
            String s = new String(words, start, end - start + 1);
            if (dict.contains(s)) {
                exist = exist || backtrack(words, end + 1, end + 1, dict, memo);
            }
            memo.put(key, exist);
            return exist;
        }
    }

    // There're other ways to implement backtrack recursion solution, we can iterate each word from dict in backtrack instead of end index
    // Time: O(n^3)
    // Space: O(n)
    class Solution1_Backtrack_WithMemo2 {
        public boolean wordBreak(String s, List<String> wordDict) {
            Set<String> wordSet = new HashSet<>(wordDict);
            Boolean[] memo = new Boolean[s.length()];
            return backtrack(s, 0, wordSet, memo);
        }

        private boolean backtrack(String s, int start, Set<String> wordSet, Boolean[] memo) {
            // Base case: reached end of string
            if (start == s.length()) {
                return true;
            }
            
            // Check memo
            if (memo[start] != null) {
                return memo[start];
            }
            
            // Try all possible words starting from 'start'
            for (int end = start + 1; end <= s.length(); end++) {
                String word = s.substring(start, end);
                
                if (wordSet.contains(word) && backtrack(s, end, wordSet, memo)) {
                    memo[start] = true;
                    return true;
                }
            }
            
            memo[start] = false;
            return false;
        }
    }

    // dp[i] represents: "Can we segment the FIRST i characters of s?"
    // Time: O(n^3) where m is avg word length
    // Space: O(n)
    class Solution2_DP {
        public boolean wordBreak(String s, List<String> wordDict) {
            int n = s.length();
            Set<String> dict = new HashSet<>(wordDict);
            boolean[] dp = new boolean[n + 1]; // can first i chars be segmented
            dp[0] = true; // empty, end before index 0
            for (int end = 1; end <= n; end++) { // [start, end)
                for (int start = 0; start < end; start++) {
                    String word = s.substring(start, end);
                    if (dict.contains(word)) {
                        dp[end] = dp[end] || dp[start];
                    }
                }
            }
            return dp[n];
        }

        public boolean wordBreak_DPWithSizeN(String s, List<String> wordDict) {
            Set<String> wordSet = new HashSet<>(wordDict);
            int n = s.length();
            
            // dp[i] represents: "Can we segment s[0...i] (inclusive)?"
            // So dp[i] refers to substring s[0...i], which has length i+1
            boolean[] dp = new boolean[n];
            
            for (int i = 0; i < n; i++) {
                // Check if entire prefix s[0...i] is a word
                if (wordSet.contains(s.substring(0, i + 1))) {
                    dp[i] = true;
                    continue;
                }
                
                // Try splitting at different positions
                for (int j = 0; j < i; j++) {
                    // Can we segment s[0...j]? AND
                    // Is s[j+1...i] a valid word?
                    if (dp[j] && wordSet.contains(s.substring(j + 1, i + 1))) {
                        dp[i] = true;
                        break;
                    }
                }
            }
            
            return dp[n - 1];
        }
    }

    // No need to create substrings using Trie; however more memory overhead used
    // Time: O(n^3)
    // Space: O(n)
    class Solution3_Trie {
        class TrieNode {
            TrieNode[] children = new TrieNode[26];
            boolean end = false;
        }

        private TrieNode root = new TrieNode();

        private void insert(String s) {
            TrieNode curr = root;
            for (char c : s.toCharArray()) {
                int index = c - 'a';
                if (curr.children[index] == null) curr.children[index] = new TrieNode();
                curr = curr.children[index];
            }
            curr.end = true;
        }

        private boolean search(String s, int start, int end) {
            TrieNode curr = root;
            for (int i = start; i < end; i++) {
                int index = s.charAt(i) - 'a';
                if (curr.children[index] == null) return false;
                curr = curr.children[index];
            }
            return curr.end;
        }

        public boolean wordBreak(String s, List<String> wordDict) {
            for (String word : wordDict) {
                insert(word);
            }

            int n = s.length();
            boolean[] dp = new boolean[n + 1]; // first i char can be segmented
            dp[0] = true;
            for (int end = 1; end <= n; end++) {
                for (int start = 0; start < end; start++) {
                    if (dp[start] && search(s, start, end)) {
                        dp[end] = true;
                    }
                }
            }
            return dp[n];
        }
    }

    class Followup_MinSegmentation {
        public int minWordBreak(String s, List<String> wordDict) {
            Set<String> wordSet = new HashSet<>(wordDict);
            int n = s.length();
            
            // dp[i] = minimum number of words to segment s[0...i-1]
            int[] dp = new int[n + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < i; j++) {
                    if (dp[j] != Integer.MAX_VALUE && 
                        wordSet.contains(s.substring(j, i))) {
                        dp[i] = Math.min(dp[i], dp[j] + 1);
                    }
                }
            }
            
            return dp[n] == Integer.MAX_VALUE ? -1 : dp[n];
        }
    }

    class Followup_WordUsedOnlyOnce {
        public boolean wordBreakOnce(String s, List<String> wordDict) {
            boolean[] used = new boolean[wordDict.size()];
            return backtrack(s, 0, wordDict, used);
        }

        private boolean backtrack(String s, int start, List<String> wordDict, boolean[] used) {
            if (start == s.length()) return true;
            
            for (int i = 0; i < wordDict.size(); i++) {
                if (used[i]) continue;
                
                String word = wordDict.get(i);
                if (s.startsWith(word, start)) {
                    used[i] = true;
                    if (backtrack(s, start + word.length(), wordDict, used)) {
                        return true;
                    }
                    used[i] = false;
                }
            }
            
            return false;
        }
    }

    class Followup_ReturnUsedWords {
        public List<String> getUsedWords(String s, List<String> wordDict) {
            Set<String> wordSet = new HashSet<>(wordDict);
            int n = s.length();
            boolean[] dp = new boolean[n + 1];
            String[] parent = new String[n + 1];  // Track which word was used
            
            dp[0] = true;
            
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < i; j++) {
                    String word = s.substring(j, i);
                    if (dp[j] && wordSet.contains(word)) {
                        dp[i] = true;
                        parent[i] = word;
                        break;
                    }
                }
            }
            
            if (!dp[n]) return new ArrayList<>();
            
            // Reconstruct
            List<String> result = new ArrayList<>();
            int pos = n;
            while (pos > 0) {
                String word = parent[pos];
                result.add(word);
                pos -= word.length();
            }
            
            Collections.reverse(result);
            return result;
        }
    }

}
