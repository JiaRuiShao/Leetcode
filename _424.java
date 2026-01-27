/**
 * 424. Longest Repeating Character Replacement
 */
public class _424 {
    class Solution {
        public int characterReplacement(String s, int k) {
            int left = 0, right = 0;
            // count the occurrences of each character in the window
            int[] windowCharCount = new int[26];
            // record the maximum count of any character in the window
            // the significance of recording this value is that the most cost-effective way to replace is to convert other characters to the one that appears the most
            int windowMaxCount = 0;
            // record the length of the result
            int res = 0;

            // start the sliding window template
            while (right < s.length()) {
                // expand the window
                int c = s.charAt(right) - 'A';
                windowCharCount[c]++;
                windowMaxCount = Math.max(windowMaxCount, windowCharCount[c]);
                right++;

                // this while can be replaced with an if statement
                while (right - left - windowMaxCount > k) {
                    // the number of miscellaneous characters right - left - windowMaxCount exceeds k
                    // at this point, k replacements are no longer able to make all characters in the window the same
                    // must shrink the window
                    windowCharCount[s.charAt(left) - 'A']--;
                    left++;
                }
                // after shrinking, the window is definitely a valid window
                res = Math.max(res, right - left);
            }
            return res;
        }
    }
}
