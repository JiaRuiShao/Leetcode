import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * 45. Jump Game II
 * 
 * Followup:
 * - Return actual path, not just the count?
 * - Maximize jumps? Can't use greedy, use DP
 * - nums[i] represents exact jump (can't jump less)? exactly one path, one pass traversal
 * - We can jump backward? Cycle could exist, DP and greedy won't work -- this becomes a graph problem, use BFS
 */
public class _45 {
    class Solution1_Greedy {
        public int jump(int[] nums) {
            int n = nums.length, steps = 0, reach = 0, jump = 0;
            for (int i = 0; i < n - 1; i++) {
                jump = Math.max(jump, i + nums[i]);
                if (reach == i) {
                    steps++;
                    reach = jump; // jump points the farthest idx this step can reach
                }
            }
            return steps;
        }

        public int jump2(int[] nums) {
            int jumps = 0;
            int farthest = 0;
            int currJumpEnd = 0;
            for (int i = 0; i < nums.length - 1; i++) { // [0, n-2]
                farthest = Math.max(farthest, i + nums[i]);
                if (farthest <= i) return -1; // doesn't occur for this problem test cases
                if (currJumpEnd == i) {
                    jumps++;
                    if (farthest >= nums.length - 1) break; // early termination
                    currJumpEnd = farthest;
                }
            }
            return jumps;
        }
    }

    class Solution2_DP {
        public int jump(int[] nums) {
            int n = nums.length;
            int[] dp = new int[n]; // min jump to from i to n - 1
            Arrays.fill(dp, Integer.MAX_VALUE / 2);
            dp[n - 1] = 0;
            // dp[i] = 1 + min(dp[i+1] ... dp[i+nums[i]])
            for (int i = n - 2; i >= 0; i--) {
                for (int j = 1; j <= nums[i] && i + j < n; j++) {
                    dp[i] = Math.min(dp[i], dp[i + j]);
                }
                dp[i]++;
            }
            return dp[0] == Integer.MAX_VALUE / 2 ? -1 : dp[0];
        }
    }

    class Wrong_Answer_Greedy_Update {
        // if it's not guaranteed to jump to n - 1
        // Q: why not work? input: [7,0,9,6,9,6,1,7,9,0,1,2,9,0,3]; output should be 2, but below code return 4
        public int jump(int[] nums) {
            int n = nums.length, jump = 0, step = 0;
            for (int pos = 0; pos < n - 1; pos++) {
                int currJump = pos + nums[pos];
                if (jump < n - 1 && currJump > jump) {
                    jump = currJump;
                    step++;
                }
                if (jump <= pos) {
                    break;
                }
            }
            return jump >= n - 1 ? step : -1;
        }
    }

    class Followup_ReturnActualPath {
        public List<Integer> jumpPath(int[] nums) {
            int n = nums.length;            
            int currentEnd = 0;
            int farthest = 0;
            int nextJumpIdx = 0;

            List<Integer> path = new ArrayList<>();
            path.add(0);
            
            for (int i = 0; i < n - 1; i++) {
                if (i + nums[i] > farthest) {
                    farthest = i + nums[i];
                    nextJumpIdx = i;
                }
                
                if (i == currentEnd) {
                    path.add(nextJumpIdx);
                    currentEnd = farthest;
                    
                    if (currentEnd >= n - 1) {
                        if (path.get(path.size() - 1) != n - 1) {
                            path.add(n - 1);
                        }
                        break;
                    }
                }
            }
            
            return path;
        }
    }

    class Followup_CanJumpBackward {
        public int minJumpsBFS(int[] nums) {
            int n = nums.length;
            boolean[] visited = new boolean[n];
            Deque<Integer> queue = new ArrayDeque<>();

            queue.offer(0);
            visited[0] = true;
            int steps = 0;

            while (!queue.isEmpty()) {
                int size = queue.size();
                steps++;
                for (int s = 0; s < size; s++) {
                    int i = queue.poll();
                    if (i == n - 1) return steps;

                    int left = Math.min(i, i + nums[i]);
                    int right = Math.max(i, i + nums[i]);

                    for (int j = left; j <= right; j++) {
                        if (j >= 0 && j < n && !visited[j]) {
                            visited[j] = true;
                            queue.offer(j);
                        }
                    }
                }
            }

            return -1; // unreachable
        }
    }
}
