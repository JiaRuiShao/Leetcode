/**
 * 498. Diagonal Traverse
 */
public class _498 {
    class Solution1_Simulation {
        public int[] findDiagonalOrder(int[][] mat) {
            int m = mat.length, n = mat[0].length;
            int[] diagonal = new int[m * n];
            int r = 0, c = 0, pos = 0;
            int d = 1; // direction: 1 means up; -1 means down
            while (pos < m * n) {
                diagonal[pos++] = mat[r][c];
                int nr = r - d, nc = c + d;
                if (nr < 0 || nc < 0 || nr >= m || nc >= n) {
                    if (d == 1) {
                        if (nc < n) c++;
                        else r++;
                    } else {
                        if (nr < m) r++;
                        else c++;
                    }
                    d = -d;
                } else {
                    r = nr;
                    c = nc;
                }
            }
            return diagonal;
        }
    }

    class Solution2_Diagonal {
        public int[] findDiagonalOrder(int[][] mat) {
            if (mat == null || mat.length == 0) return new int[0];
            
            int m = mat.length;
            int n = mat[0].length;
            int[] result = new int[m * n];
            int idx = 0;
            
            // Process each diagonal
            for (int d = 0; d < m + n - 1; d++) {
                // Determine bounds for this diagonal
                int rowStart, colStart;
                
                if (d % 2 == 0) {
                    // Even diagonal: go from bottom-left to top-right
                    rowStart = Math.min(d, m - 1);
                    colStart = d - rowStart;
                    
                    while (rowStart >= 0 && colStart < n) {
                        result[idx++] = mat[rowStart][colStart];
                        rowStart--;
                        colStart++;
                    }
                } else {
                    // Odd diagonal: go from top-left to bottom-right
                    colStart = Math.min(d, n - 1);
                    rowStart = d - colStart;
                    
                    while (colStart >= 0 && rowStart < m) {
                        result[idx++] = mat[rowStart][colStart];
                        rowStart++;
                        colStart--;
                    }
                }
            }
            
            return result;
        }
    }
}
