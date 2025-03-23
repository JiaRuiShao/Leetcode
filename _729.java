import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 729. My Calendar I
 */
public class _729 {
    class Solution1_TreeMap {
        class MyCalendar {
            TreeMap<Integer, Integer> events;

            public MyCalendar() {
                events = new TreeMap<>();
            }

            public boolean book(int startTime, int endTime) {
                Integer lastEventStart = events.floorKey(startTime);
                Integer nextEventStart = events.ceilingKey(startTime);
                if (lastEventStart != null && events.get(lastEventStart) > startTime) {
                    return false;
                }

                if (nextEventStart != null && nextEventStart < endTime) {
                    return false;
                }
                events.put(startTime, endTime);
                return true;
            }
        }
    }

    class Solution2_Brute_Force {
        class MyCalendar {
            List<int[]> calendar;

            MyCalendar() {
                calendar = new ArrayList();
            }

            public boolean book(int start, int end) {
                for (int[] event : calendar) {
                    if (event[0] < end && start < event[1]) {
                        return false;
                    }
                }
                calendar.add(new int[] {start, end});
                return true;
            }
        }
    }

}
