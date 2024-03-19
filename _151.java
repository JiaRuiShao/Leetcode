/**
 * 151. Reverse Words in a String.
 */
public class _151 {
	public String reverseWords(String s) {
		if (s == null || s.length() == 0) return s;
		
		int i = 0, j = 0;
		char[] arr = s.toCharArray();
		
		reverse(arr, 0, arr.length - 1);
		
		while (i < arr.length && ++j < arr.length) {
			if (arr[i] == ' ') i++;
			if (arr[j] == ' ' && arr[j - 1] != ' ') {
				reverse(arr, i, j - 1);
				i = j + 1;
			}
		}
		
		if (i < s.length()) {
			reverse(arr, i, s.length() - 1);
		}
		
		int end = removeDupSpaces(arr);
		return new String(arr).substring(0, end);
	}
	
	private void reverse(char[] arr, int i, int j) {
		while (i < j) {
			char temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
			i++;
			j--;
		}
	}
	
	private int removeDupSpaces(char[] arr) {
		int i = 0;
		for (int j = 0; j < arr.length; j++) {
			if (arr[j] !=  ' ') {
				if (j > 0 && arr[j - 1] == ' ' && i != 0) { // start a new word, but no leading spaces
					arr[i] = ' ';
					i++;
				}
				arr[i] = arr[j];
				i++;
			}
		}
		return i;
	}

	class Solution {
		public String reverseWords(String s) {
			char[] arr = s.toCharArray();
			int len = arr.length;
			reverse(arr, 0, len - 1);
	
			int left = 0, right = 0;
			while (right < len) {
				while (right < len && arr[right] != ' ') {
					right++;
				}
				reverse(arr, left, right - 1);
				while (right < len && arr[right] == ' ') {
					right++;
				}
				left = right;
			}
			if (left + 1 < len) {
				reverse(arr, left, len - 1);
			}
			
			int end = removeDupSpace(arr);
			return new String(arr).substring(0, end);
		}
	
		private void reverse(char[] arr, int l, int r) {
			while (l < r) {
				char temp = arr[r];
				arr[r] = arr[l];
				arr[l] = temp;
				l++;
				r--;
			}
		}
	
		private int removeDupSpace(char[] arr) {
			int len = arr.length;
			int l = 0, r = 0;
			while (r < len) {
				while (r < len && arr[r] == ' ') {
					r++;
				}
				if (l > 0 && r < len) {
					arr[l++] = ' ';
				}
				while (r < len && arr[r] != ' ') {
					arr[l++] = arr[r++];
				}
			}
			return l;
		}
	}

	public static void main(String[] args) {
		System.out.println(new _151().new Solution().reverseWords("  hello world  "));
	}
}
