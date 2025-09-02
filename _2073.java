/**
 * 2073. Time Needed to Buy Tickets
 */
public class _2073 {
    // we can also use queue to simulate the process, but it would worse time and space
    class Solution1_One_Pass_Traversal {
        public int timeRequiredToBuy(int[] tickets, int k) {
            // ppl < k takes at most tickets[k] time & ppl > k takes at most tickets[k] - 1 time
            int timeToken = tickets[k];
            for (int ppl = 0; ppl < k; ppl++) {
                timeToken += (tickets[ppl] <= tickets[k]) ? tickets[ppl] : tickets[k];
            }
            for (int ppl = k + 1; ppl < tickets.length; ppl++) {
                timeToken += (tickets[ppl] < tickets[k]) ? tickets[ppl] : tickets[k] - 1;
            }
            return timeToken;
        }
    }
}
