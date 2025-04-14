import java.util.Deque;
import java.util.LinkedList;

/**
 * 227. Basic Calculator II
 */
public class _227 {
    class Solution1 {
        public int calculate(String s) {
            if (s == null || s.length() == 0) return 0;
            int i = 0, total = 0, prev = 0, curr = 0;
            char operator = '+';
            while (i < s.length()) {
                char c = s.charAt(i++);
                if (Character.isDigit(c)) {
                    curr = curr * 10 + (c - '0');
                } else if (c != ' ') {
                    prev = cal(prev, curr, operator);
                    if (c == '+' || c == '-') {
                        total += prev;
                        prev = 0;
                    }
                    curr = 0;
                    operator = c;
                }
            }
            return total + cal(prev, curr, operator);
        }
    
        private int cal(int prev, int curr, char operator) {
            switch (operator) {
                case '+':
                    prev += curr;
                    break;
                case '-':
                    prev -= curr;
                    break;
                case '*':
                    prev *= curr;
                    break;
                case '/':
                    prev /= curr;
            }
            return prev;
        }
    }

    class Solution2_Without_Stack {
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

    class Solution3_With_Stack {
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

    // allowed: "3 + -2 * 4"
    // "-1 + 2 * 3"
    // handles duplicate + and - operand
    class FollowUp_Negative_Numbers_Allowed {

        public int calculate(String s) {
            int total = 0, curr = 0, prev = 0, sign = 1; // default sign as 1
            char lastOpr = '+';

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') {
                    // num = num * 10 + **sign** * digit
                    curr = curr * 10 + sign * (c - '0');
                }
                if (isOperator(c) || i + 1 == s.length()) {
                    // set sign and reset
                    int nextOprIdx = findNextOprIdx(i, s);
                    if (i + 1 < s.length() && nextOprIdx != -1 && s.charAt(nextOprIdx) == '-') { // doesn't work due to white spaces
                        sign = -1;
                        i = nextOprIdx;
                    } else {
                        sign = 1;
                    }
                    // normal operations
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
        
        private int findNextOprIdx(int idx, String s) {
            while (++idx < s.length()) {
                if (isOperator(s.charAt(idx))) {
                    return idx;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        Solution1 solution = new _227().new Solution1();
        System.out.println(solution.calculate("3-2*2")); // -1
        System.out.println(solution.calculate(" 3/2 ")); // 1.5
        System.out.println(solution.calculate(" 3+5 / 2 ")); // 5.5

        FollowUp_Negative_Numbers_Allowed followUp = new _227().new FollowUp_Negative_Numbers_Allowed();
        String s = "3 + -2 * 4";
        System.out.println(followUp.calculate(s)); // -5
        s = "-2 + -11 * -1";
        System.out.println(followUp.calculate(s)); // 9
        s = "-1 + 2 * 3";
        System.out.println(followUp.calculate(s)); // 5
    }
}
