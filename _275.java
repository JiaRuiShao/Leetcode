/**
 * 275. H-Index II
 */
public class _275 {
    class Solution1_Binary_Search_Intuitive {
        // find max(x) such that at least x number of elems >= x
        public int hIndex(int[] citations) { // upper bound BS
            int n = citations.length;
            int lo = citations[0], hi = Math.min(citations[n - 1], n), mid = 0;
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                if (isValid(citations, mid)) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            return hi;
        }
    
        private boolean isValid(int[] nums, int x) { // lower bound BS
            int lo = 0, hi = nums.length - 1, mid = 0;
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                if (nums[mid] >= x) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            return nums.length - lo >= x;
        }
    }

    class Solution2_Binary_Search_Not_Intuitive {
        public int hIndex(int[] citations) { // lower bound BS
            int n = citations.length;
            int left = 0, right = n - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (citations[mid] >= (n - mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return n - left;
        }
    }
}
