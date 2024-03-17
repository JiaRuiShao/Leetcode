import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 313. Super Ugly Number.
 * Handle same value multiples
 * PriorityQueue
 */
public class _313 {
	/**
	 * Merge k sorted lists.
	 */
	class Solution1_minHeap {
		/**
		 * Time: O(nlogk) where k is num of prime num in primes
		 * Space: O(n + log(k))
		 * @param n nth ugly num
		 * @param primes given prime factor numbers
		 * @return nth ugly number whose factors are all num in primes
		 */
		public int nthSuperUglyNumber(int n, int[] primes) {
			PriorityQueue<UglyNum> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
			
			for (int prime : primes) {
				minHeap.offer(new UglyNum(0, prime, 1));
			}
			
			int min = 0, prev = -1, p = 0;
			int[] superUgly = new int[n];
			while (p < n) {
				UglyNum minUglyNum = minHeap.poll();
				min = minUglyNum.val;
				if (prev != min) {
					superUgly[p++] = min;
				}
				prev = min;
				UglyNum nextUglyNum;
				try {
					nextUglyNum = new UglyNum(minUglyNum.pointer + 1, minUglyNum.prime, Math.multiplyExact(minUglyNum.prime, superUgly[minUglyNum.pointer]));
				} catch (ArithmeticException e) {
					continue;
				}
				minHeap.offer(nextUglyNum);
			}
			return superUgly[n - 1];
		}
		
		static class UglyNum {
			int pointer;
			int prime;
			int val;
			
			UglyNum(int pointer, int prime, int val) {
				this.pointer = pointer;
				this.prime = prime;
				this.val = val;
			}
		}
	}

	class Solution2_minHeap {
		public int nthSuperUglyNumber(int n, int[] primes) {
			int[] ugly = new int[n];
			// next arr = [prime, multiple, idx]
			PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] - b[1]));
			for (int prime : primes) {
				pq.offer(new int[]{prime, 1, 0});
			}
	
			for (int i = 0; i < n;) {
				int[] next = pq.poll();
				if (i == 0 || i > 0 && next[1] > ugly[i - 1]) {
					ugly[i++] = next[1];
				}
				next[1] = ugly[next[2]++] * next[0];
				pq.offer(next);
			}
	
			return ugly[n - 1];
		}
	}

	public static void main(String[] args) {
		new _313().new Solution2_minHeap().nthSuperUglyNumber(12, new int[]{2,7,13,19});
	}
}
