import java.util.HashMap;
import java.util.Map;

/**
 * 383. Ransom Note
 */
public class _383 {
    class Solution2_HashMap {
        public boolean canConstruct(String ransomNote, String magazine) {
            Map<Character, Integer> charFreq = new HashMap<>();
            for (int i = 0; i < magazine.length(); i++) {
                char c = magazine.charAt(i);
                charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
            }

            for (int i = 0; i < ransomNote.length(); i++) {
                char c = ransomNote.charAt(i);
                Integer freq = charFreq.get(c);
                if (freq == null || freq <= 0) return false;
                charFreq.put(c, charFreq.get(c) - 1);
            }
            
            return true;
        }
    }

    class Solution3_Array {
        public boolean canConstruct(String ransomNote, String magazine) {
            int[] charFreq = new int[26];
            for (int i = 0; i < magazine.length(); i++) {
                charFreq[magazine.charAt(i) - 'a']++;
            }
    
            for (int i = 0; i < ransomNote.length(); i++) {
                int c = ransomNote.charAt(i) - 'a';
                if (--charFreq[c] < 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
