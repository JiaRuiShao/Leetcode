import java.util.Deque;
import java.util.LinkedList;

/**
 * 1944. Number of Visible People in a Queue.
 * Understand process of monotonic stack better
 */
public class _1944 {

    class Solution1_Monotonic_Stack {
        public int[] canSeePersonsCount(int[] heights) {
            int n = heights.length;
            Deque<Integer> stack = new LinkedList<>();
            int[] seenToRight = new int[n];

            for (int i = n - 1; i >= 0; i--) {
                int height = heights[i];
                // count how many ppl are in betw myself and NGL
                int pplInBetw = 0;
                while (!stack.isEmpty() && stack.getFirst() < height) {
                    stack.removeFirst();
                    pplInBetw++;
                }
                // # ppl seen is #pplInBetw + 1 if there's a valid NGL or + 0 if not
                pplInBetw = stack.isEmpty() ? pplInBetw : pplInBetw + 1;
                seenToRight[i] = pplInBetw;
                stack.addFirst(height);
            }
            return seenToRight;
        }
    }
}
