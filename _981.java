import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 981. Time Based Key-Value Store
 */
public class _981 {
    // TreeMap uses red-black tree under the hood, so lot's of overhead
    // Time: O(logm) where m is timestamps per key
    // Space: O(n) where n is num of keys
    class Solution1_TreeMap {
        Map<String, TreeMap<Integer, String>> map;

        public Solution1_TreeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            map.putIfAbsent(key, new TreeMap<>());
            map.get(key).put(timestamp, value);  // TreeMap maintains sorted order
        }

        public String get(String key, int timestamp) {
            if (!map.containsKey(key)) return "";
            Map.Entry<Integer, String> entry = map.get(key).floorEntry(timestamp);
            return entry == null ? "" : entry.getValue();
        }
    }

    // Since timestamps are strictly increasing for this question
    // List would guaranteed to be sorted on time
    class Solution2_List_BinarySearch {
        class TSVal {
            String value;
            int timestamp;
            public TSVal(String value, int timestamp) {
                this.value = value;
                this.timestamp = timestamp;
            }
        }

        Map<String, List<TSVal>> map;

        public Solution2_List_BinarySearch() {
            map = new HashMap<>();
        }
        
        public void set(String key, String value, int timestamp) {
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(new TSVal(value, timestamp));
        }
        
        // upper boundary binary search
        public String get(String key, int timestamp) {
            List<TSVal> list = map.get(key);
            if (list == null) return "";
            int lo = 0, hi = list.size() - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int midTS = list.get(mid).timestamp;
                if (midTS > timestamp) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            if (hi < 0) return ""; // all timestamps > target
            return list.get(hi).value;
        }
    }
}
