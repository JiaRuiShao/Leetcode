import java.util.Arrays;
import java.util.TreeMap;

/**
 * 435. Non-overlapping Intervals
 * 
 * Followup:
 * - Intervals are given as a stream? Use TreeMap
 */
public class _435 {
    // pick the interval that ends earliest
    class Solution0_Greedy_Wrong {
        // sort by start itself won't work, but here we always remove the interval with larger end
        public int eraseOverlapIntervals(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

            int remove = 0, lastEnd = intervals[0][0];
            for (int[] interval : intervals) {
                if (interval[0] < lastEnd) {
                    remove++;
                    lastEnd = Math.min(lastEnd, interval[1]);
                } else {
                    lastEnd = interval[1];
                }
            }

            return remove;
        }

        // this is the standard approach to sort by end
        public int eraseOverlapIntervals2(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

            int remove = 0, lastEnd = intervals[0][0];
            for (int[] interval : intervals) {
                if (interval[0] < lastEnd) {
                    remove++;
                    // lastEnd = Math.min(lastEnd, interval[1]);
                } else {
                    lastEnd = interval[1];
                }
            }

            return remove;
        }
    }

    class Solution2_DP {
        public int eraseOverlapIntervals(int[][] intervals) {
            int n = intervals.length;
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

            // dp[i] = max number of non-overlapping intervals ending at i
            int[] dp = new int[n];
            Arrays.fill(dp, 1);  // Each interval forms a valid set of size 1

            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (intervals[j][1] <= intervals[i][0]) { // doesn't overlap
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
            }

            int maxNonOverlap = Arrays.stream(dp).max().getAsInt();

            return n - maxNonOverlap;
        }
    }

    class Followup_Stream {
        class IntervalManager {
            TreeMap<Integer, Integer> intervals;  // start -> end
            
            public IntervalManager() {
                intervals = new TreeMap<>();
            }
            
            public boolean addInterval(int start, int end) {
                // Check for overlaps
                Integer floorKey = intervals.floorKey(start);
                
                if (floorKey != null && intervals.get(floorKey) > start) {
                    return false;  // Overlaps with previous
                }
                
                Integer ceilingKey = intervals.ceilingKey(start);
                if (ceilingKey != null && ceilingKey < end) {
                    return false;  // Overlaps with next
                }
                
                intervals.put(start, end);
                return true;
            }
            
            public int getOverlapCount() {
                // Count would need to be tracked as intervals are rejected
                return 0;  // Implementation specific
            }
        }
    }
}
