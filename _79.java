import java.util.LinkedList;
import java.util.List;

public class _79 {

	// Time: O(m × n × 3^L) where L is word length
	// Space: O(m x n + 3^L)
	public static class Solution1_DFS {
        private boolean[][] visited;

        public boolean exist(char[][] board, String word) {
            int m = board.length;
            int n = board[0].length;
            visited = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (word.charAt(0) == board[i][j] && backtrack(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
			
            return false;
        }

        boolean backtrack(char[][] board, String word, int i, int j, int index) {
            // base cases -- need to check if all chars traversed before check index validation
			if (index == word.length()) {
                return true;
            }
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || word.charAt(index) != board[i][j] || visited[i][j]) {
                return false;
            }

			// select
            visited[i][j] = true;

			// backtrack
            if (backtrack(board, word, i + 1, j, index + 1)
                    || backtrack(board, word, i - 1, j, index + 1)
                    || backtrack(board, word, i, j + 1, index + 1)
                    || backtrack(board, word, i, j - 1, index + 1)) {
                return true;
            }

			// undo the selection
            visited[i][j] = false;
			
            return false;
        }
    }

	// Time: O(m × n × 3^L) where L is word length
	// Space: O(3^L)
	public static class Solution2_DFS_Improved {
        public boolean exist(char[][] board, String word) {
            int row = board.length;
            int col = board[0].length;

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == word.charAt(0) && backtrack(board, i, j, word, 0) == true) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean backtrack(char[][] board, int i, int j, String word, int index) {
            // base case
			if (index == word.length() - 1) {
                return true;
            }

			// select
            // store the visited char in a temp variable
            char temp = board[i][j];
            board[i][j] = ' '; // marked as used

			// backtracking
            if (i > 0 && board[i - 1][j] == word.charAt(index + 1) && backtrack(board, i - 1, j, word, index + 1) == true) {
                return true;
            }
            if (i < board.length - 1 && board[i + 1][j] == word.charAt(index + 1) && backtrack(board, i + 1, j, word, index + 1) == true) {
                return true;
            }

            if (j > 0 && board[i][j - 1] == word.charAt(index + 1) && backtrack(board, i, j - 1, word, index + 1) == true) {
                return true;
            }

            if (j < board[0].length - 1 && board[i][j + 1] == word.charAt(index + 1) && backtrack(board, i, j + 1, word, index + 1) == true) {
                return true;
            }

			// undo the selection
            board[i][j] = temp;

            return false;
        }
    }
}