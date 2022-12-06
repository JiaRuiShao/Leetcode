/**
 * 1201. Ugly Number III.
 * An ugly number is a positive integer that is divisible by a, b, or c.
 * Given an integer n, return the nth ugly number.
 */
public class _1201 {
	class Solution1_Time_Limit_Exceeded {
		public int nthUglyNumber(int n, int a, int b, int c) {
			int num = 0;
			while (n > 0) {
				if (isUglyNum(++num, a, b, c)) {
					n--;
				}
			}
			return num;
		}
		
		private boolean isUglyNum(int num, int a, int b, int c) {
			if (num % a == 0 || num % b == 0 || num % c == 0) return true;
			return false;
		}
	}
	
	class Solution_Memory_Limit_Exceeded {
		public int nthUglyNumber(int n, int a, int b, int c) {
			int pa = a, pb = b, pc = c; // product head for list a, b and c
			int p = 0; // pointer for merged ugly number list
			int[] ugly = new int[n];
			while (p < n) {
				int min = Math.min(Math.min(pa, pb), pc);
				ugly[p++] = min;
				if (min == pa) pa += a;
				if (min == pb) pb += b;
				if (min == pc) pc += c;
			}
			return ugly[n - 1];
		}
	}
	
	class Solution_Binary_Search {
		/**
		 * Use lower bound binary search to narrow down the target number (nth ugly num)
		 * Time: O(log_2(2e9) * log(m)) where m is the max(a, b, c) = log_2(5^9) * 10 * log(m) < log_2((2^3)^9) * 10 * log(m) = 270*logm
		 * @param n nth ugly number, target f(x) in binary search
		 * @param a factor a
		 * @param b factor b
		 * @param c factor c
		 * @return nth ugly number
		 */
		public int nthUglyNumber(int n, int a, int b, int c) {
			// It is guaranteed that the result will be in range [1, 2 * 10^9]
			// so the lower_bound and upper_bound of the search ranch is 1 and 2 * 10^9 separately
			int left = 1, right = (int) 2e9, mid;
			// Lower bound binary search
			while (left <= right) {
				mid = left + (right - left) / 2;
				if (f(mid, a, b, c) >= n) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
			return left;
		}
		
		/**
		 * f(num) = count(x) where x in range [1..num] and x % a == 0 || x % b == 0 || x % c == 0
		 * count(num, a) = num / a;
		 * count(num, a, b) = num / lcm(a, b) where lcm(a, b) = ab/gcd(a, b)
		 * count(num, a, b, c) = num / lcm(lcm(a, b), c)
		 *      Time: O(logm) where m is the max(a, b, c)
		 *      Space: Time: O(logm) where m is the max(a, b, c)
		 *
		 * @param num given range upper bound num
		 * @param a factor a
		 * @param b factor b
		 * @param c factor c
		 * @return # of ugly numbers within range [1, num]
		 */
		long f(int num, int a, int b, int c) {
			long countA = num / a, countB = num / b, countC = num / c;
			long countAB = num / lowestCommonMultiple(a, b);
			long countAC = num / lowestCommonMultiple(a, c);
			long countBC = num / lowestCommonMultiple(b, c);
			long countABC = num / lowestCommonMultiple(lowestCommonMultiple(a, b), c);
			// union set：A U B U C = A + B + C - A ∩ B - A ∩ C - B ∩ C + A ∩ B ∩ C
			return countA + countB + countC - countAB - countAC - countBC + countABC;
		}
		
		/**
		 * Greatest Common Divisor (GCD) of two integers A and B is the largest integer that divides both A and B.
		 * Use Euclidean algorithm to find the gcd of two integers a, b.
		 *      Time: O(log_b(a))
		 *      Space: O(log_b(a))
		 * @param a num1
		 * @param b num2
		 * @return gcd of a and b
		 */
		long greatestCommonDivisor(long a, long b) {
			if (a < b) { // make sure a >= b
				return greatestCommonDivisor(b, a);
			}
			if (b == 0) {
				return a;
			}
			return greatestCommonDivisor(b, a % b);
		}
		
		/**
		 * LCM stands for least common multiple. The least common multiple of two numbers is the smallest number that is a multiple of both of them.
		 * lcm(a, b) = (a*b)/gcd(a, b)
		 * @param a
		 * @param b
		 * @return
		 */
		long lowestCommonMultiple(long a, long b) {
			return a * b / greatestCommonDivisor(a, b);
		}
	}
}
