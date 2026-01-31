/**
 * 153. Find Minimum in Rotated Sorted Array
 */
public class _153 {
    class Solution1_BinarySearch {
        public int findMin(int[] nums) {
            // asc [0, k-1]
            // asc [k, n-1]
            int n = nums.length;
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                boolean lessThanLeft = (mid == 0 || nums[mid] < nums[mid - 1]);
                boolean lessThanRight = (mid == n - 1 || nums[mid] < nums[mid + 1]);
                if (lessThanLeft && lessThanRight) {
                    return nums[mid];
                } else if (nums[0] <= nums[mid]) { // [0, mid] sorted, search in right half
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            lo = lo == n ? 0 : lo;
            return nums[lo];
        }
    }
}