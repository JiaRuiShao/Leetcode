import java.util.HashSet;
import java.util.Set;

/**
 * 409. Longest Palindrome.
 * Given a string s which consists of lowercase or uppercase letters, return
 * the length of the longest palindrome that can be built with those letters.
 * Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
 * *
 * Example:
 * Input: s = "abccccdd"
 * Output: 7
 * Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.
 */
public class _409 {
    class Solution1_HashSet {
        /**
         * Time: O(n)
         * Space: o(n/2)
         *
         * @param s input str
         * @return len of longest palindrome
         */
        public int longestPalindrome(String s) {
            if (s == null || s.length() == 0) return 0;
            Set<Character> hs = new HashSet<Character>();
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (hs.contains(s.charAt(i))) {
                    hs.remove(s.charAt(i));
                    count++;
                } else {
                    hs.add(s.charAt(i));
                }
            }
            if (!hs.isEmpty()) return count * 2 + 1;
            return count * 2;
        }
    }

    class Solution2_Array {
        public int longestPalindrome(String s) {
            // freq array to store the chars & their freq
            final int size = 58;
            int[] freq = new int[size]; // size 60 is enough cuz 'z' is 122 & 'A' is 65 in ASCII

            // build the freq arr
            for (int i = 0; i < s.length(); i++) {
                freq[s.charAt(i) - 'A']++;
            }

            int t = 0; // whether there's odd freq
            int length = 0; // res len
            for (int i = 0; i < size; i++) {
                // update the odd len
                if (freq[i] % 2 == 1) {
                    t = 1;
                }
                // update the res len
                if (freq[i] > 1) {
                    length += (freq[i] / 2) * 2;
                }
            }
            return length + t;
        }

        public int longestPalindrome2(String s) {
            int[] counts = new int[56];
            for (char c : s.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    counts[c - 'A' + 33]++;
                } else {
                    counts[c - 'a']++;
                }
            }
            boolean hasOdd = false;
            int len = 0;
            for (int i = 0; i < 56; i++) {
                if (counts[i] % 2 != 0) {
                    hasOdd = true;
                    if (counts[i] > 1) {
                        len += counts[i] - 1;
                    }
                } else {
                    len += counts[i];
                }
            }
            return hasOdd ? len + 1 : len;
        }
    }
}
