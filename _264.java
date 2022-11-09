/**
 * 264. Ugly Number II.
 * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
 * Given an integer n, return the nth ugly number.
 */
public class _264 {
	public static void main(String[] args) {
		new Wrong_Solution().nthUglyNumber(11); // returns 14, which is not an ugly number
	}
	
	static class Solution_Time_Limit_Exceeded {
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
			int product2 = 1, product3 = 1, product5 = 1;
			// merged list
			int[] ugly = new int[n];
			
			// merge three list until nth elem
			while (p < n) {
				// update the merged list with the smallest val of three list
				int min = findSmallest(product2, product3, product5);
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
		
		private int findSmallest(int p1, int p2, int p3) {
			return Math.min(Math.min(p1, p2), p3);
		}
		
	}
	
	static class Wrong_Solution {
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
}
