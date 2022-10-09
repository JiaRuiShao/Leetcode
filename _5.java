/**
 * 5. Longest Palindromic Substring.
 * Given a string s, return the longest palindromic substring in s.
 * A string is called a palindrome string if the reverse of that string is the same as the original string.
 */
public class _5 {
	class Solution1 { // Time Limit Exceed, O(n^3) time complexity
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
	
	class Solution2 { // find palindrome centered at s[i] or s[i+1]
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
}
