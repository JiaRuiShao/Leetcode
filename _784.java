import java.util.ArrayList;
import java.util.List;

/**
 * 784. Letter Case Permutation
 */
public class _784 {
    class Solution1_Backtrack_Combination_ElemUsedOnce_NoDedup {
        // Time: O(2^n)
        // Space: O(n)
        public List<String> letterCasePermutation(String s) {
            List<String> strings = new ArrayList<>();
            backtrack(s, strings, new StringBuilder(), 0);
            return strings;
        }

        private void backtrack(String s, List<String> res, StringBuilder selected, int idx) {
            if (idx == s.length()) {
                res.add(selected.toString());
                return;
            }
            char c = s.charAt(idx++);
            selected.append(c);
            backtrack(s, res, selected, idx);
            selected.deleteCharAt(selected.length() - 1);

            if (Character.isLetter(c)) {
                char transformedChar = getTransformedChar(c);
                selected.append(transformedChar);
                backtrack(s, res, selected, idx);
                selected.deleteCharAt(selected.length() - 1);
            }
        }

        private char getTransformedChar(char c) {
            if (Character.isUpperCase(c)) {
                return Character.toLowerCase(c);
            }
            return Character.toUpperCase(c);
        }
    }
}
