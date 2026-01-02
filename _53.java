import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import helper.IdxSum;

/**
 * 53. Maximum Subarray
 * 
 * - S1: BF nested loop O(n^2), O(1)
 * - S2: prefixSum O(n), O(n)/O(1) [PREFERRED]
 * - S3: mono queue + prefixSum O(n), O(n)
 * - S4: DP Kadane's Algo O(n), O(n)/O(1) [PREFERRED]
 * 
 * Clarification:
 * - Can there be overflow issue?
 * - Can numbers be negative?
 * - Does subarray mean contiguous? Yes
 * - Min subarray length? >= 1
 * 
 * Followup:
 * - Return actual array, not just the sum? Have extra start, end & maxStart pointers
 * - Find min subarray sum instead? Flip the logic
 * - Solve the circular array version? LC 918 find maxSum (max subarray in middle) & totalSum - minSum (max subarray at ends); if all numbers are negative, return maxSum, else return max(maxSum, totalSum - minSum)
 * - Empty sum is allowed? Then default maxSum should be initialized as 0
 * - Find k non-overlapping maximum subarrays? 2D DP
 * - Maximum subarray sum with at least k elements? MonoQueue
 * - Maximum subarray sum with at most k elements? MonoQueue -- variant of 918 where k = n
 * - What if we want maximum product subarray instead of sum? LC 152
 * - What if we want maximum subarray sum with at most k deletions? DP
 */
public class _53 {
    class Solution0_BruteForce {
        public int maxSubArray(int[] nums) {
            int maxSubarray = Integer.MIN_VALUE;
            for (int i = 0; i < nums.length; i++) {
                int currentSubarray = 0;
                for (int j = i; j < nums.length; j++) {
                    currentSubarray += nums[j];
                    maxSubarray = Math.max(maxSubarray, currentSubarray);
                }
            }
            return maxSubarray;
        }
    }

    // Recurrence: dp[i] = max(nums[i], dp[i-1] + nums[i])
    class Solution1_DP_KadaneAlgo {
        public int maxSubArray(int[] nums) {
            int n = nums.length;
            
            // dp[i] = max subarray sum ending at index i
            int[] dp = new int[n];
            dp[0] = nums[0];
            
            int maxSum = dp[0];
            
            for (int i = 1; i < n; i++) {
                // Either extend previous subarray or start new
                dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
                maxSum = Math.max(maxSum, dp[i]);
            }
            
            return maxSum;
        }
    }

    // notice 
    class Solution1_DP_KadaneAlgo_Improved {
        public int maxSubArray(int[] nums) {
            int n = nums.length;
            if (n == 0) return 0;
            int maxSum = nums[0], currMaxSum = nums[0];
            // state equation: dp[i] = Math.max(nums[i], nums[i] + dp[i - 1])
            // for each elem, we can choose to use prev sum or start a new subarray
            for (int i = 1; i < n; i++) {
                currMaxSum = Math.max(nums[i], nums[i] + currMaxSum);
                maxSum = Math.max(maxSum, currMaxSum); // find max subarray sum ends at index i
            }
            return maxSum;
        }

        public int maxSubArraySpaceImproved(int[] nums) {
            int n = nums.length;
            if (n == 0) return 0;

            // base case
            int dp_0 = nums[0], dp_1 = 0, maxSum = dp_0;

            for (int i = 1; i < n; i++) {
                // dp[i] = max(nums[i], nums[i] + dp[i-1])
                dp_1 = Math.max(nums[i], nums[i] + dp_0);
                // check if curr subarray sum is the max
                maxSum = Math.max(maxSum, dp_1);
                dp_0 = dp_1;
            }
            
            return maxSum;
        }
    }

