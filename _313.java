import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 313. Super Ugly Number
 */
public class _313 {
	class Solution1_mergeKList_minHeap {
		class UglyPair {
			int prime, pos;
			long multiple;
			public UglyPair(int prime, int pos, long multiple) {
				this.prime = prime;
				this.pos = pos;
				this.multiple = multiple;
			}
		}

		public int nthSuperUglyNumber(int n, int[] primes) {
			if (n <= 0) return -1;
			int[] ugly = new int[n];
			ugly[0] = 1;
			PriorityQueue<UglyPair> minHeap = new PriorityQueue<>(Comparator.comparingLong(a -> a.multiple));
			for (int prime : primes) {
				minHeap.offer(new UglyPair(prime, 0, ugly[0])); // int[] stores {prime, multiple, pos}
			}

			for (int count = 1; count < n; ) {
				UglyPair candidate = minHeap.poll();
				int prime = candidate.prime, pos = candidate.pos;
				long nextUgly = candidate.multiple;
				if (ugly[count - 1] < nextUgly) {
					ugly[count++] = (int) nextUgly;
				}
				candidate.multiple = (long) ugly[pos] * prime;
				candidate.pos++;
				minHeap.offer(candidate);
			}
			return ugly[n - 1];
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
			int m = primes.length;
			int[] pos = new int[m];
			long[] multiple = new long[m];
			Arrays.fill(multiple, 1);
			for (int i = 0; i < n; i++) {
				long nextUgly = getMin(multiple);
				ugly[i] = (int) nextUgly;
				for (int j = 0; j < m; j++) {
					if (multiple[j] == nextUgly) {
						multiple[j] = (long) ugly[pos[j]] * (long) primes[j];
						pos[j]++;
					}
				}
			}
			return ugly[n - 1];
		}

		private long getMin(long[] arr) {
			long min = arr[0];
			for (int i = 1; i < arr.length; i++) {
				min = Math.min(min, arr[i]);
			}
			return min;
		}
	}
}
