import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 1930. Unique Length-3 Palindromic Subsequences
 */
class _1930 {
    class Solution1_Array {
        // 1 - find first & last occurrence of unique chars
        // 2 - count unique chars between the first & last index
        public int countPalindromicSubsequence(String s) {
            // record first & last index for this letter in s
            final int ALPHABET_SIZE = 26;
            int[] first = new int[ALPHABET_SIZE];
            int[] last = new int[ALPHABET_SIZE];
            Arrays.fill(first, s.length()); // initialize first to s.length()
            for (int i = 0; i < s.length(); i++) {
                int c = s.charAt(i) - 'a';
                first[c] = Math.min(first[c], i);
                last[c] = i;
            }

            int count = 0;
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (last[i] <= first[i] + 1) { // minimum size is 26
                    continue;
                }
                Set<Character> inBetw = new HashSet<>();
                for (int j = first[i] + 1; j < last[i]; j++) {
                    inBetw.add(s.charAt(j));
                }
                count += inBetw.size();
            }
            return count;
        }
    }

    class Solution2_Map {
        public int countPalindromicSubsequence(String s) {
            Set<Character> letters = new HashSet<>();
            for (Character c: s.toCharArray()) {
                letters.add(c);
            }
            
            int ans = 0;
            for (Character letter : letters) {
                int first = -1; // first occurrence index
                int last = 0; // last occurrence index
                
                for (int k = 0; k < s.length(); k++) {
                    if (s.charAt(k) == letter) {
                        if (first == -1) {
                            first = k;
                        }
                        last = k;
                    }
                }
                
                Set<Character> between = new HashSet<>();
                for (int k = first + 1; k < last; k++) {
                    between.add(s.charAt(k));
                }
                ans += between.size();
            }
            
            return ans;
        }
    }
}