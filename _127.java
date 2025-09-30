import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 127. Word Ladder
 */
public class _127 {
    class Solution1_BFS {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Set<String> whitelist = new HashSet<>();
            for (String word : wordList) whitelist.add(word);
            if (!whitelist.contains(endWord)) return 0;

            Queue<String> q = new ArrayDeque<>();
            Set<String> visited = new HashSet<>();
            q.offer(beginWord);
            visited.add(beginWord);

            int transform = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                transform++;
                for (int i = 0; i < size; i++) {
                    String word = q.poll();
                    if (word.equals(endWord)) return transform;
                    for (String nextWord : getNextWords(word)) {
                        if (whitelist.contains(nextWord) && !visited.contains(nextWord)) {
                            q.offer(nextWord);
                            visited.add(nextWord);
                        }
                    }
                }
            }
            return 0;
        }

        // we can use either char[] or StringBuilder here
        private List<String> getNextWords(String word) {
            List<String> words = new ArrayList<>();
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char oldLetter = arr[i];
                for (char letter = 'a'; letter <= 'z'; letter++) {
                    if (letter == oldLetter) continue;
                    arr[i] = letter;
                    words.add(new String(arr));
                }
                arr[i] = oldLetter;
            }
            return words;
        }
    }

    class Solution1_BidirectionalBFS {

        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Set<String> begin = new HashSet<>();
            Set<String> end = new HashSet<>();
            Set<String> visited = new HashSet<>();
            Set<String> dict = new HashSet<>(wordList); // **Important**
    
            int step = 1;
            begin.add(beginWord);
            if (dict.contains(endWord)) end.add(endWord);
            visited.add(beginWord);
            visited.add(endWord);
    
            while (!begin.isEmpty() && !end.isEmpty()) {
                // improve the performance by always spreading from the smaller set
                if (begin.size() > end.size()) {
                    Set<String> temp = begin;
                    begin = end;
                    end = temp;
                }

                Set<String> nextBegin = new HashSet<>();
                for (String word : begin) {
                    char[] chars = word.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        char prev = chars[i]; // record the original char
                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c == prev) continue;
                            chars[i] = c;
                            String newWord = new String(chars);
                            if (end.contains(newWord)) return step + 1;
                            if (dict.contains(newWord) && !visited.contains(newWord)) {
                                nextBegin.add(newWord);
                                visited.add(newWord);
                            }
                        }
                        chars[i] = prev; // change the char back
                    }
                }
                step++;
                begin = nextBegin;
            }
    
            return 0;
        }
    }
}
