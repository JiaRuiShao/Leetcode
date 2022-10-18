/**
 * 33. Search in Rotated Sorted Array.
 */
public class _33 {
	public int search(int[] nums, int target) {
		int left = 0, right = nums.length - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (nums[mid] == target) {
				return mid;
			} else if (nums[left] <= nums[mid]) { // on left side, [left, mid] sorted
				if (nums[left] <= target && target < nums[mid]) { // go left
					right = mid - 1;
				} else { // go right
					left = mid + 1;
				}
			} else { // on right side, [mid, right] sorted
				if (nums[mid] < target && target <= nums[right]) { // go right
					left = mid + 1;
				} else { // go left
					right = mid - 1;
				}
			}
		}
		return -1;
	}
}
