import java.util.ArrayList;
import java.util.List;

/**
 * 784. Letter Case Permutation
 */
public class _784 {
    class Solution1_Backtrack_By_Char_Combination_ElemUsedOnce_NoDedup {
        // Time: O(n*2^L) where n is s len and L is letter num
        // Space: auxiliary O(n) total O(n*2^L)
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

    class Solution2_Iterative {
        // Time: O(n*2^L) where n is s len and L is letter num
        // Space: auxiliary O(n) total O(n*2^L)
        public List<String> letterCasePermutation(String s) {
            List<String> res = new ArrayList<>();
            res.add(s);
            for (int i = 0; i < s.length(); i++) { // for each char
                if (!Character.isLetter(s.charAt(i))) continue;
                int size = res.size();
                for (int j = 0; j < size; j++) { // for each prev generated permutation
                    char[] t = res.get(j).toCharArray();
                    t[i] = Character.isLowerCase(t[i]) ? Character.toUpperCase(t[i])
                                                    : Character.toLowerCase(t[i]);
                    res.add(new String(t));
                }
            }
            return res;
        }
    }

}
