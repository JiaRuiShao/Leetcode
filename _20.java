import java.util.Deque;
import java.util.LinkedList;

/**
 * 20. Valid Parentheses
 */
public class _20 {
    // this is a wrong solution, consider the case where s = "([)]" that it shouldn't be considered as valid
    class Solution0_Wrong_Solution {
        public boolean isValid(String s) {
            int leftOpen = 0, curlyOpen = 0, squareOpen = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (isOpenParenthesis(c)) {
                    switch(c) {
                        case '(': {
                            leftOpen++;
                            break;
                        }
                        case '{': {
                            curlyOpen++;
                            break;
                        }
                        case '[': {
                            squareOpen++;
                            break;
                        }
                    }
                } else {
                    switch(c) {
                        case ')': {
                            if (leftOpen == 0) return false;
                            leftOpen--;
                            break;
                        }
                        case '}': {
                            if (curlyOpen == 0) return false;
                            curlyOpen--;
                            break;
                        }
                        case ']': {
                            if (squareOpen == 0) return false;
                            squareOpen--;
                            break;
                        }
                    }
                }
            }
            return leftOpen == 0 && curlyOpen == 0 && squareOpen == 0;
        }

        private boolean isOpenParenthesis(char c) {
            return c == '(' || c == '{' || c == '[';
        }
    }
    class Solution1_Stack {
        public boolean isValid(String s) {
            Deque<Character> stk = new LinkedList<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (isOpenParenthesis(c)) {
                    stk.push(c);
                } else if (stk.isEmpty() || !isMatchingParentheses(c, stk.peek())) {
                    return false;
                } else {
                    stk.pop();
                }
            }
            return stk.isEmpty();
        }

        private boolean isOpenParenthesis(char c) {
            return c == '(' || c == '{' || c == '[';
        }

        private boolean isMatchingParentheses(char close, char open) {
            return open == '(' && close == ')' || open == '{' && close == '}' || open == '[' && close == ']';
        }
    }
}
