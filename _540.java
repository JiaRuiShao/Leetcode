/**
 * 540. Single Element in a Sorted Array
 */
public class _540 {
    class Solution1_BinarySearch {
        public int singleNonDuplicate(int[] nums) {
            int n = nums.length;
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (mid % 2 == 1) {
                    mid--;
                }
                boolean leftDiff = (mid == 0 || nums[mid - 1] != nums[mid]);
                boolean rightDiff = (mid == n - 1 || nums[mid] != nums[mid + 1]);
                if (leftDiff && rightDiff) {
                    return nums[mid];
                }
                if (!rightDiff) {
                    lo = mid + 2;
                } else {
                    hi = mid - 1;
                }
            }
            return -1;
        }
    }
}
