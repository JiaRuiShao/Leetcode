import java.util.LinkedList;
import java.util.Queue;

/**
 * 225. Implement Stack using Queues
 */
public class _225 {
    class MyStack {

        Queue<Integer> q;
        Queue<Integer> empty;

        public MyStack() {
            q = new LinkedList<>();
            empty = new LinkedList<>();
        }

        public void push(int x) {
            empty.offer(x);
            while (!q.isEmpty()) {
                empty.offer(q.poll());
            }
            q = empty;
            empty = new LinkedList<>();
        }

        public int pop() {
            return q.poll();
        }

        public int top() {
            return q.peek();
        }

        public boolean empty() {
            return q.isEmpty();
        }
    }

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
}
