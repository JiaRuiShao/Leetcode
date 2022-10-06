/**
 * 724. Find Pivot Index.
 * Given an array of integers nums, calculate the pivot index of this array.
 * The pivot index is the index where the sum of all the numbers strictly to the left of the index is equal to
 * the sum of all the numbers strictly to the index's right. If the index is on the left edge of the array,
 * then the left sum is 0 because there are no elements to the left. This also applies to the right edge of the array.
 * Return the leftmost pivot index. If no such index exists, return -1.
 */
public class _724 {
	class Solution1_PreSum {
		/**
		 * Time: O(n)
		 * Space: O(n)
		 * @param nums input arr
		 * @return leftmost index where its left elements have the equal sum of its right elements; return -1 if not exists
		 */
		public int pivotIndex(int[] nums) {
			int n = nums.length;
			int[] pre = new int[n + 1];
			for (int i = 0; i < n; i++) {
				pre[i + 1] = pre[i] + nums[i];
			}
			
			for (int i = 0; i < n; i++) { // left & right  range: [0, i-1], [i+1, n-1]
				if (pre[i] == pre[n] - pre[i + 1]) return i;
			}
			return -1;
		}
	}
}
