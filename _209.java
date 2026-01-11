import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;

/**
 * 209. Minimum Size Subarray Sum
 */
public class _209 {
    class Solution1_Sliding_Window {
        public int minSubArrayLen(int target, int[] nums) {
            int left = 0, right = 0, winSum = 0, minWinLen = nums.length + 1;
            while (right < nums.length) { // [left, right)
                winSum += nums[right++];
                while (winSum >= target) {
                    minWinLen = Math.min(minWinLen, right - left);
                    winSum -= nums[left++];
                }
            }
            return minWinLen == nums.length + 1 ? 0 : minWinLen;
        }
    }

    class Solution2_Prefix_BinarySearch {
        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length;
            int[] prefix = new int[n + 1];
            for (int i = 0; i < n; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }
        
            int minLen = n + 1;
            // For each i, find the smallest j such that prefix[j] - prefix[i] ≥ target  →  prefix[j] ≥ target + prefix[i]
            for (int i = 0; i <= n; i++) {
                int toFind = target + prefix[i];
                int bound = binarySearch(prefix, i, n, toFind);
                if (bound <= n) {
                    minLen = Math.min(minLen, bound - i);
                }
            }
        
            return minLen == n + 1 ? 0 : minLen;
        }
        
        // Find the smallest index j such that prefix[j] >= target
        private int binarySearch(int[] prefix, int left, int right, int target) { // [0, n]
            while (left <= right) {
                int mid = (left + right) / 2;
                if (prefix[mid] >= target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }        
    }

    // Time: O(nlogn)
    class Solution3_FollowUp_Input_Negative_TreeMap_PreSum {
        // find min(j - i) that preSum[j] - preSum[i] >= target
        // ==> preSum[i] <= preSum[j] - target
        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length, preSum = 0, minLen = n + 1;
            TreeMap<Integer, Integer> preSumToIdx = new TreeMap<>();
            preSumToIdx.put(preSum, -1);
            for (int i = 0; i < n; i++) {
                preSum += nums[i];
                int preSumJMinusTarget = preSum - target;
                Integer preSumI = preSumToIdx.floorKey(preSumJMinusTarget);
                if (preSumI != null) {
                    minLen = Math.min(minLen, i - preSumToIdx.get(preSumI));
                }
                // here we're updating for larger index since this question is about minimum and not maximum subarray
                preSumToIdx.put(preSum, i);
            }
            return minLen == n + 1 ? 0 : minLen;
        }
    }

    // Time: O(n)
    class Solution4_FollowUp_Input_Negative_MonoQueue_PreSum {
        // find min(j - i) that preSum[j] - preSum[i] >= target
        // ==> preSum[i] <= preSum[j] - target
        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length, minLen = n + 1;
            int[] preSum = new int[n + 1];
            for (int i = 0; i < n; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
            }
            Deque<Integer> monoDeque = new ArrayDeque<>();
            // minValIdx <------> maxValIdx
            for (int i = 0; i <= n; i++) {
                // attempt to shrink from front as long as we meet the target
                while (!monoDeque.isEmpty() && preSum[i] - preSum[monoDeque.peek()] >= target) {
                    minLen = Math.min(minLen, i - monoDeque.poll());
                }
                // maintain increasing prefix sums in the deque
                while (!monoDeque.isEmpty() && preSum[i] <= preSum[monoDeque.peekLast()]) {
                    monoDeque.pollLast();
                }
                monoDeque.offer(i);
            }
            return minLen == n + 1 ? 0 : minLen;
        }
    }

    class Solution5_FollowUp_Input_Negative_Mono_Queue_PreSum {
        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length;
            int minLen = n + 1;
            
            // Each entry: { prefixSumAtIndex, index }
            Deque<int[]> deque = new ArrayDeque<>();
            deque.offerLast(new int[]{0, -1});  // prefixSum=0 at index=-1

            int prefixSum = 0;
            for (int i = 0; i < n; i++) {
                prefixSum += nums[i];
                
                // Shrink from front while valid
                while (!deque.isEmpty() && prefixSum - deque.peekFirst()[0] >= target) {
                    minLen = Math.min(minLen, i - deque.pollFirst()[1]);
                }
                
                // Maintain increasing prefix sums
                while (!deque.isEmpty() && prefixSum <= deque.peekLast()[0]) {
                    deque.pollLast();
                }
                
                // Add current prefix sum and index
                deque.offerLast(new int[]{prefixSum, i});
            }
            
            return (minLen <= n) ? minLen : 0;
        }
    }
}
