/**
 * 81. Search in Rotated Sorted Array II.
 */
public class _81 {
	public boolean search(int[] nums, int target) {
		int left = 0, right = nums.length - 1;
		while (left <= right) {
			while (left + 1 < nums.length && nums[left] == nums[left + 1]) left++;
			while (right - 1 >= 0 && nums[right] == nums[right - 1]) right--;
			if (left > right)
				return nums[left] == target;
			int mid = left + (right - left) / 2;
			if (nums[mid] == target) {
				return true;
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
		return false;
	}
}
