import java.util.Stack;

/**
 * 151. Reverse Words in a String
 */
public class _151 {
	
	class Solution1_Regex_With_StringBuilder {
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
		private static final char EMPTY_SPACE = ' ';
		public String reverseWords(String s) {
			char[] arr = s.toCharArray();
			int end = removeDupSpaces(arr); // words are in range [0, end)
			reverse(arr, 0, end);
			reverseWord(arr, end);
			return new String(arr, 0, end); // String​(char[] value, int offset, int length)
		}
	
		private int removeDupSpaces(char[] arr) { // [0, left) valid chars
			int left = 0, right = 0;
			while (right < arr.length) {
				while (right < arr.length && arr[right] == EMPTY_SPACE) right++;
				while (right < arr.length && arr[right] != EMPTY_SPACE) {
					arr[left++] = arr[right++];
				}
				while (right < arr.length && arr[right] == EMPTY_SPACE) right++;
				if (right < arr.length) arr[left++] = EMPTY_SPACE;
			}
			return left;
		}
	
		private void reverse(char[] arr, int start, int end) {
			int l = start, r = end - 1;
			while (l < r) {
				char tmp = arr[l];
				arr[l] = arr[r];
				arr[r] = tmp;
				l++;
				r--;
			}
		}
	
		private void reverseWord(char[] arr, int end) {
			int l = 0, r = 0;
			for (; r < end; r++) {
				if (arr[r] != EMPTY_SPACE) continue;
				reverse(arr, l, r);
				l = r + 1;
			}
			reverse(arr, l, end);
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
		System.out.println(new _151().new Solution3_Regex_With_StringBuilder().reverseWords("  hello world  "));
	}
}
