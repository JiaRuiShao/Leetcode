/**
 * 81. Search in Rotated Sorted Array II
 */
public class _81 {
	class Solution1_Binary_Search {
		// Time: O(logn) average / O(n) worst
		// Space: O(1)
		public boolean search(int[] nums, int target) {
			int lo = 0, hi = nums.length - 1;
			while (lo <= hi) {
				int mid = lo + (hi - lo) / 2;
				if (target == nums[mid]) {
					return true;
				}
				if (nums[lo] == nums[mid] && nums[lo] == nums[hi]) {
					lo++;
					hi--;
					continue;
				}
				if (nums[lo] <= nums[mid]) { // left side sorted, pivot at right
					if (target >= nums[lo] && target < nums[mid]) {
						hi = mid - 1;
					} else {
						lo = mid + 1;
					}
				} else { // right side sorted, pivot at left
					if (target > nums[mid] && target <= nums[hi]) {
						lo = mid + 1;
					} else {
						hi = mid - 1;
					}
				}
			}
			return false;
		}
	}
}