    class Solution2_MonoQueue {
        // max(prefixSum[i] - prevPrefixSum) ==> given prefixSum[i], find min(prevPrefixSum)
        public int maxSubArray(int[] nums) {
            int n = nums.length, sum = 0, maxSum = nums[0];
            Deque<Integer> minQ = new ArrayDeque<>(); // mono increasing q for prefixSum[i]
            minQ.offerLast(0);
            for (int i = 0; i < n; i++) {
                sum += nums[i];
                if (!minQ.isEmpty()) {
                    maxSum = Math.max(maxSum, sum - minQ.peekFirst()); // minQ.peekFirst() = min prefix sum so far
                }
                while (!minQ.isEmpty() && minQ.peekLast() >= sum) {
                    minQ.pollLast();
                }
                minQ.offerLast(sum);
            }
            return maxSum;
        }
    }

    // For this question, we don't need to record a minQ since we only need a global min prefix sum
    // max(prefixSum[i] - prefixSum[j]) ==> find min(prefixSum[j]) where j < i given a fixed prefixSum[i]
    class Solution2_MonoQueue_Improved {
        public int maxSubArray(int[] nums) {
            int n = nums.length, sum = 0, maxSum = nums[0], minSum = 0;
            for (int i = 0; i < n; i++) {
                sum += nums[i];
                maxSum = Math.max(maxSum, sum - minSum);
                minSum = Math.min(minSum, sum);
            }
            return maxSum;
        }
    }

    // This is a sliding window variant of Kadane's Algo
    // In Kadane's Algo, at each index i, we either 1) restart or 2) extend
    // Here in this sliding window implementation, we restart when prefix sum goes below 0
    class Solution2_Sliding_Window_Variant {
        public int maxSubArray(int[] nums) {
            int n = nums.length, left = 0, right = 0, winSum = 0, maxSum = nums[0];
            while (right < n) {
                int add = nums[right++];
                winSum += add;
                maxSum = Math.max(maxSum, winSum);
                while (left < right && winSum < 0) {
                    int rem = nums[left++];
                    winSum -= rem;
                }
            }
            return maxSum;
        }
    }

    // max(leftMax, rightMax, using elems from both ends)
    class Solution3_DivideAndConquer {
        public int maxSubArray(int[] nums) {
            return divide(nums, 0, nums.length - 1);
        }

        private int divide(int[] nums, int l, int r) {
            if (l == r) return nums[l]; // base case: single element

            int mid = l + (r - l) / 2;

            int leftMax = divide(nums, l, mid);
            int rightMax = divide(nums, mid + 1, r);
            int crossMax = crossSum(nums, l, mid, r);

            return Math.max(Math.max(leftMax, rightMax), crossMax);
        }

        // find max subarray sum crossing mid
        private int crossSum(int[] nums, int l, int mid, int r) {
            int leftSum = Integer.MIN_VALUE, sum = 0;
            for (int i = mid; i >= l; i--) {
                sum += nums[i];
                leftSum = Math.max(leftSum, sum);
            }

            int rightSum = Integer.MIN_VALUE;
            sum = 0;
            for (int i = mid + 1; i <= r; i++) {
                sum += nums[i];
                rightSum = Math.max(rightSum, sum);
            }

            return leftSum + rightSum;
        }
    }

    // after check, we can see we don't actually need the entire prefix sum array
    class Solution4_PrefixSum {
        // find max(preSum[j] - preSum[i]) is to find min(preSum[i]) for each j where j > i
        public int maxSubArray(int[] nums) {
            int n = nums.length;
            int[] preSum = new int[n + 1];
            preSum[0] = 0;
            for (int i = 1; i <= n; i++) {
                preSum[i] = preSum[i - 1] + nums[i - 1];
            }
            
            int maxSum_j = Integer.MIN_VALUE;
            int minSum_i = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                minSum_i = Math.min(minSum_i, preSum[i]);
                maxSum_j = Math.max(maxSum_j, preSum[i + 1] - minSum_i);
            }
            return maxSum_j;
        }

