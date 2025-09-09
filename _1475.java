import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1475. Final Prices With a Special Discount in a Shop
 */
public class _1475 {
    class Solution1_MonoStack {
        public int[] finalPrices(int[] prices) {
            int n = prices.length;
            int[] nle = new int[n];
            Deque<Integer> minStk = new ArrayDeque<>(); // mono increasing stack
            for (int i = n - 1; i >= 0; i--) {
                int price = prices[i];
                while (!minStk.isEmpty() && minStk.peek() > price) {
                    minStk.pop();
                }
                int discount = minStk.isEmpty() ? 0 : minStk.peek();
                nle[i] = price - discount;
                minStk.push(price);
            }
            return nle;
        }
    }
}
