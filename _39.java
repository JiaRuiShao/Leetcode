import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 39. Combination Sum
 */
public class _39 {

	private static void dfs(int[] candidates, int target, int index, List<Integer> temp, List<List<Integer>> results) {
		// base case
		if (target <= 0) {
			if (target == 0) results.add(new ArrayList<Integer>(temp));
			return;
		}

		// recursive rules
		for (int i = index; i < candidates.length; i++) { // **start from current number (don't add previous numbers -- repetitive)
			int candidate = candidates[i];
			// optional: pruning
			if (target - candidate < 0) continue;

			temp.add(candidate);
			dfs(candidates, target - candidate, i, temp, results);
			// backtrack -- remove the candidate from the temp , which is the last element in the temp
			temp.remove(temp.size() - 1);
		}
	}

	/**
	 * Use dfs to find all the combinations.
	 * Time: O(N ^ (T/MIN(N) + 1)) where N is the candidates size, T is the target & MIN(N) is the smallest integer in candidates
	 * 	- time = nodes# * process time each node
	 * 	- why T/MIN(N) + 1 layers/call stack? in case the target cannot be evenly divided by min(n)
	 * Space: O(T/MIN(N))
	 * 	- max stackframe layers
	 *
	 * @param candidates the candidate numbers
	 * @param target     the target sum
	 * @return all possible combinations of numbers whose sum equal to the target
	 */
	public static List<List<Integer>> combinationSum(int[] candidates, int target) {
		// used to store the combination results
		List<List<Integer>> results = new LinkedList<>();
		// used to store the current combination
		List<Integer> temp = new LinkedList<>();

		// recursive dfs
		dfs(candidates, target, 0, temp, results);

		return results;
	}

	// Time: O(n^(T / min(nums)))
	// Space: O(T / min(nums))
	class Solution1_Backtrack {
		public List<List<Integer>> combinationSum(int[] candidates, int target) {
			List<List<Integer>> combinations = new ArrayList<>();
			List<Integer> combination = new ArrayList<>();
			backtrack(candidates, 0, target, combination, combinations);
			return combinations;
		}

		private void backtrack(int[] nums, int start, int remain, List<Integer> selected, List<List<Integer>> res) {
			if (remain < 0) {
				return;
			}
			if (remain == 0) {
				res.add(new ArrayList<>(selected));
				return;
			}
			for (int i = start; i < nums.length; i++) {
				selected.add(nums[i]);
				backtrack(nums, i, remain - nums[i], selected, res);
				selected.remove(selected.size() - 1);
			}
		}
	}

	class Solution1_Backtrack_Optimized {
		public List<List<Integer>> combinationSum(int[] candidates, int target) {
			Arrays.sort(candidates); // sort for early pruning
			List<List<Integer>> combinations = new ArrayList<>();
			List<Integer> combination = new ArrayList<>();
			backtrack(candidates, 0, target, combination, combinations);
			return combinations;
		}

		private void backtrack(int[] nums, int start, int remain, List<Integer> selected, List<List<Integer>> res) {
			if (remain == 0) {
				res.add(new ArrayList<>(selected));
				return;
			}
			for (int i = start; i < nums.length; i++) {
				if (remain < nums[i]) break;
				selected.add(nums[i]);
				backtrack(nums, i, remain - nums[i], selected, res);
				selected.remove(selected.size() - 1);
			}
		}
	}

	public static void main(String[] args) {
		int[] testCandidates1 = new int[] {2, 3, 6, 7};
		int target1 = 7;
		List<List<Integer>> results1 = combinationSum(testCandidates1, target1);
		results1.stream().forEach(System.out::print); // [[2,2,3],[7]]
		System.out.println();

		int[] testCandidates2 = new int[] {2, 3, 5};
		int target2 = 8;
		List<List<Integer>> results2 = combinationSum(testCandidates2, target2);
		results2.stream().forEach(System.out::print); // [[2,2,2,2],[2,3,3],[3,5]]
	}
}