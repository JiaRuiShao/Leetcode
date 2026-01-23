/**
 * 7. Reverse Integer
 */
public class _7 {
    class Solution1_Math {
        public int reverse(int x) {
            int sign = x >= 0 ? 1 : -1;
            int num = sign * x, reversed = 0;
            while (num > 0) {
                int digit = num % 10;
                num /= 10;
                if ((Integer.MAX_VALUE - digit) / 10 < reversed) {
                    return 0;
                }
                reversed = reversed * 10 + digit;
            }
            return sign * reversed;
        }
    }
}
