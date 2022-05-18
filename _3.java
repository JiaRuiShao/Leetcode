public class _3 {
    static class Solution {
        static final int MAX_CHAR = 256;
        public int lengthOfLongestSubstring(String s) {
            int[] count = new int[MAX_CHAR];
            int left = 0, right = 0, maxLen = 0;
            char c;
            while (right < s.length()) {
                c = s.charAt(right++);
                count[c]++;
                while (count[c] > 1) {
                    count[s.charAt(left++)]--;
                }
                maxLen = Math.max(maxLen, right - left);
            }
            return maxLen;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().lengthOfLongestSubstring("pwwkew"));
    }
}
