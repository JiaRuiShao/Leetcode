/**
 * 45. Jump Game II
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
            int furthest = 0, jumpIdx = 0, steps = 0;
            for (int i = 0; i < nums.length - 1; i++) {
                furthest = Math.max(furthest, i + nums[i]);
                if (jumpIdx == i) {
                    steps++;
                    jumpIdx = furthest;
                }
            }
            return steps;
        }
    }

    class Solution2_DP_Iterative_Bottom_Up_With_Memo { // Skip the DP top-down recursive solution here
        public int jump(int[] nums) {
            int n = nums.length;
            int[] steps = new int[n];
            steps[n - 1] = 0;
            for (int i = n - 2; i >= 0; i--) {
                int reach = Math.min(i + nums[i], n - 1);
                int minStep = n;
                for (int jump = i + 1; jump <= reach; jump++) {
                    if (steps[jump] < minStep) {
                        minStep = steps[jump];
                    }
                }
                steps[i] = minStep + 1;
            }
            return steps[0];
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
}
