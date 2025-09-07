import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 53. Maximum Subarray
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

    class Solution1_DP_KadaneAlgo {
        public int maxSubArray(int[] nums) {
            int n = nums.length;
            if (n == 0) return 0;
            // dp table, dp[i] saves the max sum of subarray that ends at index i
            int[] dp = new int[n];
            // base case
            dp[0] = nums[0];
            // state equation: dp[i] = Math.max(nums[i], nums[i] + dp[i - 1])
            // for each elem, we can choose to use prev sum or start a new subarray
            for (int i = 1; i < n; i++) {
                dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
            }
            // find max subarray sum ends at index i
            int res = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                res = Math.max(res, dp[i]);
            }
            return res;
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
                dp_0 = dp_1;
                // check if curr subarray sum is the max
                maxSum = Math.max(maxSum, dp_1);
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
                    maxSum = Math.max(maxSum, sum - minQ.peekFirst());
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

}
