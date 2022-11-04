import java.util.TreeSet;

/**
 * 220. Contains Duplicate III.
 */
public class _220 {
	/**
	 * Sliding Window.
	 * Time: O(nlogn)
	 * Space: O(k) where k is indexDiff
	 *
	 * @param nums input arr
	 * @param indexDiff k
	 * @param valueDiff t
	 * @return true if found indexes i and j so that i != j && abs(i - j) <= indexDiff && abs(nums[i] - nums[j]) <= valueDiff; false if not
	 */
	public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
		TreeSet<Integer> window = new TreeSet<>();
		int left = 0, right = 0;
		while (right < nums.length) {
			// to prevent i == j，check if there's any index satisfy the requirement
			// find the num >= nums[right]
			Integer ceiling = window.ceiling(nums[right]);
			if (ceiling != null && (long) ceiling - nums[right] <= valueDiff) {
				return true;
			}
			// find the num <= nums[right]
			Integer floor = window.floor(nums[right]);
			if (floor != null && (long) nums[right] - floor <= valueDiff) {
				return true;
			}
			
			// expand the window
			window.add(nums[right]);
			right++;
			
			// shrink the window
			if (right - left > indexDiff) {
				window.remove(nums[left]);
				left++;
			}
		}
		return false;
	}
}
