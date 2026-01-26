import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 22. Generate Parentheses
 */
public class _22 {

    /**
     * Private helper backtracking function.
     * Time: O(2^{2n})
     * Space: O(1)
     * 
     * @param sb a string builder that used to store the "path" -- teh selected parenthesis
     * @param res result
     * @param left left parenthesis left
     * @param right right parenthesis left
     */
    private void backtrack(StringBuilder sb, List<String> res, int left, int right) {
        // base case
        if (left > right || left < 0 || right < 0) return; // **Left parenthesis number >= right parenthesis number
        if (left == 0 && right == 0) {
            res.add(sb.toString());
            return;
        }

        // recursive rules
        for (char parenthesis : Arrays.asList('(',')')) {
            // select 
            sb.append(parenthesis);
            // backtrack
            switch(parenthesis) {
                case '(' -> backtrack(sb, res, left - 1, right);
                case ')' -> backtrack(sb, res, left, right - 1);
            }
            // undo select
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public List<String> generateParenthesis(int n) {
        List<String> res = new LinkedList<>();
        backtrack(new StringBuilder(), res, n, n);
        return res;
    }

    class Solution1_Backtrack {
        private static final String OPEN_PARENTHESIS = "(";
        private static final String CLOSE_PARENTHESIS = ")";

        public List<String> generateParenthesis(int n) {
            StringBuilder path = new StringBuilder();
            List<String> parentheses = new ArrayList<>();
            backtrack(0, n * 2, path, parentheses);
            return parentheses;
        }

        private void backtrack(int open, int n, StringBuilder path, List<String> parentheses) {
            if (path.length() == n) {
                parentheses.add(path.toString());
                return;
            }

            if (open < n / 2) {
                path.append(OPEN_PARENTHESIS);
                backtrack(open + 1, n, path, parentheses);
                path.deleteCharAt(path.length() - 1);
            }

            if (path.length() - open < open) {
                path.append(CLOSE_PARENTHESIS);
                backtrack(open, n, path, parentheses);
                path.deleteCharAt(path.length() - 1);
            }
        }
    }
    
    public static void main(String[] args) {
        new _22().generateParenthesis(3).stream().forEach(System.out::println);
    }
}
