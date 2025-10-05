/**
 * 263. Ugly Number
 */
public class _263 {
	/**
	 * Time: O(log_2(n) + log_3(n) + log_5(n))
	 * Space: O(1)
	 *
	 * @param n given num
	 * @return true if the given num is an ugly num; false if not
	 */
	public boolean isUgly(int n) {
		if (n <= 0) return false;
		while (n % 2 == 0) n /= 2;
		while (n % 3 == 0) n /= 3;
		while (n % 5 == 0) n /= 5;
		return n == 1;
	}
}
