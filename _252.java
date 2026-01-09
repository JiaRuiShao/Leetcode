import java.util.Arrays;

/**
 * 252. Meeting Rooms
 */
public class _252 {
    class Solution1_Sort {
        public boolean canAttendMeetings(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] < intervals[i-1][1]) {
                    return false;
                }
            }
            return true;
        }
    }
}
