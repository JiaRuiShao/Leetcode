public class _567 {
    static class Solution {
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
