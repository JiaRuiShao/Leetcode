import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 973. K Closest Points to Origin
 * Followup:
 * - Return in exact sorted order?
 */
public class _973 {
    class Solution0_BruteForce {
        public int[][] kClosest(int[][] points, int k) {
            Arrays.sort(points, (a, b) -> {
                long distA = (long) a[0] * a[0] + (long) a[1] * a[1];
                long distB = (long) b[0] * b[0] + (long) b[1] * b[1];
                return Long.compare(distA, distB);
            });
            
            return Arrays.copyOfRange(points, 0, k);
        }
    }

    // Time: O(nlogk)
    // Space: O(k)
    class Solution1_PriorityQueue {
        public int[][] kClosest(int[][] points, int k) {
            PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> 
                Integer.compare(b[0] * b[0] + b[1] * b[1], a[1] * a[1] + a[0] * a[0])
            );
            for (int[] point : points) {
                maxHeap.offer(point);
                if (maxHeap.size() > k) {
                    maxHeap.poll();
                }
            }

            int pos = k - 1;
            int[][] kClosest = new int[k][2];
            while (!maxHeap.isEmpty()) {
                kClosest[pos--] = maxHeap.poll();
            }
            return kClosest;
        }
    }

    // Order of top k elements doesn't matter
    // Time: O(n) avg, O(n^2) worst
    // Space: O(logn)
    class Solution2_QuickSelect {
        public int[][] kClosest(int[][] points, int k) {
            quickSelect(points, 0, points.length - 1, k);
            return Arrays.copyOfRange(points, 0, k);
        }
        
        private void quickSelect(int[][] points, int left, int right, int k) {
            if (left >= right) return;
            
            int pivotIndex = partition(points, left, right);
            
            if (pivotIndex == k) {
                return;  // Found! First k elements are the answer
            } else if (pivotIndex < k) {
                // Need more elements, recurse right
                quickSelect(points, pivotIndex + 1, right, k);
            } else {
                // Have too many, recurse left
                quickSelect(points, left, pivotIndex - 1, k);
            }
        }
        
        private int partition(int[][] points, int left, int right) {
            // Choose pivot (can be random for better average case)
            int pivotIndex = left + (right - left) / 2;
            long pivotDist = distance(points[pivotIndex]);
            
            // Move pivot to end
            swap(points, pivotIndex, right);
            
            int storeIndex = left;
            
            // Partition around pivot
            for (int i = left; i < right; i++) {
                if (distance(points[i]) < pivotDist) {
                    swap(points, i, storeIndex);
                    storeIndex++;
                }
            }
            
            // Move pivot to final position
            swap(points, storeIndex, right);
            return storeIndex;
        }
        
        private long distance(int[] point) {
            return (long) point[0] * point[0] + (long) point[1] * point[1];
        }
        
        private void swap(int[][] points, int i, int j) {
            int[] temp = points[i];
            points[i] = points[j];
            points[j] = temp;
        }
    }
}
