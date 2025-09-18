import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 402. Remove K Digits
 */
public class _402 {
    // a monotonic increasing stack to keep digits in smallest order, popping larger ones when possible
    class Solution1_MonoStack {
        public String removeKdigits(String num, int k) {
            int n = num.length();
            Deque<Integer> minStk = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                int curr = num.charAt(i) - '0';
                while (!minStk.isEmpty() && minStk.peek() > curr && k > 0) {
                    minStk.pop();
                    k--;
                }
                if (minStk.isEmpty() && curr == 0) {
                    continue;
                }
                minStk.push(curr);
            }
            while (k > 0 && !minStk.isEmpty()) {
                k--;
                minStk.pop();
            }
            StringBuilder sb = new StringBuilder();
            while (!minStk.isEmpty()) {
                sb.append(minStk.pop());
            }
            return sb.length() == 0 ? "0" : sb.reverse().toString();
        }
    }

    class Solution0_Backtrack_TLE {

    }
}
