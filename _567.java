import java.util.HashMap;
import java.util.Map;

/**
 * 567. Permutation in String
 * Count each needed character not just the distinct character -- this will effect how we update the needed chars
 */
public class _567 {
    class Solution1_Sliding_Window_Array { // use char freq counter
        public boolean checkInclusion(String s1, String s2) {
            if (s1.length() > s2.length()) return false;
            int[] charFreq = new int[26];
            for (int i = 0; i < s1.length(); i++) {
                charFreq[s1.charAt(i) - 'a']++;
            }
            int left = 0, right = 0, charNeeded = s1.length(), winLen = charNeeded; // [left, right)
            while (right < s2.length()) {
                int toAdd = s2.charAt(right++) - 'a';
                if (charFreq[toAdd]-- > 0) charNeeded--;
                if (right - left == winLen) {
                    if (charNeeded == 0) return true;
                    int toRem = s2.charAt(left++) - 'a';
                    if (++charFreq[toRem] > 0) charNeeded++;
                }
            }
            return false;
        }
    }
    
    class Solution2_Sliding_Window_Array { // use distinct char counter
        public boolean checkInclusion(String s1, String s2) {
            if (s1.length() > s2.length()) return false;
            int[] charFreq = new int[26];
            int distinct = 0;
            for (int i = 0; i < s1.length(); i++) {
                if (charFreq[s1.charAt(i) - 'a']++ == 0) distinct++;
            }
            int left = 0, right = 0, winLen = s1.length(); // [left, right)
            while (right < s2.length()) {
                int toAdd = s2.charAt(right++) - 'a';
                if (--charFreq[toAdd] == 0) distinct--;
                if (right - left == winLen) {
                    if (distinct == 0) return true;
                    int toRem = s2.charAt(left++) - 'a';
                    if (charFreq[toRem]++ == 0) distinct++;
                }
            }
            return false;
        }
    }
    
    class Solution3_Sliding_Window_HashMap {
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

    /**
     * NO-BRAINER SOLUTION! Uses two freq map for both strings.
     * Ref: https://labuladong.online/algo/ds-class/shu-zu-lia-39fd9/hua-dong-c-c0f54/#%E4%BA%8C%E3%80%81%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%8E%92%E5%88%97
     */
    class Solution4 {
        public boolean checkInclusion(String t, String s) {
            HashMap<Character, Integer> need = new HashMap<>();
            HashMap<Character, Integer> window = new HashMap<>();
            for (int i = 0; i < t.length(); i++) {
                char c = t.charAt(i);
                need.put(c, need.getOrDefault(c, 0) + 1);
            }
    
            int left = 0, right = 0, valid = 0;
            while (right < s.length()) {
                char c = s.charAt(right++);
                if (need.containsKey(c)) {
                    window.put(c, window.getOrDefault(c, 0) + 1);
                    if (window.get(c).equals(need.get(c))) valid++;
                }
    
                while (right - left >= t.length()) {
                    if (valid == need.size()) return true;
                    char d = s.charAt(left++);
                    if (need.containsKey(d)) {
                        if (window.get(d).equals(need.get(d))) valid--;
                        window.put(d, window.getOrDefault(d, 0) - 1);
                    }
                }
            }
            return false;
        }
    }
}
