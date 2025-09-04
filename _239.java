import java.util.Deque;
import java.util.ArrayDeque;

/**
 * 239. Sliding Window Maximum
 */
public class _239 {
    class Solution1_Sliding_Window_Mono_Queue {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length - k + 1;
            if (n <= 0) return new int[0];
            int[] max = new int[n];
            Deque<Integer> monoQueue = new ArrayDeque<>();
            int left = 0, right = 0, pos = 0;
            while (right < nums.length) {
                int add = nums[right++];
                while (!monoQueue.isEmpty() && monoQueue.peekLast() < add) {
                    monoQueue.pollLast();
                }
                monoQueue.offerLast(add);
                if (right - left == k) {
                    int currMax = monoQueue.peekFirst();
                    max[pos++] = currMax;
                    
                    if (nums[left++] == currMax) {
                        monoQueue.pollFirst();
                    }
                }
            }
            return max;
        }
    }
}
