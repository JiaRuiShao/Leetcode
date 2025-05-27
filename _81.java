/**
 * 81. Search in Rotated Sorted Array II
 */
public class _81 {
	class Solution1_Binary_Search {
		public boolean search(int[] nums, int target) {
			int lo = 0, hi = nums.length - 1;
			while (lo <= hi) {
				int mid = lo + (hi - lo) / 2;
				if (nums[mid] == target) return true;
				
				// If we can't tell which half is sorted because of duplicates
				if (nums[lo] == nums[mid] && nums[mid] == nums[hi]) {
					lo++;
					hi--;
				}
				// Left half is sorted
				else if (nums[lo] <= nums[mid]) {
					if (target >= nums[lo] && target < nums[mid]) {
						hi = mid - 1;
					} else {
						lo = mid + 1;
					}
				}
				// Right half is sorted
				else {
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
