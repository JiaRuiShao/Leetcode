import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 57. Insert Interval
 * 
 * Clarification:
 * - Given intervals are valid
 * 
 * Followup: 
 * - Can you solve this in-place without extra space? Not really since output size can vary (fewer or same or n+1)
 * - Can you use binary search to optimize? Yes we can use BS to find where newInterval should start merging; 
 *   however we would still need to copy elem from [0, start] to the new array/list, so the runtime in practice would be worse
 * - What if needed to insert multiple new intervals? 
 * 
 * Edge Cases:
 * - newInterval is before all intervals
 * - newInterval is after all intervals
 * - newInterval is in the middle of intervals but without overlap
 * 
 * Red Flags:
 * - Try to sort the already sorted input
 * - Didn't handle edge cases well
 * - Use wrong overlap condition
 */
public class _57 {
    // Time: O(n)
    // Space: O(n)
    class Solution1 {
        public int[][] insert(int[][] intervals, int[] newInterval) {
            List<int[]> inserted = new ArrayList<>();
            for (int[] interval : intervals) {
                if (interval[1] < newInterval[0]) { // interval is before newInterval
                    inserted.add(interval);
                } else if (interval[0] > newInterval[1]) { // interval is after newInterval
                    inserted.add(newInterval);
                    newInterval = interval; // IMPORTANT: swap for next iteration
                } else { // overlapping -- merge
                    newInterval[0] = Math.min(interval[0], newInterval[0]);
                    newInterval[1] = Math.max(interval[1], newInterval[1]);
                }
            }
            inserted.add(newInterval); // add the last interval (either merged or original)
            return inserted.toArray(new int[inserted.size()][]);
        }
    }

    class Solution2 {
        // intervals = [[1,5]], newInterval = [0, 0]
        // intervals = [[3,5],[12,15]], newInterval = [6,6] x wrong answer
        public int[][] insert(int[][] intervals, int[] newInterval) {
            int n = intervals.length, insertIdx = 0, intervalIdx = 0;
            int[][] inserted = new int[n + 1][2];

            while (intervalIdx < n && intervals[intervalIdx][1] < newInterval[0]) {
                inserted[insertIdx++] = intervals[intervalIdx++];
            }

            while (intervalIdx < n && intervals[intervalIdx][0] <= newInterval[1]) {
                newInterval[0] = Math.min(newInterval[0], intervals[intervalIdx][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[intervalIdx][1]);
                intervalIdx++;
            }

            inserted[insertIdx++] = newInterval;

            while (intervalIdx < n) { // some more intervals to be added
                inserted[insertIdx++] = intervals[intervalIdx++];
            }

            return Arrays.copyOf(inserted, insertIdx);
        }
    }

    class Followup_MultipleIntervalsToInsert {
        public int[][] insertMultiple(int[][] intervals, int[][] newIntervals) {
            // Approach 1: Insert one by one (inefficient)
            int[][] result = intervals;
            for (int[] newInterval : newIntervals) {
                result = insert(result, newInterval);
            }
            // return result;
            // Time: O(k × n) where k = number of new intervals
            
            // Approach 2: Merge all together
            int[][] combined = new int[intervals.length + newIntervals.length][];
            System.arraycopy(intervals, 0, combined, 0, intervals.length);
            System.arraycopy(newIntervals, 0, combined, intervals.length, newIntervals.length);
            return merge(combined);  // Use Merge Intervals
            // Time: O((n+k) log(n+k))
        }

        int[][] insert(int[][] result, int[] newInterval) {
            // logic ignored
            return new int[0][];
        }

        int[][] merge(int[][] result) {
            // logic ignored
            return result;
        }
    }

    // do NOT modify in place for insertion
    class WrongSolution_ModifyInPlace {
        // intervals = [[1,5]], newInterval = [0, 0]
        // intervals = [[3,5],[12,15]], newInterval = [6,6] x wrong answer
        public int[][] insert(int[][] intervals, int[] newInterval) {
            int n = intervals.length, idx = 0;
            while (idx < n && intervals[idx][1] < newInterval[0]) {
                idx++;
            }

            // if idx == n, it means all intervals < newInterval, the new array size is n+1
            if (idx == n) return insertIntervalAtEnd(intervals, newInterval);

            int tmp = idx;
            while (tmp < n && intervals[tmp][0] <= newInterval[1]) {
                newInterval[0] = Math.min(newInterval[0], intervals[tmp][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[tmp][1]);
                tmp++;
            }

            if (tmp == idx) return insertIntervalAtFront(intervals, newInterval); // newInterval could also be in the middle

            intervals[idx] = newInterval;
            idx++;

            while (idx < n && tmp < n) { // some more intervals to be added
                intervals[idx++] = intervals[tmp++];
            }

            return Arrays.copyOf(intervals, idx);
        }

        private int[][] insertIntervalAtEnd(int[][] intervals, int[] newInterval) {
            int n = intervals.length;
            int[][] inserted = new int[n + 1][2];
            System.arraycopy(intervals, 0, inserted, 0, n);
            inserted[n] = newInterval;
            return inserted;
        }

        private int[][] insertIntervalAtFront(int[][] intervals, int[] newInterval) {
            int n = intervals.length;
            int[][] inserted = new int[n + 1][2];
            System.arraycopy(intervals, 0, inserted, 1, n);
            inserted[0] = newInterval;
            return inserted;
        }
    }
}