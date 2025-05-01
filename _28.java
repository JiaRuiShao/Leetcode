/**
 * 28. Find the Index of the First Occurrence in a String
 */
public class _28 {
    class Solution1_Sliding_Window {
        // Time: O(nk) where n is length of haystack and k is length of needle 
        public int strStr(String haystack, String needle) {
            if (needle.length() > haystack.length()) return -1;
            int windowLen = needle.length(), left = 0, right = 0;
            while (right < haystack.length()) {
                right++;
                if (right - left == windowLen) {
                    if (haystack.substring(left, right).equals(needle)) return left;
                    left++;
                }
            }
            return -1;
        }
    }

    class Solution2_Sliding_Window_Rabin_Karp {
        public int strStr(String haystack, String needle) {
            int targetLen = needle.length(), left = 0, right = 0;
            // mod is used to prevent integer overflow
            int base = 26, targetHash = 0, mod = 1_000_000_7;
            for (int i = 0; i < targetLen; i++) {
                int num = convertCharToInt(needle.charAt(i));
                targetHash = ((base * targetHash) % mod + num) % mod;
            }
            // base^(L-1) pre-calculated to remove left window char
            int windowHash = 0, leftCharWeight = 1;
            for (int i = 1; i < targetLen; i++) {
                leftCharWeight = (leftCharWeight * base) % mod;
            }
    
            while (right < haystack.length()) {
                int addRight = convertCharToInt(haystack.charAt(right));
                right++;
                windowHash = ((base * windowHash) % mod + addRight) % mod;
                if (right - left == targetLen) {
                    // double check the string to prevent hash collision
                    if (windowHash == targetHash && haystack.substring(left, right).equals(needle)) {
                        return left;
                    }
                    int removeLeft = convertCharToInt(haystack.charAt(left));
                    left++;
                    windowHash = (windowHash - (removeLeft * leftCharWeight) % mod + mod) % mod;
                }
            }
            return -1;
        }
    
        private int convertCharToInt(char c) {
            return c - 'a';
        }
    }
}
