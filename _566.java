/**
 * 566. Reshape the Matrix.
 */
public class _566 {
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int m = mat.length, n = mat[0].length;
        if (m * n != r * c) {
            return mat;
        }

        int[][] newMat = new int[r][c];
        for (int i = 0; i < m * n; i++) {
            int oldR = i / n, oldC = i % n;
            int newR = i / c, newC = i % c;
            newMat[newR][newC] = mat[oldR][oldC];
        }
        return newMat;
    }
}
