import java.util.ArrayList;
import java.util.List;

public class _438 {
    static class Solution {
        static final int MAX_CHAR = 256;
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> res = new ArrayList<>(s.length());
            int[] count = new int[MAX_CHAR]; // ASCII size counter array
            for (char c : p.toCharArray()) count[c]++;
            int counter = p.length(); // num of chars needed
            int left = 0, right = 0;
            while (right < s.length()) {
                // move right pointer
                char newChar = s.charAt(right++);
                if (count[newChar] > 0) counter--; // decrease counter if char at right is one of the char in s1
                count[newChar]--; // decrease the freq of this char in the counter array

                // keep shrinking the left window [left, right) if the window size >= s1 size
                while (right - left >= p.length()) {
                    if (counter == 0) res.add(left);
                    // move left pointer
                    if (++count[s.charAt(left++)] > 0) counter++;
                }
            }
            return res;
        }
    }
}
