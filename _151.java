import java.util.Stack;

/**
 * 151. Reverse Words in a String.
 */
public class _151 {
	class Solution1_Stack {
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

	class Solution2_Two_Pointers {

		private static final char BLANK_SPACE = ' ';
    
		public String reverseWords(String s) {
			char[] words = s.toCharArray();

			// 1- reverse the entire arr
			swap(words, 0, words.length - 1);

			// 2- reverse the words
			reverseEachWord(words);

			// 3- remove dup white spaces
			return removeDupSpace(words);
		}

		private void swap(char[] arr, int left, int right) {
			while (left < right) {
				char tmp = arr[left];
				arr[left] = arr[right];
				arr[right] = tmp;
				left++;
				right--;
			}
		}

		private void reverseEachWord(char[] words) {
			int left = 0, right = 0, len = words.length;
			while (left < len) {
				while (left < len && words[left] == BLANK_SPACE) left++;
				right = left;
				while (right < len && words[right] != BLANK_SPACE) right++;
				if (left < len && right - 1 < len) swap(words, left, right - 1);
				left = right;
			}
		}

		private String removeDupSpace(char[] words) {
			int left = 0, right = 0, len = words.length;
			while (right < len) {
				while (right < len && words[right] == BLANK_SPACE) right++;
				if (right < len && left > 0) words[left++] = BLANK_SPACE;
				while (right < len && words[right] != BLANK_SPACE) {
					words[left++] = words[right++];
				}
			}
			return new String(words).substring(0, left);
		}
	}

	public static void main(String[] args) {
		System.out.println(new _151().new Solution2_Two_Pointers().reverseWords("  hello world  "));
	}
}
