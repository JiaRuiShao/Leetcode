import java.util.HashMap;
import java.util.Map;

/**
 * 76. Minimum Window Substring
 */
public class _76 {
	class Solution1 {
		public String minWindow(String s, String t) {
			if (t == null || s == null || t.length() > s.length()) {
				return "";
			}

			Map<Character, Integer> tMap = new HashMap<>();
			for (int i = 0 ; i < t.length(); i++) {
				char c = t.charAt(i);
				tMap.put(c, tMap.getOrDefault(c, 0) + 1);
			}
	
			int left = 0, right = 0, required = t.length(), minWindowLen = s.length() + 1, winLeft = 0;
			while (right < s.length()) {
				char add = s.charAt(right++);
				if (tMap.containsKey(add)) {
					if (tMap.get(add) > 0) required--;
					tMap.put(add, tMap.get(add) - 1);
				}

				while (required == 0) {
					if (right - left < minWindowLen) {
						minWindowLen = right - left;
						winLeft = left;
					}

					char rem = s.charAt(left++);
					if (tMap.containsKey(rem)) {
						tMap.put(rem, tMap.get(rem) + 1);
						if (tMap.get(rem) > 0) required++;
					}
				}
			}
			return minWindowLen == s.length() + 1 ? "" : s.substring(winLeft, winLeft + minWindowLen);
		}
	}
	
	static class Solution2 {
		// use size as 128 for standard ASCII encoded English letters
		public String minWindow(String s, String t) {
			int[] count = new int[128]; // standard ASCII size
			for (char c : t.toCharArray()) count[c]++;
			int needed = t.length(); // # chars needed
			int left = 0, right = 0, minLen = s.length() + 1, minStart = -1;
			while (right < s.length()) {
				// move right pointer
				char newChar = s.charAt(right++);
				if (count[newChar] > 0) needed--;
				count[newChar]--; // decrease the freq of this char in the counter array
				
				// keep shrinking the left window [left, right)
				while (needed == 0) {
					if (right - left < minLen) {
						minLen = right - left;
						minStart = left;
					}
					// move left pointer
					if (++count[s.charAt(left++)] > 0) needed++;
				}
			}
			return minStart == -1 ? "" : s.substring(minStart, minStart + minLen);
		}
	}

	class Solution3_Simple_Sliding_Window {
		/**
		 * SLiding window with window as left inclusive, right exclusive: [left, right).
		 * 
		 * Time: O(m + n) where m is the size of s, n is size of t
		 * Space: O(n) where n is size of t
		 * 
		 * @param s string s
		 * @param t string t
		 * @return min substring of s that contains all chars in t
		 */
		public String minWindow(String s, String t) {
			Map<Character, Integer> freq = new HashMap<>(); // char freq map for t
			for (int i = 0; i < t.length(); i++) {
				char c = t.charAt(i);
				freq.put(c, freq.getOrDefault(c, 0) + 1);
			}
	
			int l = 0, r = 0, need = freq.size(); // 'need' tracks required unique chars
			int winLen, minLen = s.length() + 1, minStart = 0, minEnd = 0;
	
			while (r < s.length()) {
				char cAdd = s.charAt(r++);
				if (freq.containsKey(cAdd)) {
					freq.put(cAdd, freq.get(cAdd) - 1);
					if (freq.get(cAdd) == 0) need--;
				}
	
				while (need == 0) {
					winLen = r - l;
					if (minLen > winLen) {
						minLen = winLen;
						minStart = l;
						minEnd = r;
					}
	
					char cRem = s.charAt(l++);
					if (freq.containsKey(cRem)) {
						freq.put(cRem, freq.get(cRem) + 1);
						if (freq.get(cRem) == 1) need++;
					}
				}
			}
	
			return minLen <= s.length() ? s.substring(minStart, minEnd) : "";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new Solution2().minWindow("a", "a"));
	}
}
