import java.util.HashMap;
import java.util.Map;

/**
 * 523. Continuous Subarray Sum
 * Given an integer array nums and an integer k, return true if nums has a
 * continuous subarray of size
 * at least two whose elements sum up to a multiple of k, or false otherwise.
 *
 * An integer x is a multiple of k if there exists an integer n such that x = n
 * k. 0 is always a multiple of k.
 */
public class _523 {
	/**
	 * Time: O(n)
	 * Space: O(n)
	 * (preSum[j] - preSum[i]) % k == 0 ==> preSum[j] % k == preSum[i] % k`
	 */
	class Solution1_PrefixSum_HashMap {
		public boolean checkSubarraySum(int[] nums, int k) {
			int sum = 0;
			Map<Integer, Integer> sumModKToIdx = new HashMap<>();
			for (int i = 0; i <= nums.length; i++) {
				if (i > 0) sum += nums[i - 1];
				int mod = sum % k;
				if (sumModKToIdx.containsKey(mod) && i >= sumModKToIdx.get(mod) + 2) {
					return true;
				}
				sumModKToIdx.putIfAbsent(mod, i);
			}
			return false;
		}

		// preSum idx starts with 0
		public boolean checkSubarraySumImproved(int[] nums, int k) {
			// (pre[i] - pre[j]) % k == 0 && i - j >= 2
			// pre[i] % k == pre[j] % k , i - j >= 2
			int n = nums.length;
			int preSum = 0;
			Map<Integer, Integer> sumModulusK = new HashMap<>();
			sumModulusK.put(preSum, 0);
			for (int i = 1; i <= n; i++) {
				preSum = (preSum + nums[i - 1]) % k;
				if (sumModulusK.containsKey(preSum)) {
					if (i - sumModulusK.get(preSum) >= 2)
						return true;
				} else {
					sumModulusK.put(preSum, i);
				}
			}
			return false;
		}

		// preSum idx starts with -1
		// RECOMMENDED
		public boolean checkSubarraySumImproved2(int[] nums, int k) {
			int preSum = 0;
			Map<Integer, Integer> remainderIdx = new HashMap<>();
			remainderIdx.put(preSum, -1);

			for (int i = 0; i < nums.length; i++) {
				preSum += nums[i];
				int remainder = preSum % k;
				if (remainderIdx.containsKey(remainder)) {
					int prevIdx = remainderIdx.get(remainder);
					if (i - prevIdx >= 2) {
						return true;
					}
				} else {
					remainderIdx.put(remainder, i);
				}
			}
			return false;
		}
	}

	class FollowUp_CountGoodSubarray { // this logic is wrong, check further
		public int countSubarraySum(int[] nums, int k, int j) {
			int sum = 0, count = 0;
			Map<Integer, Integer> sumModKToIdx = new HashMap<>();
			Map<Integer, Integer> sumModKFreq = new HashMap<>();
			for (int i = 0; i <= nums.length; i++) {
				if (i > 0) sum += nums[i - 1];
				int mod = sum % k;
				if (sumModKToIdx.containsKey(mod) && i >= sumModKToIdx.get(mod) + 2) {
					
					j += sumModKFreq.get(mod);
				}
				sumModKToIdx.putIfAbsent(mod, i);
				sumModKFreq.put(mod, sumModKFreq.getOrDefault(mod, 0) + 1);
			}
			return count;
		}
	}
}
