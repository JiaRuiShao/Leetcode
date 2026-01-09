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
    }

    class Solution1_Greedy {
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
        // min(removedInterval) = min(n - non-overlappedIntervals) ==> max(non-overlappedIntervals)
        public int eraseOverlapIntervals(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
            int n = intervals.length;
            int[] dp = new int[n]; // max non-overlapped intervals we can select from 0..i
            dp[0] = 1;
            // dp[i] = max(dp[i-1], dp[j] + 1)
            // dp[i-1] skip interval i
            // dp[j] + 1 take interval i, where j is last non-overlapped interval with i
            for (int i = 1; i < n; i++) {
                dp[i] = dp[i-1];
                int j = lastNonOverlappedInterval(intervals, i);
                if (j >= 0) dp[i] = Math.max(dp[i], dp[j] + 1);
                // else dp[i] = Math.max(dp[i], 1);
            }
            return n - dp[n-1];
        }

        /**
         * Find largest index j < i where intervals[j] doesn't overlap with intervals[i]
         * Binary search since array is sorted by end time
         */
        private int lastNonOverlappedInterval(int[][] intervals, int i) {
            int lo = 0, hi = i - 1, k = intervals[i][0];
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (intervals[mid][1] <= k) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            return hi;
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
