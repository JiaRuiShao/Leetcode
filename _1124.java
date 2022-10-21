import java.util.HashMap;
import java.util.Map;

/**
 * 1124. Longest Well-Performing Interval.
 */
public class _1124 {
	
	class Solution1_BruteForce {
		public int longestWPI(int[] hours) {
			int tiredDay = 0, maxLen = 0;
			for (int start = 0; start < hours.length; start++) {
				tiredDay = 0;
				for (int end = start; end < hours.length; end++) {
					if (hours[end] > 8) tiredDay++;
					if (tiredDay > end - start + 1 - tiredDay) maxLen = Math.max(maxLen, end - start + 1);
				}
			}
			return maxLen;
		}
	}
	
	class Solution2_PrefixSum_HashMap {
		/**
		 * Convert the hours > 8 to 1, < 8 to -1, then this problem becomes: find the longest subarray whose sum > 0.
		 * - case 1: preSum[i] > 0, maxLen = i the interval [0, i] is valid
		 * - case 2: preSum[i] <= 0, find pre[i] - pre[j] > 0 , where i > j; consider that j is as small as possible,
		 * so we just need to find the j that pre[i] - pre[j] == 1, and i > j; maxLen = i - j
		 *
		 * @param hours input array
		 * @return longest interval
		 */
		public int longestWPI(int[] hours) {
			int n = hours.length, maxLen = 0;
			// build the prefix sum array
			int[] preSum = new int[n + 1];
			for (int i = 0; i < n; i++) {
				preSum[i + 1] = preSum[i] + (hours[i] > 8 ? 1 : -1);
			}
			// store the prefix sum and the corresponding index
			Map<Integer, Integer> sumIdx = new HashMap<>();
			for (int i = 0; i < preSum.length; i++) {
				// if preSum[i] > 0, means interval from 0 to i is a well-performing interval
				if (preSum[i] > 0) {
					maxLen = Math.max(maxLen, i);
				}
				// if preSum[i] <= 0, we need to find a j so that pre[i] - pre[j] > 0, and j is as small as possible ==> pre[i] - pre[j] == 1
				else if (sumIdx.containsKey(preSum[i] - 1)) {
					maxLen = Math.max(maxLen, i - sumIdx.get(preSum[i] - 1));
				}
				
				// update preSum[i]
				if (!sumIdx.containsKey(preSum[i])) {
					sumIdx.put(preSum[i], i);
				} // else do nothing because we want to keep the leftmost idx
			}
			return maxLen;
		}
		
		public int longestWPIImproved(int[] hours) {
			int n = hours.length, maxLen = 0, sum = 0;
			Map<Integer, Integer> sumIdx = new HashMap<>();
			sumIdx.put(sum, 0);
			for (int i = 1; i <= n; i++) {
				sum += (hours[i - 1] > 8 ? 1 : -1);
				if (sum > 0) maxLen = Math.max(maxLen, i);
				else if (sumIdx.containsKey(sum - 1)) {
					maxLen = Math.max(maxLen, i - sumIdx.get(sum - 1));
				}
				
				if (!sumIdx.containsKey(sum)) {
					sumIdx.put(sum, i);
				}
			}
			return maxLen;
		}
	}
}
