import java.util.HashMap;
import java.util.Map;

/**
 * 974. Subarray Sums Divisible by K.
 * Given an integer array nums and an integer k, return the number of non-empty subarrays that have a sum divisible by k.
 * A subarray is a contiguous part of an array.*
 */
public class _974 {
	class Solution1_PrefixSum_HashMap {
		// (pre[i] - pre[j]) % k == 0 & i > j
		// pre[i] % k == pre[j] % k, i > j
		public int subarraysDivByK(int[] nums, int k) {
			int n = nums.length, count = 0;
			int[] pre = new int[n + 1];
			for (int i = 0; i < n; i++) {
				pre[i + 1] = pre[i] + nums[i];
			}
			Map<Integer, Integer> sumFreq = new HashMap<>();
			for (int i = 0; i < pre.length; i++) {
				int remainder = pre[i] % k < 0 ? pre[i] % k + k : pre[i] % k; // get positive remainder
				if (sumFreq.containsKey(remainder)) {
					count += sumFreq.get(remainder);
				}
				sumFreq.put(remainder, sumFreq.getOrDefault(remainder, 0) + 1);
			}
			return count;
		}
		
		public int subarraysDivByKImproved(int[] nums, int k) {
			int n = nums.length, count = 0, sum = 0;
			Map<Integer, Integer> sumFreq = new HashMap<>();
			sumFreq.put(sum, 1);
			for (int i = 1; i <= n; i++) {
				sum += nums[i-1];
				int remainder = sum % k < 0 ? sum % k + k : sum % k;
				if (sumFreq.containsKey(remainder)) {
					count += sumFreq.get(remainder);
				}
				sumFreq.put(remainder, sumFreq.getOrDefault(remainder, 0) + 1);
			}
			return count;
		}
	}
}
