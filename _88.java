import java.util.Arrays;

public class _88 {
    /**
     * The elements from arr1 will be overwritten by arr2 elements.
     * Here we move the arr1 elems to the latter half of the array to make room for arr2 elems.
     * Time: O(m + n)
     * Space: O(2m + n) = O(m + n)
     */
    class Solution1_Two_Pointers {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            System.arraycopy(nums1, 0, nums1, n, m);
            int i1 = n, i2 = 0, i3 = 0;
            while (i1 < m + n && i2 < n) {
                int min = Math.min(nums1[i1], nums2[i2]);
                if (min == nums1[i1]) {
                    i1++;
                } else {
                    i2++;
                }
                nums1[i3++] = min;
            }
            if (i1 < m + n) {
                System.arraycopy(nums1, i1, nums1, i3, m + n - i1); // [i1, m+n)
            }
            if (i2 < n) {
                System.arraycopy(nums2, i2, nums1, i3, n - i2); // [i2, n)
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1,2,3,0,0,0};
        int[] nums2 = new int[] {2,5,6};
        int m = 3, n = 3;

        new _88().new Solution1_Two_Pointers().merge(nums, m, nums2, n);
        System.out.println(Arrays.toString(nums));
    }
}
