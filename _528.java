import java.util.Random;

/**
 * 528. Random Pick with Weight.
 */
public class _528 {
    class Solution {

        private int n;
        private int[] pre;
        private Random random;

        public Solution(int[] w) {
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

}
