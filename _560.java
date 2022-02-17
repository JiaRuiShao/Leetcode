import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class _560 {
    static class Solution1 {
        /**
         * Find the number of subarrays whose sum equals to k.
         * Time: O(N^2)
         * Space: O(N)
         *
         * @param nums the int arr
         * @param k target sum
         * @return the number of subarrays whose sum equals to k
         */
        public int subarraySum(int[] nums, int k) {
            int[] preSum = new int[nums.length + 1];
            for (int i = 1; i < preSum.length; i++) {
                preSum[i] = preSum[i - 1] + nums[i - 1];
            }
            int res = 0;
            for (int i = 1; i < preSum.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (preSum[i] - preSum[j] == k) res++;
                }
            }
            return res;
        }
    }

    static class Solution2 {
        /**
         * Find the number of subarrays whose sum equals to k.
         * Time: O(N)
         * Space: O(N)
         *
         * @param nums the int arr
         * @param k target sum
         * @return the number of subarrays whose sum equals to k
         */
        public int subarraySum(int[] nums, int k) {
            // use a HashMap to store the preSum & their frequencies
            Map<Integer, Integer> preSum = new HashMap<>(nums.length + 1);
            int res = 0, sum = 0, targetSum;
            preSum.put(0, 1); /**IMPORTANT base case **/
            for (int num : nums) {
                sum += num; // preSum for i
                targetSum = sum - k; // this is the target preSum[1...j]
                // update the answer if there's target preSum in the HashMap
                if (preSum.containsKey(targetSum)) {
                    res += preSum.get(targetSum);
                }
                preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
            }
            return res;
        }
    }
}
