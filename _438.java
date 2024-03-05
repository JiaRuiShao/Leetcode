import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 438. Find All Anagrams in a String.
 */
public class _438 {
	class Solution1 {
		public List<Integer> findAnagrams(String s, String p) {
			// build a freq map for target p
			int size = p.length();
			Map<Character, Integer> freq = new HashMap<>();
			for (int i = 0; i < size; i++) {
				freq.put(p.charAt(i), freq.getOrDefault(p.charAt(i), 0) + 1);
			}
			
			// left & right pointers to construct a window that satisfy the requirement
			int left = 0, right = 0, valid = 0;
			List<Integer> anagramStartIdx = new ArrayList<>();
			while (right < s.length()) {
				// char adding into the window
				char c = s.charAt(right);
				right++; // update the upper bound
				
				// update the freq map and the valid count
				if (freq.containsKey(c)) {
					freq.put(c, freq.get(c) - 1);
					if (freq.get(c) >= 0) {
						valid++;
					}
				}
				
				// update the ans & freq map when window.size is target size
				// notice here we didn't use while loop while (valid == targetK)
				// cuz the requirement here is no other chars in the middle of the substring
				if (right - left == size) {
					// requirement met
					if (valid == size) {
						anagramStartIdx.add(left);
					}
					
					// char popping out of the window
					c = s.charAt(left);
					left++; // update the lower bound
					
					// update the freq map and the valid count
					if (freq.containsKey(c)) {
						freq.put(c, freq.get(c) + 1);
						if (freq.get(c) > 0) {
							valid--;
						}
					}
				}
			}
			return anagramStartIdx;
		}
	}
	
	class Solution2 {
		
		static final int MAX_CHAR = 256;
		public List<Integer> findAnagrams(String s, String p) {
			List<Integer> res = new ArrayList<>(s.length());
			int[] count = new int[MAX_CHAR]; // ASCII size counter array
			for (char c : p.toCharArray()) count[c]++;
			int counter = p.length(); // num of chars needed
			int left = 0, right = 0;
			while (right < s.length()) {
				// move right pointer
				char newChar = s.charAt(right++);
				if (count[newChar] > 0) counter--; // decrease counter if char at right is one of the char in s1
				count[newChar]--; // decrease the freq of this char in the counter array
				
				// keep shrinking the left window [left, right) if the window size >= s1 size
				while (right - left >= p.length()) {
					if (counter == 0) res.add(left);
					// move left pointer
					if (++count[s.charAt(left++)] > 0) counter++;
				}
			}
			return res;
		}
	}

	class Solution3 {
		public List<Integer> findAnagrams(String s, String p) {
			return findPAnagramStartIdxInS(s, p);
		}
	
		private List<Integer> findPAnagramStartIdxInS(String s, String p) {
			char[] freq = new char[256];
			int sizeP = p.length();
			for (int i = 0; i < sizeP; i++) {
				freq[p.charAt(i)]++;
			}
	
			int l = 0, r = 0, need = sizeP;
			List<Integer> idx = new ArrayList<>();
			while (r < s.length()) {
				char add = s.charAt(r++);
				if (freq[add] > 0) {
					need--;
				}
				freq[add]--;
	
				if (r - l == sizeP) {
					if (need == 0) {
						idx.add(l);
					}
					char rem = s.charAt(l++);
					freq[rem]++;
					if (freq[rem] > 0) {
						need++;
					}
				}
			}
			return idx;
		}
	}
}
