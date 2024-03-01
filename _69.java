public class _69 {
    class Solution {
        /**
         f(i) = i^2 where 0 <= i <= x
         given x, find target i or if not found, find i - 1 (check if i - 1 >= 0)
         */
        public int mySqrt(int x) {
            return binarySearch(x);
        }

        private int binarySearch(int target) {
            int left = 0, right = target, mid = 0;
            long midSquare;
            while (left <= right) {
                mid = left + (right - left) / 2;
                midSquare = (long) mid * mid;
                int cpr = Long.compare(midSquare, target);
                if (cpr == 0) {
                    return mid;
                } else if (cpr < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return right >= 0 ? right : 0;
        }
    }
}
