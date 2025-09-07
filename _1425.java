import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * 1425. Constrained Subsequence Sum
 */
public class _1425 {
    class Solution1_DP_KadaneAlgo_MonoQueue {
        // dp[i] is max subseq sum that ends at index i, dp[i] = max(0, max(dp[i-k], ..., dp[i-1])) + nums[i]
        public int constrainedSubsetSum(int[] nums, int k) {
            int n = nums.length, maxSum = nums[0];
            int[] dp = new int[n]; // saves currMax for each index
            Deque<Integer> maxQ = new ArrayDeque<>(); // q for indices that keeps order of mono decreasing dp[i]
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                while (!maxQ.isEmpty() && maxQ.peekFirst() < i - k) {
                    maxQ.pollFirst();
                }
                int prevKMax = maxQ.isEmpty() ? 0 : dp[maxQ.peekFirst()];
                dp[i] = Math.max(0, prevKMax) + num;
                while (!maxQ.isEmpty() && dp[maxQ.peekLast()] <= dp[i]) {
                    maxQ.pollLast();
                }
                maxQ.offerLast(i);
                maxSum = Math.max(maxSum, dp[i]);
            }
            return maxSum;
        }
    }

    class Solution2_DP_KadaneAlgo_TreeMap {
        // maxSum[i] = max(0, lastKMaxSum) + nums[i]
        public int constrainedSubsetSum(int[] nums, int k) {
            int n = nums.length, maxSum = nums[0], currMax = 0;
            TreeMap<Integer, Integer> map = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                while (!map.isEmpty() && map.lastEntry().getValue() < i - k) {
                    map.remove(map.lastKey());
                }
                int lastKMaxSum = map.isEmpty() ? 0 : map.lastKey();
                currMax = Math.max(lastKMaxSum + num, num);
                maxSum = Math.max(maxSum, currMax);
                map.put(currMax, i);
            }
            return maxSum;
        }
    }

    class Solution3_DP_KadaneAlgo_PriorityQueue {
        class MaxSumIdx {
            int maxSum, idx;
            public MaxSumIdx(int maxSum, int idx) {
                this.maxSum = maxSum;
                this.idx = idx;
            }
        }

        // maxSum[i] = max(0, lastKMaxSum) + nums[i]
        public int constrainedSubsetSum(int[] nums, int k) {
            int n = nums.length, maxSum = nums[0], currMax = 0;
            PriorityQueue<MaxSumIdx> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b.maxSum, a.maxSum));
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                while (!maxHeap.isEmpty() && maxHeap.peek().idx < i - k) {
                    maxHeap.poll();
                }
                int lastKMaxSum = maxHeap.isEmpty() ? 0 : maxHeap.peek().maxSum;
                currMax = Math.max(lastKMaxSum + num, num);
                maxSum = Math.max(maxSum, currMax);
                maxHeap.offer(new MaxSumIdx(currMax, i));
            }
            return maxSum;
        }
    }
}
