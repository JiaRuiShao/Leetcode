import java.util.Deque;
import java.util.LinkedList;

/**
 * 227. Basic Calculator II
 */
public class _227 {
    class Solution1_Without_Stack {
        public int calculate(String s) {
            int total = 0, curr = 0, prev = 0;
            char lastOpr = '+';

            for (int pos = 0; pos < s.length(); pos++) {
                char c = s.charAt(pos);
                if (c >= '0' && c <= '9') {
                    curr = curr * 10 + (c - '0');
                }
                if (isOperator(c) || pos + 1 == s.length()) {
                    switch (lastOpr) {
                        case '+':
                            total += prev;
                            prev = curr;
                            break;
                        case '-':
                            total += prev;
                            prev = -curr;
                            break;
                        case '*':
                            prev *= curr;
                            break;
                        case '/':
                            prev /= curr;
                            break;
                    }
                    curr = 0;
                    lastOpr = c;
                }
            }
            return total + prev;
        }

        private boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/';
        }
    }

    class Solution2_With_Stack {
        public int calculate(String s) {
            Deque<Integer> st = new LinkedList<>();
            int num = 0;
            char lastOpr = '+';

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                if (Character.isDigit(c)) {
                    num = num * 10 + (c - '0');
                }

                if (isOperator(c) || i == s.length() - 1) {
                    if (lastOpr == '+') st.push(num);
                    else if (lastOpr == '-') st.push(-num);
                    else if (lastOpr == '*') st.push(st.pop() * num);
                    else if (lastOpr == '/') st.push(st.pop() / num);

                    num = 0;
                    lastOpr = c;
                }
            }

            int ans = 0;

            while (!st.isEmpty()) {
                ans += st.pop();
            }

            return ans;
        }

        private boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/';
        }
    }

    public static void main(String[] args) {
        Solution1_Without_Stack solution = new _227().new Solution1_Without_Stack();
        System.out.println(solution.calculate("3-2*2")); // -1
        System.out.println(solution.calculate(" 3/2 ")); // 1.5
        System.out.println(solution.calculate(" 3+5 / 2 ")); // 5.5
    }
}
