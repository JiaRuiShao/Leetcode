import java.util.LinkedList;
import java.util.List;

/**
 * 658. Find K Closest Elements
 * 1. Left Boundary BS
 * 2. Initialize the two pointers start range with an empty range
 */
public class _658 {
	class Solution1_Binary_Search { // lower bound BS
		public List<Integer> findClosestElements(int[] arr, int k, int x) {
			int xIdx = findLeftX(arr, x);
			return findKClosestElements(arr, k, x, xIdx);
		}

		private int findLeftX(int[] arr, int x) {
			int lo = 0, hi = arr.length - 1;
			while (lo <= hi) {
				int mid = lo + (hi - lo) / 2;
				if (arr[mid] >= x) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
			return lo;
		}

		private List<Integer> findKClosestElements(int[] arr, int k, int x, int idx) {
			List<Integer> res = new LinkedList<>();
			int left = idx - 1, right = idx;
			while (k-- > 0) {
				if (left < 0) {
					res.add(arr[right++]);
				} else if (right >= arr.length) {
					res.add(0, arr[left--]);
				} else if (Math.abs(arr[left] - x) <= Math.abs(arr[right] - x)) {
					res.add(0, arr[left--]);
				} else {
					res.add(arr[right++]);
				}
			}
			return res;
		}
	}
	
	class Solution2_Binary_Search { // right/upper boundary BS
		public List<Integer> findClosestElements(int[] arr, int k, int x) {
			int n = arr.length;
			int lo = 0, hi = n - 1, mid;
			while (lo <= hi) {
				mid = lo + (hi - lo) / 2;
				if (arr[mid] <= x) lo = mid + 1; // go right
				else if (arr[mid] > x) hi = mid - 1; // go left
			}
			List<Integer> kClosest = new LinkedList<>();
			int left = hi, right = lo;
			while (kClosest.size() < k) {
				if (left < 0) kClosest.add(arr[right++]); // addLast
				else if (right >= n) kClosest.add(0, arr[left--]); // addFirst
				else if (Math.abs(arr[right] - x) < Math.abs(arr[left] - x)) kClosest.add(arr[right++]);
				else kClosest.add(0, arr[left--]);
			}
			return kClosest;
		}
	}
}
