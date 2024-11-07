/**
 * 162. Find Peak Element.
 */
public class _162 {
	/**
	 * TIME LIMIT EXCEEDED
	 * [-2147483648]
	 * @param nums
	 * @return
	 */
	public int findPeakElement(int[] nums) {
		int lo = 0, hi = nums.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int leftVal = mid - 1 == -1 ? Integer.MIN_VALUE : nums[mid - 1];
			int rightVal = mid + 1 == nums.length ? Integer.MIN_VALUE : nums[mid + 1];
			int midVal = nums[mid];
			if (midVal > leftVal && midVal > rightVal) {
				return mid;
			} else if (midVal > leftVal) { // leftVal < mid < rightVal, go right
				lo = mid + 1;
			} else if (midVal < leftVal) { // leftVal > mid > rightVal, go left
				hi = mid - 1;
			}
		}
		return -1;
	}

	/**
	 * nums[i] could be the value of Integer.MIN_VALUE, so we need to have the = sign even the question clearly mentioned that the nums are unique
	 * @param nums given nums array
	 * @return peak elem index
	 */
	public int findPeakElementImproved(int[] nums) {
		int n = nums.length;
		int lo = 0, hi = n - 1, mid = 0;
		while (lo <= hi) {
			mid = lo + (hi - lo) / 2;
			int left = mid == 0 ? Integer.MIN_VALUE : nums[mid - 1];
			int right = mid == n - 1 ?  Integer.MIN_VALUE : nums[mid + 1];
			if (nums[mid] >= left && nums[mid] >= right) {
				return mid;
			} else if (nums[mid] >= left) {
				lo = mid + 1;
			} else {
				hi = mid - 1;
			}
		}
		return -1;
	}

	public int findPeakElementImproved2(int[] nums) {
		int n = nums.length;
		int lo = 0, hi = n - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;

			boolean isLeftSmaller = (mid == 0 || nums[mid] > nums[mid - 1]);
			boolean isRightSmaller = (mid == n - 1 || nums[mid] > nums[mid + 1]);

			if (isLeftSmaller && isRightSmaller) {
				return mid;
			} else if (mid > 0 && nums[mid - 1] > nums[mid]) {
				hi = mid - 1;
			} else {
				lo = mid + 1;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		System.out.println(new _162().findPeakElementImproved(new int[]{-2147483648})); // 0
		System.out.println(new _162().findPeakElementImproved(new int[]{-2147483648,-2147483647})); // 1
		
		System.out.println(new _162().findPeakElement(new int[]{-2147483648})); // TIME LIMIT EXCEEDED
		System.out.println(new _162().findPeakElement(new int[]{-2147483648,-2147483647})); // TIME LIMIT EXCEEDED
	}
}
