/**
 * 5. Longest Palindromic Substring
 * 
 * - S0 BF: O(n^3), O(1)
 * - S1 Expand from center: O(n^2), O(n)/O(1) [PREFERRED]
 * - S2 DP: O(n^2), O(n^2)/O(n)
 * - S3 Manacher's Algorithm: O(n), O(n) [NOT EXPECTED]
 * 
 * Followup:
 * - Count total number of palindromic substrings? LC 647
 * - Longest palindromic subsequence? LC 516 
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
				maxLen = j - i + 1;
				i--;
				j++;
			}
			return maxLen;
		}
	}

	class Solution4_DP {
		public String longestPalindrome(String s) {
			int n = s.length(), maxLen = 1, start = 0;
			boolean[][] dp = new boolean[n][n];
			for (int i = 0; i < n; i++) {
				dp[i][i] = true;
			}
			// j >= i
			// dp[i][j] = dp[i+1][j-1] if s[i] == s[j]
			for (int i = n - 1; i >= 0; i--) {
				for (int j = i + 1; j < n; j++) {
					if (s.charAt(i) == s.charAt(j)) {
						dp[i][j] = (j == i + 1) || dp[i+1][j-1];
						if (dp[i][j] && j - i + 1 > maxLen) {
							maxLen = j - i + 1;
							start = i;
						}
					}
				}
			}
			return s.substring(start, start + maxLen);
		}

		public String longestPalindrome_SpaceOptimized(String s) {
			int n = s.length(), maxLen = 1, start = 0;
			boolean[] dp = new boolean[n];
			// j >= i
			// dp[i][j] = dp[i+1][j-1] if s[i] == s[j]
			for (int i = n - 1; i >= 0; i--) {
				boolean prev = false; // dp[i+1][j-1]
				dp[i] = true;
				for (int j = i + 1; j < n; j++) {
					boolean temp = dp[j];
					if (s.charAt(i) == s.charAt(j)) {
						dp[j] = (j == i + 1) || prev;
						if (dp[j] && j - i + 1 > maxLen) {
							maxLen = j - i + 1;
							start = i;
						}
					} else {
						dp[j] = false; // explicitly set to false to override value from prev row
					}
					prev = temp;
				}
			}
			return s.substring(start, start + maxLen);
		}
	}

	class Followup_CountAllPalindromicSubstring {
		public int countSubstrings(String s) {
			int count = 0;
			
			for (int i = 0; i < s.length(); i++) {
				// Odd length palindromes
				count += expandAndCount(s, i, i);
				// Even length palindromes
				count += expandAndCount(s, i, i + 1);
			}
			
			return count;
		}

		private int expandAndCount(String s, int left, int right) {
			int count = 0;
			while (left >= 0 && right < s.length() 
				&& s.charAt(left) == s.charAt(right)) {
				count++;
				left--;
				right++;
			}
			return count;
		}
	}
}
