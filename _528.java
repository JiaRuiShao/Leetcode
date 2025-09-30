import java.util.Random;
import java.util.TreeMap;

/**
 * 528. Random Pick with Weight
 */
public class _528 {
    class Solution1_PrefixSum_BinarySearch {

        private int n;
        private int[] pre;
        private Random random;

        public Solution1_PrefixSum_BinarySearch(int[] w) {
            n = w.length;
            pre = new int[n];
            pre[0] = w[0];
            for (int i = 1; i < n; i++) {
                pre[i] = pre[i - 1] + w[i];
            }

            random = new Random();
        }

        public int pickIndex() {
            // int pick = random.nextInt(pre[n - 1] + 1); // [0, sum]
            // the above code didn't work due to first index chosen range was increased by 1, e.g., 1 is index 0 number,
            // then its range should be (0, 1] not [0, 1]
            int pick = random.nextInt(pre[n - 1]) + 1; // [1, sum]
            return findIndexInPreSum(pick);
        }

        private int findIndexInPreSum(int target) {
            int lo = 0, hi = n - 1, mid = 0;
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                if (pre[mid] >= target) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            return lo;
        }
    }
    
    // Time: O(nlogn + logn)
    class Solution2_PrefixSum_TreeMap {
        private final Random random;
        private final TreeMap<Integer, Integer> preSumToIdx; // [start, start + weight)
        private int weightSum;
        // w:      [1, 2, 1]
        // pre: [0, 1, 3, 4]
        // 0 -> (0, 1]
        // 1 -> (1, 3]
        // 2 -> (3, 4]
        // pick randomInt in [1, 4]
        public Solution2_PrefixSum_TreeMap(int[] w) {
            random = new Random();
            preSumToIdx = new TreeMap<>();
            weightSum = 0;
            for (int i = 0; i < w.length; i++) {
                weightSum += w[i];
                preSumToIdx.put(weightSum, i);
            }
        }
        
        public int pickIndex() {
            int pick = random.nextInt(weightSum) + 1; // [1, weightSum]
            return preSumToIdx.get(preSumToIdx.ceilingKey(pick));
        }
    }
}
