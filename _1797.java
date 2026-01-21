import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 1797. Design Authentication Manager
 */
public class _1797 {
    class Solution1_HashMap {
        class AuthenticationManager {

            int ttl;
            Map<String, Integer> tokenToExpiryTime;

            public AuthenticationManager(int timeToLive) {
                this.ttl = timeToLive;
                tokenToExpiryTime = new HashMap<>();
            }
            
            public void generate(String tokenId, int currentTime) {
                tokenToExpiryTime.put(tokenId, currentTime + ttl);
            }
            
            public void renew(String tokenId, int currentTime) {
                if (tokenToExpiryTime.containsKey(tokenId) && tokenToExpiryTime.get(tokenId) > currentTime) {
                    tokenToExpiryTime.put(tokenId, currentTime + ttl);
                }
            }
            
            public int countUnexpiredTokens(int currentTime) {
                tokenToExpiryTime.entrySet().removeIf(e -> e.getValue() <= currentTime);
                return tokenToExpiryTime.size();
            }
        }
    }

    class Solution2_LinkedHashMap {
        class AuthenticationManager {
            int ttl;
            Map<String, Integer> tokenToExpiryTime;

            public AuthenticationManager(int timeToLive) {
                this.ttl = timeToLive;
                tokenToExpiryTime = new LinkedHashMap<>();
            }
            
            public void generate(String tokenId, int currentTime) {
                tokenToExpiryTime.put(tokenId, currentTime + ttl);
            }
            
            public void renew(String tokenId, int currentTime) {
                if (tokenToExpiryTime.containsKey(tokenId) && tokenToExpiryTime.get(tokenId) > currentTime) {
                    tokenToExpiryTime.remove(tokenId);
                    tokenToExpiryTime.put(tokenId, currentTime + ttl);
                }
            }
            
            public int countUnexpiredTokens(int currentTime) {
                Iterator<Map.Entry<String, Integer>> iterator = tokenToExpiryTime.entrySet().iterator();
                while (iterator.hasNext() && iterator.next().getValue() <= currentTime) {
                    iterator.remove();
                }
                return tokenToExpiryTime.size();
            }
        }
    }
}
