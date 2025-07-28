import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 17. Letter Combinations of a Phone Number
 */
class _17 {
    private static void dfs(String[] allLetters, String digits, int currIdx, char[] currCombination, List<String> combinations) {
        // base case
        if (currIdx == digits.length()) {
            StringBuilder sb = new StringBuilder();
            for (char c : currCombination) { // O(n)
                sb.append(c);
            }
            combinations.add(sb.toString());
            // better performance: pass the char arr into the String's initialization parameter
            // or combinations.add(new String(currCombination));
            return;
        }

        // recursive rule
        String letters = allLetters[digits.charAt(currIdx) - '0']; // **Here we must minus the '0' to cast the result into int from String type**
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            currCombination[currIdx] = c;
            dfs(allLetters, digits, currIdx + 1, currCombination, combinations);
            // backtrack 
            currCombination[currIdx] = '\0';
        }
    }

    /**
     * Find Combinations using DFS.
     * Time: O(n*4^n)
     * Space: O(4^n + 2n)
     * 
     * @param digits input digits as String
     * @return a list of possible number combinations
     */
    static List<String> findCombinationsDFS(String digits) {
        // String to store the letter combinations for this stackframe
        char[] currCombination = new char[digits.length()];
        // List<String> to store the combination results
        List<String> combinations = new LinkedList<>();
        
        // corner cases
        if (digits == null || digits.length() == 0) return combinations;

        // set up possible letters for each input digit(0 - 9)
        String[] allLetters = new String[]{" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        // recursive function to do DFS on all the letters
        dfs(allLetters, digits, 0, currCombination, combinations);

        return combinations;
    }

    /**
     * Find letter combinations using BFS (concise version).
     * Time: O(4^n)
     * Space: O(2*4^n)
     * 
     * @param digits the input digits
     * @return a list of letter combination Strings
     */
    static List<String> findCombinationsBFSConcise(String digits) {
        // a list of String to save the prev results of letter combinations
        List<String> combinations = new LinkedList<>();
        // letters matched for the digits(0 - 9)
        String[] allLetters = new String[]{" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        
        // corner case
        if (digits == null || digits.length() == 0) return combinations;

        // BFS
        combinations.add(""); // [""] - ["" + "a"]
        for (char c : digits.toCharArray()) { // O(n)
            String letters = allLetters[c - '0'];
            List<String> temp = new LinkedList<>();
            // append each letter to the previous formed combinations & save it in the temp
            for (String prev : combinations) { // O(4^(n-1))
                for (int i = 0; i < letters.length(); i++) { // O(4)
                    temp.add(prev + letters.charAt(i));
                }
            }
            // update the previous combinations to current combinations
            combinations = temp;
        }

        return combinations;
    }

    // Time: O(n*3^n) to O(n*4^n)
    // Space: O(n)
    class Solution1_Backtrack_Combination_ElemUsedOnce_NoDedup {
        // combination -- elem used once -- no dedup needed
        public List<String> letterCombinations(String digits) {
            List<String> letters = new ArrayList<>();
            backtrack(digits, letters, new StringBuilder(), 0);
            return letters;
        }

        private void backtrack(String s, List<String> res, StringBuilder selected, int idx) {
            int n = s.length();
            if (idx == n) {
                if (selected.length() == n && n > 0) res.add(selected.toString());
                return;
            }
            for (char letter : getMappedLetters(s.charAt(idx))) {
                selected.append(letter);
                backtrack(s, res, selected, idx + 1);
                selected.deleteCharAt(selected.length() - 1);
            }
        }

        private List<Character> getMappedLetters(char num) {
            List<Character> letters = new ArrayList<>();
            switch (num) {
                case '2': {
                    letters = List.of('a', 'b', 'c');
                    break;
                }
                case '3': {
                    letters = List.of('d', 'e', 'f');
                    break;
                }
                case '4': {
                    letters = List.of('g', 'h', 'i');
                    break;
                }
                case '5': {
                    letters = List.of('j', 'k', 'l');
                    break;
                }
                case '6': {
                    letters = List.of('m', 'n', 'o');
                    break;
                }
                case '7': {
                    letters = List.of('p', 'q', 'r', 's');
                    break;
                }
                case '8': {
                    letters = List.of('t', 'u', 'v');
                    break;
                }
                case '9': {
                    letters = List.of('w', 'x', 'y', 'z');
                    break;
                }
                default: {
                }
            }
            return letters;
        }
    }

    class Solution2_Iterative_Nested_For_Loop {
        public List<String> letterCombinations(String digits) {
            List<String> letters = new ArrayList<>();
            String[] digitToLetter = {" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
            letters.add("");
            for (int i = 0; i < digits.length(); i++) {
                List<String> next = new ArrayList<>();
                int sz = letters.size();
                for (int j = 0; j < sz; j++) {
                    String prev = letters.get(j);
                    String lettersForCurrDigit = digitToLetter[digits.charAt(i) - '0'];
                    for (int k = 0; k < lettersForCurrDigit.length(); k++) {
                        next.add(prev + lettersForCurrDigit.charAt(k));
                    }
                }
                letters = next;
                next = null;
            }
            return letters;
        }
    }

    public static void main(String[] args) {
        // tests
        String[] inputs = new String[]{"", "1", "23", "69"};
        int counter;
        for (String input : inputs) {
            System.out.println("=== Test Results ===");
            counter = 0;
            /* for (String s : findCombinationsDFS(input)) {
                System.out.println(s);
                counter++;
            } */

            for (String s : new _17().new Solution1_Backtrack_Combination_ElemUsedOnce_NoDedup().letterCombinations(input)) {
                System.out.println(s);
                counter++;
            }
            System.out.printf("There are %d results for this test input.%n", counter);
        }

    }
}