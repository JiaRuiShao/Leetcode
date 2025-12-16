import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 648. Replace Words
 */
public class _648 {
    // Time: O(D + W × L²) where D is num of chars in dict; W is num of words; L is avg word len
    // Space: O(D + L)
    // Note space complexity for prefixes are O(L) and not O(WL) because the generated strings are not stored, 
    // so peak extra memory is O(L) -- they're not accumulated
    class Solution1_HashSet {
        private static final String SPACE = " ";
        public String replaceWords(List<String> dictionary, String sentence) {
            Set<String> dict = new HashSet<>(dictionary);
            StringBuilder replacedWords = new StringBuilder();
            for (String word : sentence.split("\\s")) {
                String replacedWord = word;
                for (int i = 1; i <= word.length(); i++) {
                    String prefix = word.substring(0, i); // [)
                    if (dict.contains(prefix)) {
                        replacedWord = prefix;
                        break;
                    }
                }
                replacedWords.append(replacedWord).append(SPACE);
            }
            if (replacedWords.length() > 0) {
                replacedWords.deleteCharAt(replacedWords.length() - 1);
            }
            return replacedWords.toString();
        }
    }

    // Time: O(D + WL) where D is num of chars in dict; W is num of words; L is avg word len
    // Space: O(D) with extra overhead (each char node has 26 children allocated)
    class Solution2_Trie {
        private static class TrieNode {
            TrieNode[] children = new TrieNode[26];
            boolean eow = false;
        }

        private TrieNode root;

        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.eow = true;
        }

        private void insertWords(List<String> dict) {
            for (String word : dict) {
                insert(word);
            }
        }

        // return prefix last char index in word if there's any
        private int hasPrefix(String word) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                if (curr.children[index] == null) {
                    return -1;
                }
                curr = curr.children[index];
                if (curr.eow) return i;
            }
            return -1; // never reach
        }

        public String replaceWords(List<String> dictionary, String sentence) {
            root = new TrieNode();
            insertWords(dictionary);
            StringBuilder replacedWords = new StringBuilder();
            for (String word : sentence.split("\\s+")) {
                String replaced = word;
                int index = hasPrefix(word);
                if (index != -1) replaced = word.substring(0, index + 1);
                replacedWords.append(replaced).append(" ");
            }
            return replacedWords.toString().trim();
        }
    }
}
