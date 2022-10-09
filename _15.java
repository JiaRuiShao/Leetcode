import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 15. 3Sum.
 */
public class _15 {
	public List<List<Integer>> threeSum(int[] nums) {
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
