import java.util.Arrays;
import java.util.TreeMap;

/**
 * 253. Meeting Rooms II
 */
public class _253 {
    // difference array: diff[i] = nums[i] - nums[i - 1];
    // Time: O(n + 10^6)
    class Solution1_Difference_Array {
        public int minMeetingRooms(int[][] intervals) {
            int[] rooms = new int[1_000_001];
            for (int[] interval : intervals) {
                int start = interval[0], end = interval[1];
                rooms[start]++;
                // rooms[end + 1]--; // if interval both ends are inclusive
                rooms[end]--;
            }
    
            int currRoom = 0, maxRooms = 0;
            for (int room : rooms) {
                currRoom += room;
                maxRooms = Math.max(maxRooms, currRoom);
            }
            return maxRooms;
        }
    }

    // Time: O(nlogn)
    class Solution2_Difference_TreeMap {
        public int minMeetingRooms(int[][] intervals) {
            TreeMap<Integer, Integer> diff = new TreeMap<>();

            for (int[] interval : intervals) {
                int start = interval[0], end = interval[1];
                diff.put(start, diff.getOrDefault(start, 0) + 1);
                diff.put(end, diff.getOrDefault(end, 0) - 1);
            }

            int currRooms = 0, maxRooms = 0;
            for (int change : diff.values()) {
                currRooms += change;
                maxRooms = Math.max(maxRooms, currRooms);
            }

            return maxRooms;
        }
    }

    // Time: O(nlogn)
    class Solution2_Sorting_Two_Pointers {
        public int minMeetingRooms(int[][] intervals) {
            int n = intervals.length;
            int[] start = new int[n];
            int[] end = new int[n];
            for (int i = 0; i < intervals.length; i++) {
                int[] interval = intervals[i];
                start[i] = interval[0];
                end[i] = interval[1];
            }
            Arrays.sort(start);
            Arrays.sort(end);

            int rooms = 0, maxRooms = 0, i = 0, j = 0;
            while (i < n && j < n) {
                if (start[i] < end[j]) {
                    rooms++;
                    maxRooms = Math.max(maxRooms, rooms);
                    i++;
                } else {
                    rooms--;
                    j++;
                }
            }
            return maxRooms;
        }
    }
}
