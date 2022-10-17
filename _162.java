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
	
	public int findPeakElement2(int[] nums) {
		int lo = 0, hi = nums.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			long leftVal = mid - 1 == -1 ? Long.MIN_VALUE : nums[mid - 1];
			long rightVal = mid + 1 == nums.length ? Long.MIN_VALUE : nums[mid + 1];
			long midVal = (long)nums[mid];
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
	
	public static void main(String[] args) {
		System.out.println(new _162().findPeakElement2(new int[]{-2147483648}));
		System.out.println(new _162().findPeakElement2(new int[]{-2147483648,-2147483647}));
		
		System.out.println(new _162().findPeakElement(new int[]{-2147483648})); // TIME LIMIT EXCEEDED
		System.out.println(new _162().findPeakElement(new int[]{-2147483648,-2147483647})); // TIME LIMIT EXCEEDED
	}
}
