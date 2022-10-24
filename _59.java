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
}