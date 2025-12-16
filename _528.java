import java.util.Random;
import java.util.TreeMap;

/**
 * 528. Random Pick with Weight
 * Clarification:
 * - weights positive
 * 
 * Followup:
 * - dynamic update weight ==> use Segment Tree (do NOT mention unless asked)
 */
public class _528 {
    // Time: O(n + logn)
    // This is the optimal solution
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
    class Solution2_PrefixSum_TreeMap_CeilingKey {
        private final Random random;
        private final TreeMap<Integer, Integer> preSumToIdx; // [start, start + weight)
        private int weightSum;
        // w:      [1, 2, 1]
        // pre: [0, 1, 3, 4]
        // 0 -> (0, 1]
        // 1 -> (1, 3]
        // 2 -> (3, 4]
        // pick randomInt in [1, 4]
        public Solution2_PrefixSum_TreeMap_CeilingKey(int[] w) {
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

    class Solution2_PrefixSum_TreeMap_FloorKey {
        // 0  1  2  3  4
        //--- ---------
        // pickIdx falls in [0, 1) -> maps to original index 0
        // pickIdx falls in [1, 4) -> maps to original index 1
        // TreeMap O(logn)
        private Random random;
        private TreeMap<Integer, Integer> map;
        private int weightSum;

        public Solution2_PrefixSum_TreeMap_FloorKey(int[] w) {
            random = new Random();
            map = new TreeMap<>();
            weightSum = 0;

            for (int idx = 0; idx < w.length; idx++) {
                map.put(weightSum, idx);
                weightSum += w[idx];
            }
        }
        
        public int pickIndex() {
            int picked = random.nextInt(weightSum); // [0, 3]
            int weightIdx = map.floorKey(picked); // maxKey <= picked
            return map.get(weightIdx);
        }
    }

    // A segment tree where each node stores sum of weights in its range
    // To pick an index:
    // - Generate a random number r in range [1, totalWeight]
    // - Walk the tree:
    //   - go left if r <= left.sum
    //   - else go right
    // Time: O(n + logn)
    class Followup_SegmentTree {

        static class SegmentNode {
            int start, end;
            long sum;
            SegmentNode left, right;

            SegmentNode(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }

        private SegmentNode root;
        private Random rand = new Random();

        public Followup_SegmentTree(int[] w) {
            root = build(w, 0, w.length - 1);
        }

        private SegmentNode build(int[] w, int l, int r) {
            SegmentNode node = new SegmentNode(l, r);
            if (l == r) {
                node.sum = w[l];
                return node;
            }
            int mid = l + (r - l) / 2;
            node.left = build(w, l, mid);
            node.right = build(w, mid + 1, r);
            node.sum = node.left.sum + node.right.sum;
            return node;
        }

        public int pickIndex() {
            long r = rand.nextLong(root.sum) + 1; // [1, totalSum]
            return query(root, r);
        }

        private int query(SegmentNode node, long r) {
            if (node.start == node.end) {
                return node.start;
            }
            if (node.left.sum >= r) {
                return query(node.left, r);
            } else {
                return query(node.right, r - node.left.sum);
            }
        }
    }
}
