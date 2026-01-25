/**
 * 73. Set Matrix Zeroes
 */
public class _73 {
    // Time: O(mn)
    // Space: O(mn) worst case
    class Solution0_BFS {

    }

    // Time: O(mn)
    // Space: O(m+n)
    class Solution1_TwoPass {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            boolean[] rows = new boolean[m];
            boolean[] cols = new boolean[n];
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (matrix[r][c] == 0) {
                        rows[r] = true;
                        cols[c] = true;
                    }
                }
            }

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (rows[r] || cols[c]) {
                        matrix[r][c] = 0;
                    }
                }
            }
        }
    }

    // Improvement: use first row and col as marker
    // Time: O(mn)
    // Space: O(1)
    class Solution1_SpaceOptimized {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            boolean zeroInFirstCol = false;
            boolean zeroInFirstRow = false;

            for (int r = 0; r < m; r++) {
                if (matrix[r][0] == 0) {
                    zeroInFirstCol = true;
                }
            }

            for (int c = 0; c < n; c++) {
                if (matrix[0][c] == 0) {
                    zeroInFirstRow = true;
                }
            }

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (matrix[r][c] == 0) {
                        matrix[0][c] = 0;
                        matrix[r][0] = 0;
                    }
                }
            }

            // skip first row & col
            for (int r = 1; r < m; r++) {
                for (int c = 1; c < n; c++) {
                    if (matrix[0][c] == 0 || matrix[r][0] == 0) {
                        matrix[r][c] = 0;
                    }
                }
            }

            if (zeroInFirstCol) {
                for (int r = 0; r < m; r++) {
                    matrix[r][0] = 0;
                }
            }

            if (zeroInFirstRow) {
                for (int c = 0; c < n; c++) {
                    matrix[0][c] = 0;
                }
            }
        }
    }
}
