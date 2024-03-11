import java.util.Arrays;
import java.util.OptionalInt;

/**
 * 1094. Car Pooling.
 */
public class _1094 {
    class Solution1 {
        /**
         * Difference Array to reduce the range update to O(1) time complexity.
         *
         * @param trips trips[i] = [numPassengersi, fromi, toi], 0 <= fromi < toi <= 1000
         * @param capacity the max passengers allowed
         * @return true if it is possible to pick up and drop off all passengers
         * for all the given trips, or false otherwise.
         */
        public boolean carPooling(int[][] trips, int capacity) {
            int tripLen = Arrays.stream(trips).mapToInt(trip -> trip[2]).max().getAsInt();
            int[] diff = new int[tripLen];
            for (int[] trip : trips) {
                int passenger = trip[0], from = trip[1], to = trip[2];
                diff[from] += passenger;
                if (to < tripLen) {
                    diff[to] -= passenger;
                }
            }
    
            int passengers = 0;
            for (int dif : diff) {
                passengers += dif;
                if (passengers > capacity) {
                    return false;
                }
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
        int[][] trips = new int[][]{{7,5,6}, {6,7,8}, {10,1,6}};
        System.out.println(new _1094().new Solution1().carPooling(trips, 16)); // false

        trips = new int[][]{{7,5,6}, {6,7,8}, {10,1,5}};
        System.out.println(new _1094().new Solution1().carPooling(trips, 16)); // true
    }
}
