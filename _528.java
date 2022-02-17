import java.util.Random;

public class _528 {
    class Solution {
        // 前缀和数组
        private int[] preSum;
        private Random rand = new Random();

        public Solution(int[] w) {
            int n = w.length;
            // 构建前缀和数组，偏移一位留给 preSum[0]
            preSum = new int[n + 1];
            preSum[0] = 0;
            // preSum[i] = sum(w[0..i-1])
            for (int i = 1; i <= n; i++) {
                preSum[i] = preSum[i - 1] + w[i - 1];
            }
        }

        public int pickIndex() {
            int n = preSum.length;
            // 在闭区间 [1, preSum[n - 1]] 中随机选择一个数字
            int target = rand.nextInt(preSum[n - 1]) + 1;
            // 获取 target 在前缀和数组 preSum 中的索引
            // 搜索左侧边界的二分搜索
            int left = 0, right = n;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (preSum[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            // preSum 的索引偏移了一位，还原为权重数组 w 的索引
            return left - 1;
        }
    }

}
