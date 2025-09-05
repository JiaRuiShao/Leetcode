import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * 862. Shortest Subarray with Sum at Least K
 */
public class _862 {
    // We're not able to use sliding window here because the nums contains negative elem
    // here we're maintaining a minQ with monotonic increasing order of prefix sums with the corresponding idx
    // another tricky point for this question is define long type for sum to avoid integer overflow
    // Time: O(n)
    // Space: O(n)
    class Solution1_Prefix_Sum_Mono_Queue {
        class PreSumIdx {
            long sum;
            int idx;
            public PreSumIdx(long sum, int idx) {
                this.sum = sum;
                this.idx = idx;
            }
        }

        // The reason we're maintaining a minQ is that for given prefix sum, we need the previous preSum to be as small as possible to satisfy the condition: preSum[i] - preSum[prev] >= k
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length, minWinLen = n + 1;
            long sum = 0;
            Deque<PreSumIdx> minQ = new ArrayDeque<>(); // stores preSum and idx in mono increasing order
            minQ.offer(new PreSumIdx(sum, -1));
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                sum += num;
                while (!minQ.isEmpty() && sum - minQ.peekFirst().sum >= k) {
                    minWinLen = Math.min(minWinLen, i - minQ.pollFirst().idx);
                }
                while (!minQ.isEmpty() && minQ.peekLast().sum >= sum) {
                    minQ.pollLast();
                }
                minQ.offerLast(new PreSumIdx(sum, i));
            }
            return minWinLen == n + 1 ? -1 : minWinLen;
        }
    }

    public class Solution0_Brute_Force {
        // Time: O(n^2)
        // Space: O(1)
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length;
            int ans = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                long sum = 0; // use long to avoid overflow
                for (int j = i; j < n; j++) {
                    sum += nums[j];
                    if (sum >= k) {
                        ans = Math.min(ans, j - i + 1);
                        // For this fixed i, any further j gives longer length.
                        break;
                    }
                }
            }
            return ans == Integer.MAX_VALUE ? -1 : ans;
        }
    }

    public class Solution0_PrefixSum_BruteForce {
        // Time: O(n^2)
        // Space: O(n)
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length;
            long[] pre = new long[n + 1];
            for (int i = 0; i < n; i++) pre[i + 1] = pre[i] + nums[i];

            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j <= n; j++) {
                    if (pre[j] - pre[i] >= k) {
                        ans = Math.min(ans, j - i);
                        break; // shortest for this i
                    }
                }
            }
            return ans == Integer.MAX_VALUE ? -1 : ans;
        }
    }

    // Why this is not valid solution? 
    // Because the largest value ≤ target does not necessarily have the largest index. There can be a smaller prefix value with a later index that gives a shorter window, and your floorKey skips it
    class Wrong_Solution_TreeMap {
        // sum[i] - preSum >= k ==> preSum <= sum[i] - k
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length, minLen = n + 1;
            long sum = 0;
            TreeMap<Long, Integer> preSumToIdx = new TreeMap<>();
            preSumToIdx.put(sum, -1);
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                sum += num;
                Long preSum = preSumToIdx.floorKey(sum - k);
                if (preSum != null) {
                    minLen = Math.min(minLen, i - preSumToIdx.get(preSum));
                }
                preSumToIdx.put(sum, i);
            }
            return minLen == n + 1 ? -1 : minLen;
        }
    }

    class Solution1_TreeMap {
        // Time: O(nlogn)
        // Space: O(n)
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length, minLen = n + 1;
            long sum = 0;
            TreeMap<Long, Integer> preSumToIdx = new TreeMap<>();
            preSumToIdx.put(sum, -1);
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                sum += num;
                while (!preSumToIdx.isEmpty() && preSumToIdx.firstKey() <= sum - k) {
                    Map.Entry<Long, Integer> e = preSumToIdx.pollFirstEntry();
                    minLen = Math.min(minLen, i - e.getValue());
                }
                preSumToIdx.put(sum, i);
            }
            return minLen == n + 1 ? -1 : minLen;
        }
    }

    class Solution2_PriorityQueue {
        class PreSumIdx {
            long sum;
            int idx;
            public PreSumIdx(long sum, int idx) {
                this.sum = sum;
                this.idx = idx;
            }
        }

        // Time: O(nlogn)
        // Space: O(n)
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length, minLen = n + 1;
            long sum = 0;
            PriorityQueue<PreSumIdx> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a.sum, b.sum));
            minHeap.offer(new PreSumIdx(sum, -1));
            for (int i = 0; i < n; i++) {
                int num = nums[i];
                sum += num;
                while (!minHeap.isEmpty() && minHeap.peek().sum <= sum - k) {
                    minLen = Math.min(minLen, i - minHeap.poll().idx);
                }
                minHeap.offer(new PreSumIdx(sum, i));
            }
            return minLen == n + 1 ? -1 : minLen;
        }
    }
}