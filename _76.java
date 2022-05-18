public class _76 {
    static class Solution {
        static final int MAX_CHAR = 256;
        public String minWindow(String s, String t) {
            int[] count = new int[256]; // ASCII size counter array
            for (char c : t.toCharArray()) count[c]++;
            int counter = t.length(); // num of chars needed
            int left = 0, right = 0, minLen = s.length() + 1, minStart = -1;
            while (right < s.length()) {
                // move right pointer
                char newChar = s.charAt(right++);
                if (count[newChar] > 0) counter--;
                count[newChar]--; // decrease the freq of this char in the counter array

                // keep shrinking the left window [left, right)
                while (counter == 0) {
                    if (right - left < minLen) {
                        minLen = right - left;
                        minStart = left;
                    }
                    // move left pointer
                    if (++count[s.charAt(left++)] > 0) counter++;
                }
            }
            return minStart == -1 ? "" : s.substring(minStart, minStart + minLen);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minWindow("a", "a"));
    }
}
