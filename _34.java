/**
 * 34. Find First and Last Position of Element in Sorted Array
 */
public class _34 {
    /**
     * Binary Search To Find Left & Right Boundaries
     * Time Complexity: O(logn)
     * Space Complexity: O(1)
     */
    static class Solution1_binarySearch {
        public int[] searchRange(int[] nums, int target) {
            int[] range = new int[2];
            range[0] = lowerBound(nums, target);
            if (range[0] == -1) {
                range[1] = -1;
            } else {
                range[1] = upperBound(nums, target);
            }
            return range;
        }

        private int lowerBound(int[] nums, int target) {
            int left = 0, right = nums.length - 1, mid = 0;
            if (right < 0) { // length == 0
                return -1;
            }
            while (left <= right) {
                mid = (left + right) >>> 1;
                int cmp = Integer.compare(nums[mid], target);
                if (cmp < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            if (left == nums.length)
                return -1;
            return nums[left] == target ? left : -1;
        }

        private int upperBound(int[] nums, int target) {
            int left = 0, right = nums.length - 1, mid = 0;
            while (left <= right) {
                mid = left + (right - left) / 2;
                int cmp = Integer.compare(nums[mid], target);
                if (cmp > 0) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            if (right < 0)
                return -1;
            return nums[right] == target ? right : -1;
        }
    }
}
