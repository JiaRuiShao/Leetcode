import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 155. Min Stack
 */
public class _155 {
    class Solution1_MinStack {
        class MinStack {
            Deque<Integer> minStk;
            Deque<Integer> stk; // mono decreasing stack from rear to head
    
            public MinStack() {
                minStk = new LinkedList<>();
                stk = new LinkedList<>();
            }
    
            public void push(int val) {
                stk.push(val);
                if (minStk.isEmpty() || minStk.peek() >= val) { // curr val <= minVal in minStk, push into the minStk
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

    class Solution2 {
        /*
         * We could store a pair onto the stack: the first element in the pair is the value itself,
         * the second element in the pair is the current minimum element so far seen on the stack.
         */
        class MinStack {
            Deque<int[]> stack;

            public MinStack() {
                stack = new LinkedList<>();
            }

            public void push(int val) {
                if (!stack.isEmpty()) {
                    int[] last = stack.peekLast();
                    int currentMin = last[1];
                    if (val <= currentMin) {
                        stack.addLast(new int[] {val, val});
                    } else {
                        stack.addLast(new int[] {val, currentMin});
                    }
                } else {
                    stack.addLast(new int[] {val, val});
                }
            }

            public void pop() {
                stack.pollLast();
            }

            public int top() {
                return stack.peekLast()[0];
            }

            public int getMin() {
                return stack.peekLast()[1];
            }
        }
    }

    class FollowUp_Max_Stack {

        class MaxStack {
            Deque<Integer> stk;
            Deque<int[]> maxStk; // tracks maxVal + frequency
            
            public MaxStack() {
                stk = new ArrayDeque<>();
                maxStk = new ArrayDeque<>();
            }

            public void push(int val) {
                stk.push(val);
                int[] maxPair = maxStk.peek();
                if (maxPair == null || val > maxPair[0]) {
                    maxStk.push(new int[]{val, 1});
                } else if (val == maxPair[0]) {
                    maxPair[1]++;
                }
            }

            public void pop() {
                int val = stk.pop();
                int[] maxPair = maxStk.peek();
                if (maxPair != null && val == maxPair[0] && --maxPair[1] == 0) {
                    maxStk.pop();
                }
            }

            public int top() {
                return stk.isEmpty() ? -1 : stk.peek();
            }

            public int getMax() {
                return maxStk.isEmpty() ? -1 : maxStk.peek()[0];
            }
        }
    }
}
