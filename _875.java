import java.util.Arrays;

/**
 * 875. Koko Eating Bananas.
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
				if (hours == h) {
					hi = mid - 1;
				} else if (hours > h) {
					lo = mid + 1;
				} else if (hours < h) {
					hi = mid - 1;
				}
			}
			return lo;
		}
		
		/**
		 * Calculate the max bananas in each pile*.
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
}
