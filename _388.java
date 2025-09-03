import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 388. Longest Absolute File Path
 */
public class _388 {
    // Use stack to record path hierarchy and length
    // Time: O(n)
    // Space: o(n)
    class Solution1_Stack {
        public int lengthLongestPath(String input) {
            int currLen = 0, maxLen = 0;
            Deque<Integer> dirLen = new ArrayDeque<>();
            // \t is a single character in Java, not two
            for (String dir : input.split("\n")) {
                int level = dir.lastIndexOf("\t") + 1; // num of tabs \t in curr subpath
                while (dirLen.size() > level) {
                    currLen -= dirLen.pop();
                }
                int len = dir.length() - level + 1; // minus \t add /
                currLen += len;
                dirLen.push(len);
                if (dir.contains(".")) {
                    maxLen = Math.max(maxLen, currLen - 1);
                }
            }
            return maxLen;
        }
    }
}
