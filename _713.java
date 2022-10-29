/**
 * 713. Subarray Product Less Than K.
 */
public class _713 {
	class Solution {
		public int numSubarrayProductLessThanK(int[] nums, int k) {
			int left = 0, right = 0;
			// initialize window product as 1
			int windowProduct = 1;
			// satisfied subarray count
			int count = 0;
			
			while (right < nums.length) {
				// expand the window
				windowProduct = windowProduct * nums[right];
				right++;
				
				while (left < right && windowProduct >= k) {
					// shrink the window
					windowProduct = windowProduct / nums[left];
					left++;
				}
				// now it's a valid window, notice how to calculate the num of sub-arrays
				// say, when left = 1, right = 4, the window contains numbers [1, 2, 3]
				// but not only the range [left..right] is a valid subarray, [left+1..right], [left+2..right] are also valid
				// so we need to add [1], [1,2], [1,2,3], aka (right - left) num of sub-arrays
				count += right - left;
			}
			
			return count;
		}
	}
}
