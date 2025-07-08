import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 90. Subsets II
 * Time: O(nlogn + n*2^n)
 * Space: O(logn + n) = O(n)
 */
public class _90 {
    class Solution1 {
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

    class Solution2 {
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> selected = new ArrayList<>();
            Arrays.sort(nums);
            backtrack(nums, 0, selected, res);
            return res;
        }

        private void backtrack(int[] nums, int start, List<Integer> selected, List<List<Integer>> res) {
            res.add(new ArrayList<>(selected));
            int prev = 11;
            for (int select = start; select < nums.length; select++) {
                int num = nums[select];
                if (num == prev) continue; // ignore duplicate
                selected.add(num);
                prev = num;
                backtrack(nums, select + 1, selected, res);
                selected.remove(selected.size() - 1);
            }
        }
    }
}
