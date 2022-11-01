/**
 * 1004. Max Consecutive Ones III.
 */
public class _1004 {
	public int longestOnes(int[] nums, int k) {
		int maxLen = 0, n = nums.length, zeros = 0;
		int left = 0, right = 0; // [left, right)
		while (right < n) {
			// move the right pointer forward when constraint hasn't been met
			int num = nums[right++];
			if (num == 0) {
				zeros++;
			}
			// contract the left window when couldn't satisfy the requirement
			while (zeros > k) {
				num = nums[left++];
				if (num == 0) {
					zeros--;
				}
			}
			// no need to check if zeros == k here buz
			// num of zeros could be < k for the entire array, that is, zeros will always < k
			// if num of zeros > k in the window, it will be zeros == k when get out of the previosu
			// while loop
			maxLen = Math.max(maxLen, right - left);
		}
		return maxLen;
	}
}
