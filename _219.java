import java.util.HashSet;
import java.util.Set;

/**
 * 219. Contains Duplicate II.
 */
public class _219 {
	/**
	 * Sliding Window.
	 * Time: O(n)
	 * Space: O(k)
	 *
	 * @param nums input nums
	 * @param k maxElems allowed in window
	 * @return true if found i, j so that i != j && nums[i] == nums[j] && abs(i - j) <= k; false if not
	 */
	public boolean containsNearbyDuplicate(int[] nums, int k) {
		// window: [left, right)
		int left = 0, right = 0;
		Set<Integer> windowElem = new HashSet<>();
		while (right < nums.length) {
			if (windowElem.contains(nums[right])) {
				return true;
			}
			windowElem.add(nums[right]);
			if (right - left == k) {
				windowElem.remove(nums[left++]);
			}
			right++;
		}
		return false;
	}
}
