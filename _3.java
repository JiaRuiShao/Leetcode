import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 3. Longest Substring Without Repeating Characters
 */
public class _3 {
    class Solution1_HashMap {
        public int lengthOfLongestSubstring(String s) {
            Map<Character, Integer> charFreq = new HashMap<>();
            int left = 0, right = 0, maxWinLen = 0;
            while (right < s.length()) {
                char toAdd = s.charAt(right++);
                charFreq.put(toAdd, charFreq.getOrDefault(toAdd, 0) + 1);
                while (charFreq.get(toAdd) > 1) {
                    char toRemove = s.charAt(left++);
                    charFreq.put(toRemove, charFreq.get(toRemove) - 1);
                }
                maxWinLen = Math.max(maxWinLen, right - left);
            }
            return maxWinLen;
        }
    }

    class Solution2_HashSet {
        public int lengthOfLongestSubstring(String s) {
            Set<Character> winChars = new HashSet<>();
            int left = 0, right = 0, maxWin = 0;
            while (right < s.length()) {
                char add = s.charAt(right++);
                while (winChars.contains(add)) {
                    char rem = s.charAt(left++);
                    winChars.remove(rem);
                }
                maxWin = Math.max(maxWin, right - left);
                winChars.add(add);
            }
            return maxWin;
        }
    }
    
    class Solution3_Array {
        
        private static final int MAX_CHAR = 256;

        public int lengthOfLongestSubstring(String s) {
            int[] count = new int[MAX_CHAR];
            int left = 0, right = 0, maxLen = 0;
            char c;
            while (right < s.length()) {
                c = s.charAt(right++);
                count[c]++;
                while (count[c] > 1) {
                    count[s.charAt(left++)]--;
                }
                maxLen = Math.max(maxLen, right - left);
            }
            return maxLen;
        }
    }

    public static void main(String[] args) {
        System.out.println(new _3().new Solution2().lengthOfLongestSubstring("pwwkew"));
    }
}
