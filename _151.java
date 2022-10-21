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
}
