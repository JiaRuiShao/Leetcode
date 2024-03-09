/**
 * 1314. Matrix Block Sum.
 * Given a m x n matrix mat and an integer k, return a matrix answer where each answer[i][j] is the sum of all elements mat[r][c] for:
 *      i - k <= r <= i + k
 *      j - k <= c <= j + k, and
 *      (r, c) is a valid position in the matrix
 * Reference for problem 304. Range Sum Query 2D - Immutable
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
		 * Time: O(m*n*k^2)
		 * Space: O(1)
		 *
		 * @param mat given int matrix
		 * @param k   k
		 * @return range sum of the rectangle from upper left [i-k, j-k] to lower right [i+k, j+k]
		 */
		public int[][] matrixBlockSum(int[][] mat, int k) {
			int m = mat.length, n = mat[0].length;
			int[][] ans = new int[m][n];
			int r1, r2, c1, c2;
			
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					r1 = Math.max(i - k, 0);
					r2 = Math.min(i + k, m - 1);
					c1 = Math.max(j - k, 0);
					c2 = Math.min(j + k, n - 1);
					
					for (int r = r1; r <= r2; r++) {
						for (int c = c1; c <= c2; c++) {
							ans[r][c] += mat[i][j];
						}
					}
				}
			}
			
			return ans;
		}
	}

	class Solution3_PreSum_Helper {
		private int[][] preSum;
	
		public int[][] matrixBlockSum(int[][] mat, int k) {
			int m = mat.length, n = mat[0].length;
			buildPreSum(mat, m, n);
			int r1 = 0, r2 = 0, c1 = 0, c2 = 0;
			int[][] answer = new int[m][n];
			for (int r = 0; r < m; r++) {
				r1 = (r - k) < 0 ? 0 : (r - k);
				r2 = (r + k) >= m ? m - 1 : (r + k);
				for (int c = 0; c < n; c++) {
					c1 = (c - k) < 0 ? 0 : (c - k);
					c2 = (c + k) >= n ? n - 1 : (c + k);
					answer[r][c] = findRangeSum(r1, r2, c1, c2);
				}
			}
			return answer;
		}
	
		private void buildPreSum(int[][] mat, int m, int n) {
			preSum = new int[m + 1][n + 1];
			for (int r = 0; r < m; r++) {
				for (int c = 0; c < n; c++) {
					preSum[r + 1][c + 1] = preSum[r + 1][c] + preSum[r][c + 1] - preSum[r][c] + mat[r][c];
				}
			}
		}
	
		private int findRangeSum(int r1, int r2, int c1, int c2) {
			r2++;
			c2++;
			return preSum[r2][c2] - preSum[r2][c1] - preSum[r1][c2] + preSum[r1][c1];
		}
	}
}
