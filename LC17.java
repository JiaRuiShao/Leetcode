import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* Problem:

Given a digit string, return all possible letter combinations that the number could represent.
A mapping of digit to letters (just like on the telephone buttons) is given below. 

Input:Digit string "23"
Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].

Combination
*/

class LC17 {

    /**
     * A private recursive DFS helper class.
     * 
     * @param allLetters
     * @param digits
     * @param currIdx
     * @param currCombination
     * @param combinations
     */
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
        String letters = allLetters[digits.charAt(currIdx) - '0']; // **Here we must minus the '0' to cast the result into int from String type
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
     * Find letter combinations using BFS.
     * Time: O(2*4^n)
     * Space: O(3*4^n)
     * 
     * @param digits the input digits
     * @return a list of letter combination Strings
     */
    static List<String> findCombinationsBFS(String digits) {
        // a list of String to save the final results of letter combinations
        List<String> combinations = new LinkedList<>();
        // a list of previous formed letters char arr
        Queue<String> prevCombinations = new LinkedList<String>();
        Queue<String> currCombinations = new LinkedList<String>();
        // letters matched for the digits(0 - 9)
        String[] allLetters = new String[]{" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        
        // corner case
        if (digits == null || digits.length() == 0) return combinations;

        // BFS
        prevCombinations.offer(""); // [""] - ["" + "a"]
        for (char c : digits.toCharArray()) { // O(n)
            String letters = allLetters[c - '0'];
            // append each letter to the previous formed combinations
            while (!prevCombinations.isEmpty()) { // O(4^(n-1))
                String prev = prevCombinations.poll();
                for (int i = 0; i < letters.length(); i++) { // O(4)
                    currCombinations.offer(prev + letters.substring(i, i + 1)); // [)
                }
                // currCombination : ["a", "b", "c"]
            }
            // the current combinations is now previous combinations for the letters that match the next digit 
            while (!currCombinations.isEmpty()) {// O(4^n))
                String temp = currCombinations.poll();
                if (temp.length() == digits.length()) {
                    combinations.add(temp);
                } else {
                    prevCombinations.offer(temp);
                }
            }
        }

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

            for (String s : findCombinationsBFS(input)) {
                System.out.println(s);
                counter++;
            }
            System.out.printf("There are %d results for this test input.%n", counter);
        }

    }
}