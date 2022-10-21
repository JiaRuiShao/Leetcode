import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 18. 4Sum.
 */
public class _18 {
	class Solution {
		public List<List<Integer>> fourSum(int[] nums, int target) {
			// n^2logn+nlogn
			int n = nums.length;
			Arrays.sort(nums);
			// 0, 1, 2, 3
			List<List<Integer>> quadruplets = new ArrayList<>();
			for (int a = 0; a < n - 3; a++) {
				if (a > 0 && nums[a] == nums[a - 1]) continue;
				for (int b = a + 1; b < n - 2; b++) {
					if (b > a + 1 && nums[b] == nums[b - 1]) continue;
					// binary search
					int c = b + 1, d = n - 1;
					while (c < d) {
						int left = nums[c], right = nums[d];
						long sum = (long) left + right, key = (long) target - nums[a] - nums[b];
						if (sum == key) {
							quadruplets.add(Arrays.asList(nums[a], nums[b], left, right));
							while (c < d && nums[c] == left) c++;
							while (c < d && nums[d] == right) d--;
						} else if (sum < key) {
							c++;
						} else {
							d--;
						}
					}
				}
			}
			return quadruplets;
		}
	}
}
