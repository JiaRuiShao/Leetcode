import java.util.ArrayList;
import java.util.List;

/**
 * 131. Palindrome Partitioning
 */
public class _131 {
    class Solution1_Backtrack_Combination_ElemUsedOnce_NoDedup {
        // we need to use all chars in the given String
        // combination -- no dedup -- elem used once
        // example:
        // s = "aaab"
        // output = [["a","a","a","b"],["a","aa","b"],["aa","a","b"],["aaa","b"]]
        public List<List<String>> partition(String s) {
            List<List<String>> palindromes = new ArrayList<>();
            backtrack(s, palindromes, new ArrayList<>(), 0);
            return palindromes;
        }

        private void backtrack(String s, List<List<String>> res, List<String> select, int start) {
            int n = s.length();
            if (start == n) {
                res.add(new ArrayList<>(select));
            }
            for (int i = start; i < n; i++) {
                if (!isPalindrome(s, start, i)) {
                    continue;
                }
                select.add(s.substring(start, i + 1));
                backtrack(s, res, select, i + 1);
                select.remove(select.size() - 1);
            }
        }

        private boolean isPalindrome(String s, int l, int r) {
            while (l < r) {
                if (s.charAt(l) != s.charAt(r)) {
                    return false;
                }
                l++;
                r--;
            }
            return true;
        }
    }
}
