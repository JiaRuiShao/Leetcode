import java.util.Arrays;

/**
 * 1011. Capacity To Ship Packages Within D Days.
 */
public class _1011 {
	/**
	 * Time: O(log((S-M)*n)) where n is num of orders in the given weights, M is the max order weight, and S is sum of order weights.
	 * Space: O(1)
	 *
	 * @param weights given order weights
	 * @param days target days
	 * @return min weight capacity that ship within target days
	 */
	public int shipWithinDays(int[] weights, int days) {
		return lowerBoundBinarySearch(weights, days, Arrays.stream(weights).max().getAsInt(), Arrays.stream(weights).sum());
	}
	
	private int lowerBoundBinarySearch(int[] weights, int target, int lo, int hi) {
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int days = calculateDays(weights, mid); // think why we couldn't use sum / mid + (sum % mid > 0 ? 1 : 0) to calculate the daysUsed
			if (days <= target) {
				hi = mid - 1;
			} else {
				lo = mid + 1;
			}
		}
		return lo;
	}
	
	private int calculateDays(int[] weights, int weightCapacity) {
		int days = 0, weightSum = 0;
		for (int i = 0; i < weights.length;) {
			if (weightSum + weights[i] > weightCapacity) {
				days++;
				weightSum = 0;
				continue;
			}
			weightSum += weights[i++];
		}
		// return days + weightSum > 0 ? 1 : 0; // didn't add 1 when weightSum > 0 in return
		days += weightSum > 0 ? 1 : 0;
		return days;
	}
	
	public static void main(String[] args) {
		System.out.println(new _1011().shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5));
		System.out.println(new _1011.Solution2().shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5));
	}

	static class Solution2 {
		/**
		 * This method implements a binary search between the minimum and maximum capacity.
		 * - step 1: build f(x)
		 * - step 2: find search range for capacity x
		 * - step 3: left boundary binary search with capacity x & daysToShip f(x) as days to ship the packages
		 *
		 * Time Complexity: O(n + nlogr) = O(nlogc) where n is the size of the weights, and c is difference between weights sum & max weight
		 * Space Complexity: O(1)
		 *
		 * @param weights given packages weights, min value is 1
		 * @param days target shipping days
		 * @return min capacity that needed to ship all packages in target days
		 */
		public int shipWithinDays(int[] weights, int days) {
			int[] searchRange = findCapacityRange(weights);
			int left = searchRange[0], right = searchRange[1];
			while (left <= right) {
				int mid = left + (right - left) / 2;
				int midDays = daysToShip(weights, mid);
				if (midDays <= days) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
			return left;
		}

		/**
		 * A helper method to find the search range of f(x)'s variable x.
		 * minCap is the largest weight of the given weights or else we were never able to ship the packages
		 * maxCap is the sum of the given weights so that we can ship all packages in one day
		 *
		 * Time: O(n) where n is number of packages in the given weights array
		 * Space: O(1)
		 *
		 * @param weights given packages weights, min value is 1
		 * @return an array with minCap & maxCap
		 */
		private int[] findCapacityRange(int[] weights) {
			int minCap = 0, maxCap = 0;
			for (int weight : weights) {
				maxCap += weight;
				if (weight > minCap) {
					minCap = weight;
				}
			}
			return new int[] {minCap, maxCap};
		}

		/**
		 * A helper method to calculate how many days do we need to ship all packages with the given capacity.
		 * NOTE: YOU CANNOT USE package AS THE VARIABLE NAME!!! IT'S ACCESS MODIFIER THUS IS A SPECIAL KEYWORD.
		 *
		 * Time: O(n)
		 * Space: O(1)
		 *
		 * @param weights given packages weights, min value is 1
		 * @param capacity our given capacity (mid)
		 * @return days takes to ship all packages
		 */
		private int daysToShip(int[] weights, int capacity) {
			int todayCap = 0, days = 1, packageIdx = 0;
			int lastPackageIdx = weights.length - 1;
			while (packageIdx <= lastPackageIdx) {
				int weight = weights[packageIdx];
				if (todayCap + weight <= capacity) {
					todayCap += weight;
					packageIdx++;
				} else {
					days++;
					todayCap = 0;
				}
			}
			return days;
		}
	}
	
}
