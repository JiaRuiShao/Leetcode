import java.util.ArrayList;
import java.util.List;

/**
 * 54. Spiral Matrix.
 */
public class _54 {
	public static class Solution1 {
		/**
		 * credit: https://leetcode.com/problems/spiral-matrix/discuss/20599/Super-Simple-and-Easy-to-Understand-Solution/185257
		 */
		public List<Integer> spiralOrder(int[][] matrix) {
			if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
				return new ArrayList<>();
			}
			int m = matrix.length; // row len
			int n = matrix[0].length; // col len
			int size = m * n;
			List<Integer> result = new ArrayList<>(size);
			int top = 0, bottom = m - 1;
			int left = 0, right = n - 1;
			while (result.size() < size) {
				// add the upper bound elements
				for (int j = left; j <= right && result.size() < size; j++) {
					result.add(matrix[top][j]);
				}
				top++;
				
				// add the right bound elements
				for (int i = top; i <= bottom && result.size() < size; i++) {
					result.add(matrix[i][right]);
				}
				right--;
				
				// add the lower bound elements
				for (int j = right; j >= left && result.size() < size; j--) {
					result.add(matrix[bottom][j]);
				}
				bottom--;
				
				// add the left bound elements
				for (int i = bottom; i >= top && result.size() < size; i--) {
					result.add(matrix[i][left]);
				}
				left++;
			}
			return result;
		}
	}
	
	class Solution2 {
		public List<Integer> spiralOrder(int[][] matrix) {
			List<Integer> sprial = new ArrayList<>();
			int m = matrix.length, n = matrix[0].length;
			int rowUpper = m - 1, colUpper = n - 1, rowLower = 0, colLower = 0;
			while (rowLower <= rowUpper && colLower <= colUpper) {
				if (rowLower <= rowUpper) {
					for (int col = colLower; col <= colUpper; col++) {
						sprial.add(matrix[rowLower][col]);
					}
				}
				rowLower++;
				if (colLower <= colUpper) {
					for (int row = rowLower; row <= rowUpper; row++) {
						sprial.add(matrix[row][colUpper]);
					}
				}
				colUpper--;
				if (rowLower <= rowUpper) {
					for (int col = colUpper; col >= colLower; col--) {
						sprial.add(matrix[rowUpper][col]);
					}
				}
				rowUpper--;
				if (colLower <= colUpper) {
					for (int row = rowUpper; row >= rowLower; row--) {
						sprial.add(matrix[row][colLower]);
					}
				}
				colLower++;
			}
			return sprial;
		}
	}
}
