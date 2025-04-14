import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 47. Permutations II
 */
public class _47 {
    class Solution1_Backtrack {
        public List<List<Integer>> permuteUnique(int[] nums) {
            Arrays.sort(nums);
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> selected = new ArrayList<>();
            boolean[] used = new boolean[nums.length];
            backtrack(nums, used, selected, res);
            return res;
        }

        private void backtrack(int[] nums, boolean[] used, List<Integer> selected, List<List<Integer>> res) {
            if (selected.size() == nums.length) {
                res.add(new ArrayList<>(selected));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (used[i]) continue;
                int num = nums[i];
                if (i > 0 && num == nums[i - 1] && !used[i - 1]) continue;
                selected.add(num);
                used[i] = true;
                backtrack(nums, used, selected, res);
                selected.remove(selected.size() - 1);
                used[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Solution1_Backtrack solution = new _47().new Solution1_Backtrack();
        int[] nums = {1,1,2};
        System.out.println(solution.permuteUnique(nums));
    }
}
