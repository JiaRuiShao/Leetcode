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
            List<Integer> selected = new ArrayList<>();
            backtrack(nums, selected, res, 0);
            return res;
        }
    
        private void backtrack(int[] nums, List<Integer> selected, List<List<Integer>> res, int start) {
            res.add(new ArrayList<>(selected));
            for (int select = start; select < nums.length; select++) {
                int num = nums[select];
                selected.add(num);
                backtrack(nums, selected, res, select + 1);
                selected.remove(selected.size() - 1);
            }
        }
    }
}
