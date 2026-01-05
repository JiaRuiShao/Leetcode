import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * 1425. Constrained Subsequence Sum
 * 
 * - S1: Recursion with memo O(nk), O(n)
 * - S2: Bottom-Up DP O(nk), O(n)
 * - S3: Kadane + MonoQueue O(n), O(k)
 * 
 * Followup:
 * - K vary by position k[i] for each i? DP only
 */
public class _1425 {
    // Time: O(nk)
    // Space: O(n)
    class Solution0_Recursion_WithMemo_TLE {
        public int constrainedSubsetSum(int[] nums, int k) {
            int[] memo = new int[nums.length]; // max sum seq ends at i
            Arrays.fill(memo, Integer.MIN_VALUE);
            dp(nums, nums.length - 1, k, memo);
            return Arrays.stream(memo).max().getAsInt();
        }

        // max(max(dp[i-k..i-1]), 0) + nums[i]
        private int dp(int[] nums, int i, int k, int[] memo) {
            if (i < 0) return 0;
            if (memo[i] != Integer.MIN_VALUE) return memo[i];
            int maxSum = 0;
            for (int j = 1; j <= k; j++) {
                maxSum = Math.max(maxSum, dp(nums, i - j, k, memo));
            }
            memo[i] = maxSum + nums[i];
            return memo[i];
        }
    }

    class Solution0_DP_TLE {
        public int constrainedSubsetSum(int[] nums, int k) {
            int n = nums.length, maxSum = Integer.MIN_VALUE;
            int[] dp = new int[n]; // max sum subseq ends at i
            for (int i = 0; i < n; i++) {
                for (int j = 1; j <= k && i - j >= 0; j++) {
                    dp[i] = Math.max(dp[i], dp[i - j]);
                }
                dp[i] += nums[i];
                maxSum = Math.max(maxSum, dp[i]);
            }
            return maxSum;
        }
    }

    // Time: O(n)
    // Space: O(k)
    class Solution1_DP_KadaneAlgo_MonoQueue {
        class IdxSum {
            int idx, maxSum;
            IdxSum(int idx, int maxSum) {
                this.idx = idx;
                this.maxSum = maxSum;
            }
        }
        
        public int constrainedSubsetSum(int[] nums, int k) {
            int n = nums.length, maxSum = Integer.MIN_VALUE;
            Deque<IdxSum> maxQ = new ArrayDeque<>();
            maxQ.offer(new IdxSum(-1, 0));
            for (int i = 0; i < n; i++) {
                while (!maxQ.isEmpty() && i - maxQ.peekFirst().idx > k) {
                    maxQ.pollFirst();
                }
                int lastMaxSum = maxQ.isEmpty() ? 0 : maxQ.peekFirst().maxSum;
                int currMaxSum = Math.max(lastMaxSum, 0) + nums[i];
                maxSum = Math.max(maxSum, currMaxSum);
                while (!maxQ.isEmpty() && maxQ.peekLast().maxSum < currMaxSum) {
                    maxQ.pollLast();
                }
                maxQ.offerLast(new IdxSum(i, currMaxSum));
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

    // Time: O(n × max(k))
    // Space: O(n)
    private class Followup_KVary {
        public int constrainedSubsetSumVariable(int[] nums, int[] k) {
            int n = nums.length;
            int[] dp = new int[n];
            
            int maxSum = Integer.MIN_VALUE;
            
            for (int i = 0; i < n; i++) {
                dp[i] = nums[i];
                
                // Look back k[i] positions
                for (int j = Math.max(0, i - k[i]); j < i; j++) {
                    dp[i] = Math.max(dp[i], dp[j] + nums[i]);
                }
                
                maxSum = Math.max(maxSum, dp[i]);
            }
            
            return maxSum;
        }
    }
}
