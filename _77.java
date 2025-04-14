import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 77. Combinations
 */
public class _77 {
    class Solution1_Backtrack {
        public List<List<Integer>> combine(int n, int k) {
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> selected = new LinkedList<>();
            backtrack(n, k, 1, selected, res);
            return res;
        }

        private void backtrack(int n, int k, int start, List<Integer> selected, List<List<Integer>> res) {
            if (selected.size() == k) {
                res.add(new ArrayList<>(selected));
                return;
            }
            for (int select = start; select <= n; select++) {
                selected.add(select);
                backtrack(n, k, select + 1, selected, res);
                selected.remove(selected.size() - 1);
            }
        }
    }
}
