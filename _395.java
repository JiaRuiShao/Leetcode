import java.util.HashMap;
import java.util.Map;

/**
 * 395. Longest Substring with At Least K Repeating Characters.
 */
public class _395 {
	class Solution1_SlidingWindow_HashMap {
		
		
		/**
		 * Dynamic Sliding Window.
		 * Time: O(n + uniqueCharCount * n) = O(n), given that uniqueCharCount <= 26
		 * Space: O(n) intStream of input s
		 *
		 * @param s input String
		 * @param k minFreqCount of each char
		 * @return longest substring where each char c has a freq of at least k
		 */
		public int longestSubstring(String s, int k) {
			int uniqueCharCount = findUniqueCharCount(s);
			int maxLen = 0;
			for (int i = 1; i <= uniqueCharCount; i++) {
				maxLen = Math.max(maxLen, findLongestSubstringWithUniqueCharAsIAndFreqAtLeastK(s, i, k));
			}
			return maxLen;
		}
		
		private int findUniqueCharCount(String s) {
			return (int) s.chars().distinct().count();
		}
		
		private int findLongestSubstringWithUniqueCharAsIAndFreqAtLeastK(String s, int uniqueChar, int minCount) {
			int maxLen = 0, charFreqAtLeastMinCountNum = 0;
			int left = 0, right = 0;  // [left, right)
			Map<Character, Integer> freq = new HashMap<>();
			
			while (right < s.length()) {
				char c = s.charAt(right++);
				freq.put(c, freq.getOrDefault(c, 0) + 1);
				if (freq.get(c) == minCount) {
					charFreqAtLeastMinCountNum++;
				}
				
				while (freq.size() > uniqueChar) {
					c = s.charAt(left++);
					if (freq.get(c) == minCount) {
						charFreqAtLeastMinCountNum--;
					}
					freq.put(c, freq.get(c) - 1);
					if (freq.get(c) == 0) {
						freq.remove(c);
					}
				}
				
				if (charFreqAtLeastMinCountNum == uniqueChar) {
					maxLen = Math.max(maxLen, right - left);
				}
			}
			return maxLen;
		}
	}
	
	class Solution2_SlidingWindow_Array {
		public int longestSubstring(String s, int k) {
			int uniqueCharCount = findCharacterCountInInputString(s);
			int maxLen = 0;
			for (int i = 1; i <= uniqueCharCount; i++) {
				maxLen = Math.max(maxLen, findLongestSubstringWithICharAndFreqAtLeastK(s, i, k));
			}
			return maxLen;
		}
		
		private int findCharacterCountInInputString(String s) {
			return (int) s.chars().distinct().count();
		}
		
		private int findLongestSubstringWithICharAndFreqAtLeastK(String s, int uniqueChar, int minCount) {
			int maxLen = 0, charFreqAtLeastMinCountNum = 0, charInWindow = 0;
			int left = 0, right = 0; // [left, right)
			int[] freq = new int[26];
			
			while (right < s.length()) {
				char c = s.charAt(right++);
				if (freq[c - 'a'] == 0) {
					charInWindow++;
				}
				if (++freq[c - 'a'] == minCount) {
					charFreqAtLeastMinCountNum++;
				}
				
				while (charInWindow > uniqueChar) {
					c = s.charAt(left++);
					if (freq[c - 'a'] == minCount) {
						charFreqAtLeastMinCountNum--;
					}
					if (--freq[c - 'a'] == 0) {
						charInWindow--;
					}
				}
				
				if (charFreqAtLeastMinCountNum == uniqueChar) {
					maxLen = Math.max(maxLen, right - left);
				}
			}
			return maxLen;
		}
	}
}
