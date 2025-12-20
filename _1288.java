import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1288. Remove Covered Intervals
 * 
 * 1 - BF: O(n^2), O(n)
 * 2 - Sort by start asc then end desc: O(nlogn), O(logn)
 *   - Or sort by end desc then start asc
 * 
 * Clarification:
 * - Intervals are valid? end > start
 * - Interval length > 1?
 * 
 * Followup:
 * - Can we only sort by start? We don't know if next interval is actually covered or not
 * - Return remaining intervals
 */
public class _1288 {
    class Solution1_BF {
        public int removeCoveredIntervals(int[][] intervals) {
            int n = intervals.length;
            boolean[] covered = new boolean[n];
            
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && !covered[i]) {
                        if (intervals[j][0] <= intervals[i][0] && 
                            intervals[i][1] <= intervals[j][1]) {
                            covered[i] = true;
                            break;
                        }
                    }
                }
            }
            
            int count = 0;
            for (boolean isCovered : covered) {
                if (!isCovered) count++;
            }
            return count;
        }
    }

    class Solution2_Sort {
        public int removeCoveredIntervals(int[][] intervals) {
            // sort by start ascending, if same start then by end descending
            Arrays.sort(intervals, (a, b) -> {
                if (a[0] == b[0]) {
                    return b[1] - a[1];
                    // below version is to prevent integer overflow
                    // return Integer.compare(b[1], a[1]); 
                }
                return a[0] - b[0];
                // return Integer.compare(a[0], b[0]);
            });
            
            int count = 0;
            int prevEnd = -1; // -1 as base case since all start & end > 0
            
            for (int[] interval : intervals) {
                // update count if current interval is not covered by previous
                if (interval[1] > prevEnd) {
                    count++;
                    prevEnd = interval[1];
                }
            }
            
            return count;
        }
    }

    class Followup_RemainingIntervals {
        class Solution_Sort {
            public List<int[]> getRemainingIntervals(int[][] intervals) {
                List<int[]> remaining = new ArrayList<>();
                // sort by start ascending, if same start then by end descending
                Arrays.sort(intervals, (a, b) -> {
                    if (a[0] == b[0]) {
                        return b[1] - a[1];
                        // below version is to prevent integer overflow
                        // return Integer.compare(b[1], a[1]); 
                    }
                    return a[0] - b[0];
                    // return Integer.compare(a[0], b[0]);
                });
                
                int prevEnd = -1; // -1 as base case since all start & end > 0
                
                for (int[] interval : intervals) {
                    // update count if current interval is not covered by previous
                    if (interval[1] > prevEnd) {
                        remaining.add(new int[]{interval[0], interval[1]});
                        prevEnd = interval[1];
                    }
                }
                
                return remaining;
            }
        }
    }
}
