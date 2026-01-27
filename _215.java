import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * 215. Kth Largest Element in an Array
 */
public class _215 {
    // Time: O(nlogn)
    // Space: O(logn)
    class Solution1_Sort {
        public int findKthLargest(int[] nums, int k) {
            Arrays.sort(nums);
            return nums[nums.length - k];
        }
    }

    // minHeap with size k
    // Time: O(nlogk)
    // Space: O(k)
    class Solution2_MinHeap {
        public int findKthLargest(int[] nums, int k) {
            Queue<Integer> minHeap = new PriorityQueue<>();
            for (int num : nums) {
                minHeap.offer(num);
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }
            return minHeap.peek();
        }
    }

    // Time: O(n) avg, O(n^2) worst when input contains all duplicates elem
    // Space: O(logn)
    class Solution3_Quickselect {
        public int findKthLargest(int[] nums, int k) {
            return quickselect(nums, 0, nums.length - 1, k - 1);
        }

        private int quickselect(int[] nums, int lo, int hi, int k) {
            if (lo == hi) {
                return nums[lo];
            }

            int pivotIdx = partition(nums, lo, hi);
            if (pivotIdx == k) {
                return nums[pivotIdx];
            } else if (pivotIdx < k) {
                return quickselect(nums, pivotIdx + 1, hi, k);
            } else {
                return quickselect(nums, lo, pivotIdx - 1, k);
            }
        }

        private int partition(int[] nums, int lo, int hi) {
            int mid = lo + (hi - lo) / 2;
            int pivot = nums[mid];
            swap(nums, mid, hi);
            int left = lo, right = hi - 1;
            // [lo, left): elem >= pivot
            // (right, hi]: elem < pivot
            while (left <= right) { // **IMPORTANT**
                if (nums[left] >= pivot) {
                    left++;
                } else {
                    swap(nums, left, right);
                    right--;
                }
            }
            swap(nums, left, hi);
            return left;
        }

        // use three-way partition if input contains too many duplicates
        private int threeWayPartition(int[] nums, int lo, int hi) {
            int pivotIdx = lo + new Random().nextInt(hi - lo + 1);
            int pivot = nums[pivotIdx];
            swap(nums, pivotIdx, hi);
            int left = lo, right = hi - 1, pos = lo;
            // [lo, left): elem > pivot
            // (right, hi]: elem < pivot
            while (pos <= right) {
                if (nums[pos] > pivot) {
                    swap(nums, left, pos);
                    left++;
                    pos++;
                } else if (nums[pos] < pivot) {
                    swap(nums, pos, right);
                    right--;
                } else {
                    pos++;
                }
            }
            swap(nums, left, hi);
            return left;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    // count value frequencies if they are limited
    // Time: O(n+range)
    // Space: O(range)
    class Solution4_Count {
        public int findKthLargest(int[] nums, int k) {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int num : nums) {
                min = Math.min(min, num);
                max = Math.max(max, num);
            }
            int[] freq = new int[max - min + 1];
            for (int num : nums) {
                freq[num - min]++;
            }

            for (int i = freq.length - 1; i >= 0; i--) {
                k -= freq[i];
                if (k <= 0) {
                    return min + i;
                }
            }
            return -1; // never reach
        }
    }
}
