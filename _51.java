import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 51. N-Queens
 * 
 * Clarification:
 * - Can queens attack diagonally? Yes all 8 directions
 */
public class _51 {
    class Solution1_DFS {
        public List<List<String>> solveNQueens(int n) {
            List<List<String>> res = new ArrayList<>();
            char[][] board = new char[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(board[i], '.');
            }
            backtrack(board, res, 0);
            return res;
        }

        private void backtrack(char[][] board, List<List<String>> res, int row) {
            int n = board.length;
            if (row == n) {
                res.add(drawBoard(board));
            }
            for (int col = 0; col < n; col++) {
                if (!isValid(board, row, col)) continue;
                board[row][col] = 'Q';
                backtrack(board, res, row + 1);
                board[row][col] = '.';
            }
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

        private List<String> drawBoard(char[][] board) {
            List<String> res = new ArrayList<>();
            for (int r = 0; r < board.length; r++) {
                res.add(new String(board[r]));
            }
            return res;
        }
    }

    // Improve Time complexity from O(n*n!) to O(n!)
    public class Solution2_Bitmask {
        public List<List<String>> solveNQueens(int n) {
            List<List<String>> res = new ArrayList<>();
            char[][] board = new char[n][n];
            for (char[] row : board) Arrays.fill(row, '.');
    
            backtrack(res, board, 0, 0, 0, 0, n);
            return res;
        }
    
        private void backtrack(List<List<String>> res, char[][] board, int row,
                               int cols, int diags1, int diags2, int n) {
            if (row == n) {
                res.add(drawBoard(board));
                return;
            }
    
            for (int col = 0; col < n; col++) {
                int colBit = 1 << col;
                int diag1Bit = 1 << (row - col + n); // shift by +n to ensure non-negative
                int diag2Bit = 1 << (row + col);
    
                if ((cols & colBit) != 0 || (diags1 & diag1Bit) != 0 || (diags2 & diag2Bit) != 0) continue;
    
                // Place queen
                board[row][col] = 'Q';
    
                // Mark usage
                backtrack(res, board, row + 1,
                          cols | colBit,
                          diags1 | diag1Bit,
                          diags2 | diag2Bit,
                          n);
    
                // Backtrack
                board[row][col] = '.';
            }
        }
    
        private List<String> drawBoard(char[][] board) {
            List<String> res = new ArrayList<>();
            for (char[] row : board) {
                res.add(new String(row));
            }
            return res;
        }
    }
 
    class Solution3_Selected_Columns {
        public List<List<String>> solveNQueens(int n) {
            List<List<String>> res = new ArrayList<>();
            backtrack(res, n, new ArrayList<Integer>());
            return res;
        }
    
        private void backtrack(List<List<String>> res, int n, List<Integer> cols) {
            int row = cols.size();
            if (row == n) {
                res.add(drawBoard(n, cols));
            }
            for (int col = 0; col < n; col++) {
                if (!isValid(cols, col)) continue;
                cols.add(col);
                backtrack(res, n, cols);
                cols.remove(cols.size() - 1);
            }
        }
    
        private boolean isValid(List<Integer> cols, int col) {
            int row = cols.size();
            for (int r = 0; r < row; r++) {
                if (col == cols.get(r)) { // do not allow for same col number
                    return false;
                }
                // Major Diagonal Check (top-left ↘ bottom-right)
                // On a major diagonal, the difference between row and col is the same
                if (row - r == col - cols.get(r)) {
                    return false;
                }
                // Minor Diagonal Check (top-right ↙ bottom-left)
                // On a minor diagonal, the sum of row and col is constant
                if (row + col == r + cols.get(r)) {
                    return false;
                }
            }
            return true;
        }
    
        private List<String> drawBoard(int n, List<Integer> cols) {
            List<String> res = new ArrayList<>();
            for (int r = 0; r < n; r++) {
                StringBuilder sb = new StringBuilder();
                for (int c = 0; c < n; c++) {
                    if (cols.get(r) == c) sb.append('Q');
                    else sb.append('.');
                }
                res.add(sb.toString());
            }
            return res;
        }
    }
}