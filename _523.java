import java.util.HashMap;
import java.util.Map;

/**
 * 523. Continuous Subarray Sum.
 * Given an integer array nums and an integer k, return true if nums has a
 * continuous subarray of size
 * at least two whose elements sum up to a multiple of k, or false otherwise.
 *
 * An integer x is a multiple of k if there exists an integer n such that x = n
 * * k. 0 is always a multiple of k.
 */
public class _523 {
	/**
	 * Time: O(n)
	 * Space: O(n)
	 */
	class Solution1_PrefixSum_HashMap {
		public boolean checkSubarraySum(int[] nums, int k) {
			// (preSum[j] - preSum[i]) % k == 0
			// preSum[j] % k == preSum[i] % k
			// or preSum[j] - preSum[i] == 0
			int n = nums.length;
			int[] sum = new int[n + 1];
			for (int i = 0; i < n; i++) {
				sum[i + 1] = sum[i] + nums[i];
			}

			Map<Integer, Integer> moduloMap = new HashMap<>(); // save the modulo val
			for (int i = 0; i <= n; i++) {
				int modulo = sum[i] % k;
				if (moduloMap.containsKey(modulo)) {
					if (i - moduloMap.get(modulo) >= 2) {
						return true;
					}
				} else {
					moduloMap.put(modulo, i);
				}
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
}
