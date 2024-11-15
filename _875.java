import java.util.Arrays;

/**
 * 875. Koko Eating Bananas.
 * 1. Integer Overflow
 * 2. Math calculation over while loop
 */
public class _875 {
	class Solution1_Binary_Search {
		/**
		 * This method implements a binary search between the minimum and maximum capacity.
		 * - step 1: build f(k) hoursToEat monotonic decreasing function as variable k increases
		 * - step 2: find search range for speed k: [1, max(piles)]
		 * - step 3: find min k so that f(k) == h
		 *
		 * Time Complexity: O(n + nlog(M - 1)) = O(nlogM) = O(n) where M is the maximum number bananas in the piles and n is the number of piles; worst case M = 2^31 - 1, O(n + log_2(2^31)*n) = O(n + 31n) = O(n)
		 * Space Complexity: O(1)
		 * 
		 * @param piles given piles of bananas to search
		 * @param h target f(k) hours to eat
		 * @return minimum speed k that satisfies f(k) = h
		 */
		public int minEatingSpeed(int[] piles, int h) {
			int lo = 1, hi = maxBananas(piles);
			while (lo <= hi) {
				int mid = lo + (hi - lo) / 2;
				long hours = hoursNeed(piles, mid);
				if (hours <= (long) h) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
			return lo;
		}
		
		/**
		 * Calculate the max bananas in each pile.
		 * @param piles given int[] piles
		 * @return max banana count
		 */
		private int maxBananas(int[] piles) {
			return Arrays.stream(piles).max().getAsInt();
		}
		
		/**
		 * Given current speed, calculate the hours needed to eat all piles of bananas.
		 * INTEGER OVERFLOW: use long instead of int to prevent integer overflow:
		 * When speed is min 1 & every pile has 10^9 bananas, the max number of hours is 10^9 * 10^4, which is 10^13 > 2^31 - 1, this will cause integer overflow.  
		 *
		 * @param piles given int[] piles
		 * @param speed current speed k
		 * @return hours needed to eat all bananas with speed k
		 */
		private long hoursNeed(int[] piles, int speed) {
			long hours = 0;
			for (int pile : piles) {
				hours += pile / speed;
				hours += pile % speed == 0 ? 0 : 1;
			}
			return hours;
		}
	}

	class Solution2_Binary_Search_Improved {
		public int minEatingSpeed(int[] piles, int h) {
			// Initialize the search boundaries
			int lo = 1;
			int hi = Arrays.stream(piles).max().getAsInt();
	
			// Binary search to find the minimum eating speed
			while (lo <= hi) {
				int mid = lo + (hi - lo) / 2;
				if (canEatAll(piles, mid, h)) {
					hi = mid - 1; // Try to find a smaller speed
				} else {
					lo = mid + 1; // Need a larger speed
				}
			}
			return lo;
		}
		
		private boolean canEatAll(int[] piles, int speed, int h) {
			int hours = 0;
			for (int pile : piles) {
				// Calculate hours needed for the current pile
				int hour = (pile + speed - 1) / speed; // Equivalent to Math.ceil(pile / speed)
				hours += hour;
	
				// Early exit if hours exceed h
				if (hours > h) {
					return false; // Cannot finish within h hours
				}
			}
			return true; // Can finish within h hours
		}
	}
}
