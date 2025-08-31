import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 2305. Fair Distribution of Cookies
 */
public class _2305 {
    class Solution1_Backtrack_By_Ball {
        private int minDist;
        public int distributeCookies(int[] cookies, int k) {
            minDist = Arrays.stream(cookies).sum();
            int[] assigned = new int[k];
            backtrack(cookies, 0, assigned);
            return minDist;
        }

        private void backtrack(int[] cookies, int idx, int[] assigned) {
            if (idx == cookies.length) {
                minDist = Math.min(minDist, Arrays.stream(assigned).max().getAsInt());
                return;
            }
            for (int children = 0; children < assigned.length; children++) {
                assigned[children] += cookies[idx];
                if (assigned[children] < minDist) backtrack(cookies, idx + 1, assigned);
                assigned[children] -= cookies[idx];
            }
        }
    }

    class Solution1_Backtrack_By_Ball_Prune {
        private int minDist;
        public int distributeCookies(int[] cookies, int k) {
            minDist = Arrays.stream(cookies).sum();
            int[] assigned = new int[k];
            backtrack(cookies, 0, assigned);
            return minDist;
        }

        private void backtrack(int[] cookies, int idx, int[] assigned) {
            if (idx == cookies.length) {
                minDist = Math.min(minDist, Arrays.stream(assigned).max().getAsInt());
                return;
            }
            Set<Integer> cache = new HashSet<>();
            for (int children = 0; children < assigned.length; children++) {
                assigned[children] += cookies[idx];
                if (assigned[children] < minDist && !cache.contains(assigned[children])) {
                    cache.add(assigned[children]);
                    backtrack(cookies, idx + 1, assigned);
                }
                assigned[children] -= cookies[idx];
            }
        }
    }
}
