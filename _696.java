/**
 * 696. Count Binary Substrings
 */
public class _696 {
    // Time: O(n)
    // Space: O(1)
    class Solution1_PatternRecognition {
        public int countBinarySubstrings(String s) {
            // prev is length of previous group
            // curr is length of current group
            int count = 0, prev = 0, curr = 0;
            char group = '\0';
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == group) {
                    curr++;
                } else {
                    count += Math.min(prev, curr);
                    prev = curr;
                    group = c;
                    curr = 1;
                }
            }
            count += Math.min(prev, curr);
            return count;
        }
    }

    class Solution2 {
        public int countBinarySubstrings(String s) {
            int prevGroupCount = 0;
            int currGroupCount = 1;
            int result = 0;
            
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) == s.charAt(i - 1)) {
                    // Same as previous, extend current group
                    currGroupCount++;
                } else {
                    // Different from previous, start new group
                    result += Math.min(prevGroupCount, currGroupCount);
                    
                    prevGroupCount = currGroupCount;
                    currGroupCount = 1;
                }
            }
            
            // Don't forget last pair of groups
            result += Math.min(prevGroupCount, currGroupCount);
            
            return result;
        }
    }
}
