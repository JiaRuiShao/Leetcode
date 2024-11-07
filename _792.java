import java.util.ArrayList;
import java.util.List;

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

    /**
     * Improved Solution: Queue Bucket
     * Instead of processing each word individually, process all words simultaneously as we iterate through s.
     * Create an array of queues for each lowercase letter, each queue holds iterators (or indices) to track the progress of words waiting for that character.
     * Time: O(W + N + TL) where W is the number of word in words, N is the number of characters in s, TL is the total length of words, worst case it could be WL
     * Space: O(W) where W is words arr length, here O(W) is auxiliary space; total space would be O(W + TL) since the input words as String takes O(Total Length of Words) = O(TL) = O(WL) space
     */
    class Solution2_Queue {
        public int numMatchingSubseq(String s, String[] words) {
            // Array of queues for each lowercase letter, each bucket contains pair of (word, index) where index is index's position in this word
            List<Node>[] buckets = new ArrayList[26];
            for (int i = 0; i < 26; i++) {
                buckets[i] = new ArrayList<>();
            }
        
            // Initialize the buckets with the words
            for (String word : words) {
                char firstChar = word.charAt(0);
                buckets[firstChar - 'a'].add(new Node(word, 0));
            }
        
            int res = 0;
            // Iterate through the string s
            for (char c : s.toCharArray()) {
                // Get the current bucket and create a new list for iteration
                List<Node> currentBucket = buckets[c - 'a'];
                buckets[c - 'a'] = new ArrayList<>(); // Reset the bucket
        
                for (Node node : currentBucket) {
                    node.index++; // Move to the next character in the word
                    if (node.index == node.word.length()) {
                        // Word is fully matched
                        res++;
                    } else {
                        // Add the node to the bucket of the next character needed
                        char nextChar = node.word.charAt(node.index);
                        buckets[nextChar - 'a'].add(node);
                    }
                }
            }
        
            return res;
        }
        
        // Helper class to store the word and current index
        class Node {
            String word;
            int index;
        
            public Node(String word, int index) {
                this.word = word;
                this.index = index;
            }
        }        
    }
}
