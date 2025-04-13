import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 78. Subsets
 */
public class _78 {
    class Solution1_Backtrack {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            Set<Integer> selected = new HashSet<>();
            backtrack(nums, selected, res, 0);
            return res;
        }

        private void backtrack(int[] nums, Set<Integer> selected, List<List<Integer>> res, int start) {
            res.add(new ArrayList<>(selected));
            for (int i = start; i < nums.length; i++) {
                int num = nums[i];
                if (selected.contains(num)) continue;
                selected.add(num);
                backtrack(nums, selected, res, i + 1);
                selected.remove(num);
            }
        }
    }
}
