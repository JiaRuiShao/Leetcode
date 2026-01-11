import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 792. Number of Matching Subsequences
 * 
 * - S0: BF O(mnk), O(1)
 * - S1: Prebuilt index map for s, binary search on index O(nklogm) O(m)
 * - S2: Bucket O(m+nk) O(nk) [PREFERRED]
 * - S3: Trie O(m+nk) O(nk)
 */
public class _792 {
    // Time: O(NW) where N is number of chars in s and W is number of word in words
    // Space: O(W)
    class Solution0_Brute_Force_TLE {
        public int numMatchingSubseq(String s, String[] words) {
            int n = s.length(), wordsLen = words.length;
            int matchingSubSeq = 0;
            int[] wordsIdx = new int[wordsLen];
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                for (int j = 0; j < wordsLen; j++) {
                    String word = words[j];
                    int wordIdx = wordsIdx[j];
                    if (wordIdx < word.length() && word.charAt(wordIdx) == c) {
                        if (++wordsIdx[j] == word.length()) {
                            matchingSubSeq++;
                        }
                    }
                }
            }
            return matchingSubSeq;
        }
    }
    
    class Solution1_Binary_Search_Array {
        /**
         * Process s to build an array of ArrayList with array index as char val, the following list as this char's occurrence in s.
         * Then process each word in words, use binary search to find the next occurrence of each character in the word within s, starting from the previous index + 1.
         * 
         * Time: O(N + M * log N) where N is length of s, M is the total characters in all words
         * Space: O(N)
         * Notice that the binary search's time complexity should be O(log k), where k is the number of occurrences of the searched character in s;
         * however, the worst case is that a word only contains one char, e.g. [aaaaaaaaaaaaaaaa], then k -> N
         */
        public int numMatchingSubseq(String s, String[] words) {
            // pre-process s to build a list of indexes of each char in s
            final int CHAR_MAX_SIZE = 26;
            final char a = 'a';
            List<Integer>[] charIndices = new ArrayList[CHAR_MAX_SIZE];
            for (int i = 0; i < CHAR_MAX_SIZE; i++) {
                charIndices[i] = new ArrayList<>();
            }
            for (int i = 0; i < s.length(); i++) {
                int c = s.charAt(i) - a;
                charIndices[c].add(i);
            }
    
            int matching = 0;
            for (String word : words) {
                char[] chars = word.toCharArray();
                int prevIndexInS = -1;
                boolean matches = true;
                for (int i = 0; i < chars.length; i++) {
                    int nextIndexInS = binarySearch(charIndices, chars[i] - a, ++prevIndexInS);
                    if (nextIndexInS == -1) {
                        matches = false;
                        break;
                    }
                    prevIndexInS = nextIndexInS;
                }
                matching += matches ? 1 : 0;
            }
    
            return matching;
        }
    
        // lower bound binary search
        private int binarySearch(List<Integer>[] charIndices, int c, int start) {
            List<Integer> indices = charIndices[c];
            int n = indices.size();
            int lo = 0, hi = n - 1, mid = 0;
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                if (indices.get(mid) >= start) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            return lo == n ? -1 : indices.get(lo);
        }
    }
    
    // Pre-process String s using a HashMap; only used when the chars in given String is encoded in Unicode
    class Solution2_Binary_Search_Map {
        public int numMatchingSubseq(String s, String[] words) {
            int matching = 0;
            Map<Character, List<Integer>> charIndices = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                charIndices.computeIfAbsent(s.charAt(i), k -> new ArrayList<>()).add(i);
            }
            
            for (String word : words) {
                char[] chars = word.toCharArray();
                int prevIndexInS = -1;
                boolean matches = true;
                for (int i = 0; i < chars.length; i++) {
                    int nextIndexInS = binarySearch(charIndices, chars[i], ++prevIndexInS);
                    if (nextIndexInS == -1) {
                        matches = false;
                        break;
                    }
                    prevIndexInS = nextIndexInS;
                }
                matching += matches ? 1 : 0;
            }
            return matching;
        }
        
        private int binarySearch(Map<Character, List<Integer>> charIndices, char c, int start) {
            List<Integer> indices = charIndices.get(c);
            // this char doesn't exist in s
            if (indices == null) {
                return -1;
            }
            
            int n = indices.size(), last = indices.get(n - 1);
            if (start > last) return -1;
            
            int lo = 0, hi = n - 1, mid = 0;
            while (lo <= hi) {
                mid = lo + (hi - lo) / 2;
                if (indices.get(mid) >= start) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            return lo == n ? -1 : indices.get(lo);
        }
    }

    /**
     * Instead of processing each word individually, process all words simultaneously as we iterate through s.
     * Create an array of queues for each lowercase letter, each queue holds indices to track the progress of words waiting for that character.
     * Time: O(N + M) where N is the number of characters in s, M is the total characters in all words
     * Space: O(W) where W is words arr length
     */
    class Solution3_Bucket_By_Next_Char {
        @SuppressWarnings("unchecked")
        public int numMatchingSubseq(String s, String[] words) {
            final int CHAR_MAX_SIZE = 26;
            final char a = 'a';
            // Array of queues for each lowercase letter, each bucket contains pair of (word, index) where index is index's position in this word
            List<Node>[] buckets = new ArrayList[CHAR_MAX_SIZE];
            for (int i = 0; i < CHAR_MAX_SIZE; i++) {
                buckets[i] = new ArrayList<>();
            }
        
            // Initialize the buckets with the words
            for (String word : words) {
                char firstChar = word.charAt(0);
                buckets[firstChar - a].add(new Node(word, 0));
            }
        
            int matching = 0;
            // Iterate through the string s
            for (char c : s.toCharArray()) {
                // Get the current bucket and create a new list for iteration
                List<Node> currentBucket = buckets[c - 'a'];
                buckets[c - a] = new ArrayList<>(); // **IMPORTANT: Reset the bucket**
        
                for (Node node : currentBucket) {
                    node.index++; // Move to the next character in the word
                    if (node.index == node.word.length()) {
                        // Word is fully matched
                        matching++;
                    } else {
                        // Add the node to the bucket of the next character needed
                        char nextChar = node.word.charAt(node.index);
                        buckets[nextChar - a].add(node);
                    }
                }
            }
        
            return matching;
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
