/**
 * 2571. Minimum Operations to Reduce an Integer to 0
 */
public class _2571 {
    // Time: O(1)
    // Space: O(1)
    class Solution1_BitManipulation {
        public int minOperations(int n) {
            int opr = 0;
            while (n > 0) {
                if ((3 & n) == 3) { // add 1 to clear 11
                    n++;
                    opr++;
                } else if ((1 & n) == 1) { // remove single 1
                    n--;
                    opr++;
                } else { // last bit as 0
                    n >>= 1;
                }
            }
            return opr;
        }
    }

    // find nearest power of two for n
    // Time: O(logn)
    class Solution2_DP {
        public int minOperations(int n) {
            if (n < 2) {
                return n;
            }
            int low = Integer.highestOneBit(n);
            int high = (low << 1) > 0 ? low << 1 : low;
            return 1 + minOperations(Math.min(n - low, high - n));
        }

        public int minOperationsIterative(int n) {
            int ops = 0;
            while (n >= 2) {
                int low = Integer.highestOneBit(n);
                int high = (low << 1) > 0 ? low << 1 : low;
                n = Math.min(n - low, high - n);
                ops++;
            }

            return ops + n; // n is 0 or 1
        }
    }
}
