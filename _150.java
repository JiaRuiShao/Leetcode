import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 150. Evaluate Reverse Polish Notation
 */
public class _150 {
    // Time: O(n)
    // Space: O(n)
    class Solution1_Stack {
        public int evalRPN(String[] tokens) {
            Deque<Integer> stk = new ArrayDeque<>();
            for (String token : tokens) {
                switch (token) {
                    case "+" -> stk.push(stk.pop() + stk.pop());
                    case "-" -> {
                        int b = stk.pop(), a = stk.pop();
                        stk.push(a - b);
                    }
                    case "*" -> stk.push(stk.pop() * stk.pop());
                    case "/" -> {
                        int b = stk.pop(), a = stk.pop();
                        stk.push(a / b);
                    }
                    default -> stk.push(Integer.valueOf(token));
                }
            }
            return stk.peek();
        }
    }

    // Time: O(n^2)
    // Space: O(1)
    class Solution2_Modification_In_Place {
        public int evalRPN(String[] tokens) {
            int i = 0;                       // current scan index
            int activeLen = tokens.length;   // only tokens[0..activeLen-1] are live

            while (activeLen > 1) {
                // advance to next operator within the live window
                while (!isOperator(tokens[i])) i++;

                // tokens[i-2] (left) and tokens[i-1] (right) are the operands
                int left  = Integer.parseInt(tokens[i - 2]);
                int right = Integer.parseInt(tokens[i - 1]);
                char op   = tokens[i].charAt(0);

                // compute and overwrite operator slot with the result
                tokens[i] = String.valueOf(apply(op, left, right));

                // remove the two consumed operands by shifting left
                shiftLeftBy2(tokens, i - 2, activeLen);
                activeLen -= 2;

                // step back one to catch any new operator formed by the collapse
                i = Math.max(0, i - 1);
            }

            return Integer.parseInt(tokens[0]);
        }

        // Only treat single-char tokens that are one of +-*/ as operators.
        private boolean isOperator(String t) {
            return t.length() == 1 && "+-*/".indexOf(t.charAt(0)) >= 0;
        }

        private int apply(char op, int left, int right) {
            switch (op) {
                case '+': return left + right;
                case '-': return left - right;
                case '*': return left * right;
                default : return left / right; // truncates toward 0 as required
            }
        }

        // Shift the window [from .. activeLen) two steps left, overwriting operands.
        private void shiftLeftBy2(String[] a, int from, int activeLen) {
            for (int j = from; j < activeLen - 2; j++) {
                a[j] = a[j + 2];
            }
            // (Optional) clear trailing slots for debugging readability:
            // a[activeLen - 2] = a[activeLen - 1] = null;
        }
    }

}
