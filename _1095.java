/**
 * 1095. Find in Mountain Array.
 */
public class _1095 {
    // This is MountainArray's API interface.
    class MountainArray {
        private int[] array;
        private int length;

        public MountainArray(int[] arr) {
            this.array = arr;
            this.length = arr.length;
        }

        public int get(int index) {
            return array[index];
        }
        public int length() {
            return array.length;
        }
    }

    class Solution_Binary_Search {
        /**
         * Find the peak elem whose value is > leftElem & > rightElem using binary search.
         * Then use binary search to search for left side elements which we can treat as a ascending arr with search range [0, peak];
         * if target found, directly return; if not found, search for right side elements which we can treat as a descending arr with search range [peak + 1, arrayLength - 1]
         *
         * Time: O(logn) where n is the number of elements in the given mountain array
         * Space: O(1)
         *
         * @param target target value to search for
         * @param mountainArr given array to search that contains a peak element
         * @return leftmost target index if found; else return -1
         */
        public int findInMountainArray(int target, MountainArray mountainArr) {
            int size = mountainArr.length();
            int peak = findPeakElem(mountainArr, size);
            int left = findTarget(mountainArr, size, target, 0, peak);
            return left != -1 ? left : findTarget(mountainArr, size, target, peak + 1, size - 1);
        }

        private int findPeakElem(MountainArray arr, int n) {
            int lo = 0, hi = n - 1, mid = 0;
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                int midVal = arr.get(mid);
                int leftVal = mid == 0 ? -1 : arr.get(mid - 1);
                if (midVal > leftVal) {
                    int rightVal = mid == n - 1 ? -1 : arr.get(mid + 1);
                    if (midVal > rightVal) {
                        return mid;
                    } else {
                        lo = mid + 1;
                    }
                } else {
                    hi = mid - 1;
                }

            }
            return -1;
        }

        private int findTarget(MountainArray arr, int n, int target, int lo, int hi) {
            boolean increasingArr = lo == 0; // determine whether the search array is in ascending or descending order
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2, midVal = arr.get(mid);
                if (midVal == target) {
                    return mid;
                } else if ((increasingArr && midVal < target) || (!increasingArr && midVal > target)) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            return -1;
        }
    }
}
