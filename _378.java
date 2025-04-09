import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 378. Kth Smallest Element in a Sorted Matrix
 */
public class _378 {
    class Solution1_Binary_Search {
        /**
         * Left boundary binary search + matrix traversal technique.
         * We convert this problem to a binary search problem:
         * Variable target num with search range as [matrix[0][0], matrix[m-1][n-1]]
         * Function is num of elems in the matrix that <= target: f(x) = # of elem <= x in given matrix, monotonic increase
         * Goal is to find the lower bound of variable num under the given function value k
         *
         * Time: O((m+n)log(2e9)) = O(nlog(2e9)) < O(30n) = O(n)
         * Space: O(1)
         */
        public int kthSmallest(int[][] matrix, int k) {
            int n = matrix.length; // in this question m == n
            int left = matrix[0][0], right = matrix[n - 1][n - 1], mid = 0;
            // 
            // f(x) = # of elem <= x in given matrix, monotonic increase
            while (left <= right) {
                mid = left + (right - left) / 2;
                int lessEqualCount = findLTECount(matrix, mid);
                if (lessEqualCount >= k) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }

        /**
         * Find number of elements that are LTE to the given number x.
         * We go diagonal from top-right to bottom-left or vice versa.
         *
         * Time: O(m + n) = O(2n) = O(n)
         * Space: O(1)
         */
        private int findLTECount(int[][] mtx, int x) {
            int n = mtx.length, r = n - 1, c = 0, count = 0;
            while (r >= 0 && c < n) {
                while (r >= 0 && mtx[r][c] > x) r--;
                count += r + 1;
                c++;
            }
            return count;
        }

        private int getLTECount(int[][] matrix, int target) {
            int m = matrix.length, n = matrix[0].length;
            int count = 0, r = 0, c = n - 1;
            while (r < m && c >= 0) {
                while (target < matrix[r][c]) c--;
                count += c + 1;
                r++;
            }
            return count;
        }
    }

    class Solution2_MinHeap {
        /**
         * - Time: O(nlogn + klogn)
         * - Space: O(n)
         */
        public int kthSmallest(int[][] matrix, int k) {
            int m = matrix.length, n = matrix[0].length;
            // val, rowIdx, colIdx
            PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
            for (int row = 0; row < m; row++) {
                minHeap.offer(new int[] { matrix[row][0], row, 0 });
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
