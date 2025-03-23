import java.util.Deque;
import java.util.LinkedList;

/**
 * 232. Implement Queue using Stacks
 */
public class _232 {
    class MyQueue {

        Deque<Integer> stk;
        Deque<Integer> temp;

        public MyQueue() {
            stk = new LinkedList<>();
            temp = new LinkedList<>();
        }

        public void push(int x) {
            stk.push(x);
        }

        public int pop() {
            while (!stk.isEmpty()) {
                temp.push(stk.pop());
            }
            int popNum = temp.pop();
            while (!temp.isEmpty()) {
                stk.push(temp.pop());
            }
            return popNum;
        }

        public int peek() {
            while (!stk.isEmpty()) {
                temp.push(stk.pop());
            }
            int peekNum = temp.peek();
            while (!temp.isEmpty()) {
                stk.push(temp.pop());
            }
            return peekNum;
        }

        public boolean empty() {
            return stk.isEmpty();
        }
    }

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
}
