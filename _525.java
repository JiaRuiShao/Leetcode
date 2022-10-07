import java.util.HashMap;
import java.util.Map;

/**
 * 525. Contiguous Array.
 * Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.
 */
public class _525 {
	class Solution1_BruteForce {
		/**
		 * Brute Force.
		 * Time: O(n^2) Quadratic -- exceed time limit
		 * Space: O(1)
		 *
		 * @param nums input numbers array
		 * @return longest contiguous subarray that contains the equal number of 1 and 0
		 */
		public int findMaxLength(int[] nums) {
			int n = nums.length, maxLen = 0, zeros, ones;
			for (int start = 0; start < n - 1; start++) { // start [0, n-2]
				zeros = 0;
				ones = 0;
				if (nums[start] == 0) zeros++;
				else ones++;
				for (int end = start + 1; end < n; end++) { // end [1, n-1]
					// if zeros == ones, update the maxLen
					if (nums[end] == 0) zeros++;
					else ones++;
					if (ones == zeros) maxLen = Math.max(maxLen, end - start + 1);
				}
			}
			return maxLen;
		}
	}
	
	class Solution2_PrefixSum_HashMap {
		/**
		 * Prefix Sum Array + HashMap.
		 * Time: O(n)
		 * Space: O(n)
		 * @param nums input numbers array
		 * @return longest contiguous subarray that contains the equal number of 1 and 0
		 */
		public int findMaxLength(int[] nums) {
			int n = nums.length, maxLen = 0;
			// 1 - convert the 0 to -1, problem becomes find the longest subarray whose sum is 0
			int[] preSum = new int[n + 1];
			for (int i = 0; i < n; i++) {
				preSum[i + 1] = preSum[i] + (nums[i] == 0 ? -1 : 1);
			}
			// 2 - hashmap to store the presum & the index of each element
			Map<Integer, Integer> map = new HashMap<>();
			// 3 - update the len when the presum is already added in the map
			for (int i = 0; i < preSum.length; i++) { // pay attention to the index range here, we add the presum[0] here as the starting sum, and the end index should be n
				if (!map.containsKey(preSum[i])) {
					map.put(preSum[i], i);
				} else {
					maxLen = Math.max(maxLen, i - map.get(preSum[i]));
				}
			}
			return maxLen;
		}
		
		/**
		 * Prefix Sum + HashMap.
		 * Time: O(n)
		 * Space: O(1) improve the previous approach by replacing the prefix sum array to a prefix sum int
		 * @param nums input numbers array
		 * @return longest contiguous subarray that contains the equal number of 1 and 0
		 */
		public int findMaxLengthImproved(int[] nums) {
			int n = nums.length, maxLen = 0, preSum = 0;
			// 1 - convert the 0 to -1, problem becomes find the longest subarray whose sum is 0
			for (int i = 0; i < n; i++) {
				if (nums[i] == 0) nums[i] = -1;
			}
			// 2 - hashmap to store the presum & the index of each element
			Map<Integer, Integer> map = new HashMap<>();
			// 3 - update the len when the presum is already added in the map
			map.put(preSum, 0);
			for (int i = 1; i <= n; i++) { //start from 1, end with n
				preSum += nums[i-1];
				if (!map.containsKey(preSum)) {
					map.put(preSum, i);
				} else {
					maxLen = Math.max(maxLen, i - map.get(preSum));
				}
			}
			return maxLen;
		}
	}
}
