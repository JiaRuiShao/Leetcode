/**
 * 240. Search a 2D Matrix II.
 */
public class _240 {
	/**
	 * Notice that if we use backtracking on this problem, the time complexity will be 4^(mn).
	 */
	class Solution1_Binary_Search {
		/**
		 * Start from top-right corner (or bottom-left corner), go down or left until find the target or index out of bound.
		 * Why start from top-right corner? If the elem is < target, go down, if it's > target go left.
		 * If we start from the top-left or bottom-right corner, we don't know where to go (both go down & go right are larger numbers)
		 *
		 * Time: O(m+n)
		 * Space: O(1)
		 *
		 * @param matrix int matrix
		 * @param target target number to find
		 * @return true if target found; else false
		 */
		public boolean searchMatrix(int[][] matrix, int target) {
			return findTarget(matrix, target);
		}
	
		private boolean findTarget(int[][] matrix, int target) {
			int m = matrix.length, n = matrix[0].length;
			int row = 0, col = n - 1;
			while (row < m && col >= 0) {
				int val = matrix[row][col];
				if (val == target) {
					return true;
				} else if (val < target) {
					row++;
				} else {
					col--;
				}
			}
			return false;
		}
	}

	class Solution2_Binary_Search {
		/**
		 * Start from bottom-left corner.
		 * Time: O(m+n)
		 * Space: O(1)
		 * 
		 * @param matrix int matrix
		 * @param target target number to find
		 * @return true if target found; else false
		 */
		public boolean searchMatrix(int[][] matrix, int target) {
			int m = matrix.length, n = matrix[0].length;
			int row = m - 1, col = 0;
			while (row >= 0 && col < n) {
				int cmp = matrix[row][col] - target;
				if (cmp == 0) {
					return true;
				} else if (cmp < 0) {
					col++;
				} else {
					row--;
				}
			}
			return false;
		}
	}
}
