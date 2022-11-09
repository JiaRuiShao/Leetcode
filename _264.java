/**
 * 264. Ugly Number II.
 */
public class _264 {
	public static void main(String[] args) {
		new Wrong_Solution().nthUglyNumber(11); // returns 14, which is not an ugly number
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
		
		private boolean isUglyNumber(int num) {
			return num == 1 || num % 2 == 0 || num % 3 == 0 || num % 5 == 0;
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
			int p = 0, p2 = 0, p3 = 0, p5 = 0;
			int product2 = 1, product3 = 1, product5 = 1;
			int[] ugly = new int[n];
			while (p < n) {
				int min = findSmallest(product2, product3, product5);
				ugly[p++] = min;
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
			return ugly[n - 1];
		}
		
		private int findSmallest(int p1, int p2, int p3) {
			return Math.min(Math.min(p1, p2), p3);
		}
	}
}
