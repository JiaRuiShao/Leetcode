import java.util.*;

/**
 * 224. Basic Calculator
 * Problems to be solved:
 * 1. how to read the chars as int number
 * 2. how to deal with the brackets `()`
 * 3. handle corner cases
 */
public class _224 {
    public static class Solution1_Recursion {
        int pos;

        public int calculate(String s) {
            pos = 0;
            return calcExpr(s);
        }

        private int calcExpr(String s) {
            int res = 0;
            int curNum = 0;
            int sign = 1;
            while (pos < s.length()) {
                char c = s.charAt(pos++);
                if (c == ' ') {
                    continue; // skip spaces
                }
                if (c >= '0' && c <= '9') { // check if char is a valid digit, only used for ASCII characters
                    curNum = curNum * 10 + c - '0';
                } else if (c == '(') {
                    // start a new recursion for brackets expression
                    curNum = calcExpr(s);
                } else if (c == ')') {
                    // end of current subexpression, return the result
                    return res + sign * curNum;
                } else if (c == '+' || c == '-') {
                    res += sign * curNum;
                    curNum = 0;
                    sign = (c == '-') ? -1 : 1;
                }
            }
            return res + sign * curNum;
        }

    }

    public static class Solution2_Iterative_Stack {

        public int calculate(String s) {
            Deque<Integer> stack = new LinkedList<>();
            int res = 0;
            int curNum = 0;
            int sign = 1;

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') {
                    curNum = 10 * curNum  + (c - '0');
                } else if (c == '+') {
                    res += sign * curNum;
                    curNum = 0;
                    sign = 1;
                } else if (c == '-') {
                    res += sign * curNum;
                    curNum = 0;
                    sign = -1;
                } else if (c == '(') {
                    // push result & sign to stack
                    stack.push(res);
                    stack.push(sign);
                    // reset result & sign for the expression in the parenthesis
                    sign = 1;
                    res = 0;
                } else if (c == ')'){
                    res += sign * curNum;
                    curNum = 0;
                    res *= stack.pop();
                    res += stack.pop();

                }
            }
            if (curNum != 0) res += sign * curNum;
            return res;
        }

    }
}
