import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1438. Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit
 */
public class _1438 {
    // Time: O(n^2)
    // Space: O(1)
    class Solution0_Sliding_Window_Brute_Force {

    }

    // Time: O(n)
    // Space: O(n)
    class Solution1_Sliding_Window_Mono_Queue {
        public int longestSubarray(int[] nums, int limit) {
            int n = nums.length, maxLen = 0;
            Deque<Integer> minQ = new ArrayDeque<>();
            Deque<Integer> maxQ = new ArrayDeque<>();
            int left = 0, right = 0, winDiff = 0;
            while (right < n) {
                int add = nums[right++];
                while (!minQ.isEmpty() && minQ.peekLast() > add) {
                    minQ.pollLast();
                }
                minQ.offerLast(add);
                while (!maxQ.isEmpty() && maxQ.peekLast() < add) {
                    maxQ.pollLast();
                }
                maxQ.offerLast(add);
                winDiff = maxQ.peekFirst() - minQ.peekFirst();
                while (left < right && winDiff > limit) {
                    int rem = nums[left++];
                    if (rem == minQ.peekFirst()) {
                        minQ.pollFirst();
                    }
                    if (rem == maxQ.peekFirst()) {
                        maxQ.pollFirst();
                    }
                    winDiff = maxQ.peekFirst() - minQ.peekFirst();
                }
                maxLen = Math.max(maxLen, right - left);
            }
            return maxLen;
        }
    }

    // Time: O(nlogn)
    // Space: O(n)
    class Solution2_Sliding_Window_PriorityQueue {

    }
}
