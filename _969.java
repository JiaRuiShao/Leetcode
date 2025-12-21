import java.util.ArrayList;
import java.util.List;

/**
 * 969. Pancake Sorting
 * 
 * Clarification:
 * - Are all elements unique? Yes
 */
public class _969 {
    // Time: O(n^2)
    // Space: O(1)
    // Greedy: Sort from largest to smallest; for each pos from end to start
    // 1. find the largest unsorted element
    // 2. move it to the front (flip)
    // 3. move it to its final position (flip)
    class Solution1_Greedy {
        public List<Integer> pancakeSort(int[] arr) {
            List<Integer> flipped = new ArrayList<>();
            int n = arr.length;
            
            // For each position from end to beginning
            for (int targetSize = n; targetSize > 1; targetSize--) {
                // Find index of maximum element in arr[0...targetSize-1]
                int maxIdx = findMaxIndex(arr, targetSize);
                
                // If max is already at targetSize-1, skip
                if (maxIdx == targetSize - 1) {
                    continue;
                }
                
                // Step 1: Flip max to position 0 (unless already there)
                if (maxIdx != 0) {
                    flip(arr, maxIdx);
                    flipped.add(maxIdx + 1);  // k is 1-indexed in result
                }
                
                // Step 2: Flip max from position 0 to targetSize-1
                flip(arr, targetSize - 1);
                flipped.add(targetSize);  // k is 1-indexed in result
            }
            
            return flipped;
        }

        private int findMaxIndex(int[] arr, int size) {
            int maxIdx = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIdx]) {
                    maxIdx = i;
                }
            }
            return maxIdx;
        }

        private void flip(int[] arr, int k) {
            int left = 0, right = k;
            while (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
    }
}
