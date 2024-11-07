/**
 * 35. Search Insert Position.
 */
public class _35 {
    /**
     * Left Boundary Binary Search will end up with left/lo pointer is the index of the:
     * - leftmost target elem if duplicates are allowed & target exists in given array
     * - smallest elem > target if target not found
     * - it could point to arr.length
     */
    class Solution_LeftBoundaryBinary_Search {
        public int searchInsert(int[] nums, int target) {
            int n = nums.length;
            int lo = 0, hi = n - 1, mid = 0;
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                if (nums[mid] >= target) hi = mid - 1;
                else lo = mid + 1;
            }
            return lo;
        }
    }
}
