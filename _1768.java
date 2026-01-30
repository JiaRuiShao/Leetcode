/**
 * 1768. Merge Strings Alternately
 */
public class _1768 {
    class Solution1 {
        public String mergeAlternately(String word1, String word2) {
            StringBuilder merged = new StringBuilder();
            int p1 = 0, p2 = 0, m = word1.length(), n = word2.length();
            while (p1 < m || p2 < n) {
                if (p1 < m) {
                    merged.append(word1.charAt(p1++));
                }
                if (p2 < n) {
                    merged.append(word2.charAt(p2++));
                }
            }
            return merged.toString();
        }
    }
}
