/**
 * 14. Longest Common Prefix
 * Other solutions:
 * - Trie
 * - Binary Search
 * - Divide & Conquer
 */
public class _14 {
    class Solution1 {
        public String longestCommonPrefix(String[] strs) {
            int n = strs.length, m = strs[0].length();
            for (int i = 0; i < m; i++) {
                char c = strs[0].charAt(i);
                for (int j = 1; j < n; j++) {
                    String curr = strs[j];
                    if (i >= curr.length() || curr.charAt(i) != c) {
                        return strs[0].substring(0, i);
                    }
                }
            }
            return strs[0]; // important!
        }
    }

    class Solution2 {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) return "";
            StringBuilder prefix = new StringBuilder();
            outer: for (int i = 0; i < strs[0].length(); i++) {
                char c = strs[0].charAt(i);
                for (int j = 1; j < strs.length; j++) {
                    String str = strs[j];
                    if (i >= str.length() || str.charAt(i) != c) {
                        break outer;
                    }
                }
                prefix.append(c);
            }
            return prefix.toString();
        }
    }
}
