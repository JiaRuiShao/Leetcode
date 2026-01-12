/**
 * 28. Find the Index of the First Occurrence in a String
 * 
 * - S1 Sliding Window: O(mn), O(1)
 * - S2: Rabin-Karp: O(n+m) avg / O(mn) worst, O(1)
 * - S3: KMP: O(n+m), O(m)
 */
public class _28 {
    class Solution1_Sliding_Window {
        // Time: worst O(nk) where n is length of haystack and k is length of needle
        public int strStr(String haystack, String needle) {
            int[] needleCharFreq = new int[26];
            for (int i = 0; i < needle.length(); i++) {
                needleCharFreq[needle.charAt(i) - 'a']++;
            }
            int winLen = needle.length(), charNeeded = winLen;
            int left = 0, right = 0;
            while (right < haystack.length()) {
                int toAdd = haystack.charAt(right) - 'a';
                right++;
                if (needleCharFreq[toAdd]-- > 0) {
                    charNeeded--;
                }
                if (right - left == winLen) {
                    if (charNeeded == 0 && haystack.substring(left, right).equals(needle)) {
                        return left;
                    }
                    int toRem = haystack.charAt(left) - 'a';
                    left++;
                    if (++needleCharFreq[toRem] > 0) {
                        charNeeded++;
                    }
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

    class Solution3_KMP {
        public int strStr(String haystack, String needle) {
            if (needle.isEmpty()) return 0;
            
            int n = haystack.length();
            int m = needle.length();
            
            if (m > n) return -1;
            
            // Build LPS (Longest Proper Prefix which is also Suffix) array
            int[] lps = buildLPS(needle);
            
            // KMP search
            int i = 0; // haystack pointer
            int j = 0; // needle pointer
            
            while (i < n) {
                if (haystack.charAt(i) == needle.charAt(j)) {
                    i++;
                    j++;
                    
                    // Found match
                    if (j == m) {
                        return i - m;
                    }
                } else {
                    if (j > 0) {
                        // Don't match i, use LPS to skip characters in needle
                        j = lps[j - 1];
                    } else {
                        // No match at all, move haystack pointer
                        i++;
                    }
                }
            }
            
            return -1;
        }

        // Build Longest Proper Prefix which is also Suffix array
        private int[] buildLPS(String pattern) {
            int m = pattern.length();
            int[] lps = new int[m];
            
            int len = 0; // length of previous longest prefix suffix
            int i = 1;
            
            while (i < m) {
                if (pattern.charAt(i) == pattern.charAt(len)) {
                    len++;
                    lps[i] = len;
                    i++;
                } else {
                    if (len > 0) {
                        len = lps[len - 1];
                    } else {
                        lps[i] = 0;
                        i++;
                    }
                }
            }
            
            return lps;
        }
    }
}
