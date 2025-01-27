import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 560. Subarray Sum Equals K.
 * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
 * A subarray is a contiguous non-empty sequence of elements within an array.
 */
public class _560 {
	static class Solution1_BruteForce {
		/**
		 * Find the number of subarrays whose sum equals to k.
		 * Time: O(N^2)
		 * Space: O(N)
		 *
		 * @param nums the int arr
		 * @param k    target sum
		 * @return the number of subarrays whose sum equals to k
		 */
		public int subarraySum(int[] nums, int k) {
			int[] preSum = new int[nums.length + 1];
			for (int i = 0; i < nums.length; i++) {
				preSum[i + 1] = preSum[i] + nums[i];
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
	
	static class Solution2_PrefixSum_HashMap {
		/**
		 * Find the number of subarrays whose sum equals to k.
		 * Time: O(N)
		 * Space: O(N)
		 *
		 * @param nums the int arr
		 * @param k    target sum
		 * @return the number of subarrays whose sum equals to k
		 */
		public int subarraySum(int[] nums, int k) {
			// pre[i] - pre[j] == k, && i > j
			int n = nums.length;
			int[] preSum = new int[n + 1];
			for (int i = 0; i < n; i++) {
				preSum[i + 1] = preSum[i] + nums[i];
			}
			// use a HashMap to store the preSum & their frequencies
			Map<Integer, Integer> sumFreq = new HashMap<>();
			int subarrayCount = 0;
			for (int i = 0; i < preSum.length; i++) {
				// target: pre[j] == pre[i] - k
				if (sumFreq.containsKey(preSum[i] - k)) { // found the target
					subarrayCount += sumFreq.get(preSum[i] - k); // update the total count by target freq
				}
				// update the prefix sum freq
				sumFreq.put(preSum[i], sumFreq.getOrDefault(preSum[i], 0) + 1);
			}
			return subarrayCount;
		}
		
		/**
		 * Time: O(n)
		 * Space: O(n)
		 * 
		 * @param nums
		 * @param k
		 * @return
		 */
		public int subarraySumImproved(int[] nums, int k) {
			// demo: [-1, -2, 3, 0], k = 0, res = 3
			// pre[i] - pre[j] == k, && i > j
			int n = nums.length, preSum = 0, res = 0;
			Map<Integer, Integer> freq = new HashMap<>();
			for (int i = 0; i < n + 1; i++) {
				preSum = i > 0 ? preSum + nums[i - 1] : preSum;
				int target = preSum - k;
				// target pre[j] == pre[i] - k
				if (freq.containsKey(target)) {
					res += freq.get(target);
				}
				// update the prefix sum freq
				freq.put(preSum, freq.getOrDefault(preSum, 0) + 1);
			}
			return res;
		}
	}

	static class Solution3_PrefixSum_HashMap_Wrong_Answer {
		public int subarraySum(int[] nums, int k) {
			int len = nums.length;
			int[] sum = new int[len + 1];
			for (int i = 0; i < len; i++) {
				sum[i + 1] = sum[i] + nums[i];
			}

			Set<Integer> set = new HashSet<>();
			int count = 0;
			for (int i = 0; i <= len; i++) {
				int target = sum[i] - k; // val - x = k, x = val - k
				if (set.contains(target)) count++;
				if (!set.contains(sum[i])) set.add(sum[i]);
			}
			return count;
		}
	}

	public static void main(String[] args) {
		System.out.println(new Solution3_PrefixSum_HashMap_Wrong_Answer().subarraySum(new int[]{1,-1,0}, 0)); // 3
	}
}
