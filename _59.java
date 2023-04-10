/**
 * 59. Spiral Matrix II.
 */
public class _59 {
	public static class Solution1 {
		public int[][] generateMatrix(int n) {
			int[][] matrix = new int[n][n];
			if (n == 0) return matrix;
			
			int value = 1;
			int top = 0, bottom = n - 1;
			int left = 0, right = n - 1;
			
			while (left <= right && top <= bottom) {
				// filled the upper bound
				for (int j = left; j <= right; j++) {
					matrix[top][j] = value++;
				}
				top++;
				
				// filled the right bound
				for (int i = top; i <= bottom; i++) {
					matrix[i][right] = value++;
				}
				right--;
				
				// filled the lower bound
				for (int j = right; j >= left; j--) {
					matrix[bottom][j] = value++;
				}
				bottom--;
				
				// filled the left bound
				for (int i = bottom; i >= top; i--) {
					matrix[i][left] = value++;
				}
				left++;
			}
			
			return matrix;
		}
	}
	
	class Solution2 {
		public int[][] generateMatrix(int n) {
			int[][] matrix = new int[n][n];
			int rowLower = 0, rowUpper = n - 1, colLower = 0, colUpper = n - 1;
			int start = 1, end = n * n;
			while (start <= end) {
				for (int col = colLower; col <= rowUpper && start <= end; col++) {
					matrix[rowLower][col] = start;
					start++;
				}
				rowLower++;
				for (int row = rowLower; row <= rowUpper && start <= end; row++) {
					matrix[row][colUpper] = start;
					start++;
				}
				colUpper--;
				for (int col = colUpper; col >= colLower && start <= end; col--) {
					matrix[rowUpper][col] = start;
					start++;
				}
				rowUpper--;
				for (int row = rowUpper; row >= rowLower && start <= end; row--) {
					matrix[row][colLower] = start;
					start++;
				}
				colLower++;
			}
			return matrix;
		}
	}
}