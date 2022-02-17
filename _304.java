public class _304 {
    class NumMatrix {

        private int[][] preSum;

        public NumMatrix(int[][] matrix) {
            preSum = new int[matrix.length + 1][matrix[0].length + 1];
            for (int i = 1; i < preSum.length; i++) {
                for (int j = 1; j < preSum[0].length; j++) {
                    // calculate the sum for matrix [0, 0, i, j]
                    preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i - 1][j - 1];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return preSum[row2 + 1][col2 + 1] - preSum[row1][col2 + 1] - preSum[row2 + 1][col1] + preSum[row1][col1];
        }
    }
}
