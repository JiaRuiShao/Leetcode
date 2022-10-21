import java.util.Arrays;
import java.util.OptionalInt;

/**
 * 1094. Car Pooling.
 */
public class _1094 {
    class Solution1 {
        /**
         * Car Pooling.
         *
         * @param trips trips[i] = [numPassengersi, fromi, toi], 0 <= fromi < toi <= 1000
         * @param capacity the max passengers allowed
         * @return true if it is possible to pick up and drop off all passengers
         * for all the given trips, or false otherwise.
         */
        public boolean carPooling(int[][] trips, int capacity) {
            final int MAX_KILS = 1001;
            int[] diff = new int[MAX_KILS];
            for (int[] trip : trips) {
                int numPassenger = trip[0], from = trip[1], to = trip[2] - 1; // passengers get on the bus at idx from, and get off at idx to, so the kills range is [from, to - 1]
                diff[from] += numPassenger;
                if (to + 1 < MAX_KILS) diff[to + 1] -= numPassenger;
            }
            int[] res = new int[MAX_KILS];
            res[0] = diff[0];
            for (int i = 0; i < MAX_KILS; i++) {
                if (i > 0) res[i] = res[i - 1] + diff[i];
                if (res[i] > capacity) return false;
            }
            return true;
        }
    }
    
    class Solution2 {
        public boolean carPooling(int[][] trips, int capacity) {
            int tripLen = findTripLen(trips);
            int[] diff = new int[tripLen];
            for (int[] trip : trips) {
                update(diff, trip[1], trip[2] - 1, trip[0]);
            }
            
            int[] passengers = new int[tripLen];
            for (int i = 0; i < tripLen; i++) {
                if (i == 0) {
                    passengers[i] = diff[i];
                } else {
                    passengers[i] = diff[i] + passengers[i - 1];
                }
                if (passengers[i] > capacity) return false;
            }
            return true;
        }
        
        private int findTripLen(int[][] trips) {
            OptionalInt maxTrip = Arrays.stream(trips).mapToInt(trip -> trip[2]).max();
            return maxTrip.isPresent() ? maxTrip.getAsInt() : -1;
        }
        
        private void update(int[] diff, int from, int to, int numPass) {
            diff[from] += numPass;
            if (to + 1 < diff.length) {
                diff[to + 1] -= numPass;
            }
        }
    }

    public static void main(String[] args) {
        int[][] trips = new int[][]{{9,0,1},{3,3,7}};
        new _1094().new Solution1().carPooling(trips, 4);
    }
}
