import java.util.Arrays;

/**
 * 48. Rotate Image.
 */
public class _48 {
    static class Rotate90DegreesClockwise {
        /**First swap the elements on the diagonal, then reverse each col:
         * 1, 2, 3                    1, 4, 7                      7, 4, 1
         * 4, 5, 6         becomes    2, 5, 8           becomes    8, 5, 2
         * 7, 8, 9                    3, 6, 9                      9, 6, 3
         **/
        public void rotate(int[][] matrix) {
            int m = matrix.length, tmp;
            /** First rotate on the diagonal -- swap m[i][j] & m[j][i] **/
            for (int i = 0; i < m; i++) {
                for (int j = i + 1; j < m; j++) { // OR j = 0; j < i; j++
                    /**  j starts from i + 1, so that the diagonal changes with itself, results in no change.*/
                    tmp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = tmp;
                }
            }

            System.out.println(Arrays.deepToString(matrix));

            /** Then reverse on the col */
            int left, right;
            for (int row = 0; row < m; row++) {
                // reset col range
                left = 0;
                right = m - 1;
                while (left < right) {
                    tmp = matrix[row][left];
                    matrix[row][left] = matrix[row][right];
                    matrix[row][right] = tmp;
                    left++;
                    right--;
                }
            }

            System.out.println(Arrays.deepToString(matrix));
        }
    }

    static class Rotate90DegreesCounterClockwise {
        /**First swap the elements on the diagonal, then reverse each col:
         * 1, 2, 3                    9, 6, 3                      3, 6, 9
         * 4, 5, 6         becomes    8, 5, 2           becomes    2, 5, 8
         * 7, 8, 9                    7, 4, 1                      1, 4, 7
         **/
        public void rotate(int[][] matrix) {
            int m = matrix.length, tmp;
            /** First rotate on the diagonal -- swap m[i][j] & m[m-1-j][m-1-i] **/
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m - 1 - i; j++) { // OR j = m - 1; j > m - 1 - i; j--
                    tmp = matrix[i][j];
                    matrix[i][j] = matrix[m-1-j][m-1-i];
                    matrix[m-1-j][m-1-i] = tmp;
                }
            }

            System.out.println(Arrays.deepToString(matrix));

            /** Then reverse on the col */
            int left, right;
            for (int row = 0; row < m; row++) {
                // reset col range
                left = 0;
                right = m - 1;
                while (left < right) {
                    tmp = matrix[row][left];
                    matrix[row][left] = matrix[row][right];
                    matrix[row][right] = tmp;
                    left++;
                    right--;
                }
            }

            System.out.println(Arrays.deepToString(matrix));
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
        System.out.println(Arrays.deepToString(matrix));
        new Rotate90DegreesCounterClockwise().rotate(matrix);
    }
}
