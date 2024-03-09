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

	class Solution2_PreSum {

		private int[] preSum;
	
		public int pivotIndex(int[] nums) {
			int n = nums.length;
			buildPreSum(nums, n);
			for (int i = 0; i < n; i ++) {
				if (isPivot(i)) {
					return i;
				}
			}
			return -1;
		}
	
		private void buildPreSum(int[] nums, int n) {
			preSum = new int[n + 1];
			for (int i = 0; i < n; i++) {
				preSum[i + 1] = preSum[i] + nums[i];
			}
		}
	
		private boolean isPivot(int idx) {
			int leftSum = preSum[idx];
			int rightSum = preSum[preSum.length - 1] - preSum[idx + 1];
			return leftSum == rightSum;
		}
	}

	class Solution3 {
		public int pivotIndex(int[] nums) {
            int total = 0;
            for (int num : nums) {
                total += num;
            }
            int sum = 0;
            for (int i = 0; i < nums.length; sum += nums[i++]) {
                if (sum * 2 == total - nums[i]) {
                    return i;
                }
            }
            return -1;
        }
	}
}
