/**
 * 327. Count of Range Sum.
 * Given an integer array nums and two integers lower and upper, return the
 * number of range sums that lie in [lower, upper] inclusive.
 *
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j
 * inclusive, where i <= j.
 */
public class _327 {
	class Solution1_MergeSort_PrefixSum {
		
		long[] preSum, temp; // build teh prefix sum array, use long to store to prevent integer overflow
		int count, lower, upper;
		
		public int countRangeSum(int[] nums, int lower, int upper) {
			createPreSum(nums);
			this.lower = lower;
			this.upper = upper;
			// lower <= preSum[j] - preSum[i] <= upper, j > i
			mergeSort(preSum, 0, nums.length);
			return count;
		}
		
		private void createPreSum(int[] nums) {
			preSum = new long[nums.length + 1];
			temp = new long[nums.length + 1];
			count = 0;
			for (int i = 0; i < nums.length; i++) {
				preSum[i + 1] = preSum[i] + (long) nums[i];
			}
		}
		
		private void mergeSort(long[] pre, int lo, int hi) {
			if (lo == hi) return;
			int mid = lo + (hi - lo) / 2;
			mergeSort(pre, lo, mid);
			mergeSort(pre, mid + 1, hi);
			merge(pre, lo, mid, hi);
		}
		
		private void merge(long[] pre, int lo, int mid, int hi) {
			System.arraycopy(pre, lo, temp, lo, hi - lo + 1);
			
			// left & right half, & the right half element's index > left half
			int start = mid + 1, end = mid + 1;
			for (int i = lo; i <= mid; i++) {
				// make sure pre[start, end) are in the range[lower, upper]
				while (start <= hi && pre[start] - pre[i] < lower) start++;
				while (end <= hi && pre[end] - pre[i] <= upper) end++;
				count += end - start;
			}
			
			// merge the left & right half using two pointers
			int i = lo, j = mid + 1;
			for (int p = lo; p <= hi; p++) {
				if (i == mid + 1) pre[p] = temp[j++];
				else if (j == hi + 1) pre[p] = temp[i++];
				else if (temp[i] <= temp[j]) pre[p] = temp[i++];
				else pre[p] = temp[j++];
			}
		}
	}
}
