/**
 * 9. Palindrome Number
 */
public class _9 {
    class Solution1_String {
        public boolean isPalindrome(int x) {
            String s = String.valueOf(x);
            int i = 0, j = s.length() - 1;
            while (i < j) {
                if (s.charAt(i) != s.charAt(j)) {
                    return false;
                }
                i++;
                j--;
            }
            return true;
        }
    }

    class Solution2_Math {
        public boolean isPalindrome(int x) {
            if (x < 0) return false;
            int num = x, reverse = 0;
            while (num > 0) {
                reverse = reverse * 10 + num % 10;
                num /= 10;
            }
            return reverse == x;
        }
    }
}
