import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1475. Final Prices With a Special Discount in a Shop
 */
public class _1475 {
    class Solution1_BF {
        public int[] finalPrices(int[] prices) {
            int n = prices.length;
            int[] result = new int[n];
            
            for (int i = 0; i < n; i++) {
                int discount = 0;
                
                // Find first item to the right with price ≤ current
                for (int j = i + 1; j < n; j++) {
                    if (prices[j] <= prices[i]) {
                        discount = prices[j];
                        break;  // Found first discount
                    }
                }
                
                result[i] = prices[i] - discount;
            }
            
            return result;
        }
    }

    class Solution2_MonoStack {
        public int[] finalPrices(int[] prices) {
            int n = prices.length;
            Deque<Integer> maxStk = new ArrayDeque<>();
            int[] finalPrices = new int[n];
            for (int i = n - 1; i >= 0; i--) {
                while (!maxStk.isEmpty() && maxStk.peek() > prices[i]) {
                    maxStk.pop();
                }
                finalPrices[i] = prices[i] - (maxStk.isEmpty() ? 0 : maxStk.peek());
                maxStk.push(prices[i]);
            }
            return finalPrices;
        }
    }
}
