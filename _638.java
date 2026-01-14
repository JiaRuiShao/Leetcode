import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 638. Shopping Offers
 * 
 * Clarification:
 * - Can special offers exceed my need? No you cannot buy more than you need
 * - Are special offers always a good deal? No guarantee
 * - Can offer used multiple times? Yes unlimited uses
 * 
 * Followup:
 * - Each offer can only be used once (0/1 Knapsack)
 */
public class _638 {
    // Time: average O(n*2^m) where n is num of items in prices; m is num of special offers
    // Space: average O(m)
    class Solution1_Backtrack_By_Ball_Combination_ElemUsedUnlimited_NoDedup {
        // combination -- elem used more than once -- dedup not needed
        private int bestCost;

        public int shoppingOffers(List<Integer> prices, List<List<Integer>> offers, List<Integer> needs) {
            bestCost = Integer.MAX_VALUE;
            dfs(prices, offers, needs, 0, 0);
            return bestCost;
        }

        private void dfs(List<Integer> prices, List<List<Integer>> offers, List<Integer> needs,
                        int offerIdx, int costSoFar) {
            if (costSoFar >= bestCost) return; // prune

            if (offerIdx == offers.size()) {
                bestCost = Math.min(bestCost, costSoFar + costOfRemainingItems(prices, needs));
                return;
            }

            // Option 1: skip this offer
            dfs(prices, offers, needs, offerIdx + 1, costSoFar);

            // Option 2: take this offer (possibly multiple times)
            List<Integer> offer = offers.get(offerIdx);
            if (canApplyOffer(offer, needs)) {
                applyOffer(offer, needs);
                dfs(prices, offers, needs, offerIdx, costSoFar + offerPrice(offer, prices.size()));
                revertOffer(offer, needs);
            }
        }

        private boolean canApplyOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                if (needs.get(i) < offer.get(i)) return false;
            }
            return true;
        }

        private void applyOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                needs.set(i, needs.get(i) - offer.get(i));
            }
        }

        private void revertOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                needs.set(i, needs.get(i) + offer.get(i));
            }
        }

        private int offerPrice(List<Integer> offer, int nItems) {
            return offer.get(nItems);
        }

        private int costOfRemainingItems(List<Integer> prices, List<Integer> needs) {
            int total = 0;
            for (int i = 0; i < needs.size(); i++) {
                total += needs.get(i) * prices.get(i);
            }
            return total;
        }
    }

    // when we add cache for backtrack by ball, the state is [needs, offerIndex] and not just needs
    // this offer-based solution is PREFERRED when each offer can be used at most once (0/1 Knapsack)
    // i.e. prices = [2,2], special = [[1,1,1],[1,1,2],[1,1,3],[1,1,4],[1,1,5],[1,1,6],[1,1,7],[1,1,8],[1,1,9],[1,1,10],[1,1,11],[1,1,12],[1,1,13],[1,1,14],[1,1,15]], needs = [10,10]; expected output = 10
    class Wrong_Solution_Backtrack_By_Ball_With_Memo {
        public int shoppingOffers(List<Integer> prices, List<List<Integer>> offers, List<Integer> needs) {
            Map<String, Integer> memo = new HashMap<>();
            return dfs(prices, offers, needs, 0, memo);
        }

        private int dfs(List<Integer> prices, List<List<Integer>> offers, List<Integer> needs,
                        int offerIdx, Map<String, Integer> memo) {

            if (offerIdx == offers.size()) { // used all possibly applied offers
                return costOfRemainingItems(prices, needs);
            }

            String key = needs.toString() + "-" + offerIdx;
            if (memo.containsKey(key)) return memo.get(key);

            // Option 1: skip this offer
            int cost1 = dfs(prices, offers, needs, offerIdx + 1, memo);

            // Option 2: take this offer (possibly multiple times)
            List<Integer> offer = offers.get(offerIdx);
            if (canApplyOffer(offer, needs)) {
                applyOffer(offer, needs);
                int cost2 = offer.get(prices.size()) + dfs(prices, offers, needs, offerIdx, memo);
                if (cost2 < cost1) {
                    cost1 = cost2;
                }
                revertOffer(offer, needs);
            }
            memo.put(key, cost1);
            return cost1;
        }

        private boolean canApplyOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                if (needs.get(i) < offer.get(i)) return false;
            }
            return true;
        }

        private void applyOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                needs.set(i, needs.get(i) - offer.get(i));
            }
        }

        private void revertOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                needs.set(i, needs.get(i) + offer.get(i));
            }
        }

        private int costOfRemainingItems(List<Integer> prices, List<Integer> needs) {
            int total = 0;
            for (int i = 0; i < needs.size(); i++) {
                total += needs.get(i) * prices.get(i);
            }
            return total;
        }
    }

    // Time: O(s*m*n) where n is item num; m is special offer size; S is num of unique state (max(needs[i])^n)
    // Space: O(s) = O(k^n+kn) where k is max(needs[i])
    class Solution2_Backtrack_By_Box_Combination_ElemUsedUnlimited_NoDedup_With_Memo {
        public int shoppingOffers(List<Integer> prices, List<List<Integer>> offers, List<Integer> needs) {
            Map<String, Integer> memo = new HashMap<>();
            return backtrack(prices, offers, needs, memo);
        }

        private int backtrack(List<Integer> prices, List<List<Integer>> offers, List<Integer> needs, Map<String, Integer> memo) {
            String needAsStr = needs.toString();
            if (memo.containsKey(needAsStr)) return memo.get(needAsStr);

            int minCost = getRemainingItemCost(prices, needs);
            for (List<Integer> offer : offers) {
                if (!canApplyOffer(offer, needs)) {
                    continue;
                }
                applyOffer(offer, needs);
                int cost = offer.get(prices.size()) + backtrack(prices, offers, needs, memo);
                minCost = Math.min(minCost, cost);
                revertOffer(offer, needs);
            }
            memo.put(needAsStr, minCost);
            return minCost;
        }

        private boolean canApplyOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                if (needs.get(i) < offer.get(i)) return false;
            }
            return true;
        }

        private void applyOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                needs.set(i, needs.get(i) - offer.get(i));
            }
        }

        private void revertOffer(List<Integer> offer, List<Integer> needs) {
            for (int i = 0; i < needs.size(); i++) {
                needs.set(i, needs.get(i) + offer.get(i));
            }
        }

        private int getRemainingItemCost(List<Integer> prices, List<Integer> needs) {
            int total = 0;
            for (int i = 0; i < needs.size(); i++) {
                total += needs.get(i) * prices.get(i);
            }
            return total;
        }
    }
}
