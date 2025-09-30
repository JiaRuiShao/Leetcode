import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 126. Word Ladder II
 */
public class _126 {
    class Solution0_BFS_HelperClass_TLE {
        class WordParent {
            String word;
            WordParent parent;
            public WordParent(String word, WordParent parent) {
                this.word = word;
                this.parent = parent;
            }
        }

        // why do we need self-referenced parent field?
        // with a Map of <word, parent>, we're not able to record multiple parents if there're any
        // red -> tex -> tex -> tax
        // red -> rex -> tex -> tax
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            Set<String> whitelist = new HashSet<>();
            for (String word : wordList) {
                whitelist.add(word);
            }
            if (beginWord == null || beginWord.length() == 0 || !whitelist.contains(endWord)) return List.of();
            Set<String> visited = new HashSet<>();
            Deque<WordParent> q = new ArrayDeque<>();
            List<List<String>> minSeqWords = new ArrayList<>();
            q.offer(new WordParent(beginWord, null));
            visited.add(beginWord);

            boolean found = false;
            while (!q.isEmpty() && !found) {
                int size = q.size();
                Set<String> levelVisited = new HashSet<>();
                for (int i = 0; i < size; i++) {
                    WordParent wordPair = q.poll();
                    String word = wordPair.word;
                    if (word.equals(endWord)) {
                        found = true;
                        minSeqWords.add(reconstruct(wordPair));
                        continue;
                    }
                    for (String next : generateNextSeq(word)) {
                        if (whitelist.contains(next) && !visited.contains(next)) {
                            q.offer(new WordParent(next, wordPair));
                            levelVisited.add(next);
                        }
                    }
                }
                visited.addAll(levelVisited);
            }
            return minSeqWords;
        }

        private List<String> reconstruct(WordParent node) {
            LinkedList<String> path = new LinkedList<>();
            for (WordParent p = node; p != null; p = p.parent) {
                path.addFirst(p.word);
            }
            return path;
        }

