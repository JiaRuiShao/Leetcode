import java.util.Arrays;

/**
 * 324. Wiggle Sort II
 */
public class _324 {
    class Wrong_Solution {
        // Sorting + Alternating small & large fails
        // duplicates at end thus break the rules
    }

    // Split the sorted array into two halves, and then traverse from either top/tail of them
    // Time: O(nlogn)
    // Space: O(n)
    class Solution1_Sort_ReverseInMiddle {
        public void wiggleSort(int[] nums) {
            int n = nums.length;
            
            // Step 1: Sort the array
            int[] sorted = Arrays.copyOf(nums, n);
            Arrays.sort(sorted);
            
            // Step 2: Split into two halves
            // Small half: 0 to mid-1
            // Large half: mid to n-1
            int mid = (n - 1) / 2;
            
            // Step 3: Interleave from END of each half (KEY INSIGHT!)
            int left = mid;      // End of small half
            int right = n - 1;    // End of large half
            
            for (int i = 0; i < n; i++) {
                if (i % 2 == 0) {
                    // Even positions: small elements (from end)
                    nums[i] = sorted[left--];
                } else {
                    // Odd positions: large elements (from end)
                    nums[i] = sorted[right--];
                }
            }
        }
    }

    // 1. Find median
    // 2. 3-way partition (Dutch National Flag)
    // 3. Use virtual index to place:
    //    - larger numbers → odd indices
    //    - smaller numbers → even indices
    class Solution2_QuickSort_ThreeWayPartition {
        public void wiggleSort(int[] nums) {
            int n = nums.length;
            
            // Step 1: Find median using QuickSelect - O(n) average
            int median = findMedian(nums);
            
            // Step 2: 3-way partition with VIRTUAL INDEXING
            // The virtual indexing trick means:
            // - When we place "large" elements at the "left" logical positions (0,1,2)
            // - They end up at virtual positions 1,3,5
            int left = 0;      // Next position for elements > median
            int i = 0;         // Current position
            int right = n - 1; // Next position for elements < median
            
            // Virtual index mapping:
            // Actual index i maps to virtual index (1 + 2*i) % (n|1)
            // n|1 ensures we get odd number for proper wrapping
            while (i <= right) {
                if (nums[virtualIndex(i, n)] > median) {
                    swap(nums, virtualIndex(left, n), virtualIndex(i, n));
                    left++;
                    i++;
                } else if (nums[virtualIndex(i, n)] < median) {
                    swap(nums, virtualIndex(right, n), virtualIndex(i, n));
                    right--;
                } else {
                    i++;
                }
            }
        }

            /**
         * Virtual index mapping for wiggle sort
         * Maps: 0,1,2,3,4,5 -> 1,3,5,0,2,4 (for n=6)
         * This interleaves odd and even positions!
         */
        private int virtualIndex(int index, int n) {
            return (1 + 2 * index) % (n | 1);
        }
        
        private int findMedian(int[] nums) {
            int n = nums.length;
            return quickSelect(nums, 0, n - 1, n / 2);
        }
        
        private int quickSelect(int[] nums, int left, int right, int k) {
            if (left == right) return nums[left];
            
            int pivotIndex = partition(nums, left, right);
            
            if (pivotIndex == k) {
                return nums[k];
            } else if (pivotIndex < k) {
                return quickSelect(nums, pivotIndex + 1, right, k);
            } else {
                return quickSelect(nums, left, pivotIndex - 1, k);
            }
        }
        
        private int partition(int[] arr, int left, int right) {
            int pivot = arr[right];
            int i = left;
            
            for (int j = left; j < right; j++) {
                if (arr[j] < pivot) {
                    swap(arr, i++, j);
                }
            }
            
            swap(arr, i, right);
            return i;
        }
        
        // or we could do 3-way partition around median to sort the array
        private void threeWayPartition(int[] arr, int median) {
            int i = 0, j = 0, k = arr.length - 1;
            
            while (j <= k) {
                if (arr[j] < median) {
                    swap(arr, i++, j++);
                } else if (arr[j] > median) {
                    swap(arr, j, k--);
                } else {
                    j++;
                }
            }
        }
        
        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
