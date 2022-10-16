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
			} else if (days > target) {
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
	}
	
}
