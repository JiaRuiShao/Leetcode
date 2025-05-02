import java.util.Arrays;

/**
 * 1099. Two Sum Less Than K
 */
public class _1099 {
    // Time: O(nlogn + n^2)
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

    // Time: O(nlogn)
    class Solution2_Binary_Search {
        // n1 + n2 < k ==> n2 < k - n1
        public int twoSumLessThanK(int[] nums, int k) {
            int n = nums.length, maxSum = -1;
            Arrays.sort(nums);
            for (int i = 0; i < n - 1; i++) {
                int j = binarySearch(nums, i + 1, n - 1, k - nums[i]);
                if (j > i) { // j in range: [i, n - 1]
                // if (j > i && j < n) {
                    maxSum = Math.max(maxSum, nums[i] + nums[j]);
                }
            }
            return maxSum;
        }
    
        private int binarySearch(int[] nums, int lo, int hi, int k) {
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (nums[mid] >= k) hi = mid - 1;
                else lo = mid + 1;
            }
            return hi;
        }
    }
}
