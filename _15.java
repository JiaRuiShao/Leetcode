import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 15. 3Sum
 * 
 * 1 - BF O(n^3), O(1)
 * 2 - Fixed First Elem, Find Supplements using Set O(n^2), O(n)
 * 3 - Sorting + TP O(nlogn + n^2), O(logn) dual-pivot quicksort
 * 
 * Clarification:
 * - Does order matters in duplicate triplets? [-1,0,1] and [0,-1,1] are considered the same triplet
 * - Does order matter for duplicate triplets? 
 * - Assume input array min length is 3?
 * - What to return if input array has less than 3 elem or there's no valid triplets? Return an empty list
 * 
 * Followup:
 * - Find triplet whose sum is closest to target (LC 16)
 * - Solve without sorting? Use HashMap, dedup using Set on sorted triplets
 * - Generalized to 4Sum & kSum
 */
public class _15 {

	/**
	 * Time: O(nlogn + n^2)
	 * Space: O()
	 */
	class Solution_Two_Pointers {
		// Time: O(nlogn + n^2) = O(n^2)
		// Space: O(logn) quicksort stack height
		public List<List<Integer>> threeSum(int[] nums) {
			Arrays.sort(nums);
			List<List<Integer>> triplets = new ArrayList<>();
			int n = nums.length;
			for (int i = 0; i < n - 2; i++) {
				if (i > 0 && nums[i] == nums[i - 1]) continue;
				int target = -nums[i];
				int left = i + 1, right = n - 1;
				while (left < right) {
					int sum = nums[left] + nums[right];
					if (sum == target) {
						triplets.add(Arrays.asList(nums[i], nums[left], nums[right]));
						while (left + 1 < n && nums[left + 1] == nums[left]) left++;
						left++;
						while (right - 1 > i && nums[right - 1] == nums[right]) right--;
						right--;
					} else if (sum < target) {
						left++;
					} else {
						right--;
					}
				}
			}
			return triplets;
		}
	}

	// Time: O(n^2)
	// Space: O(n)
	class Solution2_Set {
		public List<List<Integer>> threeSum(int[] nums) {
			int n = nums.length;
			Set<List<Integer>> triplets = new HashSet<>();
			Set<Integer> first = new HashSet<>();
			for (int i = 0; i < n - 2; i++) {
				int a = nums[i];
				if (first.contains(a)) continue;
				Set<Integer> seen = new HashSet<>();
				for (int j = i + 1; j < n; j++) {
					int c = nums[j], b = -a - c;
					if (seen.contains(b)) {
						List<Integer> triplet = Arrays.asList(a, b, c);
						Collections.sort(triplet);
						triplets.add(triplet);
					}
					seen.add(c);
				}
				first.add(a);
			}
			return new ArrayList(triplets);
		}
	}

	// Below solution using set to deduplicate doesn't work
	// For nums [-1,0,1,2,-1,-4], we get [[-1,0,1],[-1,2,-1],[0,1,-1]] as output, where we have duplicate combination triplet
	// It doesn't handle cross-first-element duplicates
	// This is why we have to use a Set to deduplicate the result set
	class WrongSolution_Set {
		public List<List<Integer>> threeSum(int[] nums) {
			int n = nums.length;
			List<List<Integer>> triplets = new ArrayList<>();
			Set<Integer> first = new HashSet<>();
			for (int i = 0; i < n - 2; i++) {
				int a = nums[i];
				if (first.contains(a)) continue;
				Set<Integer> seen = new HashSet<>();
				Set<Integer> second = new HashSet<>();
				for (int j = i + 1; j < n; j++) {
					int c = nums[j], b = -a - c;
					if (seen.contains(b) && !second.contains(b)) {
						triplets.add(List.of(a, b, c));
						second.add(b);
					}
					seen.add(c);
				}
				first.add(a);
			}
			return triplets;
		}
	}
}
