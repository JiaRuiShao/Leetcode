import java.util.Arrays;

/**
 * 52. N-Queens II
 */
public class _52 {
    class Solution1_Backtrack {
        public int totalNQueens(int n) {
            char[][] board = new char[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(board[i], '.');
            }
            return backtrack(board, 0);
        }

        private int backtrack(char[][] board, int row) {
            int n = board.length, res = 0;
            if (row == n) {
                return 1;
            }
            for (int col = 0; col < n; col++) {
                if (!isValid(board, row, col)) continue;
                board[row][col] = 'Q';
                res += backtrack(board, row + 1);
                board[row][col] = '.';
            }
            return res;
        }

        private boolean isValid(char[][] board, int row, int col) {
            // Check column
            for (int r = 0; r < row; r++) {
                if (board[r][col] == 'Q') return false;
            }

            // Check top-left diagonal
            for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
                if (board[r][c] == 'Q') return false;
            }

            // Check top-right diagonal
            for (int r = row - 1, c = col + 1; r >= 0 && c < board.length; r--, c++) {
                if (board[r][c] == 'Q') return false;
            }
            return true;
        }
    }
}
