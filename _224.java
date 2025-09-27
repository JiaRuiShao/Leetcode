import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 224. Basic Calculator
 */
public class _224 {
    // Clarifications:
    // operators supported? only + - ( )
    // input is valid expression
    // constraint on numbers value? If not <= 18, use BigInteger

    // Solutions:
    // 1 - Recursion
    // 2 - Stack
    class Solution1_Recursion {
        private int i = 0;

        public int calculate(String s) {
            char[] arr = s.toCharArray();
            i = 0;
            return calcExpr(arr);
        }

        private int calcExpr(char[] arr) {
            int res = 0, curr = 0, opr = 1;
            while (i < arr.length) {
                char c = arr[i++];
                if (c >= '0' && c <= '9') {
                    curr = curr * 10 + (c - '0');
                } else if (c == '(') {
                    curr = calcExpr(arr);
                } else if (c == ')') {
                    break;
                } else if (c == '+' || c == '-') {
                    res += opr * curr;
                    curr = 0;
                    opr = (c == '+') ? 1 : -1;
                }
            }
            return res + opr * curr;
        }
    }

    class Solution2_Iterative_Stack {
        public int calculate(String s) {
            Deque<Integer> stack = new ArrayDeque<>();
            int total = 0;
            int curNum = 0;
            int sign = 1;

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') {
                    curNum = 10 * curNum  + (c - '0');
                } else if (c == '+') {
                    total += sign * curNum;
                    curNum = 0;
                    sign = 1;
                } else if (c == '-') {
                    total += sign * curNum;
                    curNum = 0;
                    sign = -1;
                } else if (c == '(') {
                    // push result & sign to stack
                    stack.push(total);
                    stack.push(sign);
                    // reset result & sign for the expression in the parenthesis
                    sign = 1;
                    total = 0;
                } else if (c == ')'){
                    total += sign * curNum;
                    curNum = 0;
                    total *= stack.pop();
                    total += stack.pop();

                }
            }
            if (curNum != 0) total += sign * curNum;
            return total;
        }
    }

    class Solution3_My_Solution {
        public int calculate(String s) {
            // we either need this parenthesis index map or we need to use global variable to track the current index
            Deque<Integer> stk = new ArrayDeque<>();
            Map<Integer, Integer> parenthesisMap = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') {
                    stk.push(i);
                } else if (c == ')') {
                    parenthesisMap.put(stk.pop(), i);
                }
            }
            return calc(s, parenthesisMap, 0);
        }

        private int calc(String s, Map<Integer, Integer> parenthesisMap, int idx) {
            int total = 0;
            int num = 0;
            int sign = 1;
            while (idx < s.length()) {
                char c = s.charAt(idx++);
                if (c >= '0' && c <= '9') {
                    num = num * 10 + (c - '0');
                } else if (c == '(') {
                    num = calc(s, parenthesisMap, idx);
                    idx = parenthesisMap.get(idx - 1) + 1;
                } else if (c == ')') {
                    total += sign * num;
                    return total; // it's safe to return because # of left parenthesis == # right parenthesis
                } else if (c == '+') {
                    total += sign * num;
                    sign = 1;
                    num = 0;
                } else if (c == '-') {
                    total += sign * num;
                    sign = -1;
                    num = 0;
                }
            }
            return total + sign * num;
        }
    }
}
