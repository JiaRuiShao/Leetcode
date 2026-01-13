/**
 * 647. Palindromic Substrings
 */
public class _647 {
    class Solution1_ExpandFromCenter {
        public int countSubstrings(String s) {
            int n = s.length(), count = 0;
            for (int i = 0; i < n; i++) {
                count += getMaxPalindromeLen(s, i, i);
                count += getMaxPalindromeLen(s, i, i + 1);
            }
            return count;
        }

        private int getMaxPalindromeLen(String s, int i, int j) {
            int n = s.length(), count = 0;
            while (i >= 0 && j < n && s.charAt(i) == s.charAt(j)) {
                count++;
                i--;
                j++;
            }
            return count;
        }
    }
}
