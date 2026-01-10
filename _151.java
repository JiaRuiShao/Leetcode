import java.util.Stack;

/**
 * 151. Reverse Words in a String
 */
public class _151 {
	// \\s+ matches one or more whitespace
	// split only by " " would result in multiple empty strings
	// without trim would also have empty strings when there's leading/trailing zeros
	class Solution1_Trim_With_Split {
		public String reverseWords(String s) {
			String[] words = s.trim().split("\\s+"); // had to trim empty spaces from both ends before split
			StringBuilder sb = new StringBuilder();
			String emptySpace = " ";
			for (int i = words.length - 1; i >= 0; i--) {
				sb.append(words[i]).append(emptySpace);
			}
			return sb.length() == 0 ? s: sb.deleteCharAt(sb.length() - 1).toString();
		}
	}

	// If asked to not use split() or StringBuilder & do in-place modification with O(1) auxiliary space
	class Solution2_Two_Pointers {
		public String reverseWords(String s) {
			char[] arr = s.toCharArray();
			int len = removeSpace(arr);
			reverse(arr, 0, len);
			reverseWord(arr, len);
			return new String(arr, 0, len);
		}

		// reverse char in range [lo..hi)
		private void reverse(char[] arr, int lo, int hi) {
			hi--;
			while (lo < hi) {
				char temp = arr[lo];
				arr[lo] = arr[hi];
				arr[hi] = temp;
				lo++;
				hi--;
			}
		}

		private int removeSpace(char[] arr) {
			// [0..i): valid chars
			// [i..j): invalid chars
			// [j, n-1]: not traversed
			int n = arr.length, i = 0, j = 0; 
		
			while (j < n) {
				// remove leading/trailing zero
				while (j < n && arr[j] == ' ') {
					j++;
				}

				// append space between words (not before first or after last)
				// i > 0 ensures we're not before the first word
				// j < n ensures there's a word coming (not just trailing spaces)
				if (i > 0 && j < n) {
					arr[i++] = ' ';
				}

				// move valid chars front
				while (j < n && arr[j] != ' ') {
					arr[i++] = arr[j++];
				}
			}

			return i;
		}

		private void reverseWord(char[] arr, int n) {
			int start = 0, end = 0;
			while (end < n) {
				while (end < n && arr[end] != ' ') {
					end++;
				}
				reverse(arr, start, end);
				end++;
				start = end;
			} 
		}
	}

	class Solution3_Stack {
		public String reverseWords(String s) {
			// if allow using trim(), leading & trailing spaces are removed
			s = s.trim();
			StringBuilder sb = new StringBuilder();
			Stack<Character> stk = new Stack<>();
			
			char prev = ' ';
			for (int i = s.length() - 1; i >= 0; i--) {
				char c = s.charAt(i);
				if (c != ' ') stk.push(c);
				else if (prev != ' ') {
					while (!stk.isEmpty()) {
						sb.append(stk.pop());
					}
					sb.append(' ');
				}
				prev = c;
			}
			while (!stk.isEmpty()) {
				sb.append(stk.pop());
			}
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		System.out.println(new _151().new Solution2_Two_Pointers().reverseWords("  hello world  "));
		System.out.println(new _151().new Solution1_Trim_With_Split().reverseWords("  hello world  "));
	}
}
