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
            for (int i = 0; i < strs[0].length(); i++) {
                char c = strs[0].charAt(i);
                for (int j = 1; j < strs.length; j++) {
                    if (i == strs[j].length() || strs[j].charAt(i) != c) {
                        return strs[0].substring(0, i);
                    }
                }
            }
            return strs[0]; // **IMPORTANT **
        }
    }

    class Solution2 {
            public String longestCommonPrefix(String[] strs) {
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < strs[0].length(); i++) {
                char c = strs[0].charAt(i);
                for (int j = 1; j < strs.length; j++) {
                    if (i == strs[j].length() || strs[j].charAt(i) != c) {
                        return prefix.toString();
                    }
                }
                prefix.append(c);
            }
            return prefix.toString();
        }
    }
}
