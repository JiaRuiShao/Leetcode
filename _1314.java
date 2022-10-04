/**
 * 1314. Matrix Block Sum.
 * Given a m x n matrix mat and an integer k, return a matrix answer where each answer[i][j] is the sum of all elements mat[r][c] for:
 *      i - k <= r <= i + k
 *      j - k <= c <= j + k, and
 *      (r, c) is a valid position in the matrix
 */
public class _1314 {
	class Solution1_PreSum {
		/**
		 * Build a PreSum Array and then calculate the range sum.
		 * Time: O(mn)
		 * Space: O(mn)
		 *
		 * @param mat given int matrix
		 * @param k   k
		 * @return range sum of the rectangle from upper left [i-k, j-k] to lower right [i+k, j+k]
		 */
		public int[][] matrixBlockSum(int[][] mat, int k) {
			int m = mat.length, n = mat[0].length;
			// build the pre-sum array
			// pre[i, j] stores sum of all elements from mat[0][0] to mat[i-1][j-1]
			int[][] pre = new int[m + 1][n + 1];
			for (int row = 0; row < m; row++) {
				for (int col = 0; col < n; col++) {
					pre[row + 1][col + 1] = pre[row][col + 1] + pre[row + 1][col] - pre[row][col] + mat[row][col];
				}
			}
			// calculate the sum for mat[r][c]
			int[][] ans = new int[m][n];
			int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
			for (int row = 0; row < m; row++) {
				r1 = Math.max(row - k, 0);
				r2 = Math.min(row + k + 1, m);
				for (int col = 0; col < n; col++) {
					c1 = Math.max(col - k, 0);
					c2 = Math.min(col + k + 1, n);
					ans[row][col] = pre[r2][c2] - pre[r1][c2] - pre[r2][c1] + pre[r1][c1];
				}
			}
			return ans;
		}
	}
	
	class Solution2_BruteForce {
		
		/**
		 * Time: O(m^2*n^2)
		 * Space: O(1)
		 *
		 * @param mat given int matrix
		 * @param k   k
		 * @return range sum of the rectangle from upper left [i-k, j-k] to lower right [i+k, j+k]
		 */
		public int[][] matrixBlockSum(int[][] mat, int k) {
			int m = mat.length, n = mat[0].length;
			int[][] ans = new int[m][n];
			int r1 = 0, r2 = 0, c1 = 0, c2 = 0;
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					ans[i][j] = sumMat(mat, i, j, k);
				}
			}
			return ans;
		}
		
		int sumMat(int[][] mat, int i, int j, int k) {
			int sum = 0;
			for (int a = i - k; a <= i + k; a++)
				if (a >= 0 && mat.length > a)
					for (int b = j - k; b <= j + k; b++)
						if (b >= 0 && mat[0].length > b)
							sum += mat[a][b];
			return sum;
		}
	}
}
