import java.util.HashSet;
import java.util.Set;

/**
 * 409. Longest Palindrome
 */
public class _409 {
    class Solution1_HashSet {
        // Time: O(n)
        // Space: O(1)
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

    // Time: O(n)
    // Space: O(1)
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
            int[] freq = new int[128];
            for (char c : s.toCharArray()) {
                freq[c]++;
            }

            boolean hasOddFreq = false;
            int pair = 0;
            for (int i = 0; i < freq.length; i++) {
                pair += freq[i] / 2;
                if (freq[i] % 2 == 1) {
                    hasOddFreq = true;
                }
            }
            return hasOddFreq ? pair * 2 + 1 : pair * 2;
        }
    }
}
