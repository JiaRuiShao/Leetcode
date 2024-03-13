import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 18. 4Sum.
 * Integer Overflow as 3 * 10^9 > 2^31 - 1
 */
public class _18 {
	class Solution1 {
		public List<List<Integer>> fourSum(int[] nums, int target) {
			int n = nums.length;
			Arrays.sort(nums);
			List<List<Integer>> quadruplets = new ArrayList<>();
			for (int a = 0; a < n - 3; a++) {
				if (a > 0 && nums[a] == nums[a - 1]) continue;
				for (int b = a + 1; b < n - 2; b++) {
					if (b > a + 1 && nums[b] == nums[b - 1]) continue;
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

	class Solution2 {

		private List<List<Integer>> twoSum(int[] nums, long target, int left, int right) {
			List<List<Integer>> twins = new ArrayList<>();
			while (left < right) {
				int leftVal = nums[left];
				int rightVal = nums[right];
				long cpr = leftVal + rightVal - target;
				if (cpr == 0) {
					List<Integer> twin = new ArrayList<>();
					twin.add(leftVal);
					twin.add(rightVal);
					twins.add(twin);
					while (left < right && nums[left] == leftVal) left++;
					while (left < right && nums[right] == rightVal) right--;
				} else if (cpr > 0) {
					while (left < right && nums[right] == rightVal) right--;
				} else {
					while (left < right && nums[left] == leftVal) left++;
				}
			}
			
			return twins;
		}
	
		public List<List<Integer>> fourSum(int[] nums, int target) {
			int n = nums.length;
			List<List<Integer>> quadruplets = new ArrayList<>();
			if (n < 4) {
				return quadruplets;
			}
			Arrays.sort(nums);
			for (int i = 0; i < n - 3; i++) {
				if (i > 0 && nums[i] == nums[i - 1]) {
					continue;
				}
				for (int j = i + 1; j < n - 2; j++) {
					if (j > i + 1 && nums[j] == nums[j - 1]) {
						continue;
					}
					List<List<Integer>> twins = twoSum(nums, (long) target - nums[i] - nums[j], j + 1, n - 1);
					for (List<Integer> twin : twins) {
						twin.add(nums[i]);
						twin.add(nums[j]);
						quadruplets.add(twin);
					}
				}
			}
			return quadruplets;
		}
	}
}
