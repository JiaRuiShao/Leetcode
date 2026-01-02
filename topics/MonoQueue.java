package topics;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import helper.IdxSum;

public class MonoQueue {
    /**
     * GENERIC TEMPLATE
     * 
     * Choose type based on what you need:
     * - INCREASING (min at front): for minimum queries
     * - DECREASING (max at front): for maximum queries
     */
    public void monotonicQueuePattern(int[] nums) {
        Deque<Integer> queue = new ArrayDeque<>();  // Usually stores indices
        
        for (int i = 0; i < nums.length; i++) {
            // STEP 1: Remove invalid elements from front
            // (e.g., outside sliding window)
            while (!queue.isEmpty() && isInvalid(queue.peekFirst(), i)) {
                queue.pollFirst();
            }
            
            // STEP 2: Process result
            // Front of queue has the answer (min or max)
            processResult(queue.peekFirst());

            // STEP 3: Maintain monotonic property
            // Remove from back while monotonic property violated
            while (!queue.isEmpty() && shouldRemove(nums, queue.peekLast(), i)) {
                queue.pollLast();
            }
            
            // STEP 4: Add current element
            queue.offerLast(i);
            
        }
    }
    
    public int maxSubArrayAtLeastK(int[] nums, int k) {
        int maxSum = Integer.MIN_VALUE, sum = 0;
        Deque<IdxSum> minQ = new ArrayDeque<>();
        minQ.offer(new IdxSum(-1, 0));  // index -1 for empty prefix
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            
            // Check if window has at least k elements
            if (i - minQ.peekFirst().idx >= k) {
                maxSum = Math.max(maxSum, sum - minQ.peekFirst().sum);
            }
            
            // Maintain monotonic increasing (keep minimum at front)
            while (!minQ.isEmpty() && minQ.peekLast().sum > sum) {
                minQ.pollLast();
            }
            minQ.offerLast(new IdxSum(i, sum));
        }
        
        return maxSum;
    }

    public int maxSubarrayWithAtMostK(int[] nums) {
        int n = nums.length, sum = 0, maxSum = Integer.MIN_VALUE;
        Deque<IdxSum> minQ = new ArrayDeque<>(); // queue of indices with mono increasing preSum[idx]
        minQ.offerLast(new IdxSum(-1, sum)); // empty prefix sum

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
            minQ.offerLast(new IdxSum(i, sum));
        }
        return maxSum;
    }
}
