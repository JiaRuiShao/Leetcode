import java.util.Set;

/**
 * 345. Reverse Vowels of a String
 */
public class _345 {
    class Solution1_Set {
        public String reverseVowels(String s) {
            Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
            char[] arr = s.toCharArray();
            int lo = 0, hi = arr.length - 1;
            while (lo < hi) {
                while (lo < hi && !vowels.contains(Character.toLowerCase(arr[lo]))) {
                    lo++;
                }
                while (lo < hi && !vowels.contains(Character.toLowerCase(arr[hi]))) {
                    hi--;
                }
                if (lo < hi) {
                    char temp = arr[lo];
                    arr[lo] = arr[hi];
                    arr[hi] = temp;
                    lo++;
                    hi--;
                }
            }
            return new String(arr);
        }
    }
}
