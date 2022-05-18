import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/* 

Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

Example:
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]

Combination
Backtrack
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
    
    public static void main(String[] args) {
        new _22().generateParenthesis(3).stream().forEach(System.out::println);
    }
}
