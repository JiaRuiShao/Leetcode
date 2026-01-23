/**
 * 4. Median of Two Sorted Arrays
 */
public class _4 {
    // Time: O(m+n)
    // Space: O(1)
    class Solution1_TwoPointers {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int m = nums1.length, n = nums2.length;
            int total = m + n;
            int mid1 = (total - 1) / 2;
            int mid2 = total / 2;
            
            int i = 0, j = 0, pos = 0;
            int curr = 0, prev = 0;
            
            while (pos <= mid2) {
                prev = curr;
                if (i < m && (j >= n || nums1[i] < nums2[j])) {
                    curr = nums1[i++];
                } else {
                    curr = nums2[j++];
                }
                pos++;
            }
            
            if (mid1 == mid2) {
                return curr;
            } else { // mid2 == mid1 + 1
                return (prev + curr) / 2.0;
            }
        }
    }

    // Time: O(log(min(m, n)))
    // Space: O(1)
    class Solution2_Partition {
        // partition both sorted array to find size(leftHalf of arr1 and arr2) == size(rightHalf of arr1 and arr2) (+ 1 if even)
        // nums1: [... 10 | 5 ...]
        //             ↑    ↑
        //           left1  right1
                
        // nums2: [... 3 | 8 ...]
        //             ↑   ↑
        //           left2 right2
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int m = nums1.length, n = nums2.length;
            if (m > n) return findMedianSortedArrays(nums2, nums1); // ensures size2 is not negative
            int total = m + n, leftSize = (total + 1) / 2; // size of left half
            int lo = 0, hi = m;
            while (lo <= hi) {
                int size1 = lo + (hi - lo) / 2; // size of nums1
                int size2 = leftSize - size1; // size of nums2
                int left1 = size1 == 0 ? Integer.MIN_VALUE : nums1[size1 - 1];
                int left2 = size2 == 0 ? Integer.MIN_VALUE : nums2[size2 - 1];
                int right1 = size1 == m ? Integer.MAX_VALUE : nums1[size1];
                int right2 = size2 == n ? Integer.MAX_VALUE : nums2[size2];
                if (left1 <= right2 && left2 <= right1) {
                    if (leftSize > total - leftSize) return 1.0 * Math.max(left1, left2);
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                } else if (left1 > right2) {
                    hi = size1 - 1; // need less elem from nums1
                } else { // left2 > right1
                    lo = size1 + 1; // need more elem from nums1
                }
            }
            return -1; // never reach
        }
    }
}
