/**
 * 231. Power of Two
 */
public class _231 {
    class Solution1_Bit_Manipulation {
        // A number is a power of two if its set bit count is only 1
        public boolean isPowerOfTwo(int n) {
            return n > 0 && (n & (n - 1)) == 0;
        }
    }

    class Solution2_Recursion {
        public boolean isPowerOfTwo(int n) {
            if (n <= 0) return false;
            if (n == 1) return true;
            if (n % 2 != 0) return false;
            return isPowerOfTwo(n / 2);
        }
    }

    class Solution3_Iterative {
        public boolean isPowerOfTwo(int n) {
            if (n <= 0) return false;
            while (n % 2 == 0) n /= 2;
            return n == 1;
        }
    }
}