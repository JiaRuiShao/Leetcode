import java.util.Arrays;

/**
 * 1099. Two Sum Less Than K
 * 
 * 1 - BF: O(n^2), O(1)
 */
public class _1099 {
    // Time: O(nlogn)
    // Space: O(logn)
    class Solution1_Sort_TwoPointers {
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
    // Space: O(logn)
    // Same complexity as two pointers solution, but code logic is more complicated
    class Solution2_Sort_BinarySearch {
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
    
        // lower bound binary search, return hi as it points to the largest elem < k
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
