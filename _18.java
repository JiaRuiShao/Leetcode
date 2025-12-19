import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 18. 4Sum
 * 
 * 1 - BF O(n^4), O(1)
 * 2 - Set O(n^3), O(n^4)
 * 3 - Sort + TF O(n^3), O(logn)
 * 
 * Clarification:
 * - Value range? -10⁹ to 10⁹, target can be -10⁹ to 10⁹
 * - Does output order matters?
 * - Is the array sorted?
 * 
 * Red Flags:
 * - Didn't recognize Integer Overflow as 3 * 10^9 > 2^31 - 1
 * - Wrong deduplication logic
 */
public class _18 {
	// Time: O(n^3)
	// Space: O(logn) dual-pivot sorting call stack height
	class Solution1_Sort_TwoPointers {
		public List<List<Integer>> fourSum(int[] nums, int target) {
			Arrays.sort(nums);
			List<List<Integer>> quadruplets = new ArrayList<>();
			int n = nums.length;
			for (int i = 0; i < n - 3; i++) {
				if (i > 0 && nums[i] == nums[i - 1]) continue;
				for (int j = i + 1; j < n - 2; j++) {
					if (j > i + 1 && nums[j] == nums[j - 1]) continue;
					// long k = target - nums[i] - nums[j]; // overflow before cast and save as long
					long k = (long) target - nums[i] - nums[j];
					int left = j + 1, right = n - 1;
					while (left < right) {
						long sum = nums[left] + nums[right];
						if (sum == k) {
							quadruplets.add(List.of(nums[i], nums[j], nums[left], nums[right]));
							while (left + 1 < n && nums[left + 1] == nums[left]) left++;
							while (right - 1 > j && nums[right - 1] == nums[right]) right--;
							left++;
							right--;
						} else if (sum < k) {
							left++;
						} else {
							right--;
						}
					}
				}
			}
			return quadruplets;
		}
	}

	class Followup_kSum_Sort_Backtrack_TwoPointers {
		public List<List<Integer>> kSum(int[] nums, int target, int k) {
			Arrays.sort(nums);
			List<List<Integer>> result = new ArrayList<>();
    		kSumHelper(nums, target, k, 0, new ArrayList<>(), result);
			return result;
		}

		private void kSumHelper(int[] nums, long target, int k, int start, 
                        List<Integer> current, List<List<Integer>> result) {			
			if (k == 2) {
				// Base case: two-sum with two pointers
				int left = start, right = nums.length - 1;
				while (left < right) {
					long sum = (long) nums[left] + nums[right];
					if (sum == target) {
						List<Integer> combination = new ArrayList<>(current);
						combination.add(nums[left]);
						combination.add(nums[right]);
						result.add(combination);

						while (left < right && nums[left] == nums[left + 1]) left++;
						while (left < right && nums[right] == nums[right - 1]) right--;
						left++;
						right--;
					} else if (sum < target) {
						left++;
					} else {
						right--;
					}
				}
				return;
			}
			
			// Recursive case
			for (int i = start; i < nums.length - k + 1; i++) {
				if (i > start && nums[i] == nums[i - 1]) continue;
				current.add(nums[i]);
				kSumHelper(nums, target - nums[i], k - 1, i + 1, current, result);
				current.remove(current.size() - 1);  // Backtrack
			}
		}
	}
}
