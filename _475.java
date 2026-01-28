import java.util.Arrays;
import java.util.TreeSet;

/**
 * 475. Heaters
 */
public class _475 {
    // Time: O(mlogm + nlogm) where n is houses.length and m is heaters.length
    // Space: O(logm)
    class Solution1_BinarySearch {
        public int findRadius(int[] houses, int[] heaters) {
            Arrays.sort(heaters);
            int maxRadius = 0;
            for (int house : houses) {
                int radius = findMinDistanceToHeater(house, heaters);
                maxRadius = Math.max(maxRadius, radius);
            }
            return maxRadius;
        }

        private int findMinDistanceToHeater(int house, int[] heaters) {
            int lo = 0, hi = heaters.length - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (heaters[mid] == house) {
                    return 0;
                } else if (heaters[mid] < house) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            int minDist = Integer.MAX_VALUE;
            if (lo < heaters.length) {
                minDist = Math.min(minDist, heaters[lo] - house);
            }
            if (hi >= 0) {
                minDist = Math.min(minDist, house - heaters[hi]);
            }
            return minDist;
        }
    }

    class Solution2_TreeSet {
        public int findRadius(int[] houses, int[] heaters) {
            TreeSet<Integer> heaterSet = new TreeSet<>();
            for (int heater : heaters) {
                heaterSet.add(heater);
            }
            
            int radius = 0;
            
            for (int house : houses) {
                Integer floor = heaterSet.floor(house);   // Closest heater <= house
                Integer ceiling = heaterSet.ceiling(house); // Closest heater >= house
                
                int minDist = Integer.MAX_VALUE;
                
                if (floor != null) {
                    minDist = Math.min(minDist, house - floor);
                }
                if (ceiling != null) {
                    minDist = Math.min(minDist, ceiling - house);
                }
                
                radius = Math.max(radius, minDist);
            }
            
            return radius;
        }
    }
}
