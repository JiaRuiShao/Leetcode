import java.util.HashMap;
import java.util.Map;

/**
 * 567. Permutation in String.
 * Count each needed character not just the distinct character -- this will effect how we update the needed chars
 */
public class _567 {
    class Solution1 {
        public boolean checkInclusion(String s1, String s2) {
            // build a freq map for target s1
            int size = s1.length();
            Map<Character, Integer> freq = new HashMap<>();
            for (int i = 0; i < size; i++) {
                freq.put(s1.charAt(i), freq.getOrDefault(s1.charAt(i), 0) + 1);
            }
            
            // left & right pointers to construct a window that satisfy the requirement
            int left = 0, right = 0, valid = 0;
            while (right < s2.length()) {
                // char adding into the window
                char c = s2.charAt(right);
                right++; // update the upper bound
                
                // update the freq map and the valid count
                if (freq.containsKey(c)) {
                    freq.put(c, freq.get(c) - 1);
                    if (freq.get(c) >= 0) {
                        valid++;
                    }
                }
                
                // update the ans & freq map when window.size is target size
                // notice here we didn't use while loop while (valid == targetK)
                // cuz the requirement here is no other chars in the middle of the substring
                if (right - left == size) {
                    // requirement met
                    if (valid == size) {
                        return true;
                    }
                    
                    // char popping out of the window
                    c = s2.charAt(left);
                    left++; // update the lower bound
                    
                    // update the freq map and the valid count
                    if (freq.containsKey(c)) {
                        freq.put(c, freq.get(c) + 1);
                        if (freq.get(c) > 0) {
                            valid--;
                        }
                    }
                }
            }
            return false;
        }
    }
    
    class Solution2 {
        static final int MAX_CHAR = 256;
        public boolean checkInclusion(String s1, String s2) {
            int[] count = new int[MAX_CHAR]; // ASCII size counter array
            for (char c : s1.toCharArray()) count[c]++;
            int counter = s1.length(); // num of chars needed
            int left = 0, right = 0;
            while (right < s2.length()) {
                // move right pointer
                char newChar = s2.charAt(right++);
                if (count[newChar] > 0) counter--; // decrease counter if char at right is one of the char in s1
                count[newChar]--; // decrease the freq of this char in the counter array

                // keep shrinking the left window [left, right) if the window size >= s1 size
                while (right - left >= s1.length()) {
                    if (counter == 0) return true;
                    // move left pointer
                    if (++count[s2.charAt(left++)] > 0) counter++;
                }
            }
            return false;
        }
    }

    class Solution3 {

        private int MAX_CHAR = 256;
    
        /**
         * We can use char array here because the constraint mentioned 1 <= s1.length, s2.length <= 10^4, and each char has 2 bytes, which is 16 bits to store the data. 2^16 - 1 - 10^4 > 0, it means we can store the data as char without overflow problem.
         * Notice that char is stored as ASCII code in java, which is unsigned, so it can store up to 16 bits numbers
         * Improvements: we can store the lowercase english letters in a 26-size int arr
         * @param s1 s1
         * @param s2 s2
         * @return true if s1's permutation found in s2; else false
         */
        public boolean checkInclusion(String s1, String s2) {
            char[] freq = new char[MAX_CHAR];
            for (int i = 0; i < s1.length(); i++) {
                freq[s1.charAt(i)]++;
            }
    
            int l = 0, r = 0, maxWinLen = s1.length(), need = s1.length();
            while(r < s2.length()) {
                char cAdd = s2.charAt(r++);
                if (freq[cAdd] > 0) {
                    need--;
                }
                freq[cAdd]--;
    
                if (r - l == maxWinLen) {
                    if (need == 0) {
                        return true;
                    }
                    char cRem = s2.charAt(l++);
                    freq[cRem]++;
                    if (freq[cRem] > 0) {
                        need++;
                    }
                }
            }
    
            return false;
        }
    }

    class Solution4 {

        private char a = 'a';
    
        private short convertCharToShort(char c) {
            return (short) (c - a);
        }
    
        /**
         * Uses short array to store the char freq. Compared to char array and int array, it saves some more memory space. But is it really worth it?
         * @param s1 s1
         * @param s2 s2
         * @return true if s1's permutation found in s2; else false
         */
        public boolean checkInclusion(String s1, String s2) {
            short[] freq = new short[26];
            int maxWinLen = s1.length();
            for (int i = 0; i < maxWinLen; i++) {
                freq[convertCharToShort(s1.charAt(i))]++;
            }
    
            int l = 0, r = 0, need = maxWinLen;
            short cAdd = 0, cRem = 0;
            while(r < s2.length()) {
                cAdd = convertCharToShort(s2.charAt(r++));
                if (freq[cAdd] > 0) {
                    need--;
                }
                freq[cAdd]--;
    
                if (r - l == maxWinLen) {
                    if (need == 0) {
                        return true;
                    }
                    cRem = convertCharToShort(s2.charAt(l++));
                    freq[cRem]++;
                    if (freq[cRem] > 0) {
                        need++;
                    }
                }
            }
    
            return false;
        }
    }

    /**
     * NO-BRAINER SOLUTION! Uses two freq map for both strings.
     * Ref: https://labuladong.online/algo/ds-class/shu-zu-lia-39fd9/hua-dong-c-c0f54/#%E4%BA%8C%E3%80%81%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%8E%92%E5%88%97
     */
    class Solution5 {
        public boolean checkInclusion(String t, String s) {
            HashMap<Character, Integer> need = new HashMap<>();
            HashMap<Character, Integer> window = new HashMap<>();
            for (int i = 0; i < t.length(); i++) {
                char c = t.charAt(i);
                need.put(c, need.getOrDefault(c, 0) + 1);
            }
    
            int left = 0, right = 0;
            int valid = 0;
            while (right < s.length()) {
                char c = s.charAt(right);
                right++;
                if (need.containsKey(c)) {
                    window.put(c, window.getOrDefault(c, 0) + 1);
                    if (window.get(c).equals(need.get(c)))
                        valid++;
                }
    
                while (right - left >= t.length()) {
                    if (valid == need.size())
                        return true;
                    char d = s.charAt(left);
                    left++;
                    if (need.containsKey(d)) {
                        if (window.get(d).equals(need.get(d)))
                            valid--;
                        window.put(d, window.getOrDefault(d, 0) - 1);
                    }
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        String t = "abcdxabcde", s = "abcdeabcdx";

        new _567().new Solution4().checkInclusion(t, s);
    }
}
