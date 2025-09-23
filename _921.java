/**
 * 921. Minimum Add to Make Parentheses Valid
 */
public class _921 {
    class Solution1_Stack {
        // s = ")((("
        public int minAddToMakeValid(String s) {
            int add = 0, open = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') open++;
                else if (open == 0) add++;
                else open--;
            }
            return add + open;
        }
    }

    class Solution_FollowUp_GeneralizedParentheses {
        // s = ")((("
        public int minAddToMakeValid(String s) {
            char[] arr = s.toCharArray();
            int idx = 0, add = 0;
            for (char c : arr) {
                if (isOpenParen(c)) {
                    arr[idx++] = c;
                } else if (idx == 0 || !isMatchParen(arr[idx - 1], c)) {
                    add++;
                } else {
                    idx--;
                }
            }
            return idx + add;
        }

        private boolean isOpenParen(char c) {
            return c == '(' || c == '{' || c == '[';
        }

        private boolean isMatchParen(char close, char open) {
            return open == '(' && close == ')' || open == '{' && close == '}' || open == '[' && close == ']';
        }
    }

    public static void main(String[] args) {
        String s = "())";
        new _921().new Solution_FollowUp_GeneralizedParentheses().minAddToMakeValid(s);
    }
}
