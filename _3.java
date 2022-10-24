import java.util.HashMap;
import java.util.Map;

/**
 * 3. Longest Substring Without Repeating Characters.
 */
public class _3 {
    class Solution1 {
        public int lengthOfLongestSubstring(String s) {
            Map<Character, Integer> freq = new HashMap<>();
            int left = 0, right = 0, invalid = 0, maxLen = 0; // window: [left, right)
            while (right < s.length()) {
                char c = s.charAt(right);
                right++;
                freq.put(c, freq.getOrDefault(c, 0) + 1);
                if (freq.get(c) == 2) invalid++;
                while (invalid > 0) {
                    c = s.charAt(left);
                    left++;
                    freq.put(c, freq.get(c) - 1);
                    if (freq.get(c) == 1) invalid--;
                }
                maxLen = Math.max(maxLen, right - left);
            }
            return maxLen;
        }
    }
    
    class Solution2 {
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
        System.out.println(new _3().new Solution2().lengthOfLongestSubstring("pwwkew"));
    }
}
