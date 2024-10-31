import java.util.Arrays;

/**
 * 1658. Minimum Operations to Reduce X to Zero.
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

	public static void main(String[] args) {
		// int[] nums = new int[]{1, 1};
		// int minOperations = new _1658().minOperations(nums, 3);
		// System.out.println(minOperations);

		int[] nums = new int[]{8828,9581,49,9818,9974,9869,9991,10000,10000,10000,9999,9993,9904,8819,1231,6309};
		int minOperations = new _1658().new Solution2_Sliding_Window_Improved().minOperations(nums, 134365);
		System.out.println(minOperations);
	}
	
}
