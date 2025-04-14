import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 90. Subsets II
 */
public class _90 {
    class Solution {
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> selected = new ArrayList<>();
            Arrays.sort(nums);
            backtrack(nums, 0, selected, res);
            return res;
        }

        private void backtrack(int[] nums, int start, List<Integer> selected, List<List<Integer>> res) {
            res.add(new ArrayList<>(selected));
            for (int select = start; select < nums.length; select++) {
                int num = nums[select];
                if (select > start && num == nums[select - 1]) continue; // ignore duplicate
                selected.add(num);
                backtrack(nums, select + 1, selected, res);
                selected.remove(selected.size() - 1);
            }
        }
    }
}
