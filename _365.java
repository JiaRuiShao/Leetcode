import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 365. Water and Jug Problem
 */
public class _365 {
    // The math solution is based on Bézout’s identity: set {a·x + b·y} is exactly all multiples of g = gcd(x, y)
    class Solution1_Math {
        public boolean canMeasureWater(int x, int y, int target) {
            if (target > x + y) return false;
            if (target == 0) return true;
            return target % gcd(x, y) == 0;
        }

        private int gcd(int a, int b) {
            while (b != 0) {
                int mod = a % b;
                a = b;
                b = mod;
            }
            return a;
        }
    }

    // Time: O(xy)
    // Space: O(xy)
    class Solution2_DFS {
        int multiplier = 1_001, x, y, k;
        boolean canFill;
        public boolean canMeasureWater(int x, int y, int target) {
            this.multiplier = y + 1;
            this.x = x;
            this.y = y;
            this.k = target;
            this.canFill = false;
            Set<Integer> visited = new HashSet<>();
            dfs(0, 0, visited);
            return canFill;
        }

        private void dfs(int a, int b, Set<Integer> visited) {
            int encoded = encode(a, b);
            if (canFill || visited.contains(encoded)) return;
            if (a == k || b == k || a + b == k) {
                canFill = true;
                return;
            }
            visited.add(encoded);
            dfs(x, b, visited); // fill a
            dfs(a, y, visited); // fill b
            dfs(0, b, visited); // empty a
            dfs(a, 0, visited); // empty b
            int bToA = Math.min(x - a, b), aToB = Math.min(y - b, a);
            dfs(a + bToA, b - bToA, visited); // pour b to a
            dfs(a - aToB, b + aToB, visited); // pour a to b
        }

        private int encode(int x, int y) {
            return x * multiplier + y;
        }
    }

    class Solution3_BFS {
        public boolean canMeasureWater(int x, int y, int target) {
            int largeNumtiplier = y + 1;
            Queue<int[]> q = new ArrayDeque<>();
            Set<Integer> visited = new HashSet<>();
            q.offer(new int[]{0, 0});
            visited.add(0);
            while (!q.isEmpty()) {
                int[] water = q.poll();
                int waterX = water[0], waterY = water[1];
                if (waterX + waterY == target) return true;
                for (int[] nextPair : next(waterX, waterY, x, y)) {
                    int nextX = nextPair[0], nextY = nextPair[1];
                    int encoded = nextX * largeNumtiplier + nextY;
                    if (!visited.contains(encoded)) {
                        q.offer(new int[]{nextX, nextY});
                        visited.add(encoded);
                    }
                }
            }
            return false;
        }

        private List<int[]> next(int wx, int wy, int x, int y) {
            List<int[]> res = new ArrayList<>();
            res.add(new int[]{x, wy});
            res.add(new int[]{wx, y});
            res.add(new int[]{0, wy});
            res.add(new int[]{wx, 0});
            int xToY = Math.min(wx, y - wy), yToX = Math.min(wy, x - wx);
            res.add(new int[]{wx - xToY, wy + xToY});
            res.add(new int[]{wx + yToX, wy - yToX});
            return res;
        }
    }
}
