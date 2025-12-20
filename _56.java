import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * 56. Merge Intervals
 * 
 * - BF: O(n^2), O(1)
 * - Sort : O(nlogn), O(logn)
 * 
 * Clarification:
 * - [1,4] and [4,5] - are these overlapping? Yes they touch at 4
 * - Are intervals sorted? Usually no
 * - Assume intervals are valid? Yes assume start <= end
 * 
 * Followup:
 * - Could you merge in place to save place?
 * - Can you use stack instead?
 * - What if you need to insert a new interval and merge? LC 57
 * - How many intervals were merged?
 * 
 * Red Flags:
 * - Forget to add the last interval
 * - Wrong overlap condition
 */
public class _56 {
    // Time: O(nlogn)
    // Space: O(n)
    class Solution1_Sort {
        public int[][] merge(int[][] intervals) {
            List<int[]> merged = new ArrayList<>();
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
            int start = intervals[0][0], end = intervals[0][1];
            for (int[] interval : intervals) {
                if (interval[0] > end) {
                    merged.add(new int[]{start, end});
                    start = interval[0];
                    end = interval[1];
                } else {
                    end = Math.max(end, interval[1]);
                }
            }
            merged.add(new int[]{start, end}); // the last merged interval NEED to be added
            return merged.toArray(new int[merged.size()][]);
        }
    }

    class Solution1_Sort_MergeInPlace {
        public int[][] merge(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
            int currIdx = 0; // pos to write next interval
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] > intervals[currIdx][1]) {
                    intervals[++currIdx] = intervals[i];
                } else {
                    intervals[currIdx][1] = Math.max(intervals[currIdx][1], intervals[i][1]);
                }
            }
            currIdx++;
            return Arrays.copyOf(intervals, currIdx);
        }
    }

    class Followup_Stack {
        public int[][] merge(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
            Deque<int[]> stk = new ArrayDeque<>();
            stk.push(intervals[0]);
            for (int i = 1; i < intervals.length; i++) {
                int[] curr = stk.peek();
                if (intervals[i][0] > curr[1]) {
                    stk.push(intervals[i]);
                } else {
                    curr[1] = Math.max(curr[1], intervals[i][1]);
                }
            }
            return stk.toArray(new int[stk.size()][2]);
        }
    }

    class Followup_CountIntervalsMerged {
        // count how many intervals got merged
        public int countMerged(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
            int lastEnd = intervals[0][1], merged = 0;
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] > lastEnd) {
                    lastEnd = intervals[i][1];
                } else {
                    merged++;
                    lastEnd = Math.max(lastEnd, intervals[i][1]);
                }
            }
            return merged;
        }

        // count how many intervals after merging
        public int countIntervals(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
            int lastEnd = -1, merged = 0;
            for (int[] interval : intervals) {
                if (interval[0] > lastEnd) {
                    lastEnd = interval[1];
                    merged++;
                } else {
                    lastEnd = Math.max(lastEnd, interval[1]);
                }
            }
            return merged;
        }
    }
}
