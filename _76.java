import java.util.HashMap;
import java.util.Map;

/**
 * 76. Minimum Window Substring.
 */
public class _76 {
	class Solution1 {
		public String minWindow(String s, String t) {
			Map<Character, Integer> freq = new HashMap<>(); // stores the chars and count for given String t
			for (int i = 0; i < t.length(); i++) {
				freq.put(t.charAt(i), freq.getOrDefault(t.charAt(i), 0) + 1);
			}
			
			int start = 0, end = Integer.MAX_VALUE, left = 0, right = 0, valid = 0, size = t.length();
			while (right < s.length()) { // window [left, right)
				char c = s.charAt(right);
				right++;
				if (freq.containsKey(c)) {
					freq.put(c, freq.get(c) - 1);
					if (freq.get(c) >= 0) valid++;
				}
				
				while (valid == size) {
					// satisfy requirement(include all elements in t), update minimum window
					if (right - left < end - start) { // [start, end)
						end = right;
						start = left;
					}
					c = s.charAt(left);
					left++;
					if (freq.containsKey(c)) {
						freq.put(c, freq.get(c) + 1);
						if (freq.get(c) > 0) valid--;
					}
				}
			}
			
			return end - start == Integer.MAX_VALUE ? "" : s.substring(start, end);
		}
	}
	
	static class Solution2 {
		static final int MAX_CHAR = 256;
		
		public String minWindow(String s, String t) {
			int[] count = new int[256]; // ASCII size counter array
			for (char c : t.toCharArray()) count[c]++;
			int counter = t.length(); // num of chars needed
			int left = 0, right = 0, minLen = s.length() + 1, minStart = -1;
			while (right < s.length()) {
				// move right pointer
				char newChar = s.charAt(right++);
				if (count[newChar] > 0) counter--;
				count[newChar]--; // decrease the freq of this char in the counter array
				
				// keep shrinking the left window [left, right)
				while (counter == 0) {
					if (right - left < minLen) {
						minLen = right - left;
						minStart = left;
					}
					// move left pointer
					if (++count[s.charAt(left++)] > 0) counter++;
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
