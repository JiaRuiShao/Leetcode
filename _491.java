import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 491. Non-decreasing Subsequences
 */
public class _491 {
    // Time: O(n*2^n)
    // Space: O(n)
    class Solution1_Backtrack_Combination_ElemUsedOnce_DeDup {
        public List<List<Integer>> findSubsequences(int[] nums) {
            // combination -- dedup needed -- elem used only once
            // however this question the original order matters so we cannot sort and then compare to avoid duplicate, so we have to use a set used here for each callstack to avoid dup value
            List<List<Integer>> subseq = new ArrayList<>();
            backtrack(nums, subseq, new ArrayList<>(), 0);
            return subseq;
        }

        private void backtrack(int[] nums, List<List<Integer>> subseq, List<Integer> select, int idx) {
            if (select.size() >= 2) {
                subseq.add(new ArrayList<Integer>(select));
            }
            Set<Integer> used = new HashSet<>();
            for (int i = idx; i < nums.length; i++) {
                int curr = nums[i];
                if (used.contains(curr)) continue;
                if (!select.isEmpty() && curr < select.get(select.size() - 1)) continue;
                select.add(curr);
                used.add(curr);
                backtrack(nums, subseq, select, i + 1);
                select.remove(select.size() - 1);
            }
        }
    }
}
