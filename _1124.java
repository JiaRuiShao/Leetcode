import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1124. Longest Well-Performing Interval
 */
public class _1124 {
	
	private final Solution1_PrefixSum_MonoStack solution = new Solution1_PrefixSum_MonoStack();
	
	class Solution0_BruteForce {
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
	
	// convert hours with > 8 to 1; else -1
	// find longest subarray with sum > 0
	// hrs:     [6, 9, 9]
	// pre: [0, -1, 0, 1]
	// find max(j - i): pre[j] - pre[i] > 0 ==> find min(i) that pre[i] < pre[j]
	// use a mono stack to keep preSum[i] in mono decreasing order
	class Solution1_PrefixSum_MonoStack {
		public int longestWPI(int[] hours) {
			int n = hours.length, maxLen = 0;
			int[] preSum = new int[n + 1];
			Deque<Integer> stk = new ArrayDeque<>(); // preSum indices
			stk.push(0);
			
			for (int i = 0; i < n; i++) {
				int sum = preSum[i];
				if (hours[i] > 8) sum++; // tiring day
				else sum--; // non-tiring day
				preSum[i + 1] = sum;
				if (sum < preSum[stk.peek()]) stk.push(i + 1);
			}
			
			for (int i = n; i >= 0; i--) {
				while (!stk.isEmpty() && preSum[stk.peek()] < preSum[i]) {
					maxLen = Math.max(maxLen, i - stk.pop());
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
				if (sum > 0) maxLen = Math.max(maxLen, i); // any prefix‐sum > 0 means subarray [0..i−1] is well-performing
				else if (sumIdx.containsKey(sum - 1)) { // look for the smallest index i where sum[i] = sum[j] − 1
					maxLen = Math.max(maxLen, i - sumIdx.get(sum - 1));
				}
				
				if (!sumIdx.containsKey(sum)) {
					sumIdx.put(sum, i);
				}
			}
			return maxLen;
		}
	}
	
	// convert hours with > 8 to 1; else -1
	// find longest subarray with sum > 0
	// hours = [6,9,9], expected = 3
	class Wrong_Sliding_Window {
		public int longestWPI(int[] hours) {
			int n = hours.length;
			for (int i = 0; i < n; i++) {
				if (hours[i] > 8) hours[i] = 1; // tiring day
				else hours[i] = -1; // non-tiring day
			}
			int left = 0, right = 0, winSum = 0, maxLen = 0;
			while (right < n) {
				winSum += hours[right++];
				while (winSum <= 0 && left < right) {
					winSum -= hours[left++];
				}
				if (winSum > 0) {
					maxLen = Math.max(maxLen, right - left);
				}
			}
			return maxLen;
		}
	}
	
	@Test
	void testHours_6_6_9() {
		int[] hours = { 6, 6, 9 };
		// For [6,6,9], the prefixSum become [-1, -1, +1].
		// The longest subarray with positive sum is just [9], length = 1.
		assertEquals(1, solution.longestWPI(hours));
	}
	
	@Test
	void testHours_6_9_9() {
		int[] hours = { 6, 9, 9 };
		// For [6,9,9], the prefixSum become [-1, +1, +1].
		// The entire array sums to +1, so the answer is length = 3.
		assertEquals(3, solution.longestWPI(hours));
	}
}
