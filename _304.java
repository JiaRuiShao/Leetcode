/**
 * 304. Range Sum Query 2D - Immutable.
 * Implement the NumMatrix class:
 * - NumMatrix(int[][] matrix) Initializes the object with the integer matrix `matrix`
 * - int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the rectangle
 * defined by its upper left corner (row1, col1) and lower right corner (row2, col2)
 * You must design an algorithm where sumRegion works on O(1) time complexity.
 */
public class _304 {
	class NumMatrix {
		
		private int[][] preSum; // preSum[i][j] stores the sum of elements in [0, 0, i - 1, j -1]
		
		public NumMatrix(int[][] matrix) {
			int m = matrix.length;
			int n = matrix[0].length;
			preSum = new int[m + 1][n + 1];
			for (int row = 0; row < m; row++) {
				for (int col = 0; col < n; col++) {
					preSum[row + 1][col + 1] = preSum[row][col + 1] + preSum[row + 1][col] - preSum[row][col] + matrix[row][col];
				}
			}
		}
		
		// +1 for preSum, here the updated row1 = row + 1 - 1, & col1 = col + 1 - 1 because here row1 and col1 is inclusive
		public int sumRegion(int row1, int col1, int row2, int col2) {
			row2 += 1;
			col2 += 1;
			return preSum[row2][col2] - preSum[row1][col2] - preSum[row2][col1] + preSum[row1][col1];
		}
	}
}
