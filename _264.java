/**
 * 264. Ugly Number II
 */
public class _264 {
	// Time: O(n)
	static class Solution_MergeLinkedList {
		public int nthUglyNumber(int n) {
			int[] ugly = new int[n];
			ugly[0] = 1;
			int p1 = 0, p2 = 0, p3 = 0;
			long m1 = 1, m2 = 1, m3 = 1;
			for (int pos = 0; pos < n; ) {
				long next = Math.min(Math.min(m1, m2), m3);
				ugly[pos++] = (int) next;
				if (next == m1) {
					m1 = (long) ugly[p1++] * 2;
				}
				if (next == m2) {
					m2 = (long) ugly[p2++] * 3;
				}
				if (next == m3) {
					m3 = (long) ugly[p3++] * 5;
				}
			}
			return ugly[n - 1];
		}

		public int nthUglyNumber2(int n) {
			int i2 = 0, i3 = 0, i5 = 0; // Indices
			int m2 = 2, m3 = 3, m5 = 5; // Multiples
			int[] ugly = new int[n];
			ugly[0] = 1;
	
			for (int i = 1; i < n; i++) {
				int next = Math.min(m2, Math.min(m3, m5));
				ugly[i] = next;
	
				if (next == m2) m2 = 2 * ugly[++i2];
				if (next == m3) m3 = 3 * ugly[++i3];
				if (next == m5) m5 = 5 * ugly[++i5];
			}
			return ugly[n - 1];
		}
		
	}
	
	static class Wrong_Solution1 {
		public int nthUglyNumber(int n) {
			int num = 0;
			while (n > 0) {
				num++;
				System.out.printf("n is %d, num is %d%n", n, num);
				if (isUglyNumber(num)) {
					n--;
				}
			}
			return num;
		}
		
		// wrong: 14 shouldn't be returned as true
		private boolean isUglyNumber(int num) {
			return num == 1 || num % 2 == 0 || num % 3 == 0 || num % 5 == 0;
		}
	}

	static class Wrong_Solution2_TLE {
		/**
		 * Time: O(nlogn)
		 * Space: O(1)
		 *
		 * @param n nth ugly number
		 * @return the nth ugly number whose prime factor are: 2, 3, and 5
		 */
		public int nthUglyNumber(int n) {
			int num = 0;
			while (n > 0) {
				if (isUgly(++num)) {
					n--;
				}
			}
			return num;
		}
		
		/**
		 * Time: O(log_2(n) + log_3(n) + log_5(n))
		 * Space: O(1)
		 *
		 * @param n given num
		 * @return true if the given num is an ugly num; false if not
		 */
		private boolean isUgly(int n) {
			if (n <= 0) return false;
			while (n % 2 == 0) n /= 2;
			while (n % 3 == 0) n /= 3;
			while (n % 5 == 0) n /= 5;
			return n == 1;
		}
	}

	static class Wrong_Solution3 {
		/**
		 * When we update the next primes, we should times 2, 3 & 5 to not only whose prime factors are either 1 or themselves, but also the prime numbers generated using other prime factors besides themselves, e.g. 2x1, 2x2, [2x3], 2x4, [2x5], 2x6, 2x8,... notice that how this below solution ignores the [] operations.
		 * @param n n
		 * @return nth ugly num
		 */
		public int nthUglyNumber(int n) {
			int ugly = 1, min = 0;
			int[] primes = new int[]{2, 3, 5};
			while (--n > 0) {
				// comparing primes
				if (primes[0] <= primes[1] && primes[0] <= primes[2]) {
					min = primes[0];
				} else if (primes[1] <= primes[0] && primes[1] <= primes[2]) {
					min = primes[1];
				} else {
					min = primes[2];
				}
				// update nth ugly number
				ugly = min;
				// update next prime product
				if (primes[0] == min) {
					primes[0] *= 2;
				}
				if (primes[1] == min) {
					primes[1] *= 3;
				}
				if (primes[2] == min) {
					primes[2] *= 5;
				}
			}
			return ugly;
		}
    }
}
