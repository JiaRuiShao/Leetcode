package topics;

public class BinarySearch {
	int binarySearch(int[] nums, int target) {
		int left = 0, right = nums.length - 1;
		// search range: [left, right]
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (nums[mid] == target) {
				return mid;
			} else if (nums[mid] < target) {
				// search range: [mid+1, right]
				left = mid + 1;
			} else if (nums[mid] > target) {
				// search range: [left, mid-1]
				right = mid - 1;
			}
		}
		return -1;
	}
	
	int left_bound(int[] nums, int target) {
		int left = 0, right = nums.length - 1;
		// search range: [left, right]
		while (left <= right) { // terminate when left == right + 1
			int mid = left + (right - left) / 2;
			if (nums[mid] == target) {
				// narrow the upper bound of the search range
				right = mid - 1;
			} else if (nums[mid] < target) {
				// search range: [mid+1, right]
				left = mid + 1;
			} else if (nums[mid] > target) {
				// search range: [left, mid-1]
				right = mid - 1;
			}
		}
		
		// see if target is in nums
		if (left == nums.length) return -1; // target is larger than all elements in nums
		return nums[left] == target ? left : -1;
	}
	
	int right_bound(int[] nums, int target) {
		int left = 0, right = nums.length - 1;
		// search range: [left, right]
		while (left <= right) { // terminate when left == right + 1
			int mid = left + (right - left) / 2;
			if (nums[mid] == target) {
				// narrow the lower bound of the search range
				left = mid + 1;
			} else if (nums[mid] < target) {
				// search range: [mid+1, right]
				left = mid + 1;
			} else if (nums[mid] > target) {
				// search range: [left, mid-1]
				right = mid - 1;
			}
		}
		
		// see if target is in nums
		if (right < 0) return -1;
		return nums[right] == target ? right : -1;
	}
}
