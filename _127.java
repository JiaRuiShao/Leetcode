import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class _127 {

    static class BFS {

        private void addNextWords(String word, Queue<String> words, Set<String> dict, Set<String> visited) {
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char prev = chars[i]; // record the original char

                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == prev) continue;
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dict.contains(newWord) && !visited.contains(newWord)) {
                        words.offer(newWord);
                        visited.add(newWord);
                    }
                }

                chars[i] = prev; // change the char back
            }
            
        }

        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Queue<String> words = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            Set<String> dict = new HashSet<>(wordList);
            int step = 1;

            words.offer(beginWord);
            while (!words.isEmpty()) {
                int sz = words.size();
                for (int i = 0; i < sz; i++) {
                    String word = words.poll();
                    if (word.equalsIgnoreCase(endWord)) return step;
                    addNextWords(word, words, dict, visited);
                }
                step++;
            }

            return 0;
        }
    }

    static class BidirectionalBFS {

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
                begin = end;
                end = nextBegin;
            }
    
            return 0;
        }
    }

    static class BidirectionalBFSImproved {

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

    public static void main(String[] args) {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");
        System.out.println(new BFS().ladderLength(beginWord, endWord, wordList));
        System.out.println(new BidirectionalBFS().ladderLength(beginWord, endWord, wordList));

        wordList = Arrays.asList("hot","dot","dog","lot","log");
        System.out.println(new BFS().ladderLength(beginWord, endWord, wordList));
        System.out.println(new BidirectionalBFS().ladderLength(beginWord, endWord, wordList));
    }
}
