/**
 * 264. Ugly Number II.
 * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
 * Given an integer n, return the nth ugly number.
 */
public class _264 {
	static class Solution_MergeLinkedList {
		
		/**
		 * Think this question of merging three linked list, compare the product for each list and update the ugly array with the smallest one.
		 * Time: O(n)
		 * Space: O(n)
		 *
		 * @param n nth ugly number
		 * @return the nth ugly number whose prime factor are: 2, 3, and 5
		 */
		public int nthUglyNumber(int n) {
			// p is teh pointer of the merged ugly num list, and p2, p3, p5 represents the pointer for list of multiples of 2, 3, and 5
			int p = 0, p2 = 0, p3 = 0, p5 = 0;
			// represent the pointed val on list of multiples of 2, 3, and 5
			// int prod1 = 2, prod2 = 3, prod3 = 5;
			int product2 = 1, product3 = 1, product5 = 1;
			// merged list
			int[] ugly = new int[n];
			
			// merge three list until nth elem
			while (p < n) {
				// update the merged list with the smallest val of three list
				int min = Math.min(Math.min(p2, p3), p5);
				ugly[p++] = min;
				// update the pointers and pointed val of three lists
				if (product2 == min) {
					product2 = 2 * ugly[p2++];
				}
				if (product3 == min) {
					product3 = 3 * ugly[p3++];
				}
				if (product5 == min) {
					product5 = 5 * ugly[p5++];
				}
			}
			// return the nth ugly num
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
