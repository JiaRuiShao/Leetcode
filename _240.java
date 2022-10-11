/**
 * 240. Search a 2D Matrix II.
 */
public class _240 {
	class Solution_Binary_Search {
		/**
		 * Start from upper right corner (or lower left corner), go down or left until find the target or index out of bound.
		 * Why start from upper right corner? If the elem is < target, go down, if it's > target go left.
		 * If we start from the upper left corner, we don't know where to go (both go down & go right are larger numbers)
		 *
		 * Time: O(m+n)
		 * Space: O(1)
		 *
		 * @param matrix int matrix
		 * @param target target number to find
		 * @return true if target found; else false
		 */
		public boolean searchMatrix(int[][] matrix, int target) {
			int m = matrix.length, n = matrix[0].length;
			int i = 0, j = n - 1; // start from upper right
			while (i < m && j >= 0) { // terminate when i == m || j == -1
				if (matrix[i][j] == target) {
					return true;
				} else if (matrix[i][j] < target) {
					i++;
				} else if (matrix[i][j] > target) {
					j--;
				}
			}
			return false;
		}
	}
}
