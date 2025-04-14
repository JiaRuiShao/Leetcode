/**
 * 191. Number of 1 Bits
 */
public class _191 {
    class Solution1 {
        // n & (n - 1) flip the least significant 1 to 0
        public int hammingWeight(int n) {
            int bits = 0;
            while (n != 0) {
                bits++;
                n = n & (n - 1);
            }
            return bits;
        }
    }

    public static class Solution2 {
        public int hammingWeight(int n) {
            int bits = 0;
            int mask = 1;
            for (int i = 0; i < 32; i++) {
                if ((n & mask) != 0) {
                    bits++;
                }
                mask = mask << 1;
            }
            return bits;
        }
    }

    public static class Solution4 {
        public int hammingWeight(int n) {
            int bits = 0;
            for (int i = 0; i < 32; i++) {
                bits += n & 1;
                n  = n >>> 1;
            }
            return bits;
        }
    }
}
