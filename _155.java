import java.util.Deque;
import java.util.LinkedList;

/**
 * 155. Min Stack
 */
public class _155 {
    class MinStack {

        Deque<Integer> minStk;
        Deque<Integer> stk;

        public MinStack() {
            minStk = new LinkedList<>();
            stk = new LinkedList<>();
        }

        public void push(int val) {
            stk.push(val);
            if (minStk.isEmpty() || minStk.peek() >= val) {
                minStk.push(val);
            }
        }

        public void pop() {
            int val = stk.pop();
            if (getMin() == val) {
                minStk.pop();
            }
        }

        public int top() {
            return stk.peek();
        }

        public int getMin() {
            return minStk.peek();
        }
    }
}
