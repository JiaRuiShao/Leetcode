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
        public int minSubArrayLen(int target, int[] nums) {
            TreeMap<Integer, Integer> prefixMap = new TreeMap<>();
            prefixMap.put(0, -1); // sum 0 at index -1
            int n = nums.length, sum = 0, minLen = n + 1;

            for (int i = 0; i < n; i++) {
                sum += nums[i];
                Integer bound = prefixMap.floorKey(sum - target);
                if (bound != null) {
                    minLen = Math.min(minLen, i - prefixMap.get(bound));
                }
                prefixMap.putIfAbsent(sum, i);
            }

            return minLen == n + 1 ? 0 : minLen;
        }
    }

    // Time: O(n)
    class Solution4_FollowUp_Input_Negative_Mono_Queue_PreSum {
        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length;
            long[] prefix = new long[n + 1];
            for (int i = 0; i < n; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }

            Deque<Integer> deque = new ArrayDeque<>();
            int minLen = n + 1;

            for (int i = 0; i <= n; i++) {
                // Shrink from front while condition is met
                while (!deque.isEmpty() && prefix[i] - prefix[deque.peekFirst()] >= target) {
                    minLen = Math.min(minLen, i - deque.pollFirst());
                }

                // Maintain increasing order of prefix sums
                while (!deque.isEmpty() && prefix[i] <= prefix[deque.peekLast()]) {
                    deque.pollLast();
                }

                deque.offerLast(i);
            }

            return minLen == n + 1 ? 0 : minLen;
        }
    }
}
