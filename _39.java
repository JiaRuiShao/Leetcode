import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
Problem:

Given a set of candidate numbers (C) (without duplicates) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

The same repeated number may be chosen from C unlimited number of times.

Note:
All numbers (including target) will be positive integers. The solution set must not contain duplicate combinations.

Example 1:
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
7 is a candidate, and 7 = 7.
These are the only two combinations.

Example 2:
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]

Example 3:
Input: candidates = [2], target = 1
Output: []

Combination
DFS/Backtrack
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