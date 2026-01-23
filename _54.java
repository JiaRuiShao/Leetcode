import java.util.ArrayList;
import java.util.List;

/**
 * 54. Spiral Matrix
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
			List<Integer> spiral = new ArrayList<>();
			int m = matrix.length, n = matrix[0].length;
			int top = 0, down = m - 1, left = 0, right = n - 1;
			while (top <= down && left <= right) {
				for (int c = left; c <= right; c++) {
					spiral.add(matrix[top][c]);
				}
				top++;
				for (int r = top; r <= down; r++) {
					spiral.add(matrix[r][right]);
				}
				right--;
				if (top <= down) {
					for (int c = right; c >= left; c--) {
						spiral.add(matrix[down][c]);
					}
					down--;
				}
				if (left <= right) {
					for (int r = down; r >= top; r--) {
						spiral.add(matrix[r][left]);
					}
					left++;
				}
			}
			return spiral;
		}
	}
}
