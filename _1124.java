import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1124. Longest Well-Performing Interval
 * 
 * - S0 BF: O(n^2) O(1)
 * - S1: Prefix + MonoStack: O(n), O(n)
 * - S2: Prefix + HashMap: O(n), O(n) [PREFERRED]
 */
public class _1124 {
	
	private final Solution1_PrefixSum_MonoStack solution = new Solution1_PrefixSum_MonoStack();
	
	class Solution0_BruteForce {
		public int longestWPI(int[] hours) {
			int n = hours.length, maxLen = 0;
			
			for (int i = 0; i < n; i++) {
				int sum = 0;
				for (int j = i; j < n; j++) {
					sum += (hours[j] > 8) ? 1 : -1;
					if (sum > 0) {
						maxLen = Math.max(maxLen, j - i + 1);
					}
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
		// for each i, find min(j) so that presum[i] - presum[j] > 0 where i > j
		// ==> find min_j: presum[j] < presum[i]
		public int longestWPI(int[] hours) {
			int n = hours.length, maxLen = 0;
			int[] presum = new int[n+1];
			Deque<Integer> minStack = new ArrayDeque<>();
			minStack.push(0);
			for (int i = 1; i <= n; i++) {
				presum[i] = presum[i-1] + ((hours[i-1] > 8) ? 1 : -1);
				if (presum[i] < presum[minStack.peek()]) {
					minStack.push(i);
				}
			}

			for (int i = n; i > 0; i--) {
				while (!minStack.isEmpty() && presum[minStack.peek()] < presum[i]) {
					maxLen = Math.max(maxLen, i - minStack.pop());
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
			int n = hours.length, maxLen = 0, sum = 0;
			Map<Integer, Integer> prefixSumToIdx = new HashMap<>();
			prefixSumToIdx.put(sum, -1);
			for (int i = 0; i < n; i++) {
				sum += (hours[i] > 8) ? 1 : -1;
				if (sum > 0) { // any prefix‐sum > 0 means subarray [0..i−1] is well-performing
					maxLen = Math.max(maxLen, i + 1);
				} else if (prefixSumToIdx.containsKey(sum - 1)) { // look for the smallest index i where sum[i] = sum[j] − 1
					maxLen = Math.max(maxLen, i - prefixSumToIdx.get(sum - 1));
				}
				prefixSumToIdx.putIfAbsent(sum, i);
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
