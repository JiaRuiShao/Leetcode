/**
 * 50. Pow(x, n)
 */
public class _50 {
    // Time: O(n)
    // Space: O(1)
    class Solution0_TLE {
        public double myPow(double x, int n) {
            double pow = 1.0;
            for (int i = 0; i < Math.abs(n); i++) {
                pow = pow * x;
            }
            if (n < 0) {
                pow = 1 / pow;
            }
            return pow;
        }
    }

    // Time: O(logn)
    // Space: O(logn)
    class Solution1_FastExponentiation {
        public double myPow(double x, int n) {
            double pow = fastPow(x, Math.abs((long)n)); // overflow when n = -2^31
            if (n < 0) {
                pow = 1 / pow;
            }
            return pow;
        }

        private double fastPow(double x, long n) {
            if (n == 0) return 1.0;
            double half = fastPow(x, n / 2);
            double pow = half * half;
            if (n % 2 == 1) { // x^n = x*(x^(n/2))^2
                pow *= x;
            }
            return pow;
        }
    }

    // Time: O(logn)
    // Space: O(1)
    class Solution1_FastExponentiation_Iterative {
        public double myPow(double x, int n) {
            long posN = Math.abs((long) n);
            double pow = 1.0, pow2 = x;
            while (posN > 0) {
                if (posN % 2 == 1) {
                    pow *= pow2;
                }
                pow2 *= pow2;
                posN /= 2;
            }

            if (n < 0) {
                pow = 1 / pow;
            }
            return pow;
        }
    }
}
