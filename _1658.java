import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1658. Minimum Operations to Reduce X to Zero
 * Convert the original question to a maxLen sliding window question
 */
public class _1658 {
	/**
	 * Time: O(n)
	 * Space: O(1)
	 */
	class Solution1_Sliding_Window {
		public int minOperations(int[] nums, int x) {
			int n = nums.length, sum = Arrays.stream(nums).sum();
			// target = sumOfAllElems - x, target sum of the sliding window elems
			int target = sum - x;
			
			int left = 0, right = 0;
			int windowSum = 0; // sum of window [left, right)
			int maxLen = Integer.MIN_VALUE;
			while (right < n) {
				// expand the window when windowSum <= target
				windowSum += nums[right];
				right++;
				
				while (windowSum > target && left < right) { // prevent k > sum(nums)
					// shrink the window when windowSum > target
					windowSum -= nums[left];
					left++;
				}
				// update maxLen if found a valid ans when windowSum == target
				if (windowSum == target) {
					maxLen = Math.max(maxLen, right - left);
				}
			}
			return maxLen == Integer.MIN_VALUE ? -1 : n - maxLen;
		}
	}

	class Solution2_Sliding_Window_Improved {
		/**
		 * Here we have to check the corner case where target < 0 b/c x could be > sum of array and while (winSum > target) will always be true.
		 * 
		 * @param nums
		 * @param x target sum to be removed
		 * @return min num of left/right elems to be removed to met x target
		 */
		public int minOperations(int[] nums, int x) {
			int target = Arrays.stream(nums).sum() - x;
			int left = 0, right = 0, maxLen = -1, winSum = 0;
	
			// keep a window [left, right)  
			while (right < nums.length) {
				winSum += nums[right++];
				while (winSum > target && left < right) {
					winSum -= nums[left++];
				}
				if (winSum == target) {
					maxLen = Math.max(maxLen, right - left);
				}
			}
	
			return maxLen == -1 ? maxLen : nums.length - maxLen;
		}
	}
	
	class FollowUp_Negative_Input {
		// longest substring with sum as k = (totalSum - x) -- COULD BE EMPTY
		// goal: max(i - j) that presum[i] - presum[j] == k, where j < i
		// for each i, find min(j)
		public int minOperations(int[] nums, int x) {
			int n = nums.length, maxLen = -1, sum = 0;
			int k = Arrays.stream(nums).sum() - x;
			Map<Integer, Integer> presumToIdx = new HashMap<>();
			presumToIdx.put(sum, -1);
			for (int i = 0; i < n; i++) {
				sum += nums[i];
				presumToIdx.putIfAbsent(sum, i); // update map before update res since substring could be empty
				Integer j = presumToIdx.get(sum - k);
				if (j != null) {
					maxLen = Math.max(maxLen, i - j);
				} 
			}
			if (maxLen == -1) { // impossible
				return -1;
			}
			return n - maxLen;
		}
		
		public int minOperationsImproved(int[] nums, int x) {
			int total = Arrays.stream(nums).sum();
			int target = total - x;             // we want the longest subarray summing to (total – x)
			int n = nums.length, maxLen = -1;
			
			// map: prefixSum → earliest index where this prefixSum appeared,
			// using 1-based indices in the loop. We seed (0 → 0) for the “empty” prefix.
			Map<Integer, Integer> preSumToIdx = new HashMap<>();
			preSumToIdx.put(0, 0);
			
			int prefixSum = 0;
			// Loop i from 0 to n (inclusive). Interpret “i” as “length of prefix”:
			//   - at i=0: prefixSum = 0 (empty prefix)
			//   - at i>0: prefixSum += nums[i-1].
			for (int j = 0; j <= n; j++) { // need to start from index 0 because of corner case where x == sum(nums)
				if (j > 0) {
					prefixSum += nums[j - 1];
				}
				
				// Check if there was some earlier prefix whose sum = (prefixSum – target).
				// If so, [thatIndex … i-1] sums to 'target'.
				int k = prefixSum - target;
				if (preSumToIdx.containsKey(k)) {
					int i = preSumToIdx.get(k);
					maxLen = Math.max(maxLen, j - i);
				}
				
				// Record the first time we see this prefixSum at “index = i”
				preSumToIdx.putIfAbsent(prefixSum, j);
			}
			
			return maxLen == -1 ? -1 : n - maxLen;
		}
	}
	
	@Test
	public void testNegativeValuesAllowableRemoval() {
		int[] nums = {8828,9581,49,9818,9974,9869,9991,10000,10000,10000,9999,9993,9904,8819,1231,6309};
		int x = 134365;
		assertEquals(16, new FollowUp_Negative_Input().minOperations(nums, x));
		assertEquals(16, new FollowUp_Negative_Input().minOperationsImproved(nums, x));
	}

	public static void main(String[] args) {
		// int[] nums = new int[]{1, 1};
		// int minOperations = new _1658().minOperations(nums, 3);
		// System.out.println(minOperations);

		int[] nums = new int[]{8828,9581,49,9818,9974,9869,9991,10000,10000,10000,9999,9993,9904,8819,1231,6309};
		int minOperations = new _1658().new Solution2_Sliding_Window_Improved().minOperations(nums, 134365);
		System.out.println(minOperations);
	}
	
}
