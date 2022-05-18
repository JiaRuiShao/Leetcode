import java.util.Arrays;

public class _37 {
	// Time: O(9) - O(1)
	private boolean isValid(char c, int row, int col, char[][] b) {
		
		for (int i = 0; i < b.length; i++) {
			// if there's any other char in this row	
			if (b[row][i] == c) return false;
			// if there's any other char in this col
			if (b[i][col] == c) return false;
			// if there's any other char in the 3 x 3 smaller board (first to last row, left to right)
			if (b[(row/3)*3 + i/3][(col/3)*3 + i%3] == c) return false;
		}

		return true;
	}

	// Time: O(9^n) exponential -- where n is the number of empty cells in the given board, so n < 81
	// more specifically, (9!)^9
	// Space: O(n) -- max call stack# * space for each call stack
	private boolean backtrack(int row, int col, char[][] b) {
		// base cases
		// 1 - if we reach the lats row & last col
		if (row == b.length) return true;
		// 2 - if we reach the last col in this row
		if (col == b[0].length) return backtrack(row + 1, 0, b);
		// 3 - if this number b[row][col] already has number
		if (b[row][col] != '.') return backtrack(row, col + 1, b);

		// recursive rules
        // better way: for (char ch = '1'; ch <= '9'; ch++)
		for (char c : Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9')) {
			// if not valid, move on to the next char
			if (!isValid(c, row, col, b)) continue;

			// select this number
			b[row][col] = c;
			// continue traversing the tree
			if (backtrack(row, col + 1, b)) return true;
			// undo select
			b[row][col] = '.';
		}
		return false;
	}

	public void solveSudoku(char[][] board) {
		boolean hasSolution = backtrack(0, 0, board); 
        if (hasSolution) {
            System.out.println("Solution found!");
            Arrays.stream(board).forEach(System.out::println);
        } else {
            System.out.println("No solution found.");
        }
	}

    public static void main(String[] args) {
        // 9x9 board
        char[][] board = new char[][]
	        {{'5', '3', '.', '.', '7', '.', '.', '.', '.' },
	    	{ '6', '.', '.', '1', '9', '5', '.', '.', '.' },
	    	{ '.', '9', '8', '.', '.', '.', '.', '6', '.' },
	    	{ '8', '.', '.', '.', '6', '.', '.', '.', '3' },
	    	{ '4', '.', '.', '8', '.', '3', '.', '.', '1' },
	    	{ '7', '.', '.', '.', '2', '.', '.', '.', '6' },
	    	{ '.', '6', '.', '.', '.', '.', '2', '8', '.' },
	    	{ '.', '.', '.', '4', '1', '9', '.', '.', '5' },
	    	{ '.', '.', '.', '.', '8', '.', '.', '7', '9' }};
    	new _37().solveSudoku(board);
    }
}