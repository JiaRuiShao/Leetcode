import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * 901. Online Stock Span
 */
public class _901 {
    class Solution1_MonoStack {
        class StockSpanner {
            class Stock {
                int price, idx;
                public Stock(int price, int idx) {
                    this.price = price;
                    this.idx = idx;
                }
            }

            Deque<Stock> maxStack;
            int id;

            public StockSpanner() {
                maxStack = new ArrayDeque<>();
                id = 1;
            }
            
            public int next(int price) {
                while (!maxStack.isEmpty() && maxStack.peek().price <= price) {
                    maxStack.pop();
                }
                int span = maxStack.isEmpty() ? id : id - maxStack.peek().idx;
                maxStack.push(new Stock(price, id));
                id++;
                return span;
            }
        }
    }

    class Solution2_MonoStack {
        class StockSpanner {
            Stack<int[]> stk = new Stack<>(); // int[] tracks {price, days with prices <= current price}

            public int next(int price) {
                int count = 1;
                while (!stk.isEmpty() && price >= stk.peek()[0]) {
                    int[] prev = stk.pop();
                    count += prev[1];
                }
                stk.push(new int[]{price, count});
                return count;
            }
        }
    }
}
