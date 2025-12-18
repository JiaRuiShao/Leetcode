import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 313. Super Ugly Number
 * 
 * Clarification:
 * - Constraints on n and primes.length? n <= 10^5, primes.length <= 100
 * - There's no duplicate number in primes[]?
 * 
 * Followup:
 * - Can you use minHeap? Yes we can use minHeap + set to get next smallest ugly number when k is large
 */
public class _313 {
	// Time: O(nklognk) where k is primes.length; each number generates k candidates before being polled
	// Space: O(nk)
	class Solution1_minHeap {
		public int nthSuperUglyNumber(int n, int[] primes) {
			PriorityQueue<Integer> minHeap = new PriorityQueue<>();
			Set<Integer> set = new HashSet<>();
			int ugly = 1;
			minHeap.offer(ugly);
			set.add(ugly);
			for (int i = 1; i <= n; i++) {
				ugly = minHeap.poll();
				for (int prime : primes) {
					long next = (long) prime * ugly;
					if (next > Integer.MAX_VALUE || set.contains((int) next)) continue;
					minHeap.offer((int) next);
					set.add((int) next);
				}
			}
			return ugly;
		}
	}

	/**
	 * Using long as datatype prevents integer overflow during multiplication.
	 * Time: O(m+nm) = O(nm) where m is the size of the primes number
	 * Space: O(n+m)
	 */
	class Solution2_mergeSortedList {
		public int nthSuperUglyNumber(int n, int[] primes) {
			int[] ugly = new int[n];
			int[] indices = new int[primes.length];
			long[] next = new long[primes.length];

			ugly[0] = 1;
			for (int i = 0; i < primes.length; i++) {
				next[i] = primes[i];
			}

			for (int i = 1; i < n; i++) {
				long min = Long.MAX_VALUE;
				for (long v : next) {
					min = Math.min(min, v);
				}

				ugly[i] = (int) min;

				for (int j = 0; j < primes.length; j++) {
					if (next[j] == min) {
						indices[j]++;
						next[j] = (long) primes[j] * ugly[indices[j]];
					}
				}
			}

			return ugly[n - 1];
		}
	}
}
