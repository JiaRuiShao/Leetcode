/**
 * 704. Binary Search
 */
public class _704 {
    static class Solution_BinarySearch {
        public int search(int[] nums, int target) {
            return binarySearch(nums, target);
        }

        private int binarySearch(int[] nums, int target) {
            int left = 0, right = nums.length - 1, mid = 0;
            int cmp = 0;
            while (left <= right) {
                mid = (left + right) >>> 1;
                cmp = Integer.compare(nums[mid], target);
                if (cmp == 0) {
                    return mid;
                } else if (cmp < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return -1;
        }
    }
}
