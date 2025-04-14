import java.util.ArrayList;
import java.util.List;

/**
 * 216. Combination Sum III
 */
public class _216 {
    class Solution1 {
        public List<List<Integer>> combinationSum3(int k, int n) {
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> selected = new ArrayList<>();
            backtrack(k, n, res, selected, 1);
            return res;
        }

        private void backtrack(int k, int n, List<List<Integer>> res, List<Integer> selected, int start) {
            if (n < 0) return;
            if (selected.size() == k) {
                if (n == 0) {
                    res.add(new ArrayList<>(selected));
                }
                return;
            }
            for (int i = start; i <= 9; i++) {
                selected.add(i);
                backtrack(k, n - i, res, selected, i + 1);
                selected.remove(selected.size() - 1);
            }
        }
    }
}
