import java.util.Arrays;

/**
 * 280. Wiggle Sort
 */
public class _280 {
    // Time: O(nlogn)
    // Space: O(logn)
    class Solution0_Sort_Swap {
        public void wiggleSort(int[] nums) {
            // Step 1: Sort array
            Arrays.sort(nums);
            
            // Step 2: Swap every pair (1-2, 3-4, 5-6, ...)
            for (int i = 1; i < nums.length - 1; i += 2) {
                swap(nums, i, i + 1);
            }
        }
        
        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    // Greedy one pass to ensure each pos satisfied its constraints & not break previous one
    // Even index: should be <= next (valley)
    // Odd index: should be >= next (peak)
    class Solution1_Greedy {
        public void wiggleSort(int[] nums) {
            for (int i = 0; i < nums.length - 1; i++) {
                if (i % 2 == 0 && nums[i] > nums[i+1]) {
                    swap(nums, i, i + 1);
                } else if (i % 2 == 1 && nums[i] < nums[i+1]) {
                    swap(nums, i, i + 1);
                }
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
