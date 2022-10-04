/**
 * 493. Reverse Pairs.
 * Given an integer array nums, return the number of reverse pairs in the array.
 * A reverse pair is a pair (i, j) where:
 *      0 <= i < j < nums.length and
*       nums[i] > 2 * nums[j]
 */
public class _493 {

    // record the number of following elements that are smaller than itself
    private int count = 0;
    // a helper array used in merge
    private int[] temp;

    public int reversePairs(int[] nums) {
        int n = nums.length;
        temp = new int[n];
        // mergesort the pair array, the result is stored in the count
        sort(nums, 0, n - 1);
        return count;
    }

    private void sort(int[] nums, int lo, int hi) {
        if (lo == hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(nums, lo, mid);
        sort(nums, mid + 1, hi);
        merge(nums, lo, mid, hi);
    }

    /**
     * For each element i, the function records the number of elements jumping from i's right to i's left during the merge sort.
     * Notice we didn't update the count for elements in the left half
     *  -- because the left half is sorted, so the elements on thr right side of the left half is definitely larger than teh previous ones.
     *
     * @param nums given input array
     * @param lo lower bound
     * @param mid middle
     * @param hi upper bound
     */
    private void merge(int[] nums, int lo, int mid, int hi) {
        System.arraycopy(nums, lo, temp, lo, hi - lo + 1);

        // update the count before merge
        int end = mid + 1;
        for (int i = lo; i <= mid; i++) {
            // cast to long to prevent integer overflow
            while (end <= hi && (long) nums[i] > (long) nums[end] * 2) {
                end++;
            }
            count += end - (mid + 1);
        }

        /*for (int i = lo; i <= mid; i++) {
            for (int j = mid + 1; j <= hi; j++) {
                if (arr[i].val > (long) 2*arr[j].val) count++;
            }
        }*/

        // two pointers to merge the left & right halves
        // left arr: [lo, mid]
        // right arr: [mid+1, hi]
        int i = lo, j = mid + 1;
        for (int p = lo; p <= hi; p++) { // p is the pointer for temp array
            if (i == mid + 1) {
                nums[p] = temp[j++];
            } else if (j == hi + 1) {
                nums[p] = temp[i++];
            } else if (temp[i] > temp[j]) {
                nums[p] = temp[j++];
            } else {
                nums[p] = temp[i++];
            }
        }
    }
}
