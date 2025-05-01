import java.util.Arrays;

/**
 * 125. Valid Palindrome
 */
public class _125 {
    static class Solution1_Two_Pointers {
        /**
         * Time: O(n) where n is the input string length
         * Space: O(n) for storing the char arr
         * @param s
         * @return
         */
        public boolean isPalindrome(String s) {
            // two pointers
            int i = 0, j = s.length() - 1;
            char[] str = s.toLowerCase().toCharArray();
            while (i < j) {
                // ignore if this char is alphanumeric
                if (!Character.isLetterOrDigit(str[i])) {
                    i++;
                    continue;
                }
                if (!Character.isLetterOrDigit(str[j])) {
                    j--;
                    continue;
                }
                if (str[i] != str[j]) return false;
                i++;
                j--;
            }
            return true;
        }

        public boolean isPalindrome_Faster(String s) {
            // two pointers
            int i = 0, j = s.length() - 1;
            while (i <= j) {
                // ignore if this char is alphanumeric
                if (!Character.isLetterOrDigit(s.charAt(i))) {
                    i++;
                    continue;
                }
                if (!Character.isLetterOrDigit(s.charAt(j))) {
                    j--;
                    continue;
                }
                if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) return false;
                i++;
                j--;
            }
            return true;
        }
    }

    static class Solution2_StringBuilder {
        public boolean isPalindrome(String s) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (Character.isLetterOrDigit(s.charAt(i))) {
                    sb.append(Character.toLowerCase(s.charAt(i)));
                }
            }
            String reverse = sb.reverse().toString();
            return reverse.equals(sb.toString());
        }

        public boolean isPalindromeJava8Stream(String s) {
            StringBuilder sb = new StringBuilder();
            s.chars().filter(Character::isLetterOrDigit).map(Character::toLowerCase).forEach(sb::append);
            String reverse = sb.reverse().toString();
            return reverse.equals(sb.toString());
        }
    }

    public static void main(String[] args) {
        String s = "A man, a plan, a canal: Panama";
        System.out.println(new Solution1_Two_Pointers().isPalindrome(s));
    }
}
