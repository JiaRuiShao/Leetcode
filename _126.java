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

    class Solution2_BFS_DFS_LevelMap {
        List<List<String>> ans;
        HashMap<String, Integer> map;
        String beginW;

        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            ans = new ArrayList<>();
            HashSet<String> set = new HashSet<>(wordList);
            if (!set.contains(endWord)) return ans;

            beginW = beginWord;
            map = new HashMap<>();
            map.put(beginWord, 0);

            Queue<String> q = new LinkedList<>();
            q.offer(beginWord);
            HashSet<String> vis = new HashSet<>();
            vis.add(beginWord);

            int level = 0;
            boolean found = false;

            // BFS to build shortest distance map
            while (!q.isEmpty()) {
                int n = q.size();
                level++;
                for (int i = 0; i < n; i++) {
                    String word = q.poll();
                    if (word.equals(endWord)) found = true;
                    if (found) continue;

                    char[] arr = word.toCharArray();
                    for (int j = 0; j < word.length(); j++) {
                        char ch = arr[j];
                        for (int k = 'a'; k <= 'z'; k++) {
                            arr[j] = (char) k;
                            String newWord = new String(arr);
                            if (set.contains(newWord) && !vis.contains(newWord)) {
                                q.offer(newWord);
                                vis.add(newWord);
                                map.put(newWord, level);
                            }
                        }
                        arr[j] = ch;
                    }
                }
                if (found) break;
            }

            // DFS to reconstruct all shortest paths
            List<String> path = new ArrayList<>();
            path.add(endWord);
            dfs(path, endWord);

            return ans;
        }

        private void dfs(List<String> path, String word) {
            if (word.equals(beginW)) {
                List<String> copy = new ArrayList<>(path);
                Collections.reverse(copy);
                ans.add(copy);
                return;
            }

            char[] arr = word.toCharArray();
            for (int j = 0; j < word.length(); j++) {
                char ch = arr[j];
                for (int k = 'a'; k <= 'z'; k++) {
                    arr[j] = (char) k;
                    String newWord = new String(arr);
                    if (map.containsKey(newWord) && map.get(newWord) == map.get(word) - 1) {
                        path.add(newWord);
                        dfs(path, newWord);
                        path.remove(path.size() - 1); // backtrack
                    }
                }
                arr[j] = ch;
            }
        }
    }

    public static void main(String[] args) {
        _126.Solution solution = new _126.Solution();
        solution.findLadders("red", "tax", List.of("ted","tex","red","tax","tad","den","rex","pee"));
    }
}
