/**
 * 852. Peak Index in a Mountain Array.
 */
public class _852 {
	public int peakIndexInMountainArray(int[] arr) {
		int lo = 1, hi = arr.length - 2;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2, midVal = arr[mid];
			if (midVal > arr[mid - 1] && midVal > arr[mid + 1]) {
				return mid;
			} else if (midVal < arr[mid + 1]) {
				lo = mid + 1;
			} else if (midVal < arr[mid - 1]) {
				hi = mid - 1;
			}
		}
		return -1;
	}
}
