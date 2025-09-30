/**
 * 5. Longest Palindromic Substring
 * Given a string s, return the longest palindromic substring in s.
 * A string is called a palindrome string if the reverse of that string is the same as the original string.
 */
public class _5 {
	class Solution1_Two_Pointers_TLE { // Time Limit Exceed, O(n^3) time complexity
		public String longestPalindrome(String s) {
			int maxLen = 0, maxL = 0, maxR = 0;
			for (int left = 0; left < s.length(); left++) {
				for (int right = left; right < s.length(); right++) {
					if (isPalindrome(s, left, right) && maxLen < right - left + 1) {
						maxLen = right - left + 1;
						maxL = left;
						maxR = right;
					}
				}
			}
			return maxLen == 0 ? "" : s.substring(maxL, maxR + 1);
		}
		
		private boolean isPalindrome(String s, int l, int r) {
			while (l < r) {
				if (s.charAt(l) != s.charAt(r)) return false;
				l++;
				r--;
			}
			return true;
		}
	}
	
	// Time: O(n^2)
	// Space: O(n)
	class Solution2_Center_Expansion { // find palindrome centered at s[i] or s[i+1]
		public String longestPalindrome(String s) {
			String longestP = "";
			for (int i = 0; i < s.length(); i++) {
				String s1 = findPalindrome(s, i, i); // odd
				String s2 = findPalindrome(s, i, i + 1); // even
				if (s1.length() > longestP.length()) longestP = s1;
				if (s2.length() > longestP.length()) longestP = s2;
			}
			return longestP;
		}
		
		private String findPalindrome(String s, int l, int r) {
			while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
				l--;
				r++;
			}
			return s.substring(l + 1, r);
		}
	}
	
	/**
	 * Expand around center for every possible center.
	 * Time: O(n^2)
	 * Space: O(n) -> O(1) if don't convert to char arr
	 */
	class Solution3_Two_Pointers_Improved {
		public String longestPalindrome(String s) {
			int start = 0, maxLen = 1;
			for (int i = 0; i < s.length(); i++) {
				int oddLength = findLongestPalindromeLength(s, i, i);
				int evenLength = findLongestPalindromeLength(s, i, i + 1);
				int len = Math.max(oddLength, evenLength);
				if (len > maxLen) {
					maxLen = len;
					start = i - (len - 1) / 2;
				}
			}
			return s.substring(start, start + maxLen);
		}
	
		private int findLongestPalindromeLength(String s, int i, int j) { // [i, j]
			int maxLen = 1, n = s.length();
			while (i >= 0 && j < n && s.charAt(i) == s.charAt(j)) {
				maxLen = Math.max(maxLen, j - i + 1);
				i--;
				j++;
			}
			return maxLen;
		}
	}
}
