import java.util.ArrayList;
import java.util.List;

/**
 * 1849. Splitting a String Into Descending Consecutive Values
 */
public class _1849 {
    // Time: O(n2^n)
    class Solution1_Backtrack {
        public boolean splitString(String s) {
            // following substring val == prev substring val - 1
            return backtrack(s, 0, -1l, 0);
        }

        private boolean backtrack(String s, int start, long prev, int partition) {
            if (start == s.length()) {
                return partition > 1;
            }
            long curr = 0l;
            for (int i = start; i < s.length(); i++) {
                // we have to skip trailing zeros to prevent long overflow (~18 digits) or we need to use BigInteger
                // long curr = Long.valueOf(s.substring(start, i + 1)); // NumberFormatException
                curr = 10 * curr + (s.charAt(i) - '0'); // there would still have overflow problem, but no NumberFormatException is thrown
                if (prev != -1 && prev - 1 < curr) break; // pruning
                if ((prev == -1 || prev == curr + 1) && backtrack(s, i + 1, curr, partition + 1)) {
                    return true;
                }
            }
            return false;
        }
    }

    class FollowUp_All_Valid_Answer {
        public List<List<Long>> splitString(String s) {
            List<List<Long>> splits = new ArrayList<>();
            backtrack(s, 0, splits, new ArrayList<>());
            return splits;
        }

        private void backtrack(String s, int start, List<List<Long>> splits, List<Long> selected) {
            if (start == s.length()) {
                if (selected.size() > 1) {
                    splits.add(new ArrayList<>(selected));
                }
                return;
            }

            long curr = 0;
            for (int i = start; i < s.length(); i++) {
                curr = 10 * curr + (s.charAt(i) - '0'); // need to use BigInteger if s.length() is longer
                if (!selected.isEmpty() && selected.get(selected.size() - 1) < curr + 1) continue;
                selected.add(curr);
                backtrack(s, i + 1, splits, selected);
                selected.remove(selected.size() - 1);
            }
        }
    }
}
