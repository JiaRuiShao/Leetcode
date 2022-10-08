package topics;

public class BinarySearch {
	int binarySearch(int[] nums, int target) {
		// left pointer goes right, right pointer goes left
		int left = 0, right = nums.length - 1;
		while(left <= right) {
			int mid = (right + left) / 2;
			if(nums[mid] == target)
				return mid;
			else if (nums[mid] < target)
				left = mid + 1;
			else if (nums[mid] > target)
				right = mid - 1;
		}
		return -1;
	}
}
