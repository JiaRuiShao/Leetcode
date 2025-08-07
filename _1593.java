import java.util.HashSet;
import java.util.Set;

/**
 * 1593. Split a String Into the Max Number of Unique Substrings
 */
public class _1593 {
    // Time: O(n*2^n) exponential
    class Solution1_Backtrack_By_Selected_Set {
        private int maxUniqueSplit;
        public int maxUniqueSplit(String s) {
            Set<String> set = new HashSet<>();
            maxUniqueSplit = 1;
            backtrack(s, 0, set);
            return maxUniqueSplit;
        }

        private void backtrack(String s, int start, Set<String> selected) {
            if (start == s.length()) {
                maxUniqueSplit = Math.max(maxUniqueSplit, selected.size());
                return;
            }
            for (int i = start; i < s.length(); i++) {
                String curr = s.substring(start, i + 1);
                if (selected.contains(curr)) {
                    continue;
                }
                selected.add(curr);
                backtrack(s, i + 1, selected);
                selected.remove(curr);
            }
        }
    }

    // Time: O(n*2^n) exponential
    class Solution2_Backtrack_By_Char {
        private int maxUniqueSplit;
        public int maxUniqueSplit(String s) {
            Set<String> set = new HashSet<>();
            maxUniqueSplit = 1;
            backtrack(s, 0, set, 0);
            return maxUniqueSplit;
        }

        private void backtrack(String s, int last, Set<String> selected, int curr) {
            if (curr == s.length()) {
                maxUniqueSplit = Math.max(maxUniqueSplit, selected.size());
                return;
            }
            // option 1: do nothing
            backtrack(s, last, selected, curr + 1);
            // option 2: split
            String select = s.substring(last, curr + 1);
            if (!selected.contains(select)) {
                selected.add(select);
                backtrack(s, curr + 1, selected, curr + 1);
                selected.remove(select);
            }
        }
    }
}
