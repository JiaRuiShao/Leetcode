/**
 * 74. Search a 2D Matrix.
 * When unflatten the 1D matrix to 2D, we only need its col size
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

	class Solution2 {
		public boolean searchMatrix(int[][] matrix, int target) {
			return findTarget(matrix, target);
		}
	
		private boolean findTarget(int[][] matrix, int target) {
			int row = matrix.length, col = matrix[0].length;
			int lo = 0, hi = row * col - 1;
			while (lo <= hi) {
				int mid = lo + (hi - lo) / 2;
				int val = findMatrixVal(matrix, col, mid);
				if (val == target) {
					return true;
				} else if (val < target) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
			return false;
		}
	
		private int findMatrixVal(int[][] matrix, int c, int idx) {
			return matrix[idx / c][idx % c];
		}
	}
}
