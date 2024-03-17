import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 378. Kth Smallest Element in a Sorted Matrix.
 */
public class _378 {
    class Solution1_MinHeap {
        /**
         * - Time: O(mlogm + klogm)
         * - Space: O(m)
         * @param matrix matrix
         * @param k k
         * @return kth smallest element
         */
        public int kthSmallest(int[][] matrix, int k) {
            int m = matrix.length, n = matrix[0].length;
            // val, rowIdx, colIdx
            PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
            for (int row = 0; row < m; row++) {
                minHeap.offer(new int[]{matrix[row][0], row, 0});
            }

            int kthSmallestNum = 0;
            while (!minHeap.isEmpty() && k-- > 0) {
                int[] min = minHeap.poll();
                kthSmallestNum = min[0];
                if (min[2] + 1 < n) {
                    min[0] = matrix[min[1]][++min[2]];
                    minHeap.offer(min);
                }
            }
            return kthSmallestNum;
        }
    }

    class Solution2_Binary_Search {
        /**
         * Left boundary binary search + matrix traversal technique.
         * We convert this problem to a binary search problem:
         * variable target num with search range as [matrix[0][0], matrix[m-1][n-1]]
         * function is num of elems in the matrix that <= target
         * goal is to find the lower bound of variable num under the given function value k
         * 
         * Time: O(nlog(2e9))
         * Space: O(1)
         * @param matrix
         * @param k
         * @return
         */
        public int kthSmallest(int[][] matrix, int k) {
            int row = matrix.length, col = matrix[0].length;
            int lo = matrix[0][0], hi = matrix[row - 1][col - 1];
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int count = findCount(matrix, mid);
                if (count >= k) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            return lo;
        }    

        /**
         * Find number of elements that are smaller than or equal to the given number mid.
         * We go left and down from top-right to bottom-left.
         * This function has variable num (target) as x, & f(x) calculates the number of elements in this matrix that are smaller than or equal to the given x. This is a monotonic increasing function on variable num.
         * 
         * Time: O(m + n)
         * Space: O(1)
         * 
         * @param matrix matrix
         * @param num target
         * @return # elem <= target in matrix
         */
        private int findCount(int[][] matrix, int num) {
            int m = matrix.length, n = matrix[0].length, count = 0;
            for (int r = 0, c = n - 1; r < m && c >= 0; r++) {
                while (c >= 0 && matrix[r][c] > num) c--;
                if (c >= 0) {
                    count += (c - 0 + 1);
                }
            }
            return count;
        }
    }

    class Solution3_BruteForce_TLE_MLE {
        public int kthSmallest(int[][] matrix, int k) {
            List<Integer> list = new ArrayList<>();
            int m = matrix.length, n = matrix[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    list.add(matrix[i][j]);
                }
            }
            Collections.sort(list);
            return list.get(k - 1);
        }
    }
}
