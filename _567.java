import java.util.HashMap;
import java.util.Map;

/**
 * 567. Permutation in String.
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
}
