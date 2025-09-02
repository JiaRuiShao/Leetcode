import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 71. Simplify Path
 */
public class _71 {
    class Solution1_Stack {
        public String simplifyPath(String path) {
            String slash = "/", dot = ".", doubleDot = "..";
            Deque<String> stk = new ArrayDeque<>();
            String[] dirs = path.split(slash);
            for (String dir : dirs) {
                if (dir.isEmpty() || dir.equals(dot)) {
                    continue;
                }
                if (dir.equals(doubleDot)) {
                    if (!stk.isEmpty()) stk.pop();
                } else {
                    stk.push(dir);
                }
            }
            StringBuilder simplifiedPath = new StringBuilder();
            while(!stk.isEmpty()) {
                simplifiedPath.insert(0, stk.pop());
                simplifiedPath.insert(0, slash);
            }
            return simplifiedPath.length() == 0 ? slash : simplifiedPath.toString();
        }
    }
}
