/**
 * 74. Search a 2D Matrix.
 */
public class _74 {
	/**
	 * Flatten the matrix as an ascending array and use binary search.
	 * Given matrix row m, col as n:
	 *  - matrix[i][j] to flatten array: arrIdx = i * n + j
	 *  - arrayIdx to matrix[i][j]: i = arrIdx / n, j = arrIdx % n
	 *
	 * @param matrix int ascending matrix
	 * @param target target number to find
	 * @return true if the target is found; false if not
	 */
	public boolean searchMatrix(int[][] matrix, int target) {
		int m = matrix.length, n = matrix[0].length, left = 0, right = m * n - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			int midNum = matrix[mid / n][mid % n];
			if (midNum == target) {
				return true;
			} else if (midNum < target) {
				left = mid + 1;
			} else if (midNum > target) {
				right = mid - 1;
			}
		}
		return false;
	}
}
