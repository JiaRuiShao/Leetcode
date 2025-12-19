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
 */
public class _15 {

	/**
	 * Time: O(nlogn + n^2)
	 * Space: O()
	 */
	class Solution_Two_Pointers {
		// Time: O(nlogn + n^2) = O(n^2)
		// Space: O(logn) quicksort stack height
		public List<List<Integer>> threeSum1(int[] nums) {
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

		/**
		 * Sorting + Two Pointers + Dedup.
		 * Notice we can have the same 1st, 2nd or 3rd num val but never allow the same val to be used twice or more for the same num in the triplet, e.g. [-1, -1, -1, -1, -1, -1], -1 can be used as the 1st, 2nd and 3rd num val, but we should not allow any num to use the same val again.
		 * Dedup:
		 * 1. compare ith val with val to the left; if same, skip current index
		 * 2. compare ith val with val to the right after the while loop; if same, skip next index
		 * 
		 * @param nums nums
		 * @return triplets
		 */
		public List<List<Integer>> threeSum2(int[] nums) {
			Arrays.sort(nums);
			int n = nums.length;
			List<List<Integer>> triplets = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				// this will cause duplicate results if left has same val as i, e.g. [-1,0,1,2,-1,-4], -1 could be used as twice for the 1st num
				// we need to prevent this duplicate by skipping the same first num after the while loop
				// Q: why we cannot skip the same vals before the while loop?
				// A: we can, but we need to compare the current index val to the left; if we compare and skip the vals to the right, we will miss some valid triplet results where the 2nd or/& 3rd num have the same val as the 1st one, e.g. [-1,-1,2] valid result for nums [-1,0,1,2,-1,-4]
				int left = i + 1, right = n - 1, target = -nums[i]; 
				while (left < right) {
					int leftVal = nums[left];
					int rightVal = nums[right];
					int cpr = leftVal + rightVal - target;
					if (cpr == 0) {
						triplets.add(List.of(-target, leftVal, rightVal));
						while (left < right && nums[left] == leftVal) left++;
						while (left < right && nums[right] == rightVal) right--;
					} else if (cpr > 0) {
						while (left < right && nums[right] == rightVal) right--;
					} else {
						while (left < right && nums[left] == leftVal) left++;
					}
				}
				while (i + 1 < n && nums[i + 1] == -target) {
					i++;
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
