import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1201. Ugly Number III
 * 
 * Red Flags
 * - Using Heap/DDP (Treating like LC 264)
 * - Don't know Inclusion-Exclusion
 * - Wrong LCM calculation
 * - Use int everywhere
 */
public class _1201 {
	// brute force solution
	// Time: O(n*min(a, b, c)) time solution is not efficient if a, b, c are large
	class Solution0_BruteForce_TLE {
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
	
	class Solution0_MergeKList_Memory_Limit_Exceeded {
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

	// Time: O(n)
	// Space: O(1)
	class Solution0_MergeKList_TLE {
		public int nthUglyNumber(int n, int a, int b, int c) {
			long m1 = a, m2 = b, m3 = c;
			long ugly = 0;
			while (n-- > 0) {
				ugly = Math.min(Math.min(m1, m2), m3);
				if (ugly == m1) {
					m1 += a;
				}
				if (ugly == m2) {
					m2 += b;
				}
				if (ugly == m3) {
					m3 += c;
				}
			}
			return (int) ugly;
		}
	}
	
	// Time: O(log(2·10⁹) · log(max(a,b,c))) = O(log(max(a,b,c)))
	class Solution1_BinarySearch_InclusionExclusion_EuclideanAlgo {
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
				if (countUgly(mid, a, b, c) >= n) {
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
		private long countUgly(int num, int a, int b, int c) {
			long countA = num / a, countB = num / b, countC = num / c;
			long countAB = num / lcm(a, b);
			long countAC = num / lcm(a, c);
			long countBC = num / lcm(b, c);
			long countABC = num / lcm(lcm(a, b), c);
			// union set：A U B U C = A + B + C - A ∩ B - A ∩ C - B ∩ C + A ∩ B ∩ C
			return countA + countB + countC - countAB - countAC - countBC + countABC;
		}
		
		// recursive version of gcd
		// private long gcd(long a, long b) {
		// 	return (b == 0 || a == b) ? a : gcd(b, a % b);
		// }

		// iterative version of gcd
		private long gcd(long a, long b) {
			while (b != 0) {
				long tmp = b;
				b = a % b;
				a = tmp;
			}
			return a;
		}
		
		/**
		 * LCM stands for least common multiple. The least common multiple of two numbers is the smallest number that is a multiple of both of them.
		 * lcm(a, b) = (a*b)/gcd(a, b)
		 * @param a
		 * @param b
		 * @return
		 */
		private long lcm(long a, long b) {
			// return a * b / gcd(a, b); // have risk of overflow
			return a / gcd(a, b) * b;
		}
	}

	_1201.Solution1_BinarySearch_InclusionExclusion_EuclideanAlgo s = new _1201.Solution1_BinarySearch_InclusionExclusion_EuclideanAlgo();

	@Test
	void test() {
		int n = 2;
		// int a = 2, b = 217983653, c = 336916467;
		int a = 1, b = 1, c = 1;
		System.out.println("Testing for a = b = c");
		assertEquals(2, s.countUgly(n, a, b, c));
	}

	public static void main(String[] args) {
		new _1201().test();
	}
}
