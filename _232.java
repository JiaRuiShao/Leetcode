import java.util.Stack;

/**
 * 232. Implement Queue using Stacks
 */
public class _232 {
    class MyQueue {

        Stack<Integer> stk;
        Stack<Integer> tmp;

        public MyQueue() {
            stk = new Stack<>();
            tmp = new Stack<>();
        }
        
        public void push(int x) {
            // reverse order of stk
            while (!stk.isEmpty()) {
                tmp.push(stk.pop());
            }
            stk.push(x);
            while (!tmp.isEmpty()) {
                stk.push(tmp.pop());
            }
        }
        
        public int pop() {
            return stk.pop();
        }
        
        public int peek() {
            return stk.peek();
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
