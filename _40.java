import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 40. Combination Sum II
 */
public class _40 {

	class Solution1_Backtrack {
		// Time: O(n x 2^n)
		// Space: O(n)
		public List<List<Integer>> combinationSum2(int[] candidates, int target) {
			Arrays.sort(candidates);
			List<List<Integer>> res = new ArrayList<>();
			List<Integer> selected = new ArrayList<>();
			backtrack(candidates, target, 0, 0, selected, res);
			return res;
		}
	
		private void backtrack(int[] nums, int k, int start, int selectedSum, List<Integer> selected, List<List<Integer>> res) {
			if (selectedSum > k) return;
			if (selectedSum == k) {
				res.add(new ArrayList<>(selected));
			}
			for (int select = start; select < nums.length; select++) {
				int num = nums[select];
				if (select > start && num == nums[select - 1]) continue; // ignore the duplicate numbers
				selected.add(num);
				backtrack(nums, k, select + 1, selectedSum + num, selected, res);
				selected.remove(selected.size() - 1);
			}
		}
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