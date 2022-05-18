import java.util.LinkedList;
import java.util.List;

public class _79 {

	public static class Solution1 {
        boolean[][] visited;

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
            // base cases
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

	public static class Solution2 {
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

	int m, n;

	/**
	 * Get the next available neighbor indices.
	 * Demo: 3(m) x 4(n)
	 * 0	1	2	3
	 * 4	5	6	7
	 * 8	9	10	11
	 * 
	 * @param idx curr idx
	 * @return a list of available neighbor indices
	 */
	private List<Integer> getNext(int idx) {
		List<Integer> next = new LinkedList<>();
		// add upper idx if not first row
		if (idx / n != 0) next.add(idx - n);
		// add left idx if not first col
		if (idx % n != 0) next.add(idx - 1);
		// add right idx if not first col
		if ((idx + 1) % n != 0) next.add(idx + 1);
		// add lower idx if not first row
		if (idx / n + 1 != m) next.add(idx + n);

		return next;
	}

	private boolean backtrack(char[] b, char[] target, int len, boolean[] used, List<Integer> curr) {
		// base cases
		if (len == target.length) return true;

		// recursive rules
		for (int idx : curr) {
			// check if it's valid
			if (b[idx] != target[len] || used[idx]) continue;
			// select
			len++;
			used[idx] = true;
			List<Integer> next = getNext(idx);
			// dfs
			if (backtrack(b, target, len, used, next)) {
				return true;
			}
			// undo the select
			len--;
			used[idx] = false;
		}

		return false;
	}

	public boolean exist(char[][] board, String word) {
		m = board.length;
		n = board[0].length;

        // corner cases
		if (m * n < word.length()) return false;

        // flatten the board
        char[] b = new char[m * n];

        // initial next available indices
    	List<Integer> next = new LinkedList<>();

        int idx = 0;
        for (int r  = 0; r < m; r++) {
        	for (int c = 0; c < n; c++) {
        		b[idx] = board[r][c];
        		next.add(idx);
        		idx++;
        	}
        }

        // A B C E S F C S A D E E
        // 0 1 2 3 4 5 6 7 8 9 10 11
        // System.out.println(Arrays.toString(b));
        /*char[] b = Arrays.stream(board).flatMapToChar(o -> Arrays.stream(o)).toArray();*/

    	// used index
    	boolean[] used = new boolean[m * n];

    	return backtrack(b, word.toCharArray(), 0, used, next);
        // System.out.println(Arrays.toString(next));
    }

	public static void main(String[] args) {
		char[][] board = 
		{{'A','B','C','E'},
		 {'S','F','C','S'},
		 {'A','D','E','E'}};

		String word = "ABCCED";
		System.out.println(new _79().exist(board, word));

		word = "SEE";
		System.out.println(new _79().exist(board, word));

		word = "ABCB";
		System.out.println(new _79().exist(board, word));

		board = new char[][] {{'a', 'b'}, {'c', 'd'}};
		word = "acdb";
		System.out.println(new _79().exist(board, word));
	}
}