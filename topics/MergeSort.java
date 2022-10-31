package topics;

public class MergeSort {
    /**
     * merge sort method.
     *
     * @param arr input array to sort
     */
    public static void mergeSort(int[] arr) {
        // create a new helper array to save auxiliary space
        int[] helper = new int[arr.length];
        mergeSort(arr, helper, 0, arr.length - 1);
    }

    /**
     * Recursive helper method to merge sort arr (input) array using an auxiliary array.
     * Time Complexity: O(nlogn)
     * Space Complexity: O(n)
     *
     * @param arr    input array to sort
     * @param helper auxiliary array to use
     * @param left   left boundary
     * @param right  right boundary
     */
    private static void mergeSort(int[] arr, int[] helper, int left, int right) {
        // base case
        if (left >= right) {
            return;
        }

        // recursive case
        int mid = left + (right - left) / 2; // find midpoint, same as  (hi + lo) / 2, prevent integer overflow
        mergeSort(arr, helper, left, mid); // sort left half recursively arr[left, mid]
        mergeSort(arr, helper, mid + 1, right); // sort right half recursively arr[mid + 1, right]
        merge(arr, helper, left, mid + 1, right); // merge the left & right halves
    }

    /**
     * Instead of creating multiple arrays, this merge works with only two arrays.
     *
     * @param arr        input array
     * @param helper     auxiliary array to use during merge process
     * @param leftPos    starting point of left half
     * @param rightPos   starting point of right half
     * @param rightBound upper bound of right half
     */
    public static void merge(int[] arr, int[] helper, int leftPos, int rightPos, int rightBound) {
        // upper bound of left half
        int leftBound = leftPos + (rightBound - leftPos) / 2;
        // index of arr array, starting point of left half
        int arrIndex = leftPos;
        // total number of items to examine
        // int numOfItems = rightBound - leftPos + 1;

        // both left half and right half have items
        while (leftPos <= leftBound && rightPos <= rightBound) {
            if (arr[leftPos] <= arr[rightPos]) {
                arr[arrIndex++] = helper[leftPos++];
            } else {
                arr[arrIndex++] = helper[rightPos++];
            }
        }
        // copy rest of left half
        while (leftPos <= leftBound) {
            arr[arrIndex++] = helper[leftPos++];
        }
        // copy rest of right half
        while (rightPos <= rightBound) {
            arr[arrIndex++] = helper[rightPos++];
        }
    }

    class MergeSortTmproved {

        int[] helper; // create a new helper array to save auxiliary space, used to merge the left & right halves in merge method

        public int[] sortArray(int[] nums) { // main function
            helper = new int[nums.length];
            mergeSort(nums, 0, nums.length - 1);
            return nums;
        }

        /**
         * Recursive merge sort the given array nums[lo, hi].
         * Time Complexity: O(nlogn) = level# * time(merge) = O(logn)*n
         * Space Complexity: O(n) = O(mergeSort callstack#) + O(helper array) = O(logn+n)
         *
         * @param nums given input
         * @param lo lower bound
         * @param hi upper bound
         */
        private void mergeSort(int[] nums, int lo, int hi) {
            // base case: lo == hi
            if (lo >= hi) {
                return;
            }
            // sort the left & right halves and merge them
            int mid = lo + (hi - lo) / 2;
            mergeSort(nums, lo, mid);
            mergeSort(nums, mid + 1, hi);
            merge(nums, lo, mid, hi);
        }

        //       [1, 2, 3, 4]
        //  [1, 2]            [3, 4]
        // [1] [2]             [3] [4]
        /**
         * Time: O(n)
         *
         * @param nums given input
         * @param lo lower bound
         * @param mid end index of the left sorted half
         * @param hi upper bound
         */
        private void merge(int[] nums, int lo, int mid, int hi) {
            System.arraycopy(nums, lo, helper, lo, hi - lo + 1);

            // merge two sorted arrays together
            int i = lo, j = mid + 1;
            for (int p = lo; p <= hi; p++) {
                if (i > mid) {
                    nums[p] = helper[j++];
                } else if (j > hi) {
                    nums[p] = helper[i++];
                } else if (helper[i] <= helper[j]) {
                    nums[p] = helper[i++];
                } else {
                    nums[p] = helper[j++];
                }
            }
        }
    }
}
