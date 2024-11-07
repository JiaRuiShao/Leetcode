import java.util.ArrayList;

/**
 * 792. Number of Matching Subsequences.
 */
public class _792 {
    class Solution1_Binary_Search {
        /**
         * Process s to build an array of ArrayList with array index as char val, the following list as this char's occurrence in s.
         * Then process each word in words, use binary search to find the next occurrence of each character in the word within s, starting from the current index j.
         * 
         * Time: O(N + W * L * log N) where N is length of s, W is words size, L is the worst word length.
         * Notice that the binary search's time complexity should be O(log k), where k is the number of occurrences of the searched character in s;
         * however, the worst case is that a word only contains one char, e.g. [aaaaaaaaaaaaaaaa], then k -> N
         * 
         * @param s string to be compared
         * @param words given String arr
         * @return number of words who are considered to be subsequence of s
         */
        public int numMatchingSubseq(String s, String[] words) {
            // pre-process s to build a list of indexes of each char in s
            ArrayList<Integer>[] charToIndexes = new ArrayList[256];
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (charToIndexes[c] == null) {
                    charToIndexes[c] = new ArrayList<>();
                }
                charToIndexes[c].add(i);
            }
    
            int res = 0;
            for (String word : words) {
                // pointer i on the string word
                int i = 0;
                // pointer j on the string s
                int j = 0;
                // now determine if word is a subsequence of s
                // use charToIndexes to find the index of each character in word within s
                while (i < word.length()) {
                    char c = word.charAt(i);
                    // s does not contain the character word[i] at all
                    if (charToIndexes[c] == null) {
                        break;
                    }
                    // binary search for the smallest index greater than or equal to j
                    // search for the smallest index equal to word[i] in s[j..]
                    int pos = leftBoundBS(charToIndexes[c], j);
                    if (pos == charToIndexes[c].size()) {
                        break;
                    }
                    j = charToIndexes[c].get(pos);
                    // if found, i.e., word[i] == s[j], continue to match the next one
                    j++;
                    i++;
                }
                // if word is fully matched, then it is a subsequence of s
                if (i == word.length()) {
                    res++;
                }
            }
    
            return res;
        }
    
        // binary search for the left boundary
        int leftBoundBS(ArrayList<Integer> arr, int target) {
            int left = 0, right = arr.size();
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (target > arr.get(mid)) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        }
    }

    class Solution2_Queue {

    }
}
