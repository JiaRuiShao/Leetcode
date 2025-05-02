import java.util.HashMap;
import java.util.Map;

/**
 * 205. Isomorphic Strings
 */
public class _205 {
    class Solution1_HashMap {
        // s =  "p a p e r", t =  "t i t l e", output: true
        // ms = [0 1 0 3 4], mt = [0 1 0 3 4]
        // s =  "b a d c", t =  "b a b a", output: false
        // ms = [0 1 2 3], mt = [0 1 0 1]
        public boolean isIsomorphic(String s, String t) {
            Map<Character, Integer> mapS = new HashMap<>();
            Map<Character, Integer> mapT = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char cs = s.charAt(i), ct = t.charAt(i);
                int is = mapS.getOrDefault(cs, -1);
                int it = mapT.getOrDefault(ct, -1);
                if (is != it) return false;
                mapS.put(cs, i);
                mapT.put(ct, i);
            }
            return true;
        }
    }
}
