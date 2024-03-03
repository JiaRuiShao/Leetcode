import java.util.Arrays;

/**
 * 875. Koko Eating Bananas.
 * 1. Integer Overflow
 * 2. Math calculation over while loop
 */
public class _875 {
	class Solution_Binary_Search {
		/**
		 * Time: O(n+nlogN) where n is num of banana piles, and N is max number of bananas
		 * Space: O(1)
		 *
		 * @param piles int[] given piles of bananas
		 * @param h target hours h
		 * @return min speed to eat all bananas
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
		 * IMPORTANT: use long instead of int to prevent integer overflow
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

	class Solution2 {
		/**
		 * This method implements a binary search between the minimum and maximum capacity.
		 * - step 1: build f(k) hoursToEat monotonic decreasing function as variable k increases
		 * - step 2: find search range for speed k
		 * - step 3: check for integer overflow
		 * - step 4: implement left boundary binary search with speed k & hoursToEat f(k)
		 *
		 * Time Complexity: O(n + nlog(M - 1)) = O(nlogM) where M is the maximum number bananas in the piles and n is the number of piles
		 * Space Complexity: O(1)
		 * 
		 * @param piles given piles of bananas to search
		 * @param h target f(k) hours to eat
		 * @return minimum speed k that satisfies f(k) = h
		 */
		public int minEatingSpeed(int[] piles, int h) {
			return lowerBoundBS(piles, h, 1, Arrays.stream(piles).max().getAsInt());
		}
	
		private int lowerBoundBS(int[] piles, int h, int lo, int hi) {
			while (lo <= hi) {
				int mid = lo + (hi - lo) / 2;
				long midHrs = hoursToEat(piles, mid);
				if (midHrs <= (long) h) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
			return lo;
		}
	
		/**
		 * A private helper method to calculate f(k) hoursToEat given k.
		 * IMPORTANT: Notice how we change its time from O(n*(b/k)) to O(n) where n is pile size and b is max bananas in piles
		 * 
		 * Time: O(n) where n is pile size
		 * Space: O(1)
		 * 
		 * @param piles piles given piles of bananas to search
		 * @param speed speed k to eat bananas
		 * @return the number of hours it takes Koko to eat all piles of bananas at speed k
		 */
		private long hoursToEat(int[] piles, int speed) {
			int pile = 0, lastPile = piles.length - 1;
			long hours = 0;
			while (pile <= lastPile) {
				int bananas = piles[pile++];
				// below is too time consuming
				// while (bananas > 0) {
				//     bananas -= speed;
				//     hours++;
				// }
				hours += (bananas / speed);
				if (bananas % speed > 0) {
					hours++;
				}
			}
			return hours;
		}
	}
}
