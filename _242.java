import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 242. Valid Anagram
 */
public class _242 {
    static class Solution1_HashTable {
        /**
         * Time: O(2n)
         * Space: O(n)
         * @param s str1
         * @param t str2
         * @return true if s & t are anagram; false if not
         */
        public boolean isAnagram(String s, String t) {
            if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() != t.length()) return false;
            // store the letters used and their frequencies
            Map<Character, Integer> freq = new HashMap<>();
            // build the freq map using s
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }
            // check the t freq
            for (int i = 0; i < t.length(); i++) {
                char c = t.charAt(i);
                freq.put(c, freq.getOrDefault(c, 0) - 1);
                if (freq.get(c) < 0) return false;
            }
            return true;
        }
    }

    static class Solution2_Array {
        /**
         * Preferred than using hashmap because the worst time complexity of oeprations in hashmap is O(logn).
         * Time: O(2n)
         * Space: O(n)
         * @param s str1
         * @param t str2
         * @return true if s & t are anagram; false if not
         */
        public boolean isAnagram(String s, String t) {
            if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() != t.length()) return false;
            // store the letters used and their frequencies
            int[] freq = new int[26];
            for (int i = 0; i < s.length(); i++) {
                freq[s.charAt(i) - 'a'] += 1;
                freq[t.charAt(i) - 'a'] -= 1;
            }
            // check if there's any dismatches
            for (int n : freq) {
                if (n != 0) return false;
            }
            return true;
        }
    }

    static class Solution3_Sorting {
        /**
         * Time: O(3n+2nlogn) = O(nlogn)
         * Space: O(2n + 2logn) = O(n), here Arrays.sort uses quicksort cuz char is primitive type
         * @param s str1
         * @param t str2
         * @return true if s & t are anagram; false if not
         */
        public boolean isAnagram(String s, String t) {
            char[] schar = s.toCharArray();
            char[] tchar = t.toCharArray();
            Arrays.sort(schar);
            Arrays.sort(tchar);
            return new String(schar).equals(new String(tchar));
        }
    }
}
