import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 1257. Smallest Common Region
 */
public class _1257 {
    // Time: O(n)
    // Space: O(n)
    class Solution1_LCA {
        public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
            Map<String, String> parent = new HashMap<>();
            for (List<String> region : regions) {
                String largerRegion = region.get(0);
                for (int i = 1; i < region.size(); i++) {
                    parent.put(region.get(i), largerRegion);
                }
            }

            Set<String> parentOfRegion1 = new HashSet<>();
            while (region1 != null) {
                parentOfRegion1.add(region1);
                region1 = parent.get(region1);
            }

            while (region2 != null) {
                if (parentOfRegion1.contains(region2)) {
                    return region2;
                }
                region2 = parent.get(region2);
            }
            return null; // never reach based on question constraint
        }
    }

    class Solution1_LCA_Improved {
        public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
            Map<String, String> parent = new HashMap<>(regions.size() << 3);
            for(List<String> region : regions) {
                int n = region.size();
                String s = region.get(0);
                for(int i = 1; i < n; i++) parent.put(region.get(i), s);
            }
            String s1 = region1, s2 = region2;
            while(!s1.equals(s2)) {
                s1 = parent.getOrDefault(s1, region2);
                s2 = parent.getOrDefault(s2, region1);
            }
            return s1;
        }
    }
}
