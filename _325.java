import java.util.HashMap;
import java.util.Map;

/**
 * 325. Maximum Size Subarray Sum Equals k.
 */
public class _325 {
    class Solution1_PreSum_HashMap {
        public int maxSubArrayLen(int[] nums, int k) {
            int n = nums.length;
            long[] preSum = new long[n + 1];
            for (int i = 0; i < n; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
            }

            int maxLen = 0;
            Map<Long, Integer> map = new HashMap<>();
            for (int i = 0; i <= n; i++) {
                long val = (long) preSum[i], target = val - (long) k;
                if (map.containsKey(target)) {
                    // preSum[j] - preSum[i] -> sum(arr[i], arr[j - 1])
                    // currLen = j - 1 - i + 1
                    maxLen = Math.max(maxLen, i - map.get(target));
                }
                if (!map.containsKey(val)) {
                    map.put(val, i);
                }
            }
            return maxLen;
        }
    }
}