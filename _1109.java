public class _1109 {
    class Solution {
        /**
         * Corporate Flight Bookings.
         * Solved using difference array.
         *
         * @param bookings bookings[i] = [firsti, lasti, seatsi]
         * @param n n flights
         * @return an array answer of length n,
         * where answer[i] is the total number of seats reserved for flight i
         */
        public int[] corpFlightBookings(int[][] bookings, int n) {
            // build the difference array
            int[] diff = new int[n];
            for (int[] booking : bookings) {
                /** Notice here the index must MINUS one **/
                int first = booking[0] - 1, last = booking[1] - 1, seats = booking[2];
                diff[first] += seats;
                if (last + 1 < n) diff[last + 1] -= seats;
            }
            // build the result based on the difference array
            int[] res = new int[n];
            res[0] = diff[0];
            for (int i = 1; i < n; i++) {
                res[i] = res[i - 1] + diff[i];
            }
            return res;
        }
    }
}
