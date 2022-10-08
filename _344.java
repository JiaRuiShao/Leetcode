/**
 * 344. Reverse String.
 * Write a function that reverses a string. The input string is given as an array of characters s.
 * You must do this by modifying the input array in-place with O(1) extra memory.
 */
public class _344 {
	class Solution_Left_Right_Pointers {
		public void reverseString(char[] s) {
			int l = 0, r = s.length - 1;
			while (l <= r) {
				swap(s, l, r);
				l++;
				r--;
			}
		}
		
		private void swap(char[] s, int l, int r) {
			char tmp = s[l];
			s[l] = s[r];
			s[r] = tmp;
		}
	}
}
