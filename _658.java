import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 658. Find K Closest Elements.
 * 1. Left Boundary BS
 * 2. Initialize the two pointers start range with an empty range
 */
public class _658 {
	class Solution_Binary_Search_Left_Right_Pointers {
		/**
		 * Time: O(logn + k) where n is the number of elements in given arr
		 * Space: O(1)
		 * 
		 * @param arr elements arr
		 * @param k k nearest elements
		 * @param x target value
		 * @return closest k elements near x
		 */
		public List<Integer> findClosestElements(int[] arr, int k, int x) {
			// binary search first to find the left boundary of x
			// then use two pointers to go from the left boundary to left & right until k elements added
			return findKElems(arr, k, x, findLowerBound(arr, x));
		}
		
		// find the target index and return
		// if there's more than one target elements, return the left most index
		// if the target doesn't exist in the given arr, return the index i of the largest arr[i] < target
		private int findLowerBound(int[] arr, int target) {
			int left = 0, right = arr.length - 1;
			while (left <= right) {
				int mid = left + (right - left) / 2;
				if (arr[mid] == target) {
					right = mid - 1;
				} else if (arr[mid] < target) {
					left = mid + 1;
				} else if (arr[mid] > target) {
					right = mid - 1;
				}
			}
			return left;
		}
		
		private List<Integer> findKElems(int[] arr, int k, int x, int start) {
			// use a LinkedList to implement so that addFirst() is O(1) time complexity
			LinkedList<Integer> kSmallestElems = new LinkedList<>();
			// **IMPORTANT: start idx could be arr.length, so left has to be initialized as start - 1**
			int left = start - 1, right = start;
			while (k > 0) { // or while (kSmallestElems.size() < k)
				if (right == arr.length || (left >= 0 && Math.abs(arr[left] - x) <= Math.abs(arr[right] - x))) {
					kSmallestElems.addFirst(arr[left--]);
				} else {
					kSmallestElems.addLast(arr[right++]);
				}
				k--;
			}
			return kSmallestElems;
		}
	}

	class Solution2 {
		public List<Integer> findClosestElements(int[] arr, int k, int x) {
			int xIdx = findLeftX(arr, x);
			return findKClosestElements(arr, k, x, xIdx);
		}

		private int findLeftX(int[] arr, int x) {
			int lo = 0, hi = arr.length - 1;
			while (lo <= hi) {
				int mid = lo + (hi - lo) / 2;
				int val = arr[mid];
				if (val >= x) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
			return lo;
		}

		private List<Integer> findKClosestElements(int[] arr, int k, int x, int idx) {
			List<Integer> elements = new ArrayList<>(k);
			int len = arr.length, left = idx - 1, right = idx;
			while (right - left - 1 < k) {
				if (left < 0) {
					elements.add(arr[right++]);
				} else if (right == len) {
					elements.addFirst(arr[left--]);
				} else if (Math.abs(arr[left] - x) <= Math.abs(arr[right] - x)) {
					elements.addFirst(arr[left--]);
				} else {
					elements.add(arr[right++]);
				}
			}
			return elements;
		}
	}
}
