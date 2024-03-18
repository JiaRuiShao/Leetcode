import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 373. Find K Pairs with Smallest Sums.
 */
public class _373 {

    class Solution1_minHeap {
        /**
         * Time: O(mlogm + klogm) where m is min of nums1 length n1 & num2 length n2
         * Space: O(m)
         * 
         * @param nums1
         * @param nums2
         * @param k
         * @return
         */
        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> kPairs = new ArrayList<>(k);
            int n1 = nums1.length, n2 = nums2.length;
            int min = Math.min(n1, n2);
            int[] nums = min == n1 ? nums1 : nums2;
            int[] other = min == n1 ? nums2 : nums1;
            // int[] arr: [val1, val2, idx2]
            PriorityQueue<int[]> minHeap = new PriorityQueue<>(min, (a, b) -> a[0] - b[0] + a[1] - b[1]);
            for (int i = 0; i < min; i++) {
                minHeap.offer(new int[] {nums[i], other[0], 0});
            }
            while (k-- > 0) {
                int[] pair = minHeap.poll();
                if (nums == nums2) kPairs.add(Arrays.asList(pair[1], pair[0])); // swap pair[0] and pair[1] cuz nums1 is always saved at 0 index & pair[1] is always saved at 1 index
                else kPairs.add(Arrays.asList(pair[0], pair[1]));
                if (pair[2] + 1 < other.length) {
                    pair[1] = other[++pair[2]];
                    minHeap.offer(pair);
                }
            }
            return kPairs;
        }
    }

    class Wrong_Solution_Brute_Force {
        /**
         * We cannot use brute force like this because given array is not strictly increasing, means that arr1[2] could be less than arr2[0].
         * @param nums1
         * @param nums2
         * @param k
         * @return
         */
        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> kPairs = new ArrayList<>(k);
            int i1 = 0, i2 = 0, n1 = nums1.length, n2 = nums2.length;
            while (i1 < n1 && i2 < n2) {
                int num1 = nums1[i1], num2 = nums2[i2];
                int min = Math.min(num1, num2);
                int idx = i1, end = n1;
                int[] nums = nums1;
                if (min == num1) {
                    idx = i2;
                    end = n2;
                    nums = nums2;
                }
                for (; idx < end; idx++) {
                    kPairs.add(List.of(min, nums[idx]));
                    if (--k == 0) return kPairs;
                }
                i1++;
                i2++;
            }
            return kPairs;
        }
    }
}