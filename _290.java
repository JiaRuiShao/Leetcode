import java.util.HashMap;
import java.util.Map;

/**
 * 290. Word Pattern
 */
public class _290 {
    class Solution1_HashMap {
        public boolean wordPattern(String pattern, String s) {
            String[] words = s.split("\\s+");
            if (words.length != pattern.length()) return false;
            Map<Character, Integer> mapP = new HashMap<>();
            Map<String, Integer> mapS = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                char c = pattern.charAt(i);
                String str = words[i];
                int ip = mapP.getOrDefault(c, -1);
                int is = mapS.getOrDefault(str, -1);
                if (ip != is) return false;
                mapP.put(c, i);
                mapS.put(str, i);
            }
            return true;
        }
    }
}
