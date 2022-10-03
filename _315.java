import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 315. Count of Smaller Numbers After Self.
 * Given an integer array nums, return an integer array counts
 * where counts[i] is the number of smaller elements to the right of nums[i].
 */
public class _315 {
    class Solution1_MergeSort {
        private class Pair {
            int val, id;
            Pair(int val, int id) {
                // record this element's val
                this.val = val;
                // record this element's initial index in the given input array
                this.id = id;
            }
        }

        // helper array used for MergeSort
        private Pair[] temp;
        // record the number of following elements that are smaller than itself
        private int[] count;

        public List<Integer> countSmaller(int[] nums) {
            int n = nums.length;
            count = new int[n];
            temp = new Pair[n];
            Pair[] arr = new Pair[n];

            // build the pair array Pair(num, idx)
            for (int i = 0; i < n; i++) arr[i] = new Pair(nums[i], i);

            // mergesort the pair array, the result is stored in the count[]
            sort(arr, 0, n - 1);

            return Arrays.stream(count).boxed().collect(Collectors.toList());
        }

        private void sort(Pair[] arr, int lo, int hi) {
            if (lo == hi) return;
            int mid = lo + (hi - lo) / 2;
            sort(arr, lo, mid);
            sort(arr, mid + 1, hi);
            merge(arr, lo, mid, hi);
        }

        /**
         * For each element i, the function records the number of elements jumping from i's right to i's left during the merge sort.
         * Notice we didn't update the count for elements in the left half
         *  -- because the left half is sorted, so the elements on thr right side of the left half is definitely larger than teh previous ones.
         *
         * @param arr given input array
         * @param lo lower bound
         * @param mid middle
         * @param hi upper bound
         */
        private void merge(Pair[] arr, int lo, int mid, int hi) {
            System.arraycopy(arr, lo, temp, lo, hi - lo + 1);
            // two pointers to merge the left & right halves
            // left arr: [lo, mid]
            // right arr: [mid+1, hi]
            int i = lo, j = mid + 1;
            for (int p = lo; p <= hi; p++) { // p is the pointer for temp array
                if (i == mid + 1) {
                    arr[p] = temp[j++];
                } else if (j == hi + 1) {
                    arr[p] = temp[i++];
                    // update count for num of elements jump from right half to left half
                    count[arr[p].id] += j - (mid + 1);
                } else if (temp[i].val > temp[j].val) {
                    arr[p] = temp[j++];
                } else {
                    arr[p] = temp[i++];
                    // update count for num of elements jump from right half to left half
                    count[arr[p].id] += j - (mid + 1);
                }
            }
        }

    }
}
