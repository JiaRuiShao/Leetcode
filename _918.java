import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 918. Maximum Sum Circular Subarray
 */
public class _918 {
    class Solution1_Prefix_Sum_Mono_Queue {
        
        class SumIdx {
            int sum, idx;
            public SumIdx(int sum, int idx) {
                this.sum = sum;
                this.idx = idx;
            }
        }

        public int maxSubarraySumCircular(int[] nums) {
            int n = nums.length, sum = 0, maxSum = Integer.MIN_VALUE;
            Deque<SumIdx> minQ = new ArrayDeque<>(); // queue of indices with mono increasing preSum[idx]
            minQ.offerLast(new SumIdx(sum, -1));
            for (int i = 0; i < 2 * n; i++) {
                int num = nums[i % n];
                sum += num;
                // make sure q.size() <= n to make sure same elem used only once
                if (!minQ.isEmpty() && i - minQ.peekFirst().idx > n) {
                    minQ.pollFirst();
                }
                // max(preSum[i] - preSum[j]) == > given a fixed preSum[i], find min(preSum[j])
                if (!minQ.isEmpty()) maxSum = Math.max(maxSum, sum - minQ.peekFirst().sum);
                // maintain increasing pre[] in deque
                while (!minQ.isEmpty() && minQ.peekLast().sum >= sum) {
                    minQ.pollLast();
                }
                minQ.offerLast(new SumIdx(sum, i));
            }
            return maxSum;
        }
    }
}
