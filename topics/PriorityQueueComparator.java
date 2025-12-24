package topics;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PriorityQueueComparator {
    // Min-heap (natural order) — default if E is Comparable
    PriorityQueue<Integer> min = new PriorityQueue<>();

    // Max-heap (reverse of natural order)
    PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());

    // Case-insensitive String ordering
    PriorityQueue<String> words = new PriorityQueue<>(String.CASE_INSENSITIVE_ORDER);

    // Comparator helpers (preferred style)
    record Task(String id, int priority, long timestamp) {}
    PriorityQueue<Task> tasks = new PriorityQueue<>(Comparator
            .comparingInt(Task::priority)          // lowest priority first
            .thenComparingLong(Task::timestamp));  // tie-breaker
    
    // Max-heap by a field
    PriorityQueue<Task> maxByPriority = new PriorityQueue<>(Comparator.comparingInt(Task::priority).reversed());

    record User(String name, int level, long createdAt) {}

    /**Chained Comparator examples**/
    // 1 . Multi-field ordering with tie-breakers
    Comparator<User> byLevelThenTimeThenName = Comparator.comparingInt(User::level)                 // level asc
                .thenComparingLong(User::createdAt)        // earlier first
                .thenComparing(User::name);                // lexicographic

    List<User> sorted = List.of(new User("name", 1, System.currentTimeMillis())).stream()
                            .sorted(byLevelThenTimeThenName)
                            .toList();

    // 2. Max-heap ordering (reverse primary key)
    PriorityQueue<User> pq = new PriorityQueue<>(Comparator
                    .comparingInt(User::level)      // primary
                    .reversed()                     // max-heap by level
                    .thenComparing(User::name));    // stable-ish tie-breaker

    // 3. Handling null values safely
    record Book(String title, Integer rating) {}

    Comparator<Book> byRatingNullsLastThenTitle = Comparator.comparing(Book::rating,
                            Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(Book::title, Comparator.nullsFirst(String::compareTo));


    // 4. Nested properties (e.g., Order -> Customer.name)
    record Customer(String name) {}
    record Order(int id, Customer customer, double price) {}

    Comparator<Order> byCustomerNameThenPrice = Comparator.comparing((Order o) -> o.customer().name())
                    .thenComparingDouble(Order::price);

    // 5. Map.Entry by value, then key
    Comparator<Map.Entry<String,Integer>> byValueThenKey = Comparator.comparingInt(Map.Entry<String,Integer>::getValue)
                    .thenComparing(Map.Entry::getKey);

    // 6. Custom enum order (not declaration order)
    enum Status { NEW, IN_PROGRESS, DONE, BLOCKED }

    static final Map<Status,Integer> rank = Map.of(
            Status.BLOCKED, 0, Status.NEW, 1, Status.IN_PROGRESS, 2, Status.DONE, 3);

    Comparator<Status> byCustomRank = Comparator.comparingInt(rank::get);
}
