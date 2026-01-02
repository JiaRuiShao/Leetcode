import java.util.TreeSet;

/**
 * 363. Max Sum of Rectangle No Larger Than K
 */
public class _363 {
    class Solution {
        public int maxSumSubmatrix(int[][] matrix, int k) {
            int rows = matrix.length;
            int cols = matrix[0].length;
            int max = Integer.MIN_VALUE;
            
            for (int l = 0; l < cols; l++) {
                int[] rowSums = new int[rows];
                for (int r = l; r < cols; r++) {
                    for (int i = 0; i < rows; i++) {
                        rowSums[i] += matrix[i][r];
                    }

                    // Find the max subarray sum <= k in rowSums
                    max = Math.max(max, findMaxLessK(rowSums, k));
                    
                    // Early exit if we hit exactly k
                    if (max == k) return k;
                }
            }
            return max;
        }

        private int findMaxLessK(int[] nums, int k) {
            TreeSet<Integer> set = new TreeSet<>();
            set.add(0); // Base case for prefix sum
            int currSum = 0;
            int res = Integer.MIN_VALUE;

            for (int x : nums) {
                currSum += x;
                // We want currSum - prevSum <= k  =>  prevSum >= currSum - k
                Integer target = set.ceiling(currSum - k);
                if (target != null) {
                    res = Math.max(res, currSum - target);
                }
                set.add(currSum);
            }
            return res;
        }
    }
}
