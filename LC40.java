import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
Problem:

Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.

Each number in candidates may only be used once in the combination.

Note: The solution set must not contain duplicate combinations.

Example 1:
Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: 
[[1,1,6],[1,2,5],[1,7],[2,6]]

Example 2:
Input: candidates = [2,5,2,1,2], target = 5
Output: 
[[1,2,2],[5]]

Combination
DFS/Backtrack
*/

public class LC40 {

	private static void dfs(int[] candidates, int target, int index, List<Integer> temp, List<List<Integer>> results) {
		// base case
		if (target <= 0) {
			if (target == 0) results.add(new ArrayList<Integer>(temp));
			return;
		}

		// recursive rules
		for (int i = index; i < candidates.length; i++) { // **start from current number (don't add previous numbers -- repetitive)
			int candidate = candidates[i];
			// ignore the same numbers in the input & optional early stopping
            // notice here teh index will be i - 1 if we have dup elements
            // because we didn't create a new call stack for the dup element
			if ((i > index && candidate == candidates[i - 1]) || target - candidate < 0) continue;

			temp.add(candidate);
			dfs(candidates, target - candidate, i + 1, temp, results); // **notice here the next index should be i + 1 to avoid the same elements be added more than once
			// backtrack -- remove the candidate from the temp , which is the last element in the temp
			temp.remove(temp.size() - 1);
		}
	}

	/**
	 * Use dfs to find all the combinations where all elements are unique & sum equals to target.
     * We sort the input candidates so that later we ignore the redundant numbers.
     * Another way is to use a HashSet to track the unique numbers in the input array. 
     * 
	 * Time: O(2^N + N*Log(N)) where N is the candidates size, T is the target & MIN(N) is the smallest integer in candidates
	 * 	- each candidate only has 2 choices - to get included, or not to
	 * Space: O(N)
	 * 	- max stackframe layers
	 *
	 * @param candidates the candidate numbers
	 * @param target     the target sum
	 * @return all possible combinations of numbers whose sum equal to the target
	 */
	public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
		// used to store the combination results
		List<List<Integer>> results = new LinkedList<>();
		// used to store the current combination
		List<Integer> temp = new LinkedList<>();

        // sort the input integers
        Arrays.sort(candidates);

		// recursive dfs
		dfs(candidates, target, 0, temp, results);

		return results;
	}

	public static void main(String[] args) {
		int[] testCandidates1 = new int[] {10,1,2,7,6,1,5};
		int target1 = 8;
		List<List<Integer>> results1 = combinationSum2(testCandidates1, target1);
		results1.stream().forEach(System.out::print); // [[1,1,6],[1,2,5],[1,7],[2,6]]
		System.out.println();

		int[] testCandidates2 = new int[] {2,5,2,1,2};
		int target2 = 5;
		List<List<Integer>> results2 = combinationSum2(testCandidates2, target2);
		results2.stream().forEach(System.out::print); // [1,2,2],[5]
	}
}