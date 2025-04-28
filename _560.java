import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 560. Subarray Sum Equals K
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

		// input: [2], k = 2
		public int subarraySum2(int[] nums, int k) {
			// nums[i] + ... + nums[j] == k, j >= i
			// sum(j) - sum(i - 1) == k
			// sum(j) - k == sum(i), j > i
			int[] preSum = new int[nums.length]; // preSum[i] = sum(nums[0..i])
			preSum[0] = nums[0];
			Map<Integer, Integer> sumToFreq = new HashMap<>();
			sumToFreq.put(0, 1); // base case
			int subarray = 0;
	
			for (int i = 0; i < nums.length; i++) {
				if (i > 0) preSum[i] = preSum[i - 1] + nums[i];
				int target = preSum[i] - k;
				if (sumToFreq.containsKey(target)) {
					subarray += sumToFreq.get(target);
				}
				sumToFreq.put(preSum[i], sumToFreq.getOrDefault(preSum[i], 0) + 1);
			}
			return subarray;
		}
		
		/**
		 * Time: O(n)
		 * Space: O(n)
		 * 
		 * @param nums
		 * @param k
		 * @return
		 */
		public int subarraySum3(int[] nums, int k) {
			int sum = 0, subarray = 0;
			Map<Integer, Integer> sumToFreq = new HashMap<>();
			sumToFreq.put(0, 1);
	
			for (int i = 0; i < nums.length; i++) {
				sum += nums[i];
				int target = sum - k;
				if (sumToFreq.containsKey(target)) {
					subarray += sumToFreq.get(target);
				}
				sumToFreq.put(sum, sumToFreq.getOrDefault(sum, 0) + 1);
			}
			return subarray;
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

	class Solution4_Wrong_Answer {
		public int subarraySum(int[] nums, int k) {
			// nums[i] + ... + nums[j] == k, j >= i
			// sum(j) - sum(i - 1) == k
			// sum(j) == k + sum(i), j > i
			int[] preSum = new int[nums.length]; // preSum[i] = sum(nums[0..i])
			preSum[0] = nums[0];
			Map<Integer, Integer> sumToFreq = new HashMap<>();
			sumToFreq.put(preSum[0] + k, 1);
			int subarray = 0;
	
			for (int i = 1; i < nums.length; i++) {
				preSum[i] = preSum[i - 1] + nums[i];
				if (sumToFreq.containsKey(preSum[i])) {
					subarray += sumToFreq.get(preSum[i]);
				}
				sumToFreq.put(preSum[i] + k, sumToFreq.getOrDefault(preSum[i] + k, 0) + 1);
			}
			return subarray;
		}
	}

	public static void main(String[] args) {
		System.out.println(new Solution3_PrefixSum_HashMap_Wrong_Answer().subarraySum(new int[]{1,-1,0}, 0)); // 3
	}
}
