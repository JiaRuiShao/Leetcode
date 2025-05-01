import java.util.Arrays;

/**
 * 1099. Two Sum Less Than K
 */
public class _1099 {
    class Solution1_Two_Pointers {
        public int twoSumLessThanK(int[] nums, int k) {
            Arrays.sort(nums);
            int left = 0, right = nums.length - 1, sum = 0, maxSum = -1;
            while (left < right) {
                sum = nums[left] + nums[right];
                if (sum >= k) right--;
                else {
                    maxSum = Math.max(maxSum, sum);
                    left++;
                }
            }
            return maxSum;
        }
    }
}