        private List<String> generateNextSeq(String word) {
            List<String> nextSeq = new ArrayList<>();
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char old = arr[i];
                for (char letter = 'a'; letter < 'z'; letter++) {
                    if (letter == old) continue;
                    arr[i] = letter;
                    nextSeq.add(new String(arr));
                }
                arr[i] = old;
            }
            return nextSeq;
        }
    }

    class Solution0_BFS_ParentMap_TLE {
        // use whitelist to function as visited set, remove the level visited words from whitelist
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            Set<String> whitelist = new HashSet<>(wordList);
            if (beginWord == null || beginWord.length() == 0 || !whitelist.contains(endWord)) return List.of();
            Map<String, Set<String>> parents = new HashMap<>();
            Deque<String> q = new ArrayDeque<>();
            List<List<String>> minSeqWords = new ArrayList<>();
            q.offer(beginWord);
            whitelist.remove(beginWord);

            boolean found = false;
            while (!q.isEmpty() && !found) {
                int size = q.size();
                Set<String> levelVisited = new HashSet<>();
                for (int i = 0; i < size; i++) {
                    String word = q.poll();
                    if (word.equals(endWord)) {
                        found = true;
                        continue;
                    }
                    for (String next : generateNextSeq(word)) {
                        if (whitelist.contains(next)) {
                            q.offer(next);
                            levelVisited.add(next);
                            parents.computeIfAbsent(next, k -> new HashSet<>()).add(word);
                        }
                    }
                }
                whitelist.removeAll(levelVisited);
            }
            List<String> path = new LinkedList<>();
            path.add(endWord);
            reconstruct(endWord, beginWord, parents, path, minSeqWords);
            return minSeqWords;
        }

        private void reconstruct(String word, String end, Map<String, Set<String>> parents, List<String> path, List<List<String>> res) {
            if (word.equals(end)) {
                res.add(new ArrayList<>(path));
                return;
            }
            if (parents.get(word) == null) return;
            for (String parent : parents.get(word)) {
                path.add(0, parent);
                reconstruct(parent, end, parents, path, res);
                path.remove(0);
            }
        }

        private List<String> generateNextSeq(String word) {
            List<String> nextSeq = new ArrayList<>();
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char old = arr[i];
                for (char letter = 'a'; letter <= 'z'; letter++) {
                    if (letter == old) continue;
                    arr[i] = letter;
                    nextSeq.add(new String(arr));
                }
                arr[i] = old;
            }
            return nextSeq;
        }
    }

    class Solution1_BFS_Dedup {
        // whitelist is used to track both: if the word has been traversed in the previous level && if the word is in given wordList
        // enQueue set is to track ensure one word can only be traversed once; we can use a Set<String> for each level instead of using a Queue & Set<String> enQueue here
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            Set<String> whitelist = new HashSet<>(wordList);
            if (beginWord == null || beginWord.length() == 0 || !whitelist.contains(endWord)) return List.of();
            Map<String, List<String>> parents = new HashMap<>();
            Deque<String> q = new ArrayDeque<>();
            List<List<String>> minSeqWords = new ArrayList<>();
            q.offer(beginWord);
            whitelist.remove(beginWord);

            boolean found = false;
            while (!q.isEmpty() && !found) {
                int size = q.size();
                Set<String> levelVisited = new HashSet<>();
                for (int i = 0; i < size; i++) {
                    String word = q.poll();
                    if (word.equals(endWord)) {
                        found = true;
                        continue;
                    }
                    for (String next : generateNextSeq(word)) {
                        if (whitelist.contains(next)) {
                            if (!levelVisited.contains(next)) q.offer(next);
                            levelVisited.add(next);
                            parents.computeIfAbsent(next, k -> new ArrayList<>()).add(word);
                        }
                    }
                }
                whitelist.removeAll(levelVisited);
            }
            List<String> path = new LinkedList<>();
            path.add(endWord);
            reconstruct(endWord, beginWord, parents, path, minSeqWords);
            return minSeqWords;
        }

        private void reconstruct(String word, String end, Map<String, List<String>> parents, List<String> path, List<List<String>> res) {
            if (word.equals(end)) {
                res.add(new ArrayList<>(path));
                return;
            }
            if (parents.get(word) == null) return;
            for (String parent : parents.get(word)) {
                path.add(0, parent);
                reconstruct(parent, end, parents, path, res);
                path.remove(0);
            }
        }

        private List<String> generateNextSeq(String word) {
            List<String> nextSeq = new ArrayList<>();
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char old = arr[i];
                for (char letter = 'a'; letter <= 'z'; letter++) {
                    if (letter == old) continue;
                    arr[i] = letter;
                    nextSeq.add(new String(arr));
                }
                arr[i] = old;
            }
            return nextSeq;
        }
    }

    class Solution1_BFS_Dedup_Set {
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            Set<String> dict = new HashSet<>(wordList);
            List<List<String>> res = new ArrayList<>();
            if (!dict.contains(endWord)) return res;

            Map<String, List<String>> graph = new HashMap<>();
            Set<String> currentLevel = new HashSet<>();
            currentLevel.add(beginWord);

            boolean found = false;
            Set<String> visited = new HashSet<>();

            while (!currentLevel.isEmpty() && !found) {
                Set<String> nextLevel = new HashSet<>();
                visited.addAll(currentLevel);

                for (String word : currentLevel) {
                    char[] chs = word.toCharArray();

                    for (int i = 0; i < chs.length; i++) {
                        char old = chs[i];

                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c == old) continue;
                            chs[i] = c;
                            String nextWord = new String(chs);

                            if (dict.contains(nextWord) && !visited.contains(nextWord)) {
                                graph.computeIfAbsent(nextWord, k -> new ArrayList<>()).add(word);
                                nextLevel.add(nextWord);

                                if (nextWord.equals(endWord)) found = true;
                            }
                        }

                        chs[i] = old;
                    }
                }

                currentLevel = nextLevel;
            }

            if (found) {
                List<String> path = new ArrayList<>();
                path.add(endWord);
                dfs(endWord, beginWord, graph, path, res);
            }

            return res;
        }

        private void dfs(String word, String beginWord, Map<String, List<String>> graph, List<String> path, List<List<String>> res) {
            if (word.equals(beginWord)) {
                List<String> validPath = new ArrayList<>(path);
                Collections.reverse(validPath);
                res.add(validPath);
                return;
            }

            if (!graph.containsKey(word)) return;

            for (String parent : graph.get(word)) {
                path.add(parent);
                dfs(parent, beginWord, graph, path, res);
                path.remove(path.size() - 1);
            }
        }
    }

    // easiest solution to implement -- BFS to track the valid words and their levels
    // backtrack from end word and check from built map for valid path
    // no dedup needed like in parents map
    // say N is word len, L is num of word in wordList, K is num of valid path
    // Time: O(L*26N+K*L*26N) = O(KLN)
    // Space: O(NL)
    class Solution2_BFS_LevelMap_Backtrack {
        // aa -> ab -> bb
        // aa -> ba -> bb
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            Set<String> whitelist = new HashSet<>(wordList);
            if (!whitelist.contains(endWord)) {
                return new ArrayList<>();
            }

            Map<String, Integer> wordToLevel = new HashMap<>();
            bfs(beginWord, endWord, whitelist, wordToLevel);
            
            List<List<String>> transformSeq = new ArrayList<>();
            List<String> path = new LinkedList<>();
            path.add(0, endWord);
            backtrack(endWord, beginWord, wordToLevel, path, transformSeq);
            return transformSeq;
        }

        private void backtrack(String start, String end, Map<String, Integer> map, List<String> path, List<List<String>> res) {
            if (start.equals(end)) {
                res.add(new ArrayList<>(path));
                return;
            }
            int currLevel = map.getOrDefault(start, -1);
            for (String nextWord : findNextWords(start)) {
                int nextWordLevel = map.getOrDefault(nextWord, -1);
                if (nextWordLevel == currLevel - 1) {
                    path.add(0, nextWord);
                    backtrack(nextWord, end, map, path, res);
                    path.remove(0);
                }
            }
        }

        private void bfs(String begin, String end, Set<String> whitelist, Map<String, Integer> wordToLevel) {
            Queue<String> q = new ArrayDeque<>();
            int level = 0;
            q.offer(begin);
            whitelist.remove(begin);
            wordToLevel.put(begin, level);

            while (!q.isEmpty()) {
                int size = q.size();
                level++;
                for (int i = 0; i < size; i++) {
                    String word = q.poll();
                    if (word.equals(end)) {
                        return;
                    }
                    for (String nextWord : findNextWords(word)) {
                        if (whitelist.contains(nextWord)) {
                            q.offer(nextWord);
                            whitelist.remove(nextWord);
                            wordToLevel.putIfAbsent(nextWord, level);
                        }
                    }
                }
            }
        }

        private List<String> findNextWords(String word) {
            List<String> words = new ArrayList<>();
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char old = arr[i];
                for (char newC = 'a'; newC <= 'z'; newC++) {
                    if (newC == old) {
                        continue;
                    }
                    arr[i] = newC;
                    words.add(new String(arr));
                }
                arr[i] = old;
            }
            return words;
        }
    }

    public static void main(String[] args) {
        // _126.Solution solution = new _126.Solution();
        // solution.findLadders("red", "tax", List.of("ted","tex","red","tax","tad","den","rex","pee"));
    }
}
