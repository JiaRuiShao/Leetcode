/**
 * 772. Basic Calculator III
 */
public class _772 {

    class Solution1_Global_Pointer {
        int i = 0;
        public int calculate(String s) {
            if (s == null || s.length() == 0) return 0;
            int total = 0, curr = 0, prev = 0;
            char operator = '+';

            while (i < s.length()) {
                char c = s.charAt(i++);
                if (Character.isDigit(c)) {
                    curr = curr * 10 + c - '0';
                } else if (c == '(') {
                    curr = calculate(s);
                } else if (c == ')') {
                    break;
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
        private int cal(int prev, int curr, char opr) {
            if (opr == '+') return prev + curr;
            else if (opr == '-') return prev - curr;
            else if (opr == '*') return prev * curr;
            else return prev / curr;
        }
    }
    class Solution3_My_Original_Solution {
        int i = 0;
        public int calculate(String s) {
            int total = 0, prev = 0, curr = 0;
            char operator = '+';
            for ( ;i < s.length(); i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) {
                    curr = curr * 10 + (c - '0');
                } else if (c == '(') {
                    i++;
                    curr = calculate(s);
                    c = s.charAt(i);
                } else if (c == ')') {
                    break;
                }
                
                if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.length() - 1 || (i + 1 < s.length() && s.charAt(i + 1) == ')')) {
                    switch(operator) {
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
                    operator = c;
                }
            }
            return total + prev;
        }
    }

    public static void main(String[] args) {
        Solution3_My_Original_Solution s = new _772().new Solution3_My_Original_Solution();
        String str = "2*(5+5*2)/3+(6/2+8)";
        System.out.println(s.calculate(str));
    }
}
