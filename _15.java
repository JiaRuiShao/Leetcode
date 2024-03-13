import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 15. 3Sum.
 */
public class _15 {

	/**
	 * Time: O(nlogn + n^2)
	 * Space: O()
	 */
	class Solution_Two_Pointers {
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
		public List<List<Integer>> threeSum1(int[] nums) {
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

		public List<List<Integer>> threeSum2(int[] nums) {
			Arrays.sort(nums);
			List<List<Integer>> triplets = new ArrayList<>();
			for (int i = 0; i < nums.length - 2; i++) {
				if (i >= 1 && nums[i] == nums[i - 1]) {
					continue;
				}
				int left = i + 1;
				int right = nums.length - 1;
				while (left < right) {
					int sum = nums[i] + nums[left] + nums[right];
					if (sum == 0) {
						triplets.add(Arrays.asList(nums[i], nums[left], nums[right]));
						// ignore the duplicate numbers
						while (left < right && nums[left + 1] == nums[left]) {
							left++;
						}
						while (left < right && nums[right - 1] == nums[right]) {
							right--;
						}
						// these two lines are critical and easy to forget, if so, it'll TLE
						left++;
						right--;
					} else if (sum > 0) {
						right--;
					} else {
						left++;
					}
				}
			}
			return triplets;
		}
	}
}
