import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 560. Subarray Sum Equals K
 * 
 * Clarification:
 * - Could nums[i] and k be negative? [IMPORTANT]
 * - Empty subarray allowed? (No - problem says non-empty)
 */
public class _560 {
	class Solution0_BruteForce {
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
	
	class Solution2_PrefixSum_HashMap {
		// presum[j] - presum[i] == k ==> for presum[j], find count of presum[j] - k where j > i
		public int subarraySum(int[] nums, int k) {
			Map<Integer, Integer> preSumFreq = new HashMap<>();
			preSumFreq.put(0, 1);
			int count = 0, sum = 0;
			for (int num : nums) {
				sum += num;
				if (preSumFreq.containsKey(sum - k)) {
					count += preSumFreq.get(sum - k);
				}
				preSumFreq.put(sum, preSumFreq.getOrDefault(sum, 0) + 1);
			}
			return count;
		}
	}

	class WrongSolution_PrefixSum_HashMap {
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

	// **Sliding window doesn't work with negative numbers**
	class WrongSolution_SlidingWindow {
		public int subarraySum(int[] nums, int k) {
			int count = 0;
			int sum = 0;
			int left = 0;
			
			for (int right = 0; right < nums.length; right++) {
				sum += nums[right];
				
				// ❌ Wrong! Can't shrink window with negatives
				while (sum > k && left <= right) {
					sum -= nums[left++];
				}
				
				if (sum == k) {
					count++;
				}
			}
			
			return count;
		}
	}

	public static void main(String[] args) {
		System.out.println(new _560().new WrongSolution_SlidingWindow().subarraySum(new int[]{1,-1,0}, 0)); // 3
	}
}
