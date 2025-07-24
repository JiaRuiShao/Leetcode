/**
 * 526. Beautiful Arrangement
 */
public class _526 {
    // Time: O(n!)
    // Space: O(n)
    class Solution1_Backtrack_Permutation_ElementsUsedOnce {
        private int beautifulArrangeCount;

        public int countArrangement(int n) {
            // array is base-1 index
            beautifulArrangeCount = 0;
            boolean[] visited = new boolean[n + 1];
            backtrack(n, visited, 1);
            return beautifulArrangeCount;
        }

        private void backtrack(int n, boolean[] used, int idx) {
            if (idx == n + 1) {
                beautifulArrangeCount++;
                return;
            }
            for (int num = 1; num <= n; num++) {
                if (used[num]) continue;
                if (!isNumValid(num, idx)) continue;
                used[num] = true;
                backtrack(n, used, idx + 1);
                used[num] = false;
            }
        }

        private boolean isNumValid(int num, int idx) {
            return num % idx == 0 || idx % num == 0;
        }
    }

    class Solution2_Backtrack_Permutation_ElementsUsedOnce_Without_Global_Variable {
        public int countArrangement(int n) {
            // array is base-1 index
            // permutation -- elem used only once
            return backtrack(n, new boolean[n + 1], 1);
        }

        private int backtrack(int n, boolean[] used, int idx) {
            if (idx == n + 1) {
                return 1;
            }
            int beautifulArrangeCount = 0;
            for (int num = 1; num <= n; num++) {
                if (used[num])
                    continue;
                if (!isNumValid(num, idx))
                    continue;
                used[num] = true;
                beautifulArrangeCount += backtrack(n, used, idx + 1);
                used[num] = false;
            }
            return beautifulArrangeCount;
        }

        private boolean isNumValid(int num, int idx) {
            return num % idx == 0 || idx % num == 0;
        }
    }
}