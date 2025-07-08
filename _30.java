import java.util.*;

/**
 * 30. Substring with Concatenation of All Words
 */
public class _30 {
    // credit: https://leetcode.com/problems/substring-with-concatenation-of-all-words/solutions/1753357/clear-solution-easy-to-understand-with-diagrams-o-n-x-w-approach
    // Time: O(wordLen · (n/wordLen * wordLen)) = O(n*wordLen)
    class Solution1_Sliding_Window_Reuse_Freq_Map {
        // words are of the same length, seen them as one 'char'
        public List<Integer> findSubstring(String s, String[] words) {
            List<Integer> permutationIndices = new ArrayList<>();
            if (s == null || words == null || words.length == 0 || words[0].length() == 0) return permutationIndices;
    
            int n = s.length(), winLen = 0, wordLen = words[0].length();
            Map<String, Integer> freq = new HashMap<>();
            for (String word : words) {
                freq.put(word, freq.getOrDefault(word, 0) + 1);
                winLen += word.length();
            }
            
            for (int i = 0; i < wordLen; i++) {
                int left = i, right = i, required = words.length;
                while (right + wordLen <= n) {
                    String add = s.substring(right, right + wordLen);
                    right += wordLen;
                    if (freq.containsKey(add)) {
                        if (freq.get(add) > 0) required--;
                        freq.put(add, freq.get(add) - 1);
                    }
    
                    if (right - left == winLen) {
                        if (required == 0) {
                            permutationIndices.add(left);
                        }
                        String rem = s.substring(left, left + wordLen);
                        left += wordLen;
                        if (freq.containsKey(rem)) {
                            freq.put(rem, freq.get(rem) + 1);
                            if (freq.get(rem) > 0) required++;
                        }
                    }
                }
                // needed to maintain the freq map
                while (left + wordLen <= n) {
                    String word = s.substring(left, left + wordLen);
                    left += wordLen;
                    if (freq.containsKey(word)) {
                        freq.put(word, freq.get(word) + 1);
                        if (freq.get(word) > 0) required++;
                    }
                }
                System.out.printf("Current Idx: %d; required: %d; freqMap: %s%n", i, required, freq);
            }
            // permutationIndices.sort(Comparator.naturalOrder());
            return permutationIndices;
        }
    }

    class Solution2_Sliding_Window {
        public List<Integer> findSubstring(String s, String[] words) {
            List<Integer> permutationIndices = new ArrayList<>();
            if (s == null || words == null || words.length == 0 || words[0].length() == 0) return permutationIndices;

            int n = s.length(), winLen = 0, wordLen = words[0].length();
            Map<String, Integer> freq = new HashMap<>();
            for (String word : words) {
                freq.put(word, freq.getOrDefault(word, 0) + 1);
                winLen += word.length();
            }

            for (int i = 0; i < wordLen; i++) {
                int left = i, right = i, required = words.length;;
                Map<String, Integer> clone = new HashMap<>(freq);
                while (right + wordLen <= n) {
                    String add = s.substring(right, right + wordLen);
                    right += wordLen;
                    if (clone.containsKey(add)) {
                        if (clone.get(add) > 0) required--;
                        clone.put(add, clone.get(add) - 1);
                    }

                    if (right - left == winLen) {
                        if (required == 0) {
                            permutationIndices.add(left);
                        }
                        String rem = s.substring(left, left + wordLen);
                        left += wordLen;
                        if (clone.containsKey(rem)) {
                            clone.put(rem, clone.get(rem) + 1);
                            if (clone.get(rem) > 0) required++;
                        }
                    }
                }
            }

            return permutationIndices;
        }
    }

    public static void main(String[] args) {
//        Solution1_Sliding_Window slidingWindow = new _30().new Solution1_Sliding_Window();
        Solution2_Sliding_Window slidingWindow = new _30().new Solution2_Sliding_Window();
        // System.out.println(slidingWindow.findSubstring("lingmindraboofooowingdingbarrwingmonkeypoundcake", new String[]{"fooo","barr","wing","ding","wing"})); // [13]
        System.out.println(slidingWindow.findSubstring("aaaaaaaaaaaaaa", new String[]{"aa","aa"})); // [0,1,2,3,4,5,6,7,8,9,10]
    }
}
