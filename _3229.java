/**
 * 3229. Minimum Operations to Make Array Equal to Target
 */
public class _3229 {
    class Solution1_Greedy {
        public long minimumOperations(int[] nums, int[] target) {
            long minOpr = 0, prevDiff = 0;
            for (int i = 0; i < nums.length; i++) {
                long diff = target[i] - nums[i];
                if (prevDiff * diff >= 0) {
                    minOpr += Math.max(Math.abs(diff) - Math.abs(prevDiff), 0);
                } else {
                    minOpr += Math.abs(diff);
                }
                prevDiff = diff;
            }
            return minOpr;
        }
    }
}