        public int maxSubArrayImproved(int[] nums) {
            int currSum = 0, maxSum = Integer.MIN_VALUE, minSum = Integer.MAX_VALUE;
            for (int i = 0; i < nums.length; i++) {
                minSum = Math.min(minSum, currSum);
                currSum += nums[i];
                maxSum = Math.max(maxSum, currSum - minSum);
            }
            return maxSum;
        }
    }

    class Followup_ReturnSubarray {
        public int[] maxSubArray(int[] nums) {
            int currSum = 0, maxSum = Integer.MIN_VALUE, start = 0, maxEnd = 0, maxStart = 0;
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                if (currSum < 0) {
                    currSum = num;
                    start = i;
                } else {
                    currSum += num;
                }
                
                if (currSum > maxSum) {
                    maxSum = currSum;
                    maxStart = start; // start is inclusive
                    maxEnd = i + 1; // end is exclusive
                }
            }
            return Arrays.copyOfRange(nums, maxStart, maxEnd);
        }
    }

    class Followup_MinSubArray {
        public int minSubArray(int[] nums) {
            int n = nums.length, sum = nums[0], minSum = nums[0];
            for (int i = 1; i < n; i++) {
                sum = Math.min(sum + nums[i], nums[i]);
                minSum = Math.min(minSum, sum);
            }
            return minSum;
        }
    }

    class Followup_MaxSumKSubarray {
        // dp[i][j] = max sum subarrays using first i elements with j subarrays
        // dp[i][j] = max(dp[i-1][j], dp[j...i-1][j-1] + nums[j...i-1])
        public int maxSumKSubarrays(int[] nums, int k) {
            int n = nums.length;
            
            // dp[i][j] = max sum using first i elements with j subarrays
            int[][] dp = new int[n + 1][k + 1];
            
            for (int i = 0; i <= n; i++) {
                Arrays.fill(dp[i], Integer.MIN_VALUE);
            }
            dp[0][0] = 0;
            
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= Math.min(i, k); j++) {
                    // Option 1: Don't include nums[i-1]
                    dp[i][j] = dp[i - 1][j];
                    
                    // Option 2: Include nums[i-1] in jth subarray
                    int sum = 0;
                    for (int p = i; p >= j; p--) {
                        sum += nums[p - 1];
                        if (dp[p - 1][j - 1] != Integer.MIN_VALUE) {
                            dp[i][j] = Math.max(dp[i][j], dp[p - 1][j - 1] + sum);
                        }
                    }
                }
            }
            
            return dp[n][k];
        }
    }

    class Followup_MaxSumWithAtLeastKElem {
        public int maxSubArrayAtLeastK(int[] nums, int k) {
            int n = nums.length;
            int maxSum = Integer.MIN_VALUE, sum = 0;
            Deque<IdxSum> minQ = new ArrayDeque<>();
            minQ.offer(new IdxSum(-1, 0));
            for (int i = 0; i < n; i++) {
                sum += nums[i];
                // update maxSum if we have at least k elements
                if (!minQ.isEmpty() && i - minQ.peekFirst().idx >= k) {
                    maxSum = Math.max(maxSum, sum - minQ.peekFirst().sum);
                }
                // maintain monotonic increasing queue
                while (!minQ.isEmpty() && minQ.peekLast().sum > sum) {
                    minQ.pollLast();
                }
                minQ.offerLast(new IdxSum(i, sum));
            }
            return maxSum;
        }
    }

    class Followup_MaxSumWithAtLeastKDeletions {
        public int maxSubArrayWithKDeletions(int[] nums, int k) {
            int n = nums.length;
            
            // dp[i][j] = max sum ending at i with j deletions used
            int[][] dp = new int[n][k + 1];
            
            dp[0][0] = nums[0];
            dp[0][1] = 0;  // Delete first element
            
            int maxSum = Math.max(dp[0][0], dp[0][1]);
            
            for (int i = 1; i < n; i++) {
                for (int j = 0; j <= k; j++) {
                    // Include nums[i]
                    dp[i][j] = nums[i];
                    if (i > 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i-1][j] + nums[i]);
                    }
                    
                    // Delete nums[i]
                    if (j > 0 && i > 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i-1][j-1]);
                    }
                    
                    maxSum = Math.max(maxSum, dp[i][j]);
                }
            }
            
            return maxSum;
        }
    }
}
