import java.util.Arrays;

/**
 * 1658. Minimum Operations to Reduce X to Zero.
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
			while (right < nums.length) {
				// expand the window when windowSum <= target
				windowSum += nums[right];
				right++;
				
				while (windowSum > target && left < right) {
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
	
}
