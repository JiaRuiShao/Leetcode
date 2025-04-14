import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 46. Permutations
 */
public class _46 {

    // Time: O(N * N!)
    // Space: O(N)
    class Solution1_Backtrack {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> result = new LinkedList<>();
            List<Integer> path = new LinkedList<>(); // **record "path"**
            backtrack(nums, path, result);
            return result;
        }


        private void backtrack(int[] nums, List<Integer> path, List<List<Integer>> result) {
            // base case
            if (path.size() == nums.length) {
                result.add(new ArrayList<>(path));
                return;
            }

            // recursive rule
            for (int num : nums) {
                // illegal selection when the current num is already in the path, move on to the next element
                if (path.contains(num)) continue; // O(n)
                // **select
                path.add(num);
                // **move on to the next call stack in decision tree
                backtrack(nums, path, result);
                // **deselect
                path.remove(path.size() - 1);
            }
        }
    }

    class Solution2_Backtrack_Improved {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> result = new LinkedList<>(); // save the results
            List<Integer> path = new LinkedList<>(); // **record "path"
            boolean[] used = new boolean[nums.length]; // **record each elements usage
            backtrack(nums, used, path, result);
            return result;
        }

        // Use boolean arr that tracks whether each element is contained in current path or not.
        // Time: O(N * N!)
        // Space: O(N)
        private void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
            // base case
            if (path.size() == nums.length) {
                result.add(new ArrayList<>(path));
                return;
            }

            // recursive rule
            for (int select = 0; select < nums.length; select++) {
                int num = nums[select];
                if (used[select]) continue;
                // **select**
                path.add(num);
                used[select] = true;
                // **move on to the next call stack in decision tree**
                backtrack(nums, used, path, result);
                // **deselect**
                path.remove(path.size() - 1);
                used[select] = false;
            }
        }
    }

    class FollowUp_Elements_Can_Used_Unlimited {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> used = new LinkedList<>();
            backtrack(nums, res, used);
            return res;
        }

        private void backtrack(int[] nums, List<List<Integer>> res, List<Integer> used) {
            if (used.size() == nums.length) {
                res.add(new ArrayList<>(used));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                used.add(nums[i]);
                backtrack(nums, res, used);
                used.remove(used.size() - 1);
            }
        }
    }
    
    public static void main(String[] args) {
        Solution2_Backtrack_Improved solution = new _46().new Solution2_Backtrack_Improved();
		int[] test1 = new int[] {1, 2, 3};
		solution.permute(test1).stream().forEach(System.out::print); // [[[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
		System.out.println();

		int[] test2 = new int[] {0, 1};
		solution.permute(test2).stream().forEach(System.out::print); // [[0,1],[1,0]]
        System.out.println();

        int[] test3 = new int[] {1};
		solution.permute(test3).stream().forEach(System.out::print); // [[1]]
        System.out.println();
	}
}
