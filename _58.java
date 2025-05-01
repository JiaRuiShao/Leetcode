/**
 * 58. Length of Last Word
 */
public class _58 {
    class Solution1_Two_Pointers {
        public int lengthOfLastWord(String s) {
            int n = s.length();
            int left = n - 1, right = n - 1;
            while (right >= 0 && s.charAt(right) == ' ') {
                right--;
            }
            left = right;
            while (left >= 0 && s.charAt(left) != ' ') {
                left--;
            }
            return right - left;
        }
    }
}
