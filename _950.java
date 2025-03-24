import java.util.Arrays;
import java.util.LinkedList;

/**
 * 950. Reveal Cards In Increasing Order
 */
public class _950 {
    class Solution {
        /**
         * Key is to revert the steps.
         * Convert the steps into pseudocode:
         * initialize an empty list revealed_cards
         * while deck is not empty:
         *     top_card = deck.remove_top()
         *     revealed_cards.add_last(top_card)
         *
         *     if deck is not empty:
         *         temp_card = deck.remove_top()
         *         deck.add_last(temp_card)
         * @param deck here the sorted deck is actually the revealed cards
         * @return the original deck
         */
        public int[] deckRevealedIncreasing(int[] deck) {
            LinkedList<Integer> res = new LinkedList<>();
            Arrays.sort(deck);
            for (int i = deck.length - 1; i >= 0; i--) {
                if (!res.isEmpty()) {
                    res.addFirst(res.removeLast());
                }
                res.addFirst(deck[i]);
            }
            return res.stream().mapToInt(Integer::intValue).toArray();
        }
    }
}
