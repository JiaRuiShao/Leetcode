import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * 253. Meeting Rooms II
 * 
 * - S0: Brute Force O(n^2), O(1)
 * - S1: Difference Array/List/TreeMap O(nlogn), O(n) [PREFERRED]
 * - S2: Sorting + MinHeap O(nlogn), O(n) [PREFERRED]
 * - S3: Sorting + Two Pointers O(nlogn), O(n)
 */
public class _253 {
    // for this question, we can't use sort by end and then compare with neighbor interval
    // unlike LC 435, we need to track all active meetings
    class Solution_Wrong {
        // intervals = [[8,14],[12,13],[6,13],[1,9]] expected = 3, got 2
        public int minMeetingRooms(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> {
                if (a[1] == b[1]) return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            });
            
            int overlapped = 1, maxOverlapped = 1;
            int prevEnd = intervals[0][0];
            for (int[] interval : intervals) {
                if (interval[0] < prevEnd) {
                    overlapped++;
                    maxOverlapped = Math.max(maxOverlapped, overlapped);
                } else {
                    prevEnd = interval[1];
                    overlapped = 1;
                }
            }
            return maxOverlapped;
        }
    }

    // difference array: diff[i] = nums[i] - nums[i - 1];
    // Time: O(n + 10^6)
    class Solution1_Difference_Array {
        public int minMeetingRooms_Array(int[][] intervals) {
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

        // Space Optimized Version of Difference Array
        // Time: O(n + range) where range = max_time - min_time
        // Space: O(range)
        public int minMeetingRooms(int[][] intervals) {
            if (intervals == null || intervals.length == 0) {
                return 0;
            }
            
            // Find time range
            int minTime = Integer.MAX_VALUE;
            int maxTime = Integer.MIN_VALUE;
            
            for (int[] interval : intervals) {
                minTime = Math.min(minTime, interval[0]);
                maxTime = Math.max(maxTime, interval[1]);
            }
            
            // Difference array
            int[] diff = new int[maxTime - minTime + 1];
            
            for (int[] interval : intervals) {
                int start = interval[0] - minTime;
                int end = interval[1] - minTime;
                
                diff[start]++;    // Meeting starts
                diff[end]--;      // Meeting ends
            }
            
            // Find max prefix sum
            int rooms = 0;
            int maxRooms = 0;
            
            for (int delta : diff) {
                rooms += delta;
                maxRooms = Math.max(maxRooms, rooms);
            }
            
            return maxRooms;
        }

        public int minMeetingRooms_TreeMap(int[][] intervals) {
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

        public int minMeetingRooms_List(int[][] intervals) {
            List<int[]> events = new ArrayList<>();
            
            for (int[] interval : intervals) {
                events.add(new int[]{interval[0], 1});   // Start: +1
                events.add(new int[]{interval[1], -1});  // End: -1
            }
            
            // Sort by time, with ends before starts at same time
            Collections.sort(events, (a, b) -> {
                if (a[0] != b[0]) return a[0] - b[0];
                return a[1] - b[1];  // End (-1) before Start (+1)
            });
            
            int rooms = 0;
            int maxRooms = 0;
            for (int[] event : events) {
                rooms += event[1];
                maxRooms = Math.max(maxRooms, rooms);
            }
            
            return maxRooms;
        }
    }

    // Time: O(nlogn)
    // Space: O(n)
    class Solution2_PriorityQueue {
        public int minMeetingRooms(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
            // minHeap to store end times of ongoing meetings
            PriorityQueue<Integer> endTimes = new PriorityQueue<>();
            endTimes.offer(intervals[0][0]);
            for (int[] interval : intervals) {
                if (interval[0] >= endTimes.peek()) { // free one room
                    endTimes.poll();
                }
                endTimes.offer(interval[1]);
            }
            return endTimes.size();
        }
    }

    // Time: O(nlogn)
    class Solution3_Sorting_Two_Pointers {
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

    class Followup_ReturnActualRoom {
        public int[] assignRooms(int[][] intervals) {
            int n = intervals.length;
            // track original positions
            Integer[] indices = new Integer[n];
            for (int i = 0; i < n; i++) {
                indices[i] = i;
            }

            // sort by start time
            Arrays.sort(indices, (a, b) -> Integer.compare(intervals[a][0], intervals[b][0]));
            // minHeap stores [end_time, room_number]
            PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
            
            int[] assignment = new int[n];
            int nextRoom = 0;
            for (int i : indices) {
                int[] meeting = intervals[i];
                int roomNum;
                if (!minHeap.isEmpty() && minHeap.peek()[0] <= meeting[0]) {
                    // use previous room
                    roomNum = minHeap.poll()[1];
                } else {
                    // allocate a new room
                    roomNum = nextRoom++;
                }
                assignment[i] = roomNum;
                minHeap.offer(new int[]{meeting[1], roomNum});
            }
            
            return assignment;
        }
    }
}
